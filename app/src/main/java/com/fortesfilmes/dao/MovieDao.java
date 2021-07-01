package com.fortesfilmes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fortesfilmes.model.MovieModel;

import java.sql.SQLException;
import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie_model")
    List<MovieModel> findAll();

    @Query("SELECT * FROM movie_model WHERE title = :title")
    MovieModel findByTitle(String title);

    @Query("SELECT * FROM movie_model WHERE title LIKE '%' || :title || '%'")
    List<MovieModel> searchByTitle(String title);

    @Query("SELECT * FROM movie_model WHERE favorite = 1")// true = 1, false = 0
    List<MovieModel> findAllFavorite();

//    @Query("SELECT * FROM movie_model WHERE favorite = 1")
//    List<MovieModel> findAllFavorite();

    @Insert
    void persist(MovieModel movie) throws Exception;

    @Update
    void update(MovieModel movie);

    @Delete
    void delete(MovieModel movie);
}
