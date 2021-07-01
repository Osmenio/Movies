package com.fortesfilmes.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fortesfilmes.R;

import java.util.Date;

public class MovieHolder extends RecyclerView.ViewHolder {

//    public TextView title;
//    public TextView description;
//    public TextView releaseDate;
//    public TextView genre;
//    public ImageView cover;

    public FrameLayout frame;
    public TextView title;
    public TextView year;
    //    public TextView rated;
//    public TextView released;
//    public TextView runtime;
//    public TextView genre;
//    public TextView director;
//    public TextView writer;
//    public TextView actors;
//    public TextView plot;
    public ImageView poster;
    public RatingBar rbRating;

    public MovieHolder(@NonNull View itemView) {
        super(itemView);
        frame = (FrameLayout) itemView.findViewById(R.id.fl_item_list_movie);
        title = (TextView) itemView.findViewById(R.id.tv_title);
        year = (TextView) itemView.findViewById(R.id.tv_date);
        poster = (ImageView) itemView.findViewById(R.id.iv_cover);
        rbRating = (RatingBar) itemView.findViewById(R.id.rb_rating);
    }
}
