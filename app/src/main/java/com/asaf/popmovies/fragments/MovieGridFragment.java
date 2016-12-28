package com.asaf.popmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asaf.popmovies.R;
import com.asaf.popmovies.adapters.MovieGridAdapter;
import com.asaf.popmovies.helpers.ApiHelper;
import com.asaf.popmovies.objects.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridFragment extends Fragment
        implements MovieGridAdapter.MovieGridAdapterListener {

    private static final String TAG = MovieGridFragment.class.getSimpleName();
    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;
    public static final int UPCOMING = 2;

    private RecyclerView mRvMovieGrid;
    private MovieGridAdapter mMovieGridAdapter = null;

    public static MovieGridFragment newInstance() {
        return new MovieGridFragment();
    }

    public MovieGridFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_grid, container, false);

        if (savedInstanceState == null) {
            mRvMovieGrid = (RecyclerView) root.findViewById(R.id.rv_movie_grid);
            mMovieGridAdapter = new MovieGridAdapter(getContext(), mRvMovieGrid,
                    ApiHelper.getInstance().getPopularMoviesList());
            mMovieGridAdapter.setListener(this);
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

    }

    public void setData(int data) {
        switch (data) {
            case POPULAR:
                mMovieGridAdapter = new MovieGridAdapter(getContext(), mRvMovieGrid,
                        ApiHelper.getInstance().getPopularMoviesList());
                break;

            case TOP_RATED:
                mMovieGridAdapter = new MovieGridAdapter(getContext(), mRvMovieGrid,
                        ApiHelper.getInstance().getTopRatedMoviesList());
                break;

            case UPCOMING:
                mMovieGridAdapter = new MovieGridAdapter(getContext(), mRvMovieGrid,
                        ApiHelper.getInstance().getUpcomingMoviesList());
                break;
        }
        mMovieGridAdapter.setListener(this);
    }

}
