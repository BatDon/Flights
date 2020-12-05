package com.example.flights.Retrofit;

import com.example.flights.Constants;
import com.example.flights.RelativeUrlCreator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;


public class RetrofitClient {

    private static Retrofit retrofit;

    private static OkHttpClient loggingInterceptor=createLoggingInteceptor();

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        GsonConverterFactory gsonConverterFactory=GsonConverterFactory.create(gson);

        if (retrofit == null) {
            Timber.i("retrofit instance equals null");
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
//                    .baseUrl(new RelativeUrlCreator().createURI("Colombia","COP","es-CO","Bogota"))
                    .addConverterFactory(gsonConverterFactory)
                    .client(loggingInterceptor)
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient createLoggingInteceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return client;
    };
}


