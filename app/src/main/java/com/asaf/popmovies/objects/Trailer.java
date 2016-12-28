package com.asaf.popmovies.objects;

import java.io.Serializable;

/**
 * Created by asafvaron on 27/12/2016.
 */
public class Trailer implements Serializable {
    private String id;
    private String key;
    private String name;
    private int size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Trailer(String id, String key, String name, int size) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.size = size;
    }
}
