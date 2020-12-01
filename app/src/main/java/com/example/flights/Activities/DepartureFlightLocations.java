package com.example.flights.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.flights.Activities.DatabaseClasses.FavoriteFlightsDatabaseActivity;
import com.example.flights.Adapters.FlightDeparturesAdapter;
import com.example.flights.Constants;
import com.example.flights.Pojos.Place;
import com.example.flights.R;
import com.example.flights.ViewModels.MainActivityViewModel;
import com.example.flights.ViewModels.MainActivityViewModelFactory;

import java.util.List;

import timber.log.Timber;

public class DepartureFlightLocations extends AppCompatActivity implements FlightDeparturesAdapter.OnFlightDepartureListener {

    MainActivityViewModel mainActivityViewModel;
    List<Place> placeRecyclerList;
    Place placeArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure_flight_locations);

        setUpViewModel();
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
                Intent intent=new Intent(this, FavoriteFlightsDatabaseActivity.class);
                startActivity(intent);
                //new RetrofitRequester().requestMovies(this);
                break;
            }
        }
        return true;
    }

    private void setUpViewModel(){
        MainActivityViewModelFactory mainActivityViewModelFactory=new MainActivityViewModelFactory(getApplication());
        mainActivityViewModel = ViewModelProviders.of(this, mainActivityViewModelFactory).get(MainActivityViewModel.class);
        mainActivityViewModel.getPlaceDir();
        placeRecyclerList=mainActivityViewModel.getPlaceDir();
        if(placeRecyclerList==null){
            Timber.i("placeRecyclerList equals null");
        }
        int i=0;
        for(Place place: placeRecyclerList){
            Timber.i(place.getCountryName());
            Timber.i(String.valueOf(i));
            i++;
        }

        placeArray=new Place[placeRecyclerList.size()];

        placeRecyclerList.toArray(placeArray);

        createDepartureRecyclerview(placeArray);

        setUpViewModelOnChanged();

        //context=movieDetailsViewModel.getApplication();
    }

    public void setUpViewModelOnChanged(){
        Observer<List<Place>> observer=new Observer<List<Place>>() {
            int i=0;
            @Override
            public void onChanged(@Nullable final List<Place> placeList) {
                i=placeList.size();
                //mainActivityViewModel.getPlaceDir();
                // Update the cached copy of the words in the adapter.
                Timber.i("onChanged triggered");
                if(i>0){
                    //placeRecyclerList=placeList;
//                    for (int j = 0; j < placeList.size() ; j++) {
//                        Timber.i(placeList.get(i).getCountryName());
//                    }
                    //setUpGridAdapter();
                }
//                i++;
//                //mainViewModel.requestMovies();
//                resultList=movies;
//                if(mainViewModel.getAllMovies().getValue()!=null){
//                    mainViewModel.requestMovies();
//                }
            }
        };

        mainActivityViewModel.getAllPlaces().observe(this,observer);
    }

    public void createDepartureRecyclerview(Place [] placeArray){

        // Lookup the recyclerview in activity layout
        RecyclerView departureRecyclerView = (RecyclerView) findViewById(R.id.departureRecyclerView);


        FlightDeparturesAdapter adapter = new FlightDeparturesAdapter(this, placeArray,this);
        departureRecyclerView.setAdapter(adapter);
        departureRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onFlightDepartureClick(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
        Intent flightDateIntent=new Intent(this, FlightDate.class);
        flightDateIntent.putExtra(Constants.FLIGHT_ORIGIN_POSITION, position);
        startActivity(flightDateIntent);
    }
}