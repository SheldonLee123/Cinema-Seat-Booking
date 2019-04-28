package com.example.sheldon.cinemademo;

import android.app.Application;

public class GlobalVariable extends Application {

//    private String serverIp = "192.168.0.101";
//    private String serverIp = "192.168.43.68";
    private String serverIp = "192.168.1.107";
//    private String serverIp = "192.168.1.111";

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}
