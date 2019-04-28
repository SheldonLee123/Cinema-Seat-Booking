package com.example.sheldon.cinemademo.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

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

import java.util.ArrayList;

public class PayOrder extends AppCompatActivity {

    GlobalVariable globalVariable = new GlobalVariable();
    String serverIp = globalVariable.getServerIp();
    String ordersubmit_url = "http://"+ serverIp +":8080/Android_Project/OrderSubmit";

    private RadioButton rb_creaditcard;
    private RadioButton rb_wechat;
    private RadioButton rb_alipy;
    private RadioButton rb_bank;
    private RadioButton rb_balance;
    private Double price;
    private int ticketNumber;
    private TextView tv_price;
    private Button btn_confirm;
    AlertDialog alertDialog;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private SharedPreferences sp;
    private int timetable_id;
    private String user_id;
    private ArrayList<String> selectedseat = new ArrayList<>();
    private String ordersubmitstatus;
    private int seat_row, seat_column;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);

        price = getIntent().getDoubleExtra("price", 0);
        ticketNumber = getIntent().getIntExtra("ticketNumber", 0);
        timetable_id = getIntent().getIntExtra("timetable_id", 0);

        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        user_id = sp.getString("user_id", null);

        selectedseat = getIntent().getStringArrayListExtra("selectedseat");

        tv_price = findViewById(R.id.tv_totalprice);
        tv_price.setText(String.valueOf( price * ticketNumber ));

        rb_creaditcard = findViewById(R.id.rb_creaditcard);
        Drawable drawable1 = getResources().getDrawable(R.drawable.creditcard);
        drawable1.setBounds(0,0,80,80);
        rb_creaditcard.setCompoundDrawables(drawable1, null, null, null);

        rb_wechat = findViewById(R.id.rb_wechat);
        Drawable drawable2 = getResources().getDrawable(R.drawable.wechat);
        drawable2.setBounds(0,0,80,80);
        rb_wechat.setCompoundDrawables(drawable2, null, null, null);

        rb_alipy = findViewById(R.id.rb_alipay);
        Drawable drawable3 = getResources().getDrawable(R.drawable.alipay);
        drawable3.setBounds(0,0,80,80);
        rb_alipy.setCompoundDrawables(drawable3, null, null, null);

        rb_bank = findViewById(R.id.rb_bankcard);
        Drawable drawable4 = getResources().getDrawable(R.drawable.bank);
        drawable4.setBounds(0,0,80,80);
        rb_bank.setCompoundDrawables(drawable4, null, null, null);

        rb_balance = findViewById(R.id.rb_balance);
        Drawable drawable5 = getResources().getDrawable(R.drawable.balance);
        drawable5.setBounds(0,0,80,80);
        rb_balance.setCompoundDrawables(drawable5, null, null, null);

        btn_confirm = findViewById(R.id.btn_confirmpay);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(rb_creaditcard.isChecked()){
//
//                }
//                if(rb_wechat.isChecked()){
//
//                }
//                if(rb_alipy.isChecked()){
//
//                }
//                if(rb_bank.isChecked()){
//
//                }
//                if(rb_balance.isChecked()){
//
//                }
                alertDialog = new AlertDialog.Builder(PayOrder.this).create();
                alertDialog.setTitle("Payment");
                alertDialog.setMessage("Do you confirm to buy the tickets?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                        payOrder();
                        Intent intent = new Intent(PayOrder.this, MainActivity.class);
                        intent.putExtra("jump_id", 3);
                        startActivity(intent);
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                });
                alertDialog.show();
            }

        });
    }

    private void payOrder() {

        for( i = 0; i < ticketNumber; i++) {

            String temp = selectedseat.get(i);
            seat_row = Integer.parseInt(temp.split(",")[0]) + 1;
            seat_column = Integer.parseInt(temp.split(",")[1]) + 1;

            request = new JsonArrayRequest(ordersubmit_url + "?user_id=" + user_id + "&timetable_id=" + timetable_id + "&price=" + price + "&seat_row=" + seat_row + "&seat_column=" + seat_column, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    JSONObject jsonObject = null;
                    JSONObject userinfo = null;

                    try{
                        jsonObject = response.getJSONObject(0);
                        ordersubmitstatus = jsonObject.getString("OrderSubmitStatus");
                        alertDialog = new AlertDialog.Builder(PayOrder.this).create();
                        alertDialog.setTitle("OrderSubmitStatus");
//                    Toast.makeText(LoginActivity.this, loginstatus, Toast.LENGTH_LONG).show();
                        if(ordersubmitstatus.equals("Success")) {

                            alertDialog.setMessage(ordersubmitstatus + " row" + seat_row + "col" + seat_column);
                            alertDialog.show();

                            if(i == ticketNumber - 1){
                                Intent intent = new Intent(PayOrder.this, MainActivity.class);
                                intent.putExtra("jump_id", 3);
                                startActivity(intent);
                            }


                        }else{
                            alertDialog.setMessage(ordersubmitstatus);
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
}
