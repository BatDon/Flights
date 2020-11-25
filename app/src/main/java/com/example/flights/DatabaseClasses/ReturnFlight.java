package com.example.flights.DatabaseClasses;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ReturnFlight {

    String destinationAirport;
    String returnDate;

    public ReturnFlight(String destinationAirport, String returnDate) {
        this.destinationAirport = destinationAirport;
        this.returnDate = returnDate;
    }

    public ReturnFlight() {
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("destinationAirport", destinationAirport);
        result.put("returnDate", returnDate);

        return result;
    }
}
