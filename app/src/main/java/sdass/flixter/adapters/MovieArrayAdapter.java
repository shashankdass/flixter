package sdass.flixter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import sdass.flixter.MovieViewHolder;
import sdass.flixter.PopularMovieViewHolder;
import sdass.flixter.R;
import sdass.flixter.models.Movie;

/**
 * Created by sdass on 8/1/16.
 */
public class MovieArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Movie> mMovies;

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        mMovies = movies;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Movie> list) {
        int curSize = getItemCount();
        mMovies.addAll(list);
        notifyItemRangeInserted(curSize, list.size());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.item_movie, viewGroup, false);
                viewHolder = new MovieViewHolder(v1);
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.popular_movie_item, viewGroup, false);
                viewHolder = new PopularMovieViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                MovieViewHolder vh1 = (MovieViewHolder) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case 1:
                PopularMovieViewHolder vh2 = (PopularMovieViewHolder) viewHolder;
                configureViewHolder2(vh2, position);
                break;

        }
    }
    private void configureViewHolder1(MovieViewHolder vh1, int position) {
        Movie movie = (Movie) mMovies.get(position);
        if (movie != null) {
            vh1.getTitle().setText( movie.getOriginalTitle());
            vh1.getOverview().setText(movie.getOverview());
            vh1.getImage().setImageResource(0);
            Picasso.with(getContext()).load(movie.getPosterPath()).
                    placeholder(R.drawable.vid_placeholder).error(R.drawable.bomby).
                    transform(new RoundedCornersTransformation(10, 10)).into(vh1.getImage());

        }
    }

    private void configureViewHolder2(PopularMovieViewHolder vh2, int position) {
        Movie movie = (Movie) mMovies.get(position);
        vh2.getImage().setImageResource(0);
        Picasso.with(getContext()).load(movie.getBackdropPath())
                .placeholder(R.drawable.vid_placeholder)
                .error(R.drawable.bomby).resize(2000,1000)
                .transform(new RoundedCornersTransformation(10, 10)).into(vh2.getImage());

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = mMovies.get(position);
        if(Float.parseFloat(movie.getPopularity()) <= 5.0){
            return 0;
        } else {
            return 1;
        }
    }
}
