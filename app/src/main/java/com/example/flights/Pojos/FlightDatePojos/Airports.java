package com.example.flights.Pojos.FlightDatePojos;

import java.io.Serializable;
import java.util.List;

import com.example.flights.Pojos.Place;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//http://www.jsonschema2pojo.org/

public class Airports implements Serializable {

    @SerializedName("Quotes")
    @Expose
    private List<Quote> quotes = null;
    @SerializedName("Carriers")
    @Expose
    private List<Carrier> carriers = null;
    @SerializedName("Places")
    @Expose
    private List<Place> places = null;
    @SerializedName("Currencies")
    @Expose
    private List<Currency> currencies = null;
    @SerializedName("Dates")
    @Expose
    private Dates dates;

    public Airports(List<Quote> quotes, List<Carrier> carriers, List<Place> places, List<Currency> currencies, Dates dates) {
        this.quotes = quotes;
        this.carriers = carriers;
        this.places = places;
        this.currencies = currencies;
        this.dates = dates;
    }

    public Airports() {
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public List<Carrier> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

}
