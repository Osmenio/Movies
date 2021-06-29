package com.fortesfilmes.service;

import com.fortesfilmes.model.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApiInterface {

//    @Headers("X-Mashape-Key: AuuyclCPjcmshv2iOPq190OpzLrMp1FJWwejsnJrdfwOUr4h44")

//    @FormUrlEncoded
//    @GET("saga")
//    Call<MovieModel> findAll(@Field("from-type") String from_type,
//                                      @Field("from-value") String from_value,
//                                      @Field("to-type") String to_type) ;

    @GET("saga")
    Call<List<MovieModel>> getMovies();
//    {
//        return null;
//    }
}
