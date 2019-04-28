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
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();
    String login_url = "http://"+ serverIp +":8080/Android_Project/Login";

    AlertDialog alertDialog;
    private String loginstatus;
    private ImageView imageView;
    private TextView textView;
    private EditText et_email=null;
    private EditText et_password=null;
    private Button login;
    int count = 0;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private SharedPreferences sp;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        login = findViewById(R.id.login);
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

    public void login(View view) {
        email = et_email.getText().toString();
        password = et_password.getText().toString();
//        String type = "login";
//        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
//        backgroundWorker.execute(type, e_mail, pass_word);

//        Toast.makeText(this, email+password, Toast.LENGTH_LONG).show();

        request = new JsonArrayRequest(login_url + "?email=" + email + "&password=" + password, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                JSONObject userinfo = null;

                try{
                    jsonObject = response.getJSONObject(0);
                    loginstatus = jsonObject.getString("LoginStatus");
                    alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("LoginStatus");
//                    Toast.makeText(LoginActivity.this, loginstatus, Toast.LENGTH_LONG).show();
                    if(loginstatus.equals("Success")) {

                        userinfo = jsonObject.getJSONObject("UserInfo");

//                        Toast.makeText(LoginActivity.this, userinfo.toString(), Toast.LENGTH_LONG).show();

                        sp = getSharedPreferences("User",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putString("balance", userinfo.getString("balance"));
                        editor.putString("phone", userinfo.getString("phone"));
                        editor.putString("username", userinfo.getString("username"));
                        editor.putString("user_id", userinfo.getString("id"));
                        editor.commit();

                        alertDialog.setMessage(loginstatus);
                        alertDialog.show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("jump_id", 3);
                        startActivity(intent);

                    }else{
                        alertDialog.setMessage(loginstatus);
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

    public void switch_register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}
