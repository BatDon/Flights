package com.example.flights.Pojos.FlightDatePojos;

import com.example.flights.Pojos.Place;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Quotes {

    @SerializedName("Quotes")
    @Expose
    private List<Quote> quotes = null;

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setPlaces(List<Quote> quotes) {
        this.quotes = quotes;
    }

}
