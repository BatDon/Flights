package com.example.flights.Pojos.FlightDatePojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//http://www.jsonschema2pojo.org/
public class Carrier implements Serializable {

    @SerializedName("CarrierId")
    @Expose
    private Integer carrierId;
    @SerializedName("Name")
    @Expose
    private String name;

    public Carrier(Integer carrierId, String name) {
        this.carrierId = carrierId;
        this.name = name;
    }

    public Carrier() {
    }

    public Integer getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Integer carrierId) {
        this.carrierId = carrierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
