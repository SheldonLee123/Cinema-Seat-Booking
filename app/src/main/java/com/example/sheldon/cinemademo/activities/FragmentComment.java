package com.example.sheldon.cinemademo.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheldon.cinemademo.GlobalVariable;
import com.example.sheldon.cinemademo.R;
import com.example.sheldon.cinemademo.adapters.CommentAdapter;
import com.example.sheldon.cinemademo.adapters.RecyclerViewAdapter;
import com.example.sheldon.cinemademo.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FragmentComment extends Fragment {

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();
    String addcomment_url = "http://"+ serverIp +":8080/Android_Project/AddComment";

    private RecyclerView recyclerView;
    private Button btn_addcomment;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private SharedPreferences sp;
    private String user_id;
    private String film_id;
    private String content;
    private String commentsubmitstatus;
    AlertDialog alertDialog;
    private EditText et_comment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        sp = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        user_id = sp.getString("user_id", null);

        film_id = (String) getArguments().get("film_id");

        List<Comment> lstComment = (List<Comment>) getArguments().get("film_comments");
//        Comment comment = lstComment.get(0);
//        Toast.makeText(getActivity(), lstComment.get(0).getContent(), Toast.LENGTH_LONG).show();

        recyclerView = view.findViewById(R.id.recycleview_comment);
        CommentAdapter myadapter = new CommentAdapter(getActivity(), lstComment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(myadapter);

        et_comment = view.findViewById(R.id.et_comment);

        btn_addcomment = view.findViewById(R.id.btn_addcomment);
        btn_addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id == null){
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("AddCommentStatus");
                    alertDialog.setMessage("Please login First!");
                    alertDialog.show();
                }else{
                    content = et_comment.getText().toString();
                    AddComment();
                }

//                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void AddComment() {
        request = new JsonArrayRequest(addcomment_url + "?user_id=" + user_id + "&film_id=" + film_id + "&content=" + content, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("AddCommentStatus");

                try{
                    jsonObject = response.getJSONObject(0);
                    commentsubmitstatus = jsonObject.getString("AddCommentStatus");
//                    Toast.makeText(getActivity(), "123", Toast.LENGTH_LONG).show();
                    if(commentsubmitstatus.equals("Add Success!")) {

                        alertDialog.setMessage(commentsubmitstatus);
                        alertDialog.show();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("jump_id", 2);
                        startActivity(intent);

                    }else{

                        alertDialog.setMessage(commentsubmitstatus);
                        alertDialog.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }


}
