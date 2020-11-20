package com.example.flights.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.flights.Pojos.FlightDatePojos.Currency;
import com.example.flights.Pojos.FlightDatePojos.InboundLeg;
import com.example.flights.Pojos.FlightDatePojos.OutboundLeg;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.Pojos.Place;
import com.example.flights.R;
import com.example.flights.Retrofit.RetrofitRequester;
import com.example.flights.Retrofit.RetrofitRequesterQuote;
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

import timber.log.Timber;


public class FlightDateViewModel extends AndroidViewModel implements RetrofitRequesterQuote.onRetrofitListener {

    Context context;
    public static final String TAG=FlightDateViewModel.class.getSimpleName();

    List<Quote> quoteList;
    public MutableLiveData<List<Quote>> liveDataQuoteList=new MutableLiveData<List<Quote>>(){};

    List<Currency> currencyList;
//    Quote quote;
//    String minPrice;
//    String direct;
//    OutboundLeg outboundLeg;
//    InboundLeg inboundLeg;
//    String quoteDateTime;

    Quote quote;
    Integer quoteId;
    Integer minPrice;
    Boolean direct;
    OutboundLeg outboundLeg;
    InboundLeg inboundLeg;
    String quoteDateTime;

    Currency currency;


    ArrayList<Quote> quoteArrayList;
    ArrayList<Currency> currencyArrayList;
    public ArrayList<Currency> setCurrencyAL;

    int position;

    public FlightDateViewModel(@NonNull Application application) {
        super(application);
        context=application;
//        this.movieId=movieId;

//        Place place=new Place("1","Stockholm","2","3","4","UK");
        Quote quote=new Quote();
        List<Quote> quoteList=new ArrayList<Quote>();
        quoteList.add(quote);
        liveDataQuoteList.setValue(quoteList);
        this.position=position;
    }

//    public void requestRelatedMovies(){new RetrofitRequesterRelated().requestMovies(this)}

    public MutableLiveData<List<Quote>> getAllQuotes(){
        return this.liveDataQuoteList;
    }

    public void requestFlightQuotes(){
        new RetrofitRequesterQuote(getApplication()).requestQuotes(FlightDateViewModel.this);
    }

    public void writeToFile(ArrayList<Quote> quoteList) {

        try {
            FileOutputStream fileOut = new FileOutputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileNameQuote+".txt"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(quoteList);
            objectOut.close();
            fileOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeToCurrencyFile(ArrayList<Currency> currencyList) {

        try {
            FileOutputStream fileOut = new FileOutputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileNameCurrency+".txt"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(currencyList);
            objectOut.close();
            fileOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Quote> getQuoteDir() {
        ArrayList<Quote> quoteArrayList;

        try {

//            int fileNumber=Integer.parseInt(file_location);
//            file_location=Integer.toString(fileNumber);
//            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathToRelatedMoviesFile)));
            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileNameQuote+".txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            quoteArrayList = (ArrayList) ois.readObject();



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

////        setValuesForFavorites(placeArrayList);
//        ArrayList<Quote> allQuotes=new ArrayList<>();
//
//        for (int i = 0; i < quoteArrayList.size(); i++) {
//            Timber.i("setValuesForFavories i= "+i);
//            setValuesForFavorites(quoteArrayList, i);
//            Quote quote=getQuoteDetails();
//            allQuotes.add(quote);
//        }
//        return allQuotes;
        return quoteArrayList;
    }

    public ArrayList<Currency> getCurrencyDir() {
        ArrayList<Currency> currencyArrayList;

        try {

//            int fileNumber=Integer.parseInt(file_location);
//            file_location=Integer.toString(fileNumber);
//            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathToRelatedMoviesFile)));
            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileNameCurrency+".txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            currencyArrayList = (ArrayList) ois.readObject();



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

////        setValuesForFavorites(placeArrayList);
//        ArrayList<Quote> allQuotes=new ArrayList<>();
//
//        for (int i = 0; i < quoteArrayList.size(); i++) {
//            Timber.i("setValuesForFavories i= "+i);
//            setValuesForFavorites(quoteArrayList, i);
//            Quote quote=getQuoteDetails();
//            allQuotes.add(quote);
//        }
//        return allQuotes;
        return currencyArrayList;
    }


    private void setValuesForFavorites(ArrayList<Quote> quoteList, int position) {

        int quoteId;
        int minPrice;
        boolean direct;
        OutboundLeg outboundLeg;
        InboundLeg inboundLeg;
        String quoteDateTime;

        quote = quoteList.get(position);

        quoteId=quote.getQuoteId();
        minPrice = quote.getMinPrice();
        direct = quote.getDirect();
        outboundLeg = quote.getOutboundLeg();
        inboundLeg= quote.getInboundLeg();
        quoteDateTime=quote.getQuoteDateTime();

    }


    public Quote getQuoteDetails(){
        Quote quote=new Quote(quoteId, minPrice, direct, outboundLeg, inboundLeg, quoteDateTime);
        return quote;
    }

    public ArrayList<Quote> getQuoteArrayList(){
        return quoteArrayList;
    }

//    public ArrayList<Currency> getCurrencyArrayList(){
//        return this.currencyArrayList;
//    }

    public List<Currency> getCurrencyList(){
        return this.currencyList;
    }

    public Currency getCurrency(){
        if(this.currencyList.size()==1){
            return currencyList.get(0);
        }
        return null;
    }

//    public void onRetrofitQuoteFinished(List<Object> quoteCurrencyList) {
//          if(quoteCurrencyList==null){
//            Timber.i("quoteList equals null");
//            transformToLiveData(null);
//            return;
//        }

    public void onRetrofitQuoteFinished(ArrayList<Quote> quoteArrayList, ArrayList<Currency> currencyArrayList) {
//        if(quoteCurrencyList==null){
//            Timber.i("quoteList equals null");
//            transformToLiveData(null);
//            return;
//        }
//        ArrayList<Quote> quoteArrayList = new ArrayList<Quote>(quoteList);
//        ArrayList<Quote> quoteArrayList = new ArrayList<Quote>();
//        ArrayList<Object> objectquoteArrayList= quoteCurrencyList.get(0);
//        for(Object object:objectquoteArrayList)
       //quoteArrayList.add((Quote)quoteCurrencyList.get(0));
//        currencyArrayList = new ArrayList<Currency>();
        this.currencyArrayList=currencyArrayList;
        if(this.currencyArrayList==null){
            Timber.i("onRetrofitFinished currencyArrayList equals null");
        }
        if(this.currencyArrayList.isEmpty()){
            Timber.i("onRetrofitFinished currencyArrayList is empty");
        }
        Timber.i("currencyArrayList size= "+this.currencyArrayList.size());
        Currency currency1=this.currencyArrayList.get(0);
        String symbol=currency1.getSymbol();
        Timber.i("symbol equals "+symbol);
        setCurrencyAL=this.currencyArrayList;
        //currencyArrayList.add((Currency)quoteCurrencyList.get(1));
        Timber.i("FlightDateViewModel onRetrofitFinished called");
        Timber.i("quoteList size= %s", quoteArrayList.size());
        this.quoteList=quoteArrayList;
        currencyList=currencyArrayList;

        //writeToFile(placeArrayList);
        transformToLiveData(quoteList);
    }

    private void transformToLiveData(List<Quote> quoteList){
        this.liveDataQuoteList.postValue(quoteList);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }



}