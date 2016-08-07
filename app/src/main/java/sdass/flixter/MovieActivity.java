package sdass.flixter;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import sdass.flixter.adapters.MovieArrayAdapter;
import sdass.flixter.models.Movie;

public class MovieActivity extends AppCompatActivity {
    ArrayList<Movie> movies = new ArrayList();
    MovieArrayAdapter movieArrayAdapter ;
    @BindView(R.id.rvMovies) RecyclerView rvItems;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    static int page = 1;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save custom values into the bundle
        savedInstanceState.putParcelableArrayList("movie_list", movies);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        movies = savedInstanceState.getParcelableArrayList("movie_list");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        bindDataToAdapter();
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        getMoviesAsync(client, url);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++;
                fetchNewMovies(page);
                swipeContainer.setRefreshing(false);

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        ItemClickSupport.addTo(rvItems).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Movie movie = movies.get(position);
                        if(Float.parseFloat(movie.getPopularity()) < 5.0) {
                            Intent i = new Intent(MovieActivity.this, MovieDetailsActivity.class);
                            // put "extras" into the bundle for access in the second activity

                            i.putExtra("movie", Parcels.wrap(movie));
                            // brings up the second activity
                            startActivity(i);
                        } else {
                            Intent i = new Intent(MovieActivity.this, PopularMovieDetailsActivity.class);
                            // put "extras" into the bundle for access in the second activity

                            i.putExtra("movie", Parcels.wrap(movie));
                            // brings up the second activity
                            startActivity(i);

                        }
                    }
                }
        );
    }
    private void bindDataToAdapter() {
        // Bind adapter to recycler view object
        movieArrayAdapter = new MovieArrayAdapter(this,movies );
        rvItems.setAdapter(movieArrayAdapter);
    }

    public void fetchNewMovies(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/now_playing?page="+page+"&&api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        getMoviesAsync(client, url);
    }

    private void getMoviesAsync(AsyncHttpClient client, String url) {
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJSONResults = null;
                try {
                    movieArrayAdapter.clear();
                    movieJSONResults = response.getJSONArray("results");
                    ArrayList<Movie> newMovies = Movie.fromJSONArray(movieJSONResults);
                    movieArrayAdapter.addAll(newMovies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

}
