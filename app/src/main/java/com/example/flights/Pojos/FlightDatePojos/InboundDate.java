package com.example.flights.Pojos.FlightDatePojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
//http://www.jsonschema2pojo.org/
public class InboundDate {

    @SerializedName("PartialDate")
    @Expose
    private String partialDate;
    @SerializedName("Price")
    @Expose
    private Integer price;
    @SerializedName("QuoteDateTime")
    @Expose
    private String quoteDateTime;
    @SerializedName("QuoteIds")
    @Expose
    private List<Integer> quoteIds = null;

    public String getPartialDate() {
        return partialDate;
    }

    public void setPartialDate(String partialDate) {
        this.partialDate = partialDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getQuoteDateTime() {
        return quoteDateTime;
    }

    public void setQuoteDateTime(String quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
    }

    public List<Integer> getQuoteIds() {
        return quoteIds;
    }

    public void setQuoteIds(List<Integer> quoteIds) {
        this.quoteIds = quoteIds;
    }

}
