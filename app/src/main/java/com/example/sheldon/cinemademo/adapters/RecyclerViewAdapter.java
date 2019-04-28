package com.example.sheldon.cinemademo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sheldon.cinemademo.activities.FilmDetail;
import com.example.sheldon.cinemademo.model.Film;
import com.example.sheldon.cinemademo.R;

import java.io.Serializable;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    public static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private List<Film> mData;
    RequestOptions option;

    public RecyclerViewAdapter(Context mContext, List<Film> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.film_row_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called");

        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_rating.setText(mData.get(position).getRating());
        holder.tv_director.setText("by " + mData.get(position).getDirector());
        holder.tv_category.setText(mData.get(position).getCategorie());

        //Load Image from the internet and set it into ImageView using Glide
        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(option).into(holder.img_filmimage);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Onclick: clicked on:" + mData.get(position).getName());

//                Toast.makeText(mContext, mData.get(position).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, FilmDetail.class);
                intent.putExtra("image_url", mData.get(position).getImage_url());
                intent.putExtra("film_name", mData.get(position).getName());
                intent.putExtra("film_director", mData.get(position).getDirector());
                intent.putExtra("film_type", mData.get(position).getCategorie());
                intent.putExtra("film_rate", mData.get(position).getRating());
                intent.putExtra("film_actor", mData.get(position).getActor());
                intent.putExtra("film_introduction", mData.get(position).getIntroduction());
                intent.putExtra("film_comments", (Serializable) mData.get(position).getComments());          //transfer implements Serializable
                intent.putExtra("film_timetable", (Serializable) mData.get(position).getTimetable());
                intent.putExtra("film_id", mData.get(position).getFilm_id());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_rating;
        TextView tv_director;
        TextView tv_category;
        ImageView img_filmimage;
        LinearLayout parentLayout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.anime_name);
            tv_category = itemView.findViewById(R.id.categorie);
            tv_rating = itemView.findViewById(R.id.rating);
            tv_director = itemView.findViewById(R.id.director);
            img_filmimage = itemView.findViewById(R.id.iv_filmimage);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
