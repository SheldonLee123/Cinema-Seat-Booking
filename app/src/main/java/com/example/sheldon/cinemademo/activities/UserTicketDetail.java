package com.example.sheldon.cinemademo.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.model.Ticket;

public class UserTicketDetail extends AppCompatActivity {

    private Ticket ticket;
    private TextView tv_filmname;
    private TextView tv_filmtime_screen;
    private TextView tv_seat;
    private TextView tv_price;
    private TextView tv_purchase_time;
    private TextView tv_phonenumber;
    private TextView tv_roomid;
    private SharedPreferences sp;
    private String phone;
    private ImageView iv_filmimage;
    private String image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ticket_detail);

        ticket = (Ticket) getIntent().getSerializableExtra("Order");

        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        phone = sp.getString("phone",null);

        tv_filmname = findViewById(R.id.tv_filmname);
        tv_filmtime_screen = findViewById(R.id.tv_filmtime_screen);
        tv_seat = findViewById(R.id.tv_seat);
        tv_price = findViewById(R.id.tv_price);
        tv_purchase_time = findViewById(R.id.tv_purchase_time);
        tv_phonenumber = findViewById(R.id.tv_phonenumber);
        iv_filmimage = findViewById(R.id.iv_filmimage);
        tv_roomid = findViewById(R.id.tv_roomid);

        tv_filmname.setText(ticket.getFilm_name());
        tv_filmtime_screen.setText(ticket.getDate() + " " + ticket.getStart_time() + " ~ " + ticket.getEnd_time() + " " + "(English" + ticket.getScreen() + ")");
        tv_seat.setText("row" + ticket.getSeat_row() + "col" + ticket.getSeat_column());
        tv_price.setText("$" + ticket.getPrice());
        tv_phonenumber.setText("Phone Number:   " + phone);
        tv_purchase_time.setText("Purchase Time:   " + ticket.getPurchase_time());
        tv_roomid.setText("ATCO Dream Cinema Room " + ticket.getRoom_id());

        RequestOptions option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        image_url = ticket.getImage_url();
        Glide.with(this).load(image_url).apply(option).into(iv_filmimage);

    }
}
