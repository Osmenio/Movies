package com.fortesfilmes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fortesfilmes.R;
import com.fortesfilmes.interfaces.Interfaces;
import com.fortesfilmes.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private final List<MovieModel> movies;

//    private View.OnClickListener onClickListener = null;
    private Interfaces.OnClickAdapter onClickListener = null;

    public MovieAdapter(ArrayList movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        return new MovieHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_movie, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.title.setText(movies.get(position).getTitle());
        holder.year.setText(movies.get(position).getYear());
        holder.rbRating.setRating(movies.get(position).getRating());
        Picasso.get()
                .load(movies.get(position).getPoster())
                .fit().centerCrop()
                .into(holder.poster);

        //
        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickAdapterLintener(movies.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void updateList(List<MovieModel> list) {
        int size  = movies.size();
        movies.clear();
        notifyItemRangeRemoved(0, size);

        movies.addAll(list);
        notifyItemInserted(getItemCount());
    }

    public void setOnClickListener(Interfaces.OnClickAdapter onClickListener) {
        this.onClickListener = onClickListener;
//        if (onClickListener != null) {
//            holder.frame.setOnClickListener(onClickListener);
//        }
    }
}
