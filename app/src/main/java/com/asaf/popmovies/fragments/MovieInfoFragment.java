package com.asaf.popmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asaf.popmovies.R;
import com.asaf.popmovies.activities.YouTubePlayerActivity;
import com.asaf.popmovies.adapters.MovieTrailersAdapter;
import com.asaf.popmovies.objects.Movie;
import com.asaf.popmovies.objects.Trailer;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubePlayer;
import com.thefinestartist.ytpa.enums.Orientation;

/**
 * Created by asafvaron on 27/12/2016.
 */

public class MovieInfoFragment extends Fragment implements MovieTrailersAdapter.MovieTrailersAdapterListener{

    private static final String TAG = MovieInfoFragment.class.getSimpleName();
    private Movie mSelectedMovie;
    private RecyclerView mRvMovieTrailerList;
    private MovieTrailersAdapter mMovieTrailersAdapter;

    public static MovieInfoFragment newInstance(Movie movie) {
        MovieInfoFragment f = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getSerializable("movie") != null) {
            mSelectedMovie = (Movie) getArguments().getSerializable("movie");
//            ApiHelper.getInstance().getMoreInfo(mSelectedMovie);
            Log.d(TAG, "onCreate: selected Movie: " + mSelectedMovie);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_info, container, false);

        Glide.with(getContext())
                .load(mSelectedMovie.getPosterUrl())
                .error(R.drawable.image_broken)
                .crossFade()
                .into((ImageView) root.findViewById(R.id.movie_poster));

        ((TextView) root.findViewById(R.id.movie_title)).setText(mSelectedMovie.getTitle());
        ((TextView) root.findViewById(R.id.release_year)).setText(mSelectedMovie.getReleaseYear());

        int runtime = mSelectedMovie.getDuration();
        String duration = (runtime == 0)
                ? "Not Available" : String.valueOf(runtime) + "min";
        ((TextView) root.findViewById(R.id.duration)).setText(duration);

        String rate = String.valueOf(mSelectedMovie.getRate()) + "/10";
        ((TextView) root.findViewById(R.id.rating)).setText(rate);
        ((TextView) root.findViewById(R.id.info)).setText(mSelectedMovie.getInfo());

        mRvMovieTrailerList = (RecyclerView) root.findViewById(R.id.rv_movie_trailers_list);
        mMovieTrailersAdapter = new MovieTrailersAdapter(getContext(), mRvMovieTrailerList,
                mSelectedMovie.getTrailersList());
        mMovieTrailersAdapter.setListener(this);
        return root;
    }

    @Override
    public void showYoutubeVideo(Trailer trailer) {
//        Log.d(TAG, "showYouTubeVideo: videoID: " + videoUrl);
        Intent intent = new Intent(getContext(), YouTubePlayerActivity.class);

        Log.i(TAG, "showYoutubeVideo: id = " + trailer.getId());
        Log.i(TAG, "showYoutubeVideo: key = " + trailer.getKey());
        // Youtube video ID (Required, You can use YouTubeUrlParser to parse Video Id from url)
//        intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, YouTubeUrlParser.getVideoId(videoUrl));
        String key = trailer.getKey();
        intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, key);

        // Youtube player style (DEFAULT as default)
        intent.putExtra(YouTubePlayerActivity.EXTRA_PLAYER_STYLE, YouTubePlayer.PlayerStyle.DEFAULT);

        // Screen Orientation Setting (AUTO for default)
        // AUTO, AUTO_START_WITH_LANDSCAPE, ONLY_LANDSCAPE, ONLY_PORTRAIT
        intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, Orientation.AUTO);

        // Show audio interface when user adjust volume (true for default)
        intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, true);

        // If the video is not playable, use Youtube app or Internet Browser to play it
        // (true for default)
        intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);

        // Animation when closing youtubePlayerActivity (none for default)
        intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.slide_up);
        intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.slide_out_down);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
