package com.example.sheldon.cinemademo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class SearchResult extends AppCompatActivity {

    public static final String TAG = "SearchResult";

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();

    private final String JSON_URL = "http://"+ serverIp +":8080/Android_Project/SearchForFilm";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Film> lstFilm = new ArrayList<>();
    private RecyclerView recyclerView;
    private String film_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = findViewById(R.id.recycleviewid);
        jsonrequest();
    }

    private void jsonrequest() {

        film_index = getIntent().getStringExtra("film_index");

        request = new JsonArrayRequest(JSON_URL + "?film_index=" + film_index, new Response.Listener<JSONArray>() {
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
                    allfilm = jsonObject.getJSONArray("FilmResultForSearch");
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
                            timetable.setDate(timetableObject.getString("str_date"));

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


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void setuprecyclerview(List<Film> lstFilm) {

        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstFilm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myadapter);
    }
}
