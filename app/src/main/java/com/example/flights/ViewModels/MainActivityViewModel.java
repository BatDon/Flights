package com.example.flights.ViewModels;

import android.app.Application;
import android.content.Context;
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
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel implements RetrofitRequester.onRetrofitListener {

    Context context;
    int movieId;
    public static final String TAG=MainActivityViewModel.class.getSimpleName();

    List<Place> placeList;
    public MutableLiveData<List<Place>> liveDataPlacetList=new MutableLiveData<List<Place>>(){};

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context=application;
//        this.movieId=movieId;

//        Place place=new Place("1","Stockholm","2","3","4","UK");
        Place place=new Place();
        List<Place> placetList=new ArrayList<Place>();
        placetList.add(place);
        liveDataPlacetList.setValue(placetList);
    }

//    public void requestRelatedMovies(){new RetrofitRequesterRelated().requestMovies(this)}

    public MutableLiveData<List<Place>> getAllPlaces(){
        return this.liveDataPlacetList;
    }

    public void requestFlightDestinations(){
        new RetrofitRequester(getApplication()).requestPlaces(MainActivityViewModel.this);
    }

    public void writeToFile(List<Place> placeList) {

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

    public void onRetrofitFinished(List<Place> placeList) {
        this.placeList=placeList;
        writeToFile(placeList);
        transformToLiveData(placeList);
    }

    private void transformToLiveData(List<Place> placeList){
        this.liveDataPlacetList.postValue(placeList);
    }



}
