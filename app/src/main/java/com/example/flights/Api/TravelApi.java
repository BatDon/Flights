package com.example.flights.Api;

import android.content.res.Resources;

import com.example.flights.Pojos.Places;
import com.example.flights.R;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TravelApi {

//    @GET("/UK/GBP/en-GB/?query=Stockholm")



    @GET("/apiservices/autosuggest/v1.0/UK/GBP/en-GB/")
    Call<Places> getPlaces(@Query("query") String place_name);

    @GET("/UK/GBP/en-GB")
    Call<Places> getPlaces(@Query("query") String place_name);

    //Chose url dynamically depending on user selection
//    @GET("/apiservices/autosuggest/v1.0/UK/GBP/en-GB/")
//    Call<Places> getPlaces(@Query("query") String place_name);

}
