package com.example.flights.Pojos.FlightDatePojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//http://www.jsonschema2pojo.org/
public class Quote {

    @SerializedName("QuoteId")
    @Expose
    private Integer quoteId;
    @SerializedName("MinPrice")
    @Expose
    private Integer minPrice;
    @SerializedName("Direct")
    @Expose
    private Boolean direct;
    @SerializedName("OutboundLeg")
    @Expose
    private OutboundLeg outboundLeg;
    @SerializedName("InboundLeg")
    @Expose
    private InboundLeg inboundLeg;
    @SerializedName("QuoteDateTime")
    @Expose
    private String quoteDateTime;

    public Quote() {
    }

    public Quote(Integer quoteId, Integer minPrice, Boolean direct, OutboundLeg outboundLeg, InboundLeg inboundLeg, String quoteDateTime) {
        this.quoteId = quoteId;
        this.minPrice = minPrice;
        this.direct = direct;
        this.outboundLeg = outboundLeg;
        this.inboundLeg = inboundLeg;
        this.quoteDateTime = quoteDateTime;
    }

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Boolean getDirect() {
        return direct;
    }

    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    public OutboundLeg getOutboundLeg() {
        return outboundLeg;
    }

    public void setOutboundLeg(OutboundLeg outboundLeg) {
        this.outboundLeg = outboundLeg;
    }

    public InboundLeg getInboundLeg() {
        return inboundLeg;
    }

    public void setInboundLeg(InboundLeg inboundLeg) {
        this.inboundLeg = inboundLeg;
    }

    public String getQuoteDateTime() {
        return quoteDateTime;
    }

    public void setQuoteDateTime(String quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
    }

}
