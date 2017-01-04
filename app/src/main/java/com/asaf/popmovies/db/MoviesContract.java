package com.asaf.popmovies.db;

import android.net.Uri;

/**
 * Created by asafvaron on 04/01/2017.
 */

public class MoviesContract {


    //the provider's authority
    public final static String AUTHORITY = "com.asaf.popmovies.provider";


    //table Places
    public static class Popular {

        //table's name
        public final static String TABLE_NAME = "popular_movies";

        //uri
        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        //columns
        public final static String _ID = "_id";
        public final static String ID = "id";
        public final static String TITLE = "title";
        public final static String INFO = "info";
        public final static String POSTER = "poster";
        public final static String RELEASE_YEAR = "release_year";
        public final static String RATE = "rate";
        public final static String DURATION = "duration";

    }


    //table Places
    public static class TopRated {

        //table's name
        public final static String TABLE_NAME = "top_rated_movies";

        //uri
        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        //columns
        public final static String _ID = "_id";
        public final static String ID = "id";
        public final static String TITLE = "title";
        public final static String INFO = "info";
        public final static String POSTER = "poster";
        public final static String RELEASE_YEAR = "release_year";
        public final static String RATE = "rate";
        public final static String DURATION = "duration";

    }


    //table Places
    public static class Upcoming {

        //table's name
        public final static String TABLE_NAME = "upcoming_movies";

        //uri
        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        //columns
        public final static String _ID = "_id";
        public final static String ID = "id";
        public final static String TITLE = "title";
        public final static String INFO = "info";
        public final static String POSTER = "poster";
        public final static String RELEASE_YEAR = "release_year";
        public final static String RATE = "rate";
        public final static String DURATION = "duration";

    }

}
