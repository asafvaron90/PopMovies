package com.asaf.popmovies.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.asaf.popmovies.R;
import com.asaf.popmovies.fragments.MovieGridFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    FragmentManager mFm;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            MovieGridFragment movieGridFragment = MovieGridFragment.newInstance();
            mFm.beginTransaction().add(R.id.fragment_container, movieGridFragment,
                    MovieGridFragment.class.getSimpleName()).commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: newConfig: " + newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        MovieGridFragment movieGridFragment =
                (MovieGridFragment) mFm.findFragmentByTag(MovieGridFragment.class.getSimpleName());

        switch (id) {
            case R.id.popular:
                Log.d(TAG, "onOptionsItemSelected: popular");
                mToolbar.setTitle(getResources().getString(R.string.popular_title));
                movieGridFragment.setData(MovieGridFragment.POPULAR);
                return true;

            case R.id.top_rated:
                Log.d(TAG, "onOptionsItemSelected: top rated");
                mToolbar.setTitle(getResources().getString(R.string.top_rated_title));
                movieGridFragment.setData(MovieGridFragment.TOP_RATED);
                return true;

            case R.id.upcoming:
                Log.d(TAG, "onOptionsItemSelected: upcoming");
                mToolbar.setTitle(getResources().getString(R.string.upcoming_title));
                movieGridFragment.setData(MovieGridFragment.TOP_RATED);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
