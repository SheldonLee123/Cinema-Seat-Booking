package com.example.sheldon.cinemademo.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheldon.cinemademo.GlobalVariable;
import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.adapters.TicketAdapter;
import com.example.sheldon.cinemademo.model.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookedTickets extends AppCompatActivity {

    public static final String TAG = "HistoryTicketsActivity";

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();

    private final String JSON_URL = "http://"+ serverIp +":8080/Android_Project/GetOrders";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Ticket> lstTicket = new ArrayList<>();
    private RecyclerView recyclerView;
    private SharedPreferences sp;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_tickets);

        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        user_id = sp.getString("user_id",null);

        recyclerView = findViewById(R.id.recycleviewid_bookedticket);
        jsonrequest();
    }

    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL + "?user_id=" + user_id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                JSONArray overdueorder = null;
                JSONObject overdueorderObject = null;

//                Toast.makeText(HistoryTickets.this, "123", Toast.LENGTH_LONG).show();

                try{
                    jsonObject = response.getJSONObject(0);
                    overdueorder = jsonObject.getJSONArray("AvailableOrders");
                    for(int i = 0; i < overdueorder.length(); i++) {
                        overdueorderObject = overdueorder.getJSONObject(i);
                        Ticket ticket = new Ticket();
                        ticket.setFilm_name(overdueorderObject.getString("film_name"));
                        ticket.setDate(overdueorderObject.getString("str_date"));
                        ticket.setStart_time(overdueorderObject.getString("start_time"));
                        ticket.setEnd_time(overdueorderObject.getString("end_time"));
                        ticket.setScreen(overdueorderObject.getString("screen"));
                        ticket.setRoom_id(overdueorderObject.getString("room_id"));
                        ticket.setSeat_row(overdueorderObject.getString("seat_row"));
                        ticket.setSeat_column(overdueorderObject.getString("seat_column"));
                        ticket.setPrice(overdueorderObject.getString("price"));
                        ticket.setPurchase_time(overdueorderObject.getString("purchase_time"));
                        ticket.setImage_url(overdueorderObject.getString("imgeUrl"));

//                        Toast.makeText(HistoryTickets.this, String.valueOf(overdueorder.length()), Toast.LENGTH_LONG).show();

                        lstTicket.add(ticket);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(lstTicket);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void setuprecyclerview(List<Ticket> lstTicket) {

        TicketAdapter myadapter = new TicketAdapter(this, lstTicket);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myadapter);
    }
}
