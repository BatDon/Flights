package com.example.flights.Pojos.FlightDatePojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//http://www.jsonschema2pojo.org/
//public class Place {
public class PlaceFlightDate implements Serializable {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("PlaceId")
    @Expose
    private Integer placeId;
    @SerializedName("IataCode")
    @Expose
    private String iataCode;
    @SerializedName("SkyscannerCode")
    @Expose
    private String skyscannerCode;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("CityId")
    @Expose
    private String cityId;
    @SerializedName("CountryName")
    @Expose
    private String countryName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getSkyscannerCode() {
        return skyscannerCode;
    }

    public void setSkyscannerCode(String skyscannerCode) {
        this.skyscannerCode = skyscannerCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
