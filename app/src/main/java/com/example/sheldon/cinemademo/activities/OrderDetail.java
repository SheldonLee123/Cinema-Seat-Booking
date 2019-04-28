package com.example.sheldon.cinemademo.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheldon.cinemademo.AmountView;
import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.model.Timetable;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail extends AppCompatActivity {

    private AmountView mAmountView;
    private String film_name;
    private ArrayList<String> selectedseat = new ArrayList<>();
    private Timetable timetable = new Timetable();
    private String seat = "";
    private TextView tv_phone;
    private String phone;
    private SharedPreferences sp;
    private Double price;
    private Double total_price = 0.0;
    private int ticketNumber;
    private int timetable_id;
    private int row;
    private int column;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        film_name = getIntent().getStringExtra("film_name");
        selectedseat = getIntent().getStringArrayListExtra("selectedseat");
        timetable = (Timetable) getIntent().getSerializableExtra("timetable");
        timetable_id = timetable.getId();

        tv_phone= findViewById(R.id.tv_phone);
        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        phone = sp.getString("phone",null);
        tv_phone.setText(phone);
        price = timetable.getPrice_actual();


//        Toast.makeText(this, timetable.getSt_time(), Toast.LENGTH_LONG).show();

        for(int i = 0; i < selectedseat.size(); i++){
            String temp = selectedseat.get(i);
            row = Integer.parseInt(temp.split(",")[0]) + 1;
            column = Integer.parseInt(temp.split(",")[1]) + 1;
            if(row==1){
                seat = seat + " row" + String.valueOf(row) + "col" + String.valueOf(column) + "(vip)";          //vip seat
                total_price += price + 30;
            }else{
                seat = seat + " row" + String.valueOf(row) + "col" + String.valueOf(column);
                total_price += price;
            }

        }

        TextView textView2 = findViewById(R.id.film_name_ticket);
        textView2.setText(film_name + " " + selectedseat.size() + "tickets");

        TextView textView3 = findViewById(R.id.film_time_screen);
        textView3.setText(timetable.getDate() + " " + timetable.getSt_time() + "~" + timetable.getEnd_time() + " (English "+ timetable.getScreen() +")");

        TextView textView4 = findViewById(R.id.film_room_seat);
        textView4.setText("Room " + timetable.getRoom_id() + seat);

        TextView textView5 = findViewById(R.id.film_price_total);
        ticketNumber = selectedseat.size();
        textView5.setText("$" + String.valueOf(total_price));


        TextView textView1 = findViewById(R.id.snack_icon);
        Drawable drawable1 = getResources().getDrawable(R.drawable.popcornlogo);
        drawable1.setBounds(0,0,70,70);
        textView1.setCompoundDrawables(null, null, drawable1, null);

        mAmountView = findViewById(R.id.amount_view1);
        mAmountView.setGoods_storage(50);
        mAmountView.setAmount(1);
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount, int position) {
                Toast.makeText(getApplicationContext(), "Amount=>  " + amount, Toast.LENGTH_SHORT).show();
            }
        });

        mAmountView = findViewById(R.id.amount_view2);
        mAmountView.setGoods_storage(50);
        mAmountView.setAmount(1);
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount, int position) {
                Toast.makeText(getApplicationContext(), "Amount=>  " + amount, Toast.LENGTH_SHORT).show();
            }
        });

        Button button = findViewById(R.id.btn_imm_pay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetail.this, PayOrder.class);
                intent.putExtra("price", price);
                intent.putExtra("ticketNumber", ticketNumber);
                intent.putStringArrayListExtra("selectedseat", selectedseat);
                intent.putExtra("timetable_id", timetable_id);
                startActivity(intent);
            }
        });
    }
}
