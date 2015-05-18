package com.bojie.materialtest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bojie.materialtest.R;
import com.bojie.materialtest.extras.Constants;
import com.bojie.materialtest.network.VolleySingleton;
import com.bojie.materialtest.pojo.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bojiejiang on 5/10/15.
 */
public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeAdapter.BoxOfficeViewHolder> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<Movie> mMovieArrayList = new ArrayList<>();
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private Context mContext;
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BoxOfficeAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        mContext = context;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        mMovieArrayList = movieArrayList;
        notifyItemRangeChanged(0, movieArrayList.size());
    }

    @Override
    public BoxOfficeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.custom_movie_box_office, parent, false);
        BoxOfficeViewHolder viewHolder = new BoxOfficeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BoxOfficeViewHolder holder, int position) {
        Movie currentMovie = mMovieArrayList.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());

        Date movieReleaseDate = currentMovie.getReleaseDateTheater();
        if (movieReleaseDate != null) {
            String formattedDate = mDateFormat.format(movieReleaseDate);
            holder.movieReleaseDate.setText(formattedDate);
        } else {
            holder.movieReleaseDate.setText(Constants.NA);
        }

        int audienceScore = currentMovie.getAudienceScore();
        if (audienceScore == -1) {
            holder.movieAudienceScore.setRating(0.0F);
            holder.movieAudienceScore.setAlpha(0.5F);
        } else {
            holder.movieAudienceScore.setRating(audienceScore / 20.0F);
            holder.movieAudienceScore.setAlpha(1.0F);
        }

        //
        String urlThumbnail = currentMovie.getUrlThumbnail();

        loadImages(urlThumbnail, holder);
    }

    private void loadImages(String urlThumbnail, final BoxOfficeViewHolder holder) {
        if (!urlThumbnail.equals(Constants.NA)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }

    static class BoxOfficeViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;

        public BoxOfficeViewHolder(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
        }
    }
}
