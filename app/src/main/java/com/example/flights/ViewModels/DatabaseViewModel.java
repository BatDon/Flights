package com.example.flights.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.flights.Adapters.FavoriteFlightsAdapter;
import com.example.flights.FavoriteFlightsData.FavoriteFlights;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class DatabaseViewModel extends AndroidViewModel {

    FirebaseDatabase firebaseDatabase;
    FavoriteFlightsAdapter favoriteFlightsAdapter;
    ArrayList<FavoriteFlights> favoriteFlightsArrayList= new ArrayList<>();
    FavoriteFlights[] favoriteFlightsArray;

    DatabaseViewModel databaseViewModel;

    int i=0;

    public int index=0;

    public MutableLiveData<List<FavoriteFlights>> liveDatafavoriteFlightsArrayList=new MutableLiveData<List<FavoriteFlights>>(){};

    DatabaseViewModel(@NonNull Application application) {
        super(application);
        //favoriteFlightsArrayList=new ArrayList<FavoriteFlights>();
        //liveDatafavoriteFlightsArrayList.setValue(favoriteFlightsArrayList);
    }

    public MutableLiveData<List<FavoriteFlights>> getLiveDatafavoriteFlightsArrayList() {
        return liveDatafavoriteFlightsArrayList;
    }

    public String getFavoriteFlightsIndex(){
        String indexString=Integer.toString(index);
        return indexString;
    }

    public void increaseIndex(){
        index++;
    }

    public void setIndex(int index){
        this.index=index;
    }

    public ArrayList<FavoriteFlights> getFavoriteFlightsArrayList() {
        return favoriteFlightsArrayList;
    }

//    public void getDatabaseData(){
//        Timber.i("getDatabaseData called");
//
//        //favoriteFlightsArray=new FavoriteFlights[]{};
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference rootDB=firebaseDatabase.getReference();
//
//        ValueEventListener flightListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
////                int totalSnapshots=0;
////                int counter=0;
////                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
////                    //totalSnapshots++;
////                }
//
//
//
//
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//
//
//                    String key=dataSnapshot1.getKey();
//                    Timber.i("key= "+key);
//                    DatabaseReference flightKey=rootDB.child(key);
//                    ValueEventListener valueEventListener=new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            int totalSnapshots=0;
////                            int counter=0;
////                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
////                                totalSnapshots++;
////                            }
//
////                            if(favoriteFlightsArrayList!=null || favoriteFlightsArrayList.size()>0){
//////                                Timber.i("onDataChange called call createRecyclerView");
////                                if(i==0){
////                                    createRecyclerView(favoriteFlightsArrayList);
////                                    Timber.i("onDataChange called call createRecyclerView");
////                                    i++;
////                                }
////                                else{
////                                    FavoriteFlights[] favoriteFlightsArray=favoriteFlightsArrayList.toArray(new FavoriteFlights[favoriteFlightsArrayList.size()]);
////                                    favoriteFlightsAdapter=new FavoriteFlightsAdapter(FavoriteFlightsActivity.this, favoriteFlightsArray);
////                                    favoriteFlightsAdapter.notifyDataSetChanged();
////                                }
////                               // updateRecyclerView();
////                            }
//                            // for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()) {
//                            String currency = dataSnapshot.child("currency").getValue(String.class);
//                            Timber.i("onDataChange currency= %s", currency);
//                            String destinationAirportName = dataSnapshot.child("destinationAirportName").getValue(String.class);
//                            Timber.i("onDataChange destinationAirportName= %s", destinationAirportName);
//                            String id = dataSnapshot.child("id").getValue(String.class);
//                            Timber.i("onDataChange id= %s", id);
//                            String originPlace = dataSnapshot.child("originPlace").getValue(String.class);
//                            Timber.i("onDataChange originPlace= %s", originPlace);
//                            String outgoingDate = dataSnapshot.child("outgoingDate").getValue(String.class);
//                            Timber.i("onDataChange outgoingDate= %s", outgoingDate);
//                            String price = dataSnapshot.child("price").getValue(String.class);
//                            Timber.i("onDataChange price= %s", price);
//                            String returnDate = dataSnapshot.child("returnDate").getValue(String.class);
//                            Timber.i("onDataChange returnDate= %s", returnDate);
//                            FavoriteFlights favoriteFlights = new FavoriteFlights(id, originPlace, currency, price, outgoingDate, destinationAirportName, returnDate);
//                            favoriteFlightsArrayList.add(favoriteFlights);
////                            if(favoriteFlightsArrayList!=null || favoriteFlightsArrayList.size()>0){
////                                Timber.i("onDataChange called call createRecyclerView");
////                                createRecyclerView(favoriteFlightsArrayList);
////                            }
//                            //Timber.i("createRecyclerViewCalled");
//
//                            //   }
////                            createRecyclerView(favoriteFlightsArrayList);
//
//
//                            //favoriteFlightsAdapter.submitList(favoriteFlightsArrayList);
//                            //createRecyclerView();
////                            findViewById(R.id.favoriteFlightsRecyclerView).setVisibility(View.VISIBLE);
////                            findViewById(R.id.loading_progress_bar).setVisibility(View.INVISIBLE);
//                            //favoriteFlightsAdapter=new FavoriteFlightsAdapter(this, favoriteFlightsList)
////                            }
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            Timber.e("error reading database");
//                        }
//                    };
//                    //   createRecyclerView(favoriteFlightsArrayList);
//
//                    flightKey.addListenerForSingleValueEvent(valueEventListener);
////                    DatabaseReference
////
////                    for (String child: flightKey.get
////                         ) {
////
////                    }
//                    //keyRef.get
////                    String flightDetail= dataSnapshot1.getValue(String.class);
////                    Timber.i("onDataChange flightDetail"+flightDetail);
//
////                    if(counter==totalSnapshots){
////                        createRecyclerView(favoriteFlightsArrayList);
////                    }
//                }
//
//                //call adapter here
//
//
//                // Get Post object and use the values to update the UI
////                FavoriteFlights favoriteFlights=dataSnapshot.getValue(FavoriteFlights.class);
////                favoriteFlightsArray
////                List<FavoriteFlights> favoriteFlightsList=new ArrayList<FavoriteFlights>();
////                favoriteFlightsList.add(favoriteFlights);
//
////                Timber.i("favoriteFlightsAdapter submit list called below");
////                favoriteFlightsAdapter.submitList(favoriteFlightsArrayList);
////                createRecyclerView();
////                findViewById(R.id.favoriteFlightsRecyclerView).setVisibility(View.VISIBLE);
////                findViewById(R.id.loading_progress_bar).setVisibility(View.INVISIBLE);
////                //favoriteFlightsAdapter=new FavoriteFlightsAdapter(this, favoriteFlightsList)
//
////                if(totalSnapshots.equals())
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Timber.i("unable to read values from database");
//                // ...
//            }
//        };
//        rootDB.addValueEventListener(flightListener);
////            createRecyclerView(favoriteFlightsArrayList);
//    }


}
