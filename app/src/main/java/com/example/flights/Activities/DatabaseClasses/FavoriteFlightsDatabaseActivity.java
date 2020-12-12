package com.example.flights.Activities.DatabaseClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flights.Activities.DepartureFlightLocations;
import com.example.flights.Activities.FavoriteFlightsDetails;
import com.example.flights.Activities.FlightDate;
import com.example.flights.Adapters.firebaseAdapter.FirebaseFavoriteFlightsAdapter;
import com.example.flights.DatabaseClasses.OutgoingFlight;
import com.example.flights.FavoriteFlightsData.FavoriteFlights;
import com.example.flights.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import timber.log.Timber;

import static com.example.flights.Constants.DEPARTURE_DATE;
import static com.example.flights.Constants.DESTINATION_PLACE;
import static com.example.flights.Constants.ORIGIN_PLACE;
import static com.example.flights.Constants.PRICE;
import static com.example.flights.Constants.RETURN_DATE;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;

public class FavoriteFlightsDatabaseActivity extends AppCompatActivity implements FirebaseFavoriteFlightsAdapter.OnFavoriteFlightsListener{
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private CollectionReference favoriteFlightRef = db.collection("Flights");

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

    //FirebaseDatabase.getInstance();
    DatabaseReference flightsDatabaseReference;


    private FirebaseFavoriteFlightsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_flights);
        Toolbar favoriteToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(favoriteToolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else{
            Timber.i("support action bar is null");
        }
        flightsDatabaseReference=firebaseDatabase.getReference();
        Timber.i("flightsDatabaseReference= %s", flightsDatabaseReference.toString());
        setUpRecyclerView();


        //flightsDatabaseReference.child();
    }
    private void setUpRecyclerView() {
//        String id=favoriteFlightRef.getId();
//        String individualFlight=favoriteFlightRef.toString()+0;

        //com.google.firebase.database.Query queryRealtimeDB=flightsDatabaseReference.orderByKey();

//        CollectionReference individualFlightRef=db.collection(individualFlight);
        //Query query = individualFlightRef.orderByChild("id", Query.Direction.DESCENDING);
//        Query query = favoriteFlightRef.orderBy("id", Query.Direction.DESCENDING);
//        FirestoreRecyclerOptions<FavoriteFlights> options = new FirestoreRecyclerOptions.Builder<FavoriteFlights>()
//                .setQuery(query, FavoriteFlights.class)
//                .build();

        //snapshot
//        flightsDatabaseReference

        FirebaseRecyclerOptions<FavoriteFlights> options
                = new FirebaseRecyclerOptions.Builder<FavoriteFlights>()
                .setQuery(flightsDatabaseReference, FavoriteFlights.class)
                .build();


        adapter = new FirebaseFavoriteFlightsAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.favoriteFlightsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
//        ViewGroup.LayoutParams layoutParams=recyclerView.getLayoutParams();
//        layoutParams.height=0;
//        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setVisibility(View.VISIBLE);
//        findViewById(R.id.loading_progress_bar).setVisibility(View.GONE);





        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //delete from database here
//                outerLoopDatabseCalls = false;
//                innerLoopDatabseCalls = false;

//                recyclerViewCalls++;
//                totalSnapshots = 0;
//                counter = 0;
//                favoriteFlightsArrayList.clear();
                //onCreate(savedInstanceState);


                //adapter deleteItem
                //adapter.deleteItem();

                int adapterPosition=viewHolder.getAdapterPosition();
//
//                //long itemId=viewHolder.getItemId();
//
//
                Timber.i("adapterPosition= "+adapterPosition);
                //Timber.i("itemId= "+itemId);
//
                ObservableSnapshotArray<FavoriteFlights> observableSnapshotArray=adapter.getSnapshots();
                FavoriteFlights favoriteFlights=observableSnapshotArray.get(adapterPosition);
                String idCurrencySign=favoriteFlights.getPrice();
                String idNumber=favoriteFlights.getId();
                //String idNumber= idCurrencySign.replaceAll("[^\\d]", "");
//                String flightToDelete="Flights"+idNumber;
                String flightToDelete="Flight"+idNumber;
//
//                String pathToDelete=firebaseDatabase.getReference(flightToDelete).toString();
//                Timber.i("pathToDelete "+pathToDelete);
//
//                String pathToDeleteChild=firebaseDatabase.getReference().child(flightToDelete).toString();
//                Timber.i("pathToDeleteChild "+pathToDeleteChild);
//
//
//                String pathToDeleteParent=firebaseDatabase.getReference().child(flightToDelete).getParent().toString();
//                Timber.i("pathToDeleteParent "+pathToDeleteParent);

//                firebaseDatabase.child(flightToDelete);

                firebaseDatabase.getReference().child(flightToDelete).removeValue();
//                firebaseDatabase.getReference().child(flightToDelete).removeValue();
//                firebaseDatabase.getReference().child(flightToDelete).removeValue();
//                firebaseDatabase.getReference().child(flightToDelete).removeValue();
//                firebaseDatabase.getReference().child(flightToDelete).setValue(null);
//
//                firebaseDatabase.getReference().child(flightToDelete).setValue(null);
//                firebaseDatabase.getReference().child(flightToDelete).setValue(null);

                //firebaseDatabase.getReference(flightToDelete).removeValue();






//                observableSnapshotArray.
//                adapter.getItemId()
//
//                FavoriteFlights favoriteFlights = favoriteFlightsAdapter.getFavoriteFlightAt(viewHolder.getAdapterPosition());
//                String idToDelete = favoriteFlights.getId();
//                Task<Void> flightDeletedTask = rootDB.child(idToDelete).removeValue();
//
//                FavoriteFlights favoriteFlights = adapter.getSnapshots();
//
//                String idToDelete = favoriteFlights.getId();
//                Task<Void> flightDeletedTask = rootDB.child(idToDelete).removeValue();



                Toast.makeText(FavoriteFlightsDatabaseActivity.this, "FavoriteFlight deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


    }

//    public FavoriteFlights getFavoriteFlightAt(int position) {
//        return favoriteFlightsArray[position];
//    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    @Override
//    public void OnFavoriteFlightsListener(int position) {
//
//    }

    @Override
    public void onFavoriteFlightsClick(FavoriteFlights favoriteFlights) {
        String outgoingDate=favoriteFlights.getOutgoingDate();
        String originPlace=favoriteFlights.getOriginPlace();
        String price=favoriteFlights.getPrice();
        String currency=favoriteFlights.getCurrency();
        String currencyPrice=currency+" "+price;
        String returnDate=favoriteFlights.getReturnDate();
        String destinationPlace=favoriteFlights.getDestinationAirportName();



        Timber.i("onFavoriteFlightsClick price= "+price);

        Intent intent=new Intent(this, FavoriteFlightsDatabaseActivity.class);
        startActivity(intent);

        Intent carrierIntent=new Intent(this, FavoriteFlightsDetails.class);
        carrierIntent.putExtra(DEPARTURE_DATE, outgoingDate);
        carrierIntent.putExtra(ORIGIN_PLACE, originPlace);
        carrierIntent.putExtra(PRICE, currencyPrice);
        carrierIntent.putExtra(RETURN_DATE, returnDate);
        carrierIntent.putExtra(DESTINATION_PLACE, destinationPlace);
        startActivity(carrierIntent);
        overridePendingTransition(R.anim.anim_right_slide_in, R.anim.anim_left_slide_out);

    }
}
