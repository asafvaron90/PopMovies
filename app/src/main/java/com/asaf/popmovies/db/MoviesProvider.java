package com.asaf.popmovies.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.List;

/**
 * Created by asafvaron on 04/01/2017.
 */

public class MoviesProvider extends ContentProvider {
    //global attributes
    DbOpenHelper dbOpenHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int res = db.delete(getTableName(uri), selection, selectionArgs);

        // notifies the table on change
        getContext().getContentResolver().notifyChange(uri, null);

        return res;
    }

    /**
     * This method we created to find the URI of the table
     *
     * @param uri - used to get the tables uri
     *
     * @return only the tables name
     */
    private String getTableName(Uri uri) {
        // retrieving the path after the authority
        List<String> segments = uri.getPathSegments();

        return segments.get(0);

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long id = db.insert(getTableName(uri), null, values);

        // notify table on change
        getContext().getContentResolver().notifyChange(uri, null);

        if(id > 0){
            //method that help insert String to uri
            return ContentUris.withAppendedId(uri, id);
        }else{

            return null;
        }

    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new DbOpenHelper(getContext());

        if (dbOpenHelper == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = db.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int res = db.update(getTableName(uri), values, selection, selectionArgs);

        // notifies the table on change
        getContext().getContentResolver().notifyChange(uri, null);

        return res;
    }

}
