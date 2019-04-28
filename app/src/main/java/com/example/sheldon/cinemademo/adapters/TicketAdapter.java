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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.activities.UserTicketDetail;
import com.example.sheldon.cinemademo.model.Ticket;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    public static final String TAG = "TicketAdapter";

    private Context mContext;
    private List<Ticket> mData;

    public TicketAdapter(Context mContext, List<Ticket> mData) {
        this.mContext = mContext;
        this.mData = mData;

//        Toast.makeText(mContext, String.valueOf(getItemCount()), Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.historyticket_row_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called");

        holder.tv_filmname.setText(mData.get(position).getFilm_name() + " 1Ticket");
        holder.tv_filmtime.setText(mData.get(position).getDate() + " " + mData.get(position).getStart_time());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserTicketDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Order", mData.get(position));
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

        TextView tv_filmname;
        TextView tv_filmtime;
        LinearLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_filmname = itemView.findViewById(R.id.tv_filmname);
            tv_filmtime = itemView.findViewById(R.id.tv_filmtime);
            parentLayout = itemView.findViewById(R.id.parent_layout_ticket);

        }
    }

}
