package com.asaf.popmovies.objects;

import com.asaf.popmovies.helpers.ApiHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asafvaron on 27/12/2016.
 */

public class Movie implements Serializable {

    private int id;
    private String title;
    private String info;
    private String posterUrl;
    private String releaseYear;
    private double rate;
    private int duration;
    private List<Trailer> trailersList;

    public Movie(int id, String title, String info, String posterUrl, String releaseYear, double rate) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.posterUrl = posterUrl;
        this.releaseYear = releaseYear;
        this.rate = rate;
        this.trailersList = new ArrayList<>();
        ApiHelper.getInstance().getMoreInfo(this);
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<Trailer> getTrailersList() {
        return trailersList;
    }

    public void setTrailersList(List<Trailer> trailersList) {
        this.trailersList = trailersList;
    }

    @Override
    public String toString() {
        return "\nid: " + id
                + "\ntitle: " + title
                + "\ninfo: " + info
                + "\nposterUrl: " + posterUrl
                + "\nreleaseYear: " + releaseYear
                + "\nrate: " + rate
                + "\nduration: " + duration
                + "\ntrailers: " + trailersList.toString();
    }
}
