package com.example.flights.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Place implements Parcelable, Serializable {

    @SerializedName("PlaceId")
    @Expose
    private String placeId;
    @SerializedName("PlaceName")
    @Expose
    private String placeName;
    @SerializedName("CountryId")
    @Expose
    private String countryId;
    @SerializedName("RegionId")
    @Expose
    private String regionId;
    @SerializedName("CityId")
    @Expose
    private String cityId;
    @SerializedName("CountryName")
    @Expose
    private String countryName;

//    protected Place(Parcel in) {
//        placeId = in.readString();
//        placeName = in.readString();
//        countryId = in.readString();
//        regionId = in.readString();
//        cityId = in.readString();
//        countryName = in.readString();
//    }


    public Place(String placeId, String placeName, String countryId, String regionId, String cityId, String countryName) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.countryId = countryId;
        this.regionId = regionId;
        this.cityId = cityId;
        this.countryName = countryName;
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeId);
        dest.writeString(placeName);
        dest.writeString(countryId);
        dest.writeString(regionId);
        dest.writeString(cityId);
        dest.writeString(countryName);
    }

    public Place() {
    }

    protected Place(Parcel parcel) {
        this.placeId = parcel.readString();
        this.placeName = parcel.readString();
        this.countryId = parcel.readString();
        this.regionId = parcel.readString();
        this.cityId = parcel.readString();
        this.countryName = parcel.readString();
    }

    //Create a parcelable object in order to avoid Room, since Room can't store lists
//    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
//        @Override
//        public Place createFromParcel(Parcel source) {
//            return new Place(source);
//        }
//
//        @Override
//        public Place[] newArray(int size) {
//            return new Place[size];
//        }
//    };
}
