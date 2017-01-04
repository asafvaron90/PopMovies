package com.asaf.popmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asaf.popmovies.R;
import com.asaf.popmovies.db.MoviesContract;
import com.asaf.popmovies.fragments.MovieGridFragment;
import com.asaf.popmovies.objects.Movie;
import com.bumptech.glide.Glide;

import java.util.List;


public class MovieGridAdapterCursor extends RecyclerViewCursorAdapter<MovieGridAdapterCursor.MovieViewHolder> {
    private static final String TAG = MovieGridAdapterCursor.class.getSimpleName();

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<Movie> mMovieList;
    private MovieGridAdapterListener mListener;

    public interface MovieGridAdapterListener {
        void movieClicked(Movie m);
    }

    public MovieGridAdapterCursor(Cursor c, Context context, RecyclerView rv, List<Movie> movieList) {
        super(c);
        Log.d(TAG, "MovieGridAdapter: ");
        this.mContext = context;
        this.mRecyclerView = rv;
        this.mInflater = LayoutInflater.from(mContext);
        this.mMovieList = movieList;
        initRecyclerView();
    }

    public void setListener(MovieGridFragment movieGridFragment) {
        mListener = movieGridFragment;
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        if (mContext.getResources().getBoolean(R.bool.isTablet)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(this);
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(
                MoviesContract.Popular.ID));
        String title = cursor.getString(cursor.getColumnIndex(
                MoviesContract.Popular.TITLE));
        String info = cursor.getString(cursor.getColumnIndex(
                MoviesContract.Popular.INFO));
        String releaseYear = cursor.getString(cursor.getColumnIndex(
                MoviesContract.Popular.RELEASE_YEAR));
        int rate = cursor.getInt(cursor.getColumnIndex(
                MoviesContract.Popular.RATE));
        String posterUrl = cursor.getString(cursor.getColumnIndex(
                MoviesContract.Popular.POSTER));

        final Movie m = new Movie(id, title, info, posterUrl, releaseYear, rate);

        // load the movie poster
        Glide.with(mContext)
                .load(posterUrl)
//                .centerCrop()
                .error(R.drawable.ic_image_broken_white_48dp)
                .crossFade()
                .into(holder.moviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.movieClicked(m);
            }
        });

        // set the info fragment
        if (mContext.getResources().getBoolean(R.bool.isTablet) &&
                cursor.getPosition() == mMovieList.size() - 1) {
            Log.d(TAG, "onBindViewHolder: tablet - setting info fragment");
            mListener.movieClicked(mMovieList.get(0));
        }
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(newCursor);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(mInflater.inflate(R.layout.movie_grid_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            this.moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }

}
