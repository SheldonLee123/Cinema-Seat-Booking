package com.example.sheldon.cinemademo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.adapters.CommentAdapter;
import com.example.sheldon.cinemademo.adapters.TimetableAdapter;
import com.example.sheldon.cinemademo.model.Comment;
import com.example.sheldon.cinemademo.model.Timetable;

import java.util.List;

public class FragmentTimetable extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        List<Timetable> lstTimetable = (List<Timetable>) getArguments().get("film_timetable");
        String film_name = (String) getArguments().get("film_name");

        recyclerView = view.findViewById(R.id.recycleview_timetable);
        TimetableAdapter myadapter = new TimetableAdapter(getActivity(), lstTimetable, film_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(myadapter);

        return view;
    }
}
