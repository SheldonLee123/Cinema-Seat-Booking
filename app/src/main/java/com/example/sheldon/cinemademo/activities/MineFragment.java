package com.example.sheldon.cinemademo.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheldon.cinemademo.R;

public class MineFragment extends Fragment {

    private TextView textView;
    private SharedPreferences sp;
    private String username;
    private Button btn_logout;
    AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mine, container, false);
        textView = view.findViewById(R.id.press_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView textView7 = view.findViewById(R.id.tv_username);

        //judge login status
        sp = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        username = sp.getString("username",null);
//        Toast.makeText(getActivity(), username, Toast.LENGTH_LONG).show();
        if(username != null){
            textView.setText("Congratulations! Logged in!");
            textView.setTextSize(15);
            textView.setClickable(false);
            textView7.setText(username);
            textView7.setTextSize(18);
        }

        TextView textView1 = view.findViewById(R.id.textview1);
        Drawable drawable1 = getResources().getDrawable(R.drawable.bookedtickets);
        drawable1.setBounds(0,0,80,80);
        textView1.setCompoundDrawables(drawable1, null, null, null);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLogin()){
                    Intent intent = new Intent(getActivity(), BookedTickets.class);
                    startActivity(intent);
                }else{
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("You must login first!");
                    alertDialog.show();
                }
            }
        });

        TextView textView2 = view.findViewById(R.id.textview2);
        Drawable drawable2 = getResources().getDrawable(R.drawable.historytickets);
        drawable2.setBounds(0,0,80,80);
        textView2.setCompoundDrawables(drawable2, null, null, null);

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLogin()){
                    Intent intent = new Intent(getActivity(), HistoryTickets.class);
                    startActivity(intent);
                }else{
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("You must login first!");
                    alertDialog.show();
                }
            }
        });

        TextView textView3 = view.findViewById(R.id.textview3);
        Drawable drawable3 = getResources().getDrawable(R.drawable.myinformation);
        drawable3.setBounds(0,0,80,80);
        textView3.setCompoundDrawables(drawable3, null, null, null);

        TextView textView4 = view.findViewById(R.id.textview4);
        Drawable drawable4 = getResources().getDrawable(R.drawable.money);
        drawable4.setBounds(0,0,80,80);
        textView4.setCompoundDrawables(drawable4, null, null, null);

        TextView textView5 = view.findViewById(R.id.textview5);
        Drawable drawable5 = getResources().getDrawable(R.drawable.mycomments);
        drawable5.setBounds(0,0,80,80);
        textView5.setCompoundDrawables(drawable5, null, null, null);

        TextView changePassword = view.findViewById(R.id.textview6);
        Drawable changePassword2 = getResources().getDrawable(R.drawable.padlock);
        changePassword2.setBounds(0,0,80,80);
        changePassword.setCompoundDrawables(changePassword2, null, null, null);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLogin()){
                    Intent intent = new Intent(getActivity(), PasswordModify.class);
                    startActivity(intent);
                }else {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("You must login first!");
                    alertDialog.show();
                }
            }
        });

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("jump_id", 3);
                startActivity(intent);
            }
        });

        return view;
    }

    private boolean checkLogin(){
        sp = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        username = sp.getString("username",null);
        if(username != null){
            return true;
        }else {
            return false;
        }
    }

}
