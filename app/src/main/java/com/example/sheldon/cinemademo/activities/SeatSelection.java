package com.example.sheldon.cinemademo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheldon.cinemademo.GlobalVariable;
import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.model.Seat;
import com.example.sheldon.cinemademo.model.Timetable;
import com.qfdqc.views.seattable.SeatTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatSelection extends AppCompatActivity {

    private static final String TAG = "SeatSelectionActivity";

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();

    private final String JSON_URL = "http://"+ serverIp +":8080/Android_Project/GetSeatInfo";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private String roomId;
    private String timetableId;
    private String film_name;
    private int row = 0;
    private int column = 0;
    public SeatTable seatTableView;
    private List<Seat> lstSeat = new ArrayList<>();
    private Timetable timetable = new Timetable();
    private ArrayList<String> selectedseat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        getIncomingIntent();
        jsonrequest();

        Button btn_submit = findViewById(R.id.btn_choose_seat);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedseat = seatTableView.getSelectedSeat();
//                Toast.makeText(SeatSelection.this, selectedseat.get(0), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SeatSelection.this, OrderDetail.class);
                intent.putExtra("film_name", film_name);
                intent.putStringArrayListExtra("selectedseat", selectedseat);
                Bundle bundle = new Bundle();
                bundle.putSerializable("timetable", timetable);
                intent.putExtras(bundle);
//                Toast.makeText(SeatSelection.this, timetable.getSt_time(), Toast.LENGTH_LONG).show();

                startActivity(intent);
            }
        });

    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if (getIntent().hasExtra("room_id") && getIntent().hasExtra("id") && getIntent().hasExtra("film_name") && getIntent().hasExtra("timetable")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            roomId = getIntent().getStringExtra("room_id");
            timetableId = getIntent().getStringExtra("id");
            film_name = getIntent().getStringExtra("film_name");
            timetable = (Timetable) getIntent().getSerializableExtra("timetable");
//            Toast.makeText(this, timetable.getSt_time(), Toast.LENGTH_LONG).show();
        }
    }

    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL + "?timetable_id=" + timetableId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                JSONArray allbookedseat = null;
                JSONObject allseatObject = null;

                try{
                    jsonObject = response.getJSONObject(0);
                    allbookedseat = jsonObject.getJSONArray("AllBookedSeats");
                    for(int i = 0; i < allbookedseat.length(); i++) {
                        allseatObject = allbookedseat.getJSONObject(i);
                        Seat seat = new Seat();
                        seat.setRow(Integer.parseInt(allseatObject.getString("seat_row")));
                        seat.setColumn(Integer.parseInt(allseatObject.getString("seat_column")));

                        lstSeat.add(seat);
                    }

                    row = Integer.parseInt(jsonObject.getString("row"));
                    column = Integer.parseInt(jsonObject.getString("column"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                drawSeat();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void drawSeat() {
        seatTableView = findViewById(R.id.seatView);
        seatTableView.setScreenName("Hall " + roomId + " Screen");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
//                if(column==2) {
//                    return false;
//                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                for(int i = 0; i < lstSeat.size(); i++) {
                    Seat temp = lstSeat.get(i);
                    if(row == temp.getRow()-1 && column == temp.getColumn()-1){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
//        Toast.makeText(SeatSelection.this, String.valueOf(row)+ String.valueOf(column), Toast.LENGTH_LONG).show();

        seatTableView.setData(row, column);
    }

}
