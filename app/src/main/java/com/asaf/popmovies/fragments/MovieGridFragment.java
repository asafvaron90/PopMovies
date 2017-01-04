package com.asaf.popmovies.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asaf.popmovies.R;
import com.asaf.popmovies.adapters.MovieGridAdapterCursor;
import com.asaf.popmovies.db.MoviesContract;
import com.asaf.popmovies.helpers.ApiHelper;
import com.asaf.popmovies.objects.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridFragment extends Fragment
        implements MovieGridAdapterCursor.MovieGridAdapterListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MovieGridFragment.class.getSimpleName();
    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;
    public static final int UPCOMING = 2;

    private RecyclerView mRvMovieGrid;
    //    private MovieGridAdapter mMovieGridAdapter = null;
    private MovieGridAdapterCursor mMovieGridAdapter = null;

    public static MovieGridFragment newInstance() {
        return new MovieGridFragment();
    }

    public MovieGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_grid, container, false);
        root.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        if (savedInstanceState == null) {
            mRvMovieGrid = (RecyclerView) root.findViewById(R.id.rv_movie_grid);
            // init adapter when loader is finished
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(POPULAR, null, this);
    }

    @Override
    public void movieClicked(Movie m) {
        Log.d(TAG, "movieClicked: m = " + m.getTitle());

        MovieInfoFragment frag = MovieInfoFragment.newInstance(m);
        Log.d(TAG, "movieClicked: orientation=" + getActivity().getResources().getConfiguration().orientation);
        Log.d(TAG, "movieClicked: is tablet=" + getActivity().getResources().getBoolean(R.bool.isTablet));
        if (getActivity().getResources().getBoolean(R.bool.isTablet)) {
            // replace the second fragment container
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_info, frag,
                    MovieInfoFragment.class.getSimpleName()).addToBackStack(null).commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag,
                    MovieInfoFragment.class.getSimpleName()).addToBackStack(null).commit();
        }
    }

    public void setData(int data) {
        switch (data) {
            case POPULAR:
                getLoaderManager().restartLoader(POPULAR, null, this);
                break;

            case TOP_RATED:
                getLoaderManager().restartLoader(TOP_RATED, null, this);
                break;

            case UPCOMING:
                getLoaderManager().restartLoader(UPCOMING, null, this);
                break;
        }
    }

        @Override
        public Loader<Cursor> onCreateLoader ( int id, Bundle args){
            Log.d(TAG, "onCreateLoader: id = " + id);

            switch (id) {
                case POPULAR:
                    return new CursorLoader(getActivity(),
                            MoviesContract.Popular.CONTENT_URI, null, null, null,
                            MoviesContract.Popular._ID + " asc");
                case TOP_RATED:
                    return new CursorLoader(getActivity(),
                            MoviesContract.TopRated.CONTENT_URI, null, null, null,
                            MoviesContract.TopRated._ID + " asc");

                case UPCOMING:
                    return new CursorLoader(getActivity(),
                            MoviesContract.Upcoming.CONTENT_URI, null, null, null,
                            MoviesContract.Upcoming._ID + " asc");
                default:
                    return new CursorLoader(getActivity(),
                            MoviesContract.Popular.CONTENT_URI, null, null, null,
                            MoviesContract.Popular._ID + " asc");
            }
        }

        @Override
        public void onLoadFinished (Loader < Cursor > loader, Cursor c){
            Log.d(TAG, "onLoadFinished: ");
            if (mMovieGridAdapter == null) {
                mMovieGridAdapter = new MovieGridAdapterCursor(c, getContext(), mRvMovieGrid,
                        ApiHelper.getInstance().getPopularMoviesList());
                mMovieGridAdapter.setListener(this);
            }

            mMovieGridAdapter.swapCursor(c);
        }

        @Override
        public void onLoaderReset (Loader < Cursor > loader) {
            Log.d(TAG, "onLoaderReset: ");
            //If the loader is reset, we need to clear out the
            //current cursor from the adapter.
            mMovieGridAdapter.swapCursor(null);
        }
    }
