package com.example.flights.Pojos.FlightDatePojos;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//http://www.jsonschema2pojo.org/
public class Dates implements Serializable {

    @SerializedName("OutboundDates")
    @Expose
    private List<OutboundDate> outboundDates = null;

    @SerializedName("InboundDates")
    @Expose
    private List<InboundDate> inboundDates = null;

    public List<OutboundDate> getOutboundDates() {
        return outboundDates;
    }

    public void setOutboundDates(List<OutboundDate> outboundDates) {
        this.outboundDates = outboundDates;
    }

    public List<InboundDate> getInboundDates() {
        return inboundDates;
    }

    public void setInboundDates(List<InboundDate> inboundDates) {
        this.inboundDates = inboundDates;
    }

}
