package com.example.sheldon.cinemademo.model;

import java.io.Serializable;

public class Comment implements Serializable {

    private String user;
    private String content;
    private String time;

    public Comment() {
    }

    public Comment(String user, String content, String time) {
        this.user = user;
        this.content = content;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
