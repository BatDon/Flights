package com.example.flights.ViewModels;

import android.app.Application;
import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.flights.Activities.MainActivity;
import com.example.flights.Pojos.Place;
import com.example.flights.R;
import com.example.flights.Retrofit.RetrofitRequester;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class MainActivityViewModel extends AndroidViewModel implements RetrofitRequester.onRetrofitListener {

    Context context;
    public static final String TAG=MainActivityViewModel.class.getSimpleName();

    List<Place> placeList;
    public MutableLiveData<List<Place>> liveDataPlacetList=new MutableLiveData<List<Place>>(){};
    Place place;
    String placeId;
    String placeName;
    String countryId;
    String regiondId;
    String cityId;
    String countryName;
    ArrayList<Place> placeArrayList;

    int position;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context=application;
//        this.movieId=movieId;

//        Place place=new Place("1","Stockholm","2","3","4","UK");
        Place place=new Place();
        List<Place> placeList=new ArrayList<Place>();
        placeList.add(place);
        liveDataPlacetList.setValue(placeList);
    }

//    public void requestRelatedMovies(){new RetrofitRequesterRelated().requestMovies(this)}

    public MutableLiveData<List<Place>> getAllPlaces(){
        return this.liveDataPlacetList;
    }

    public void requestFlightDestinations(){
        new RetrofitRequester(getApplication()).requestPlaces(MainActivityViewModel.this);
    }

    public void writeToFile(ArrayList<Place> placeList) {

        try {
            FileOutputStream fileOut = new FileOutputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileName+".txt"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(placeList);
            objectOut.close();
            fileOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Place> getPlaceDir() {
//        ArrayList<Place> placeArrayList;

        try {

//            int fileNumber=Integer.parseInt(file_location);
//            file_location=Integer.toString(fileNumber);
//            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathToRelatedMoviesFile)));
            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileName+".txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            placeArrayList = (ArrayList) ois.readObject();



            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println(context.getString(R.string.class_not_found));
            c.printStackTrace();
            return null;
        }

//        setValuesForFavorites(placeArrayList);
        ArrayList<Place> allPlaces=new ArrayList<>();

        for (int i = 0; i < placeArrayList.size(); i++) {
            Timber.i("setValuesForFavories i= "+i);
            setValuesForFavorites(placeArrayList, i);
            Place place=getPlaceDetails();
            allPlaces.add(place);
        }
        return allPlaces;
    }


    private void setValuesForFavorites(ArrayList<Place> placeList, int position) {
        place = placeList.get(position);
        placeId = place.getPlaceId();
        placeName = place.getPlaceName();
        countryId = place.getCountryId();
        regiondId = place.getRegionId();
        cityId = place.getCityId();
        countryName = place.getCountryName();
    }


    public Place getPlaceDetails(){
        Place place=new Place(placeId, placeName, countryId, regiondId, cityId, countryName);
        return place;
    }

    public void onRetrofitFinished(List<Place> placeList) {
        if(placeList==null){
            return;
        }
        Timber.i("MainActivityViewModel onRetrofitFinished called");
        Timber.i("placeList size= %s", placeList.size());
        this.placeList=placeList;
        ArrayList<Place> placeArrayList = new ArrayList<Place>(placeList);
        writeToFile(placeArrayList);
        transformToLiveData(placeList);
    }

    private void transformToLiveData(List<Place> placeList){
        this.liveDataPlacetList.postValue(placeList);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }



}
