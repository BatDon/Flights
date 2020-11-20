package com.example.flights.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.flights.Adapters.FlightDateCurrencyAdapter;
import com.example.flights.Adapters.FlightDeparturesAdapter;
import com.example.flights.Constants;
import com.example.flights.Pojos.FlightDatePojos.Currency;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.R;
import com.example.flights.ViewModels.FlightDateViewModel;
import com.example.flights.ViewModels.FlightDateViewModelFactory;

import java.util.ArrayList;

import timber.log.Timber;

public class FlightDateCurrency extends AppCompatActivity implements FlightDateCurrencyAdapter.OnDateCurrencyListener{

    ArrayList<Quote> queryArrayList;
    ArrayList<Currency> currencyArrayList;
    Currency currency;

    FlightDateViewModel flightDateViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("onCreate called");
        setContentView(R.layout.activity_flight_date_currency);
        setUpFlightDateViewModel();
        //getCurrencyIntent();
        //getCurrency();
        getQuotesFromFile();
        createRecyclerView();
    }

    private void setUpFlightDateViewModel(){
        FlightDateViewModelFactory flightDateViewModelFactory=new FlightDateViewModelFactory(getApplication());
        flightDateViewModel= ViewModelProviders.of(this, flightDateViewModelFactory).get(FlightDateViewModel.class);
    }

//    private void getCurrency(){
//        if(flightDateViewModel.getCurrencyArrayList()!=null){
//            int size=flightDateViewModel.getCurrencyArrayList().size();
//            Timber.i("currencyArrayListSize= "+size);
//            currency=flightDateViewModel.getCurrencyArrayList().get(0);
//        };
//        if(flightDateViewModel.setCurrencyAL!=null){
//            currency=flightDateViewModel.setCurrencyAL.get(0);
//        }
//        //currency=flightDateViewModel.getCurrency();
////        if(currency==null){
////            Timber.i("getCurrency called currency equals null");
////        }
//
//    }

//    private void getCurrencyIntent() {
//        Intent intent = getIntent();
//        if (intent == null) {
//            Timber.i("Error getting position");
//            closeOnError();
//        }
//
//        if(intent.hasExtra(Constants.CURRENCY)){
//            currency=intent.getParcelableExtra(Constants.CURRENCY);
//        }
//
////        if(intent.hasExtra(Constants.QUOTE_ARRAY_LIST)){
////            queryArrayList=intent.getParcelableArrayListExtra(Constants.QUOTE_ARRAY_LIST);
////            currency=intent.getParcelableExtra(Constants.CURRENCY);
////
////        }
//
//    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void getQuotesFromFile() {
       queryArrayList= flightDateViewModel.getQuoteDir();
    }

    private void getCurrenciesFromFile(){
        currencyArrayList=flightDateViewModel.getCurrencyDir();
    }

    private void createRecyclerView() {
        //ArrayList<Currency> currencyArrayList = flightDateViewModel.getCurrencyArrayList();
        if(flightDateViewModel.getCurrencyDir() != null){
            Timber.i("setCurrencyAl does not equal null");
            currency=flightDateViewModel.getCurrencyDir().get(0);
            //currency=flightDateViewModel.setCurrencyAL.get(0);
        }
        else{
            Timber.i("setCurrencyAl equals null");
        }
        Quote[] quoteArray;

//        if (currencyArrayList != null) {
//            if (!currencyArrayList.isEmpty()) {
//                //currency = currencyArrayList.get(0);
//                String currencySymbol = currencyArrayList.get(0).getSymbol();
//                Timber.i("currencySymbol= " + currencySymbol);
//            }
//        }
//        else{
//            Timber.i("currencyArrayList equals null");
//        }


        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        String originPlaceId = sharedpreferences.getString(Constants.KEY_PREFERENCE_PLACE_ID, "SFO-sky");
        String destinationPlace = sharedpreferences.getString(Constants.KEY_PREFERENCE_DESTINATION_PLACE, "LAX-sky");
        String inboundDate = sharedpreferences.getString(Constants.KEY_PREFERENCE_INBOUND_DATE, "");

        if(queryArrayList==null){
            Timber.i("queryArrayList equals null");
            quoteArray=new Quote[0];
        }
        else{
            quoteArray= new Quote[queryArrayList.size()];
            queryArrayList.toArray(quoteArray);
        }

//        quoteArray= new Quote[queryArrayList.size()];
//        queryArrayList.toArray(quoteArray);

        FlightDateCurrencyAdapter flightDateCurrencyAdapter = new FlightDateCurrencyAdapter(FlightDateCurrency.this, originPlaceId, destinationPlace, inboundDate
                , currency, quoteArray, FlightDateCurrency.this);



        // Lookup the recyclerview in activity layout
        RecyclerView dateCurrencyRecyclerView = (RecyclerView) findViewById(R.id.dateCurrencyRecyclerView);

        // Attach the adapter to the recyclerview to populate items
        dateCurrencyRecyclerView.setAdapter(flightDateCurrencyAdapter);
        // Set layout manager to position the items
        dateCurrencyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onFlightDepartureClick(int position) {

    }
}