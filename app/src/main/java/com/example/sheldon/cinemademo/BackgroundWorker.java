package com.example.sheldon.cinemademo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheldon.cinemademo.activities.LoginActivity;
import com.example.sheldon.cinemademo.activities.MainActivity;
import com.example.sheldon.cinemademo.activities.MineFragment;
import com.example.sheldon.cinemademo.model.Seat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    private String type;
    private String email;
    private String password;
    private String loginstatus;
    private SharedPreferences sp;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;

    public BackgroundWorker(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        email = params[1];
        password = params[2];
        GlobalVariable globalVariable = new GlobalVariable();
        String serverIp = globalVariable.getServerIp();
        String login_url = "http://"+ serverIp +":8080/Android_Project/Login";
        String register_url = "http://"+ serverIp +":8080/Android_Project/Register";
        if(type.equals("login")) {
            request = new JsonArrayRequest(login_url + "?email=" + email + "?password=" + password, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    JSONObject jsonObject = null;
                    JSONArray userinfo = null;
                    JSONObject userobject = null;

                    try{
                        jsonObject = response.getJSONObject(0);
                        loginstatus = jsonObject.getString("LoginStatus");
                        if(loginstatus.equals("Success")) {
                            userinfo = jsonObject.getJSONArray("UserInfo");
                            userobject = userinfo.getJSONObject(0);
                            sp = context.getSharedPreferences("User",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putString("balance", userobject.getString("balance"));
                            editor.putString("phone", userobject.getString("phone"));
                            editor.putString("user_id", userobject.getString("id"));
                            editor.apply();
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


            requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        }
        else if(type.equals("register")){
            try {
                String email = params[1];
                String user_name = params[2];
                String pass_word = params[3];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("account", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"+URLEncoder.encode("uname", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"+URLEncoder.encode("pwd", "UTF-8")+"="+URLEncoder.encode(pass_word, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("LoginStatus");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(loginstatus);
        if(type.equals("login") && loginstatus.equals("Success")){
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("jump_id", 3);
            context.startActivity(intent);
        }
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
