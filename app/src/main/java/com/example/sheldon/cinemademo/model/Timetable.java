package com.example.sheldon.cinemademo.model;

import java.io.Serializable;

public class Timetable implements Serializable {

    private String st_time;
    private String end_time;
    private Double price_origin;
    private Double price_actual;
    private int room_id;
    private String screen;
    private String date;
    private int id;

    public Timetable() {
    }

    public Timetable(String st_time, String end_time, Double price_origin, Double price_actual, int room_id, String screen, int id, String date) {
        this.st_time = st_time;
        this.end_time = end_time;
        this.price_origin = price_origin;
        this.price_actual = price_actual;
        this.room_id = room_id;
        this.screen = screen;
        this.id = id;
        this.date = date;
    }

    public String getSt_time() {
        return st_time;
    }

    public void setSt_time(String st_time) {
        this.st_time = st_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Double getPrice_origin() {
        return price_origin;
    }

    public void setPrice_origin(Double price_origin) {
        this.price_origin = price_origin;
    }

    public Double getPrice_actual() {
        return price_actual;
    }

    public void setPrice_actual(Double price_actual) {
        this.price_actual = price_actual;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
