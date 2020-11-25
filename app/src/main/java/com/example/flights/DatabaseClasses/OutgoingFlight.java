package com.example.flights.DatabaseClasses;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class OutgoingFlight {

    //currency, price, quoteDateTime
    String originPlace;
    String currency;
    String price;
    String outgoingDate;

    public OutgoingFlight() {
    }

    public OutgoingFlight(String originPlace, String currency, String price, String outgoingDate) {
        this.originPlace=originPlace;
        this.currency = currency;
        this.price = price;
        this.outgoingDate = outgoingDate;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("originPlace", originPlace);
        result.put("currency", currency);
        result.put("price", price);
        result.put("outgoingDate", outgoingDate);

        return result;
    }
}
