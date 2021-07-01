package com.fortesfilmes.service;

import com.fortesfilmes.model.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApiInterface {

    @GET("saga")
    Call<List<MovieModel>> getMovies();
}
