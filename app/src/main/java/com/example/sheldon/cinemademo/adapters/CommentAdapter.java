package com.example.sheldon.cinemademo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    public static final String TAG = "CommentAdapter";
    private Context mContext;
    private List<Comment> mData;
//    RequestOptions option;

    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
//        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.comment_row_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called");

        holder.tv_user.setText(mData.get(position).getUser());
        holder.tv_content.setText(mData.get(position).getContent());
        holder.tv_time.setText(mData.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_user;
        TextView tv_content;
        TextView tv_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_user = itemView.findViewById(R.id.tv_commentuser);
            tv_content = itemView.findViewById(R.id.tv_comment);
            tv_time = itemView.findViewById(R.id.tv_commenttime);
        }
    }
}
