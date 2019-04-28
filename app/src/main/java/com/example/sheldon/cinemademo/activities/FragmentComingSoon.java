package com.example.sheldon.cinemademo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheldon.cinemademo.GlobalVariable;
import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.adapters.RecyclerViewAdapter;
import com.example.sheldon.cinemademo.model.Comment;
import com.example.sheldon.cinemademo.model.Film;
import com.example.sheldon.cinemademo.model.Timetable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentComingSoon extends Fragment {

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();

    private final String JSON_URL = "http://"+ serverIp +":8080/Android_Project/GetFilms";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Film> lstFilm;
    private RecyclerView recyclerView;

    View view;
    View mView;

    public FragmentComingSoon() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }

        view = inflater.inflate(R.layout.now_showing_fragment, container, false);

        lstFilm = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleviewid);
        jsonrequest();

        mView = view;

        return view;
    }

    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                JSONArray allfilm = null;
                JSONObject allfilmObject = null;
                JSONArray allcomments = null;
                JSONObject commentObject = null;
                JSONArray alltimetable = null;
                JSONObject timetableObject = null;

                try{
                    jsonObject = response.getJSONObject(0);
                    allfilm = jsonObject.getJSONArray("UnReleased");
                    for(int i = 0; i < allfilm.length(); i++) {
                        allfilmObject = allfilm.getJSONObject(i);
                        Film film = new Film();
                        List<Comment> comments = new ArrayList<>();
                        List<Timetable> timetables = new ArrayList<>();
                        film.setName(allfilmObject.getString("name"));
                        film.setDescription(allfilmObject.getString("introduction"));
                        film.setCategorie(allfilmObject.getString("type"));
                        film.setDirector(allfilmObject.getString("director"));
                        film.setImage_url(allfilmObject.getString("image"));
                        film.setRating(allfilmObject.getString("rate"));
                        film.setActor(allfilmObject.getString("actor"));
                        film.setIntroduction(allfilmObject.getString("introduction"));
                        film.setFilm_id(allfilmObject.getString("id"));

                        allcomments = allfilmObject.getJSONArray("comments");

                        for(int j = 0; j < allcomments.length(); j++) {
                            commentObject = allcomments.getJSONObject(j);
                            Comment comment = new Comment();
                            comment.setUser(commentObject.getString("username"));
                            comment.setContent(commentObject.getString("content"));
                            comment.setTime(commentObject.getString("str_date"));

                            comments.add(comment);

//                            if(i==0&&j==0) {
//                                Toast.makeText(getActivity(), commentObject.getString("str_date"), Toast.LENGTH_LONG).show();
//                            }
                        }

                        alltimetable = allfilmObject.getJSONArray("timetables");

                        for(int j = 0; j < alltimetable.length(); j++) {
                            timetableObject = alltimetable.getJSONObject(j);
                            Timetable timetable = new Timetable();
                            timetable.setSt_time(timetableObject.getString("st_time"));
                            timetable.setEnd_time(timetableObject.getString("end_time"));
                            timetable.setPrice_origin(timetableObject.getDouble("price_origin"));
                            timetable.setPrice_actual(timetableObject.getDouble("price_actual"));
                            timetable.setRoom_id(timetableObject.getInt("room_id"));
                            timetable.setScreen(timetableObject.getString("screen"));
                            timetable.setId(timetableObject.getInt("id"));

                            timetables.add(timetable);
                        }

                        film.setComments(comments);
                        film.setTimetable(timetables);

                        lstFilm.add(film);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(lstFilm);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    private void setuprecyclerview(List<Film> lstFilm) {

        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(getActivity(), lstFilm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(myadapter);
    }
}
