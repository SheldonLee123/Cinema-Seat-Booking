package com.example.sheldon.cinemademo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.adapters.MyAdapter;
import com.example.sheldon.cinemademo.model.Movie;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    HorizontalInfiniteCycleViewPager viewPager;
    List<Movie> movieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initData();
        viewPager = view.findViewById(R.id.view_pager);
        MyAdapter adapter = new MyAdapter(getActivity(), movieList);
        viewPager.setAdapter(adapter);

        return view;
    }

    private void initData() {
        movieList.add(new Movie("C A P A T A I N  M A R V E L",getString(R.string.long_text),R.drawable.movie1));
        movieList.add(new Movie("G R E E N  B O O K",getString(R.string.long_text),R.drawable.movie2));
        movieList.add(new Movie("M O R E  T H A N  B L U E",getString(R.string.long_text),R.drawable.movie3));
    }
}
