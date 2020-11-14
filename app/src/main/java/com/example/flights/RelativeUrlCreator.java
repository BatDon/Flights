package com.example.flights;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;

import retrofit2.http.GET;
import timber.log.Timber;

public class RelativeUrlCreator {


    public String createURI(String country, String currency, String locale, String placeName){

        Uri.Builder uriBuilder=new Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("skyscanner-skyscanner-flight-search-v1.p.rapidapi.com");
        uriBuilder.appendPath("apiservices");
        uriBuilder.appendPath("autosuggest");
        uriBuilder.appendPath("v1.0");
        uriBuilder.appendPath(country);
        uriBuilder.appendPath(currency);
        uriBuilder.appendPath(locale);
        uriBuilder.appendQueryParameter("query",placeName);
        String uriString=uriBuilder.toString();
        Log.i("RelativeUrlCreator",uriString);
        return uriString;
//        URIBuilder uriBuilder=URIBuilder.getInstance(Constants.BASE_URL);
//
//        URI uri = UriBuilder.fromUri("http://{country}:{currency}/{locale}")
//                .path("123")
//                .queryParam("query", placeName)
//                .build(host, port);
    }

//    https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/UK/GBP/en-GB/?query=Colombia






//    URL url;
//
//    {
//        try {
//            url = new URL(new URL(Constants.BASE_URL),"/apiservices/autosuggest/v1.0/");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    addCountry(String countryName){
//        String file = "/countryName";
//        url.
//    }
//
//    @GET("/apiservices/autosuggest/v1.0/UK/GBP/en-GB/")
//
//    URLBuilder urlb = new URLBuilder("www.example.com");
//        urlb.setConnectionType("http");
//        urlb.addSubfolder("somesub");
//        urlb.addSubfolder("anothersub");
//        urlb.addParameter("param lol", "unknown");
}
