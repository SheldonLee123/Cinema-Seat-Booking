package com.example.sheldon.cinemademo.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheldon.cinemademo.GlobalVariable;
import com.example.sheldon.cinemademo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PasswordModify extends AppCompatActivity {

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();
    String pwd_modify_url = "http://"+ serverIp +":8080/Android_Project/PwdModify";

    private Button submit;
    private Button cancel;
    private EditText et_email;
    private EditText et_password;
    private String email;
    private String password;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private String pwdmodifystatus;
    AlertDialog alertDialog;
    private SharedPreferences sp;
    private String email_local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_modify);

        submit = findViewById(R.id.btn_submit);
        cancel = findViewById(R.id.btn_cancel);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = et_email.getText().toString();
                password = et_password.getText().toString();

                sp = getSharedPreferences("User", Context.MODE_PRIVATE);
                email_local = sp.getString("email",null);

                alertDialog = new AlertDialog.Builder(PasswordModify.this).create();
                alertDialog.setTitle("ModifyStatus");

                if(email.equals(email_local) == false) {
                    alertDialog.setMessage("Sorry, worng email!");
                    alertDialog.show();
                    return;
                }

                request = new JsonArrayRequest(pwd_modify_url + "?email=" + email + "&password=" + password, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObject = null;

                        try{
//                            Toast.makeText(PasswordModify.this, email+password, Toast.LENGTH_LONG).show();

                            jsonObject = response.getJSONObject(0);
                            pwdmodifystatus = jsonObject.getString("ModifyStatus");

                            if(pwdmodifystatus.equals("Modify Successed!")) {

                                alertDialog.setMessage(pwdmodifystatus);
                                alertDialog.show();

                                Intent intent = new Intent(PasswordModify.this, MainActivity.class);
                                intent.putExtra("jump_id", 3);
                                startActivity(intent);

                            }else{
                                alertDialog.setMessage(pwdmodifystatus);
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

                requestQueue = Volley.newRequestQueue(PasswordModify.this);
                requestQueue.add(request);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordModify.this, MainActivity.class);
                intent.putExtra("jump_id", 3);
                startActivity(intent);
            }
        });
    }
}
