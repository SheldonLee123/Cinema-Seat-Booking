package com.example.sheldon.cinemademo.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.activities.SeatSelection;
import com.example.sheldon.cinemademo.model.Comment;
import com.example.sheldon.cinemademo.model.Timetable;

import java.io.Serializable;
import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.MyViewHolder> {

    public static final String TAG = "TimetableAdapter";
    private Context mContext;
    private List<Timetable> mData;
    private String film_name;

    public TimetableAdapter(Context mContext, List<Timetable> mData, String film_name) {
        this.mContext = mContext;
        this.mData = mData;
        this.film_name = film_name;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.timetable_row_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called");

        holder.tv_st_time.setText(mData.get(position).getSt_time());
        holder.tv_end_time.setText(mData.get(position).getEnd_time() + "finish");
        holder.tv_screen.setText("English " + mData.get(position).getScreen());
        holder.tv_room.setText("Room " + String.valueOf(mData.get(position).getRoom_id()));
        holder.tv_origin_price.setText("Special $" + String.valueOf(mData.get(position).getPrice_origin()));
        holder.tv_actual_price.setText("Member $" + String.valueOf(mData.get(position).getPrice_actual()));

        holder.btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Onclick: clicked on:" + mData.get(position).getSt_time());

//                Toast.makeText(mContext, mData.get(position).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, SeatSelection.class);
                intent.putExtra("room_id", String.valueOf(mData.get(position).getRoom_id()));
                intent.putExtra("id", String.valueOf(mData.get(position).getId()));
                intent.putExtra("film_name", film_name);
                Bundle bundle = new Bundle();
                bundle.putSerializable("timetable", mData.get(position));
                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_st_time;
        TextView tv_end_time;
        TextView tv_screen;
        TextView tv_room;
        TextView tv_origin_price;
        TextView tv_actual_price;
        Button btn_buy_ticket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_st_time = itemView.findViewById(R.id.tv_st_time);
            tv_end_time = itemView.findViewById(R.id.tv_end_time);
            tv_screen = itemView.findViewById(R.id.tv_screen);
            tv_room = itemView.findViewById(R.id.tv_room);
            tv_origin_price = itemView.findViewById(R.id.tv_origin_price);
            tv_actual_price = itemView.findViewById(R.id.tv_actual_price);
            btn_buy_ticket = itemView.findViewById(R.id.btn_buyticket);
        }
    }
}
