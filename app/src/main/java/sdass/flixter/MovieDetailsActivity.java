package sdass.flixter;

import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import sdass.flixter.models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    @BindView(R.id.rb_movie) RatingBar ratingBar;
    @BindView(R.id.iv_movieImagePopular) ImageView detailMovieImage;
    @BindView(R.id.tv_movieDetails) TextView detailsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        Float popularity = Float.parseFloat(movie.getPopularity());
        int ratings = (int) (popularity/2);
        ratingBar.setRating(ratings);
        detailsTextView.setText(movie.getOverview());
        Picasso.with(this).load(movie.getPosterPath()).placeholder(R.drawable.vid_placeholder)
                .error(R.drawable.bomby).transform(new RoundedCornersTransformation(10, 10)).into(detailMovieImage);
    }

}
