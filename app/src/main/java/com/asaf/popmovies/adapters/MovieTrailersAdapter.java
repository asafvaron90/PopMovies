package com.asaf.popmovies.adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asaf.popmovies.R;
import com.asaf.popmovies.fragments.MovieInfoFragment;
import com.asaf.popmovies.objects.Trailer;

import java.util.List;

/**
 * Created by asafvaron on 27/12/2016.
 */
public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.TrailerViewHolder>{

    private final LayoutInflater mInflater;
    private final RecyclerView mRecyclerView;
    private final Context mContext;
    private List<Trailer> mTrailerList;
    private MovieTrailersAdapterListener mListener;

    public interface MovieTrailersAdapterListener {
        void showYoutubeVideo(Trailer trailer);
    }
    public MovieTrailersAdapter(Context context, RecyclerView rv, List<Trailer> mSelectedMovieTrailers) {
        this.mContext = context;
        this.mRecyclerView = rv;
        mTrailerList = mSelectedMovieTrailers;
        this.mInflater = LayoutInflater.from(mContext);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(this);
    }

    public void setListener(MovieInfoFragment movieGridFragment) {
        mListener = movieGridFragment;
    }

    @Override
    public MovieTrailersAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailerViewHolder(mInflater.inflate(R.layout.trailer_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieTrailersAdapter.TrailerViewHolder holder, int position) {

        final Trailer t = mTrailerList.get(position);
        holder.name.setText(mTrailerList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Going for YouTube", Toast.LENGTH_SHORT).show();
                mListener.showYoutubeVideo(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{

        ImageView play;
        TextView name;
        TrailerViewHolder(View itemView) {
            super(itemView);
            play = (ImageView) itemView.findViewById(R.id.play);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
