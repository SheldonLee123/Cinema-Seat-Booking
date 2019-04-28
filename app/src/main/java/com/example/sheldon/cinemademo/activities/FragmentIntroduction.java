package com.example.sheldon.cinemademo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheldon.cinemademo.R;

public class FragmentIntroduction extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduction, container, false);

        String film_intro = (String)getArguments().get("film_introduction");
        TextView textView = view.findViewById(R.id.introduction);
        textView.setText(film_intro);

        return view;
    }
}
