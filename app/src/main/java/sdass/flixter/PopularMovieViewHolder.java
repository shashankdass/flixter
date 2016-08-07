package sdass.flixter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdass on 8/5/16.
 */
public  class PopularMovieViewHolder extends RecyclerView.ViewHolder{
    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    @BindView(R.id.iv_movieImagePopular) public ImageView image;

    public PopularMovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
