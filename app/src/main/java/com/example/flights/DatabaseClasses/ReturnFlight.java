package com.example.flights.DatabaseClasses;

public class ReturnFlight {

    String currency;
    String price;
    String quoteDateTime;

    public ReturnFlight(String currency, String price, String quoteDateTime) {
        this.currency = currency;
        this.price = price;
        this.quoteDateTime = quoteDateTime;
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

    public String getQuoteDateTime() {
        return quoteDateTime;
    }

    public void setQuoteDateTime(String quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
    }
}
