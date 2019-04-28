package com.example.sheldon.cinemademo.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheldon.cinemademo.BackgroundWorker;
import com.example.sheldon.cinemademo.GlobalVariable;
import com.example.sheldon.cinemademo.OnSwipeTouchListener;
import com.example.sheldon.cinemademo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();
    String register_url = "http://"+ serverIp +":8080/Android_Project/Register";

    AlertDialog alertDialog;
    private ImageView imageView;
    private TextView textView;
    private TextView mail = null;
    private EditText et_username=null;
    private EditText et_password=null;
    private EditText et_phone = null;
    private String email;
    private String username;
    private String password;
    private String phone;
    int count = 0;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private String registerstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        mail = findViewById(R.id.email);
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        et_phone = findViewById(R.id.et_phone);
        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });
    }

    public void register(View view) {
        email = mail.getText().toString();
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        phone = et_phone.getText().toString();

        request = new JsonArrayRequest(register_url + "?email=" + email + "&password=" + password + "&username=" + username + "&phone=" + phone, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                try{
                    jsonObject = response.getJSONObject(0);
                    registerstatus = jsonObject.getString("RegisterStatus");
                    alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("RegisterStatus");
//                    Toast.makeText(LoginActivity.this, loginstatus, Toast.LENGTH_LONG).show();
                    if(registerstatus.equals("Success")) {

                        alertDialog.setMessage(registerstatus);
                        alertDialog.show();

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }else{
                        alertDialog.setMessage(registerstatus);
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

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
