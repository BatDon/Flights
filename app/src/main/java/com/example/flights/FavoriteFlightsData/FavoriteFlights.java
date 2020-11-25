package com.example.flights.FavoriteFlightsData;

import androidx.room.PrimaryKey;

public class FavoriteFlights {

//    @PrimaryKey(autoGenerate = true)
//    private int id;
    String id;

    String originPlace;
    String currency;
    String price;
    String outgoingDate;

    String destinationAirportName;
    String returnDate;

    public FavoriteFlights() {
    }

    public FavoriteFlights(String id, String originPlace, String currency, String price, String outgoingDate, String destinationAirportName, String returnDate) {
        this.id=id;
        this.originPlace = originPlace;
        this.currency = currency;
        this.price = price;
        this.outgoingDate = outgoingDate;
        this.destinationAirportName = destinationAirportName;
        this.returnDate = returnDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOutgoingDate() {
        return outgoingDate;
    }

    public void setOutgoingDate(String outgoingDate) {
        this.outgoingDate = outgoingDate;
    }

    public String getDestinationAirportName() {
        return destinationAirportName;
    }

    public void setDestinationAirportName(String destinationAirportName) {
        this.destinationAirportName = destinationAirportName;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
