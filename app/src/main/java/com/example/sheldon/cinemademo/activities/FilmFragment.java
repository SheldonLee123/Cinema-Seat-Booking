package com.example.sheldon.cinemademo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.activities.FragmentComingSoon;
import com.example.sheldon.cinemademo.activities.FragmentNowShowing;
import com.example.sheldon.cinemademo.adapters.ViewPagerAdapter;

public class FilmFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    View mView;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }

        View view =  inflater.inflate(R.layout.fragment_film, container, false);
        tabLayout = view.findViewById(R.id.tablayout_id);
        viewPager = view.findViewById(R.id.viewpager_id);
        viewPager.setOffscreenPageLimit(2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        // Adding Fragments
        adapter.AddFragment(new FragmentNowShowing(), "Now Showing");
        adapter.AddFragment(new FragmentComingSoon(), "Coming Soon");
        //Adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(getActivity(), query, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), SearchResult.class);
                intent.putExtra("film_index", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mView = view;

        return view;
    }
}
