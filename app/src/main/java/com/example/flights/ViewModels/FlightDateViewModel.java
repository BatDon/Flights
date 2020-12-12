package com.example.flights.ViewModels;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.flights.Pojos.FlightDatePojos.Carrier;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import timber.log.Timber;


public class FlightDateViewModel extends AndroidViewModel implements RetrofitRequesterQuote.onRetrofitListener {

    Context context;
    public static final String TAG=FlightDateViewModel.class.getSimpleName();

//    List<Quote> quoteList;
//    public MutableLiveData<List<Quote>> liveDataQuoteList=new MutableLiveData<List<Quote>>(){};
    public MutableLiveData<ArrayList<Quote>> liveDataQuoteArrayList=new MutableLiveData<ArrayList<Quote>>(){};

//    List<Currency> currencyList;
//    List<Carrier> carrierList;


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
    ArrayList<Carrier> carrierArrayList;
    public ArrayList<Currency> setCurrencyAL;

    int position;
    int itemPosition;

    int createViewModel=0;

    public FlightDateViewModel(@NonNull Application application) {
        super(application);
        Timber.i("FlightDateViewModel constructor called");
        context=application;
//        this.movieId=movieId;

//        Place place=new Place("1","Stockholm","2","3","4","UK");
//        if(createViewModel==0) {
            Quote quote = new Quote();
            ArrayList<Quote> quoteList = new ArrayList<Quote>();
            quoteList.add(quote);
            liveDataQuoteArrayList.setValue(quoteList);
//        }
        this.position=position;
        createViewModel++;
    }

//    public void requestRelatedMovies(){new RetrofitRequesterRelated().requestMovies(this)}

    public MutableLiveData<ArrayList<Quote>> getAllQuotes(){
        return this.liveDataQuoteArrayList;
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

    public void writeToCarrierFile(ArrayList<Carrier> carrierList) {

        try {
            FileOutputStream fileOut = new FileOutputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileNameCarrier+".txt"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(carrierList);
            objectOut.close();
            fileOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Quote> getQuoteDir() {
//        ArrayList<Quote> quoteArrayList;

        try {

//            int fileNumber=Integer.parseInt(file_location);
//            file_location=Integer.toString(fileNumber);
//            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathToRelatedMoviesFile)));
            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileNameQuote+".txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.quoteArrayList = (ArrayList) ois.readObject();



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

        return this.quoteArrayList;
    }

    public ArrayList<Currency> getCurrencyDir() {
//        ArrayList<Currency> currencyArrayList;

        try {

//            int fileNumber=Integer.parseInt(file_location);
//            file_location=Integer.toString(fileNumber);
//            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathToRelatedMoviesFile)));
            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileNameCurrency+".txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.currencyArrayList = (ArrayList) ois.readObject();



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

        return this.currencyArrayList;
    }

    public ArrayList<Carrier> getCarrierDir() {
//        List list;
//        ArrayList arrayList ;

        try {

//            int fileNumber=Integer.parseInt(file_location);
//            file_location=Integer.toString(fileNumber);
//            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathToRelatedMoviesFile)));
            FileInputStream fis = new FileInputStream(new File(context.getString(R.string.pathWithoutFileName)+R.string.fileNameCarrier+".txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.carrierArrayList = (ArrayList<Carrier>) ois.readObject();
            //list=convertObjectToList(ois.readObject());
//            arrayList = new ArrayList(list);

//            Timber.i("getCarrierDir carrierArrayList "+carrierArrayList.size())

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
           Timber.i("Class not found");
            c.printStackTrace();
            return null;
        }

        return this.carrierArrayList;
//        return arrayList;
    }

//    public static List<?> convertObjectToList(Object obj) {
//        List<?> list = new ArrayList<>();
//        if (obj.getClass().isArray()) {
//            list = Arrays.asList((Object[])obj);
//        } else if (obj instanceof Collection) {
//            list = new ArrayList<>((Collection<?>)obj);
//        }
//        return list;
//    }

//    public static ArrayList<Carrier> convertObjectToList(Object obj) {
//        ArrayList<Carrier> list = new ArrayList<Carrier>();
//        if (obj.getClass().isArray()) {
//            list = Arrays.asList((Object[])obj);
//        } else if (obj instanceof Collection) {
//            list = new ArrayList<>((Collection<?>)obj);
//        }
//        return list;
//    }


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

    public ArrayList<Currency> getCurrencyArrayList(){
        return this.currencyArrayList;
    }

    public ArrayList<Carrier> getCarrierArrayList(){return this.carrierArrayList;}

    public Currency getCurrency(){
        if(this.currencyArrayList.size()==1){
            return currencyArrayList.get(0);
        }
        return null;
    }

//    public void onRetrofitQuoteFinished(List<Object> quoteCurrencyList) {
//          if(quoteCurrencyList==null){
//            Timber.i("quoteList equals null");
//            transformToLiveData(null);
//            return;
//        }

    public void onRetrofitQuoteFinished(ArrayList<Quote> quoteArrayList, ArrayList<Currency> currencyArrayList, ArrayList<Carrier> carrierArrayList) {

        this.quoteArrayList=quoteArrayList;
        this.currencyArrayList=currencyArrayList;
        this.carrierArrayList=carrierArrayList;

        if (this.quoteArrayList == null || this.quoteArrayList.isEmpty()) {
            Timber.i("onRetrofitFinished quoteArrayList equals null or is empty");
            Toast.makeText(context, "Flight does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        if(this.currencyArrayList==null || this.currencyArrayList.isEmpty()){
            Timber.i("onRetrofitFinished currencyArrayList equals null or is empty");
            Toast.makeText(context, "Flight does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        if(this.carrierArrayList==null || this.currencyArrayList.isEmpty()){
            Timber.i("onRetrofitFinished carrierArrayList equals null or is empty");
            Toast.makeText(context, "Flight does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        Timber.i("currencyArrayList size= "+this.currencyArrayList.size());

        if(this.carrierArrayList!=null && carrierArrayList.size()>0){
            Carrier carrier=carrierArrayList.get(0);
            String carrierName=carrier.getName();
            Timber.i("carrierName= "+carrierName);
        }

        //currencyArrayList.add((Currency)quoteCurrencyList.get(1));
        Timber.i("FlightDateViewModel onRetrofitFinished called");
        Timber.i("quoteList size= %s", quoteArrayList.size());
//        this.quoteList=quoteArrayList;

//        if(this.quoteList==null){
//            Timber.i("quote list is empty");
//            return;
//        }
//
//        if(carrierArrayList==null){
//            Timber.i("carrier list does not exist");
//            return;
//        }
//        if(carrierArrayList.isEmpty()){
//            Timber.i("carrier list is empty");
//            return;
//        }
//        currencyList=currencyArrayList;
//        carrierList=carrierArrayList;
//
//        this.carrierArrayList=carrierArrayList;

        //writeToFile(placeArrayList);
        transformToLiveData(quoteArrayList);
    }

    private void transformToLiveData(ArrayList<Quote> quoteArrayList){
        this.liveDataQuoteArrayList.postValue(quoteArrayList);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setItemPosition(int itemPosition){
        this.itemPosition=itemPosition;
    }

    public int getItemPosition(){
        return this.itemPosition;
    }



}