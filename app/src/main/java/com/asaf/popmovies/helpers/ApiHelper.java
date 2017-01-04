package com.asaf.popmovies.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.asaf.popmovies.db.MoviesContract;
import com.asaf.popmovies.fragments.MovieGridFragment;
import com.asaf.popmovies.objects.Movie;
import com.asaf.popmovies.objects.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApiHelper {
    private static final String TAG = ApiHelper.class.getSimpleName();

    // urls
    private static final String API_KEY = "?api_key=99e49e3887ea4ee5465e1c68a8ca6bf0";
    private static final String MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String MOVIE_DEFAULT_API_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String TOP_RATED_MOVIES_URL = "https://api.themoviedb.org/3/movie/top_rated";
    private static final String UPCOMING_MOVIES_URL = "https://api.themoviedb.org/3/movie/upcoming";
    private static final String MOVIE_TRAILERS = "&append_to_response=videos";


    private static ApiHelper sInstance = null;

    private RequestQueue mRequestQueue;
    private Context mContext;
    private List<Movie> mPopularList;
    private List<Movie> mTopRatedList;
    private List<Movie> mUpcomingList;

    public void init(Context context) {
        this.mContext = context;
        getPopularMovies();
        getTopRatedMovies();
        getUpcomingMovies();
    }

    public static synchronized ApiHelper getInstance() {
        if (sInstance == null)
            sInstance = new ApiHelper();
        return sInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        Log.d(TAG, "addToRequestQueue: " + req.toString() + " request tag: " + tag);

        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        Log.d(TAG, "addToRequestQueue: " + req.toString());
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        Log.d(TAG, "cancelPendingRequests: " + tag.toString());
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void getPopularMovies() {
        mPopularList = new ArrayList<>();
        String url = POPULAR_MOVIES_URL + API_KEY;
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, "onResponse: Popular - " + response);
                        JSONArray results;
                        try {
                            if (response.has("results")) {
                                results = response.getJSONArray("results");

                                if (results != null) {
                                    for (int i = 0; i < results.length(); i++) {
                                        mPopularList.add(getMovie(MovieGridFragment.POPULAR,
                                                results.getJSONObject(i)));
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: " + error.getLocalizedMessage());
                    }
                }
        );
        addToRequestQueue(req);
    }

    public void getTopRatedMovies() {
        mTopRatedList = new ArrayList<>();
        String url = TOP_RATED_MOVIES_URL + API_KEY;
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, "onResponse: Top Rated - " + response);
                        JSONArray results;
                        try {
                            if (response.has("results")) {
                                results = response.getJSONArray("results");

                                if (results != null) {
                                    for (int i = 0; i < results.length(); i++) {
                                        mTopRatedList.add(getMovie(MovieGridFragment.TOP_RATED,
                                                results.getJSONObject(i)));
                                    }
                                }
                                Log.w(TAG, "mTopRatedList size = " + mTopRatedList.size());

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: " + error.getLocalizedMessage());
                    }
                }
        );
        addToRequestQueue(req);
    }

    public void getUpcomingMovies() {
        mUpcomingList = new ArrayList<>();
        String url = UPCOMING_MOVIES_URL + API_KEY;
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, "onResponse: Upcoming - " + response);
                        JSONArray results;
                        try {
                            if (response.has("results")) {
                                results = response.getJSONArray("results");

                                if (results != null) {
                                    for (int i = 0; i < results.length(); i++) {
                                        mUpcomingList.add(getMovie(MovieGridFragment.UPCOMING,
                                                results.getJSONObject(i)));
                                    }
                                }
                                Log.w(TAG, "mUpcomingList size = " + mUpcomingList.size());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: " + error.getLocalizedMessage());
                    }
                }
        );
        addToRequestQueue(req);
    }


    private Movie getMovie(int db_id, JSONObject object) {
        ContentValues values = new ContentValues();

        Log.d(TAG, "getMovie: ");
        Movie movie = null;
        try {
            // movie id
            int id = 0;
            if (object.has("id")) {
                id = object.getInt("id");
            }

            // title
            String title = "";
            if (object.has("title")) {
                title = object.getString("title");
            } else if (object.has("original_title")) {
                title = object.getString("original_title");
            }

            // info
            String overview = "";
            if (object.has("overview")) {
                overview = object.getString("overview");
            }

            // release date
            String releaseData = "";
            if (object.has("release_date")) releaseData = object.getString("release_date");
            if (!releaseData.isEmpty()) {
                // get only the year
                Pattern p = Pattern.compile("\\d\\d\\d\\d");
                Matcher m = p.matcher(releaseData);
                if (m.find()) {
                    releaseData = m.group();
                }
            }

            // rate
            double rate = 0;
            if (object.has("vote_average")) {
                rate = object.getDouble("vote_average");
            }

            String posterUrl = "";
            if (object.has("poster_path")) {
                posterUrl = MOVIE_IMAGE_URL + object.getString("poster_path");
            }

            switch (db_id) {
                case MovieGridFragment.POPULAR:
                    values.put(MoviesContract.Popular.ID, id);
                    values.put(MoviesContract.Popular.TITLE, title);
                    values.put(MoviesContract.Popular.INFO, overview);
                    values.put(MoviesContract.Popular.RELEASE_YEAR, releaseData);
                    values.put(MoviesContract.Popular.RATE, rate);
                    values.put(MoviesContract.Popular.POSTER, posterUrl);
                    mContext.getContentResolver().insert(
                            MoviesContract.Popular.CONTENT_URI, values);
                    break;
                case MovieGridFragment.TOP_RATED:
                    values.put(MoviesContract.TopRated.ID, id);
                    values.put(MoviesContract.TopRated.TITLE, title);
                    values.put(MoviesContract.TopRated.INFO, overview);
                    values.put(MoviesContract.TopRated.RELEASE_YEAR, releaseData);
                    values.put(MoviesContract.TopRated.RATE, rate);
                    values.put(MoviesContract.TopRated.POSTER, posterUrl);
                    mContext.getContentResolver().insert(
                            MoviesContract.TopRated.CONTENT_URI, values);
                    break;
                case MovieGridFragment.UPCOMING:
                    values.put(MoviesContract.Upcoming.ID, id);
                    values.put(MoviesContract.Upcoming.TITLE, title);
                    values.put(MoviesContract.Upcoming.INFO, overview);
                    values.put(MoviesContract.Upcoming.RELEASE_YEAR, releaseData);
                    values.put(MoviesContract.Upcoming.RATE, rate);
                    values.put(MoviesContract.Upcoming.POSTER, posterUrl);
                    mContext.getContentResolver().insert(
                            MoviesContract.Upcoming.CONTENT_URI, values);
                    break;
            }

            movie = new Movie(id, title, overview, posterUrl, releaseData, rate);

            Log.i(TAG, "getMovie: Movie saved to DB");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getMovie: " + movie.toString());
        return movie;
    }

    public List<Movie> getPopularMoviesList() {
        return mPopularList;
    }

    public List<Movie> getTopRatedMoviesList() {
        return mTopRatedList;
    }

    public List<Movie> getUpcomingMoviesList() {
        return mUpcomingList;
    }

    public void getMoreInfo(Movie mSelectedMovie) {
        Log.i(TAG, "getMoreInfo: movie: " + mSelectedMovie.getTitle());
        getRunTimeAndTrailers(mSelectedMovie);
    }

    private void getRunTimeAndTrailers(final Movie movie) {
        final List<Trailer> trailerList = new ArrayList<>();
        String url = MOVIE_DEFAULT_API_URL + movie.getId() + API_KEY + MOVIE_TRAILERS;
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("runtime")) {
                                try {
                                    movie.setDuration(response.getInt("runtime"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                movie.setDuration(0);
                            }

                            if (response.has("videos")) {
                                JSONArray trailers = response.getJSONObject("videos")
                                        .getJSONArray("results");

                                if (trailers != null) {
                                    for (int i = 0; i < trailers.length(); i++) {
                                        JSONObject o = trailers.getJSONObject(i);
//                                        Log.d(TAG, "onResponse: o = " + o.toString());
                                        if (o.getString("site").equalsIgnoreCase("YouTube")) {
                                            String id = o.getString("id");
                                            String key = o.getString("key");
                                            String name = o.getString("name");
                                            int size = o.getInt("size");
                                            trailerList.add(new Trailer(id, key, name, size));
                                        }
                                    }
                                    movie.setTrailersList(trailerList);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ERR: " + error.getLocalizedMessage());
                    }
                }
        );
        addToRequestQueue(req);
    }
}
