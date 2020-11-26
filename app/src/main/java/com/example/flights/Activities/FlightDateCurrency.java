package com.example.flights.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.flights.Adapters.FlightDateCurrencyAdapter;
import com.example.flights.Adapters.FlightDeparturesAdapter;
import com.example.flights.Constants;
import com.example.flights.DatabaseClasses.OutgoingFlight;
import com.example.flights.DatabaseClasses.ReturnFlight;
import com.example.flights.FavoriteFlightsData.FavoriteFlights;
import com.example.flights.Pojos.FlightDatePojos.Currency;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.R;
import com.example.flights.ViewModels.DatabaseViewModel;
import com.example.flights.ViewModels.DatabaseViewModelFactory;
import com.example.flights.ViewModels.FlightDateViewModel;
import com.example.flights.ViewModels.FlightDateViewModelFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class FlightDateCurrency extends AppCompatActivity implements FlightDateCurrencyAdapter.OnDateCurrencyListener{

    ArrayList<Quote> queryArrayList;
    ArrayList<Currency> currencyArrayList;
    Currency currency;

    FlightDateViewModel flightDateViewModel;
    FirebaseDatabase firebaseDatabase;

    DatabaseViewModel databaseViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("onCreate called");
        setContentView(R.layout.activity_flight_date_currency);
        setUpFlightDateViewModel();
        setUpDatabaseViewModel();
        //getCurrencyIntent();
        //getCurrency();
        getQuotesFromFile();
        createRecyclerView();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite_flights: {
                Toast.makeText(this, R.string.action_favorite_flights, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, FavoriteFlightsActivity.class);
                startActivity(intent);
                //new RetrofitRequester().requestMovies(this);
                break;
            }
        }
        return true;
    }

    private void setUpFlightDateViewModel(){
        FlightDateViewModelFactory flightDateViewModelFactory=new FlightDateViewModelFactory(getApplication());
        flightDateViewModel= ViewModelProviders.of(this, flightDateViewModelFactory).get(FlightDateViewModel.class);
    }

    private void setUpDatabaseViewModel(){
        DatabaseViewModelFactory databaseViewModelFactory=new DatabaseViewModelFactory(getApplication());
        databaseViewModel= ViewModelProviders.of(this, databaseViewModelFactory).get(DatabaseViewModel.class);
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


    int recyclerViewItemPosition=0;

    //adds flight to favoriteFlights

    @Override
    public void onFlightDepartureClick(String originPlaceId,String departureDate,
                                       String formattedPrice, String destinationPlaceId,
                                       String returnDate, int position){

        Toast.makeText(this, "Saved Flight to favorites", Toast.LENGTH_SHORT).show();

//        databaseViewModel.setIndex(queryArrayList.size());
//        String id=databaseViewModel.getFavoriteFlightsIndex();
        String id=Integer.toString(position);


        int endOfIndex = formattedPrice.indexOf(" ");

        String price="";
        String currency="";
        //found T character
        if (endOfIndex != -1)
        {
            currency= formattedPrice.substring(0 , endOfIndex);
            price=formattedPrice.substring(endOfIndex+1);
        }

        FavoriteFlights favoriteFlights=new FavoriteFlights(id, originPlaceId, currency, price,
                departureDate, destinationPlaceId, returnDate);

//        DatabaseReference rootDB=firebaseDatabase.getReference("/");
//        DatabaseReference flightsDatabaseReference=firebaseDatabase.getReference("Flights");
        String idPrice = price.replaceAll("[^\\d]", "");
        DatabaseReference flightsDatabaseReference=firebaseDatabase.getReference("Flights"+idPrice);
//        DatabaseReference flightsDatabaseReference=firebaseDatabase.getReference();

//        String flightPath="/Flight"+id+"/";
        //String flightPath="Flight"+id;

//        Map<String, FavoriteFlights> favoriteFlightsHashMap = new HashMap<>();
//        favoriteFlightsHashMap.put(flightPath, favoriteFlights);

//        flightsDatabaseReference.child(id).setValue(favoriteFlights);
        //flightsDatabaseReference.updateChildren(favoriteFlights);
        flightsDatabaseReference.setValue(favoriteFlights);
        //rootDB.child(id).setValue(favoriteFlights);


//        rootDB.setValue(favoriteFlightsHashMap);

//
//
//        rootDB.setValue(flightPath);
//        rootDB.setValue(favoriteFlights);
//        rootDB.child(flightPath).setValue(favoriteFlights);

        databaseViewModel.increaseIndex();




        recyclerViewItemPosition++;



       // databaseViewModel.getDatabaseData();


//
//        CollectionReference favoriteFlightRef = FirebaseFirestore.getInstance()
//                .collection("Flight");
//        favoriteFlightRef.add(favoriteFlights);
//        Toast.makeText(this, "Saved Flight to favorites", Toast.LENGTH_SHORT).show();
//        finish();
    }
}