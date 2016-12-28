package com.asaf.popmovies;

import android.app.Application;

import com.asaf.popmovies.helpers.ApiHelper;


public class PopMoviesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiHelper.getInstance().init(getApplicationContext());
    }
}
