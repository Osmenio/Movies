package com.fortesfilmes.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(tableName = "movie_model")
public class MovieModel {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String title;

    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String poster;
    private boolean favorite;
    private float rating;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    //
    public static Comparator<MovieModel> titleAsc = new Comparator<MovieModel>() {
        public int compare(MovieModel movie1, MovieModel movie2) {
            return movie1.getTitle().compareTo(movie2.getTitle());
        }
    };
    //
    public static Comparator<MovieModel> titleDesc = new Comparator<MovieModel>() {
        public int compare(MovieModel movie1, MovieModel movie2) {
            return movie2.getTitle().compareTo(movie1.getTitle());
        }
    };
    //
    public static Comparator<MovieModel> ratingDesc = new Comparator<MovieModel>() {
        public int compare(MovieModel movie1, MovieModel movie2) {
            return Float.compare(movie2.getRating(), movie1.getRating());
        }
    };
    //
    public static Comparator<MovieModel> ratingAsc = new Comparator<MovieModel>() {
        public int compare(MovieModel movie1, MovieModel movie2) {
            return Float.compare(movie1.getRating(), movie2.getRating());
        }
    };
}
