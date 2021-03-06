package com.example.flights.Api;

import android.content.res.Resources;

import com.example.flights.Constants;
import com.example.flights.Pojos.FlightDatePojos.Airports;
import com.example.flights.Pojos.FlightDatePojos.Dates;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.Pojos.FlightDatePojos.Quotes;
import com.example.flights.Pojos.Places;
import com.example.flights.R;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TravelApi {

//TODO Header here



    //places path
    @GET("/apiservices/autosuggest/v1.0/{country}/{currency}/{locality}/")
    Call<Places> getPlaces(@HeaderMap Map<String, String> headers, @Path("country") String country, @Path ("currency") String currency,
                           @Path("locality") String locality, @Query("query") String place_name);


//    //places path
//    @GET("/apiservices/autosuggest/v1.0/{country}/{currency}/{locality}/")
//    Call<Places> getPlaces(@Path("country") String country,@Path ("currency") String currency,
//                           @Path("locality") String locality, @Query("query") String place_name);


//TODO Header here



//    @GET("/apiservices/browsedates/v1.0/{country}/{currency}/{locality}/{originPlace}/{destinationPlace}/{outboundDate}/")
//    Call<Quotes>getQuotes(@Path("country") String country, @Path ("currency") String currency,
//                          @Path("locality") String locality, @Path("originPlace") String originPlace,
//                          @Path("destinationPlace") String destinationPlace, @Path("outboundDate") String outboundDate,
//                          @Query("inboundDate") String inboundDate);
//
////TODO Header here
//
//
//    @GET("/apiservices/browsedates/v1.0/{country}/{currency}/{locality}/{originPlace}/{destinationPlace}/{outboundDate}/")
//    Call<Quotes>getQuoteCurrency(@Path("country") String country, @Path ("currency") String currency,
//                          @Path("locality") String locality, @Path("originPlace") String originPlace,
//                          @Path("destinationPlace") String destinationPlace, @Path("outboundDate") String outboundDate,
//                          @Query("inboundDate") String inboundDate);
//
////TODO Header here
//
//    @GET("/apiservices/browsedates/v1.0/{country}/{currency}/{locality}/{originPlace}/{destinationPlace}/{outboundDate}/")
//    Call<Quotes>getQuotesNoInboundDate(@Path("country") String country,@Path ("currency") String currency,
//                               @Path("locality") String locality, @Path("originPlace") String originPlace,
//                               @Path("destinationPlace") String destinationPlace, @Path("outboundDate") String outboundDate);
//
////TODO Header here
//
//    @GET("/apiservices/browsedates/v1.0/{country}/{currency}/{locality}/{originPlace}/{destinationPlace}/{outboundDate}/")
//    Call<Quotes>getQuotesNoInboundDateCurrency(@Path("country") String country,@Path ("currency") String currency,
//                                       @Path("locality") String locality, @Path("originPlace") String originPlace,
//                                       @Path("destinationPlace") String destinationPlace, @Path("outboundDate") String outboundDate);





//Airport call to get quote and currency



    @GET("/apiservices/browsedates/v1.0/{country}/{currency}/{locality}/{originPlace}/{destinationPlace}/{outboundDate}/")
    Call<Airports>getAirport( @HeaderMap Map<String, String> headers, @Path("country") String country, @Path ("currency") String currency,
                             @Path("locality") String locality, @Path("originPlace") String originPlace,
                             @Path("destinationPlace") String destinationPlace, @Path("outboundDate") String outboundDate,
                             @Query("inboundDate") String inboundDate);
//
//    @GET("/apiservices/browsedates/v1.0/{country}/{currency}/{locality}/{originPlace}/{destinationPlace}/{outboundDate}/")
//    Call<Airports>getAirport(@Path("country") String country, @Path ("currency") String currency,
//                             @Path("locality") String locality, @Path("originPlace") String originPlace,
//                             @Path("destinationPlace") String destinationPlace, @Path("outboundDate") String outboundDate,
//                             @Query("inboundDate") String inboundDate);


    @GET("/apiservices/browsedates/v1.0/{country}/{currency}/{locality}/{originPlace}/{destinationPlace}/{outboundDate}/")
    Call<Airports>getAirportNoInboundDate(@HeaderMap Map<String, String> headers, @Path("country") String country,@Path ("currency") String currency,
                                          @Path("locality") String locality, @Path("originPlace") String originPlace,
                                          @Path("destinationPlace") String destinationPlace, @Path("outboundDate") String outboundDate);

//    @GET("/apiservices/browsedates/v1.0/{country}/{currency}/{locality}/{originPlace}/{destinationPlace}/{outboundDate}/")
//    Call<Airports>getAirportNoInboundDate(@Path("country") String country,@Path ("currency") String currency,
//                                       @Path("locality") String locality, @Path("originPlace") String originPlace,
//                                       @Path("destinationPlace") String destinationPlace, @Path("outboundDate") String outboundDate);

//    @GET("users/{username}")
//    Call<User> getUser(@Path("username") String username);

//    @GET("/UK/GBP/en-GB")
//    Call<Places> getPlaces(@Query("query") String place_name);

    //Chose url dynamically depending on user selection
//    @GET("/apiservices/autosuggest/v1.0/UK/GBP/en-GB/")
//    Call<Places> getPlaces(@Query("query") String place_name);

}
