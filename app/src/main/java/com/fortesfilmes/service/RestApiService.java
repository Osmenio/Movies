package com.fortesfilmes.service;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiService {

    public static final String API_BASE_URL = "https://private-b34167-rvmarvel.apiary-mock.com/";

    public static <S> S createService(Class<S> serviceInterface, String apiBaseUrl) {

        //
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS);

        httpClient.addInterceptor(loggingInterceptor);

        //
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
//                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceInterface);
    }
}
