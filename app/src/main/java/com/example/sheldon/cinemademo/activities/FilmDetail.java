package com.example.sheldon.cinemademo.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.adapters.ViewPagerAdapter;
import com.example.sheldon.cinemademo.model.Comment;
import com.example.sheldon.cinemademo.model.Timetable;

import java.io.Serializable;
import java.util.List;

public class FilmDetail extends AppCompatActivity {

    private static final String TAG = "FilmDetailActivity";

    private TabLayout tabLayout;
    private ViewPager viewPager;

    FragmentIntroduction fragmentIntroduction = new FragmentIntroduction();
    FragmentComment fragmentComment = new FragmentComment();
    FragmentTimetable fragmentTimetable = new FragmentTimetable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        getIncomingIntent();
        init();

    }

    private void init() {
        tabLayout = findViewById(R.id.tablayout_filmdetail);
        viewPager = findViewById(R.id.vp_filmdetail);
        viewPager.setOffscreenPageLimit(3);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Adding Fragments
        adapter.AddFragment(fragmentIntroduction, "Introduction");
        adapter.AddFragment(fragmentComment, "Comment");
        adapter.AddFragment(fragmentTimetable, "Timetable");
        //Adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("film_name") && getIntent().hasExtra("film_director") && getIntent().hasExtra("film_type")  && getIntent().hasExtra("film_actor") && getIntent().hasExtra("film_introduction") && getIntent().hasExtra("film_comments") && getIntent().hasExtra("film_timetable") && getIntent().hasExtra("film_id")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String filmName = getIntent().getStringExtra("film_name");
            String filmDirector = getIntent().getStringExtra("film_director");
            String filmType = getIntent().getStringExtra("film_type");
            String filmActor = getIntent().getStringExtra("film_actor");
            String filmIntro = getIntent().getStringExtra("film_introduction");
            List<Comment> comments = (List<Comment>) getIntent().getSerializableExtra("film_comments");
            List<Timetable> timetables = (List<Timetable>) getIntent().getSerializableExtra("film_timetable");
            String film_id = getIntent().getStringExtra("film_id");

//            Toast.makeText(this, comments.get(1).getContent(), Toast.LENGTH_LONG).show();

            //transmit parameter from activity to fragment
            Bundle bundle = new Bundle();
            bundle.putString("film_introduction",filmIntro);
            fragmentIntroduction.setArguments(bundle);

            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("film_comments", (Serializable) comments);
            bundle1.putString("film_id", film_id);
            fragmentComment.setArguments(bundle1);

            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("film_timetable", (Serializable) timetables);
            bundle2.putString("film_name", filmName);
            fragmentTimetable.setArguments(bundle2);

            setFilm(imageUrl, filmName, filmDirector, filmType, filmActor);
        }
    }

    public void setFilm(String imageUrl, String filmName, String filmDirector, String filmType, String flimActor) {
        Log.d(TAG, "setFilm: setting to image and name to widgets");

//        Toast.makeText(this, filmName, Toast.LENGTH_SHORT).show();

        TextView name = findViewById(R.id.tv_filmname);
        TextView director = findViewById(R.id.tv_filmdirector);
        TextView type = findViewById(R.id.tv_filmtype);
        TextView actor = findViewById(R.id.tv_actor);
        name.setText(filmName);
        director.setText("by " + filmDirector);
        type.setText(filmType);
        actor.setText(flimActor);

        ImageView image = findViewById(R.id.image_filmdetail);
        ImageView imagePost = findViewById(R.id.image_filmpost);

        RequestOptions option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        Glide.with(this).load(imageUrl).apply(option).into(image);
        Glide.with(this).load(imageUrl).apply(option).into(imagePost);
    }
}
