package com.asaf.popmovies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by asafvaron on 04/01/2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = DbOpenHelper.class.getSimpleName();

    private static final String DB_NAME = "popmovies_table.db";
    private static final int DB_VERSION = 4;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating the Popular Movie's data base
        String popular_sql = "CREATE TABLE " + MoviesContract.Popular.TABLE_NAME + "("
                + MoviesContract.Popular._ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MoviesContract.Popular.ID + " REAL,"
                + MoviesContract.Popular.TITLE + " TEXT,"
                + MoviesContract.Popular.INFO + " TEXT,"
                + MoviesContract.Popular.POSTER + " TEXT,"
                + MoviesContract.Popular.DURATION + " REAL,"
                + MoviesContract.Popular.RATE + " REAL,"
                + MoviesContract.Popular.RELEASE_YEAR + " TEXT" + ")";

        db.execSQL(popular_sql);

        // creating the TopRated Movie's data base
        String top_rated_sql = "CREATE TABLE " + MoviesContract.TopRated.TABLE_NAME + "("
                + MoviesContract.TopRated._ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MoviesContract.TopRated.ID + " REAL,"
                + MoviesContract.TopRated.TITLE + " TEXT,"
                + MoviesContract.TopRated.INFO + " TEXT,"
                + MoviesContract.TopRated.POSTER + " TEXT,"
                + MoviesContract.TopRated.DURATION + " REAL,"
                + MoviesContract.TopRated.RATE + " REAL,"
                + MoviesContract.TopRated.RELEASE_YEAR + " TEXT" + ")";

        db.execSQL(top_rated_sql);

        // creating the TopRated Movie's data base
        String upcoming_sql = "CREATE TABLE " + MoviesContract.Upcoming.TABLE_NAME + "("
                + MoviesContract.Upcoming._ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MoviesContract.Upcoming.ID + " REAL,"
                + MoviesContract.Upcoming.TITLE + " TEXT,"
                + MoviesContract.Upcoming.INFO + " TEXT,"
                + MoviesContract.Upcoming.POSTER + " TEXT,"
                + MoviesContract.Upcoming.DURATION + " REAL,"
                + MoviesContract.Upcoming.RATE + " REAL,"
                + MoviesContract.Upcoming.RELEASE_YEAR + " TEXT" + ")";

        db.execSQL(upcoming_sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG, "onUpgrade table");
        // deleting old table and updating the new one
        String popular_sql = "DROP TABLE IF EXISTS " + MoviesContract.Popular.TABLE_NAME;
        String top_rated_sql = "DROP TABLE IF EXISTS " + MoviesContract.TopRated.TABLE_NAME;
        String upcoming_sql = "DROP TABLE IF EXISTS " + MoviesContract.Upcoming.TABLE_NAME;

        Log.d(TAG, "table is deleted/upgraded "
                + MoviesContract.Popular.TABLE_NAME);

        db.execSQL(popular_sql);
        db.execSQL(top_rated_sql);
        db.execSQL(upcoming_sql);
        onCreate(db);
    }
}
