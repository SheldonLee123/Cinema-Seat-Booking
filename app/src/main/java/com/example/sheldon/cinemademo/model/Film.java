package com.example.sheldon.cinemademo.model;

import java.util.List;

public class Film {

    private String name;
    private String Description;
    private String rating;
    private String categorie;
    private String director;
    private String image_url;
    private String actor;
    private String introduction;
    private List<Comment> comments;
    private List<Timetable> timetable;
    private String film_id;

    public Film() {
    }

    public Film(String name, String description, String rating, String categorie, String director, String image_url, String actor, String introduction, List<Comment> comments, List<Timetable> timetable) {
        this.name = name;
        Description = description;
        this.rating = rating;
        this.categorie = categorie;
        this.director = director;
        this.image_url = image_url;
        this.actor = actor;
        this.introduction = introduction;
        this.comments = comments;
        this.timetable = timetable;
    }

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Timetable> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Timetable> timetable) {
        this.timetable = timetable;
    }
}
