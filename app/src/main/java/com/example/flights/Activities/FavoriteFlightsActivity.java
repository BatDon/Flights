package com.example.flights.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.example.flights.Adapters.FavoriteFlightsAdapter;
import com.example.flights.DatabaseClasses.OutgoingFlight;
import com.example.flights.DatabaseClasses.ReturnFlight;
import com.example.flights.FavoriteFlightsData.FavoriteFlights;
import com.example.flights.R;
import com.example.flights.ViewModels.DatabaseViewModel;
import com.example.flights.ViewModels.DatabaseViewModelFactory;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import timber.log.Timber;

public class FavoriteFlightsActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FavoriteFlightsAdapter favoriteFlightsAdapter;
    ArrayList<FavoriteFlights> favoriteFlightsArrayList;
    FavoriteFlights[] favoriteFlightsArray;

    Bundle savedInstanceState;

    DatabaseViewModel databaseViewModel;

    DatabaseReference rootDB;


    boolean outerLoopDatabseCalls=false;
    boolean innerLoopDatabseCalls=false;

    int i=0;

    int recyclerViewCalls=0;

    static int totalSnapshots=0;
    static int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState=savedInstanceState;
        setContentView(R.layout.activity_favorite_flights);

        favoriteFlightsArrayList=new ArrayList<>();


        setUpDatabaseViewModel();
        createRecyclerView(databaseViewModel.getFavoriteFlightsArrayList());
        getDatabaseData();


//        try {
//            Thread.sleep(3000);
//            createRecyclerView(favoriteFlightsArrayList);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }




    }

    private void setUpDatabaseViewModel(){
        DatabaseViewModelFactory databaseViewModelFactory=new DatabaseViewModelFactory(getApplication());
        databaseViewModel= ViewModelProviders.of(this, databaseViewModelFactory).get(DatabaseViewModel.class);
    }


    //THIS WORKS but don't know when to update Recyclerview

        public void getDatabaseData(){
        favoriteFlightsArrayList=new ArrayList<>();
        Timber.i("getDatabaseData called");

        //favoriteFlightsArray=new FavoriteFlights[]{};
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootDB=firebaseDatabase.getReference();

        ValueEventListener flightListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                totalSnapshots=0;
                counter=0;
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    totalSnapshots++;
                }




                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){


                    Timber.i("for loop totalSnapshots= "+totalSnapshots+" counter= "+counter);
//                    counter++;


                    String key=dataSnapshot1.getKey();
                    Timber.i("key= "+key);
                    DatabaseReference flightKey=rootDB.child(key);
                    ValueEventListener valueEventListener=new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            counter++;
//                            int totalSnapshots=0;
//                            int counter=0;
//                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                totalSnapshots++;
//                            }

//                            if(favoriteFlightsArrayList!=null || favoriteFlightsArrayList.size()>0){
////                                Timber.i("onDataChange called call createRecyclerView");
//                                if(i==0){
//                                    createRecyclerView(favoriteFlightsArrayList);
//                                    Timber.i("onDataChange called call createRecyclerView");
//                                    i++;
//                                }
//                                else{
//                                    FavoriteFlights[] favoriteFlightsArray=favoriteFlightsArrayList.toArray(new FavoriteFlights[favoriteFlightsArrayList.size()]);
//                                    favoriteFlightsAdapter=new FavoriteFlightsAdapter(FavoriteFlightsActivity.this, favoriteFlightsArray);
//                                    favoriteFlightsAdapter.notifyDataSetChanged();
//                                }
//                               // updateRecyclerView();
//                            }
                           // for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()) {
                                String currency = dataSnapshot.child("currency").getValue(String.class);
                                Timber.i("onDataChange currency= %s", currency);
                                String destinationAirportName = dataSnapshot.child("destinationAirportName").getValue(String.class);
                                Timber.i("onDataChange destinationAirportName= %s", destinationAirportName);
                                String id = dataSnapshot.child("id").getValue(String.class);
                                Timber.i("onDataChange id= %s", id);
                                String originPlace = dataSnapshot.child("originPlace").getValue(String.class);
                                Timber.i("onDataChange originPlace= %s", originPlace);
                                String outgoingDate = dataSnapshot.child("outgoingDate").getValue(String.class);
                                Timber.i("onDataChange outgoingDate= %s", outgoingDate);
                                String price = dataSnapshot.child("price").getValue(String.class);
                                Timber.i("onDataChange price= %s", price);
                                String returnDate = dataSnapshot.child("returnDate").getValue(String.class);
                                Timber.i("onDataChange returnDate= %s", returnDate);
                                FavoriteFlights favoriteFlights = new FavoriteFlights(id, originPlace, currency, price, outgoingDate, destinationAirportName, returnDate);
                                favoriteFlightsArrayList.add(favoriteFlights);
//                                innerLoopDatabseCalls=true;
                                //createRecyclerView(favoriteFlightsArrayList);
//                            if(favoriteFlightsArrayList!=null || favoriteFlightsArrayList.size()>0){
//                                Timber.i("onDataChange called call createRecyclerView");
//                                createRecyclerView(favoriteFlightsArrayList);
//                            }
                                //Timber.i("createRecyclerViewCalled");

                         //   }
//                            createRecyclerView(favoriteFlightsArrayList);


                            //favoriteFlightsAdapter.submitList(favoriteFlightsArrayList);
                            //createRecyclerView();
//                            findViewById(R.id.favoriteFlightsRecyclerView).setVisibility(View.VISIBLE);
//                            findViewById(R.id.loading_progress_bar).setVisibility(View.INVISIBLE);
                            //favoriteFlightsAdapter=new FavoriteFlightsAdapter(this, favoriteFlightsList)
//                            }
                            if(totalSnapshots==counter){
                                Timber.i("totalSnapshots= "+totalSnapshots+" counter= "+counter);
                                innerLoopDatabseCalls=true;
                                createRecyclerView(favoriteFlightsArrayList);
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Timber.e("error reading database");
                        }
                    };
                 //   createRecyclerView(favoriteFlightsArrayList);

                    flightKey.addListenerForSingleValueEvent(valueEventListener);
//                    DatabaseReference
//
//                    for (String child: flightKey.get
//                         ) {
//
//                    }
                    //keyRef.get
//                    String flightDetail= dataSnapshot1.getValue(String.class);
//                    Timber.i("onDataChange flightDetail"+flightDetail);

//                    if(counter==totalSnapshots){
//                        createRecyclerView(favoriteFlightsArrayList);
//                    }
                }

//                innerLoopDatabseCalls=true;
                outerLoopDatabseCalls=true;

                createRecyclerView(favoriteFlightsArrayList);

                //call adapter here


                // Get Post object and use the values to update the UI
//                FavoriteFlights favoriteFlights=dataSnapshot.getValue(FavoriteFlights.class);
//                favoriteFlightsArray
//                List<FavoriteFlights> favoriteFlightsList=new ArrayList<FavoriteFlights>();
//                favoriteFlightsList.add(favoriteFlights);

//                Timber.i("favoriteFlightsAdapter submit list called below");
//                favoriteFlightsAdapter.submitList(favoriteFlightsArrayList);
//                createRecyclerView();
//                findViewById(R.id.favoriteFlightsRecyclerView).setVisibility(View.VISIBLE);
//                findViewById(R.id.loading_progress_bar).setVisibility(View.INVISIBLE);
//                //favoriteFlightsAdapter=new FavoriteFlightsAdapter(this, favoriteFlightsList)

//                if(totalSnapshots.equals())
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Timber.i("unable to read values from database");
                // ...
            }
        };
        rootDB.addValueEventListener(flightListener);
//            createRecyclerView(favoriteFlightsArrayList);
    }



















//    public void getDatabaseData(){
//        Timber.i("getDatabaseData called");
//        //favoriteFlightsArray=new FavoriteFlights[]{};
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference rootDB=firebaseDatabase.getReference();
//
//        ValueEventListener flightListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    String key = dataSnapshot1.getKey();
//                    Timber.i("key= " + key);
//                    DatabaseReference flightKey = rootDB.child(key);
//                    String flightKeypath=flightKey.toString();
//                    Timber.i("flightKeypath= "+flightKeypath);
//                    flightKey.child("currency").getValue(String.class);
//
//                    String currency = dataSnapshot.child("currency").getValue(String.class);
//                    Timber.i("onDataChange currency= %s", currency);
//                    String destinationAirportName = dataSnapshot.child("destinationAirportName").getValue(String.class);
//                    Timber.i("onDataChange destinationAirportName= %s", destinationAirportName);
//                    String id = dataSnapshot.child("id").getValue(String.class);
//                    Timber.i("onDataChange id= %s", id);
//                    String originPlace = dataSnapshot.child("originPlace").getValue(String.class);
//                    Timber.i("onDataChange originPlace= %s", originPlace);
//                    String outgoingDate = dataSnapshot.child("outgoingDate").getValue(String.class);
//                    Timber.i("onDataChange outgoingDate= %s", outgoingDate);
//                    String price = dataSnapshot.child("price").getValue(String.class);
//                    Timber.i("onDataChange price= %s", price);
//                    String returnDate = dataSnapshot.child("returnDate").getValue(String.class);
//                    Timber.i("onDataChange returnDate= %s", returnDate);
//                    FavoriteFlights favoriteFlights = new FavoriteFlights(id, originPlace, currency, price, outgoingDate, destinationAirportName, returnDate);
//                    favoriteFlightsArrayList.add(favoriteFlights);
//                    Timber.i("createRecyclerViewCalled");
//                }
//                createRecyclerView(favoriteFlightsArrayList);
//
//
//                            //favoriteFlightsAdapter.submitList(favoriteFlightsArrayList);
//                            //createRecyclerView();
////                            findViewById(R.id.favoriteFlightsRecyclerView).setVisibility(View.VISIBLE);
////                            findViewById(R.id.loading_progress_bar).setVisibility(View.INVISIBLE);
//                            //favoriteFlightsAdapter=new FavoriteFlightsAdapter(this, favoriteFlightsList)
////                            }
//
//
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////                            Timber.e("error reading database");
////                        }
////                    }
//
////                    flightKey.addListenerForSingleValueEvent(valueEventListener);
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
//    }






    //TESTING THIS ONE



//            public void getDatabaseData(){
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
//                           // for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()) {
//                                String currency = dataSnapshot.child("currency").getValue(String.class);
//                                Timber.i("onDataChange currency= %s", currency);
//                                String destinationAirportName = dataSnapshot.child("destinationAirportName").getValue(String.class);
//                                Timber.i("onDataChange destinationAirportName= %s", destinationAirportName);
//                                String id = dataSnapshot.child("id").getValue(String.class);
//                                Timber.i("onDataChange id= %s", id);
//                                String originPlace = dataSnapshot.child("originPlace").getValue(String.class);
//                                Timber.i("onDataChange originPlace= %s", originPlace);
//                                String outgoingDate = dataSnapshot.child("outgoingDate").getValue(String.class);
//                                Timber.i("onDataChange outgoingDate= %s", outgoingDate);
//                                String price = dataSnapshot.child("price").getValue(String.class);
//                                Timber.i("onDataChange price= %s", price);
//                                String returnDate = dataSnapshot.child("returnDate").getValue(String.class);
//                                Timber.i("onDataChange returnDate= %s", returnDate);
//                                FavoriteFlights favoriteFlights = new FavoriteFlights(id, originPlace, currency, price, outgoingDate, destinationAirportName, returnDate);
//                                favoriteFlightsArrayList.add(favoriteFlights);
////                            if(favoriteFlightsArrayList!=null || favoriteFlightsArrayList.size()>0){
////                                Timber.i("onDataChange called call createRecyclerView");
////                                createRecyclerView(favoriteFlightsArrayList);
////                            }
//                                //Timber.i("createRecyclerViewCalled");
//
//                         //   }
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
//                 //   createRecyclerView(favoriteFlightsArrayList);
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





















        public void createRecyclerView(ArrayList<FavoriteFlights> favoriteFlightsArrayList) {
            RecyclerView favoriteFlightsRecyclerView=null;

            if(outerLoopDatabseCalls && innerLoopDatabseCalls){

                if(recyclerViewCalls>0){
                    Timber.i("recyclerViewCalls>0");
                    favoriteFlightsAdapter = new FavoriteFlightsAdapter(this, favoriteFlightsArray);
                    favoriteFlightsAdapter.notifyDataSetChanged();
                    if(favoriteFlightsRecyclerView==null){
                        favoriteFlightsRecyclerView=findViewById(R.id.favoriteFlightsRecyclerView);
                    }
                    favoriteFlightsRecyclerView.setAdapter(favoriteFlightsAdapter);
                }else {

                    Timber.i("createRecyclerView called");
                    Timber.i("favoriteFlightsArrayList size= " + favoriteFlightsArrayList.size());
                    FavoriteFlights[] favoriteFlightsArray = favoriteFlightsArrayList.toArray(new FavoriteFlights[favoriteFlightsArrayList.size()]);


                    favoriteFlightsRecyclerView = findViewById(R.id.favoriteFlightsRecyclerView);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    //                favoriteFlightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    favoriteFlightsRecyclerView.setLayoutManager(linearLayoutManager);
                    favoriteFlightsRecyclerView.setHasFixedSize(true);
                    if (favoriteFlightsAdapter == null) {

                        favoriteFlightsAdapter = new FavoriteFlightsAdapter(this, favoriteFlightsArray);
                    }
                    //            favoriteFlightsAdapter.notifyDataSetChanged();

                    favoriteFlightsRecyclerView.setAdapter(favoriteFlightsAdapter);

                    findViewById(R.id.favoriteFlightsRecyclerView).setVisibility(View.VISIBLE);
                    findViewById(R.id.loading_progress_bar).setVisibility(View.INVISIBLE);
                }

                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                            //delete from database here
                            outerLoopDatabseCalls = false;
                            innerLoopDatabseCalls = false;

                            recyclerViewCalls++;
                            totalSnapshots = 0;
                            counter = 0;
                            favoriteFlightsArrayList.clear();
                            //onCreate(savedInstanceState);

                            FavoriteFlights favoriteFlights = favoriteFlightsAdapter.getFavoriteFlightAt(viewHolder.getAdapterPosition());
                            String idToDelete = favoriteFlights.getId();
                            Task<Void> flightDeletedTask = rootDB.child(idToDelete).removeValue();

//
//
//                            favoriteFlightsArrayList.remove(idToDelete);
//                            FavoriteFlights[] favoriteFlightsArray=favoriteFlightsArrayList.toArray(new FavoriteFlights[favoriteFlightsArrayList.size()]);
//
//                            //favoriteFlightsAdapter.updateData(favoriteFlightsArray);
//
//
//                            RecyclerView favoriteFlightsRecyclerView = findViewById(R.id.favoriteFlightsRecyclerView);
//                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FavoriteFlightsActivity.this);
//                            //                favoriteFlightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//                            favoriteFlightsRecyclerView.setLayoutManager(linearLayoutManager);
//                            favoriteFlightsRecyclerView.setHasFixedSize(true);
//
//
//                            favoriteFlightsAdapter = new FavoriteFlightsAdapter(FavoriteFlightsActivity.this, favoriteFlightsArray);

                            //            favoriteFlightsAdapter.notifyDataSetChanged();

//                            favoriteFlightsRecyclerView.setAdapter(favoriteFlightsAdapter);
//
//                            //list.remove(position);
//                            favoriteFlightsRecyclerView.removeViewAt(Integer.parseInt(idToDelete));
//                            favoriteFlightsAdapter.notifyItemRemoved(Integer.parseInt(idToDelete));
//                            favoriteFlightsAdapter.notifyItemRangeChanged(Integer.parseInt(idToDelete), favoriteFlightsArray.length);
//                            favoriteFlightsAdapter.notifyDataSetChanged();
//                            onCreate(savedInstanceState);
//                            getDatabaseData();

    //                        createRecyclerView(favoriteFlightsArrayList);

//                            favoriteFlightsRecyclerView.setAdapter(null);
//                            favoriteFlightsRecyclerView.setLayoutManager(null);
//                            favoriteFlightsRecyclerView.setAdapter(favoriteFlightsAdapter);
//                            favoriteFlightsRecyclerView.setLayoutManager(linearLayoutManager);
//                            favoriteFlightsAdapter.notifyDataSetChanged();
//                            //favoriteFlightsAdapter.notifyDataSetChanged();
//                                                        onCreate(savedInstanceState);

                            Toast.makeText(FavoriteFlightsActivity.this, "FavoriteFlight deleted", Toast.LENGTH_SHORT).show();
                        }
                    }).attachToRecyclerView(favoriteFlightsRecyclerView);
             }
            else{
                return;
            }
        }

//        public void updateRecyclerView(){
//            favoriteFlightsAdapter.notifyDataSetChanged();
//        }
}
