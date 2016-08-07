package sdass.flixter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdass on 8/5/16.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_movieImage) public ImageView image;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.tv_overview) public TextView overview;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getOverview() {
        return overview;
    }

    public void setOverview(TextView overview) {
        this.overview = overview;
    }


}