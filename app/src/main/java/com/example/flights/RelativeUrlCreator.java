package com.example.flights;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.http.GET;

public class RelativeUrlCreator {

    URL url;

    {
        try {
            url = new URL(new URL(Constants.BASE_URL),"/apiservices/autosuggest/v1.0/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    addCountry(String countryName){
        url.

    }

    @GET("/apiservices/autosuggest/v1.0/UK/GBP/en-GB/")

    URLBuilder urlb = new URLBuilder("www.example.com");
        urlb.setConnectionType("http");
        urlb.addSubfolder("somesub");
        urlb.addSubfolder("anothersub");
        urlb.addParameter("param lol", "unknown");
}
