package com.example.flights.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flights.Adapters.FlightCarrierAdapter;
import com.example.flights.Adapters.FlightDeparturesAdapter;
import com.example.flights.Pojos.FlightDatePojos.Carrier;
import com.example.flights.Pojos.Place;
import com.example.flights.R;
import com.example.flights.ViewModels.FlightDateViewModel;
import com.example.flights.ViewModels.FlightDateViewModelFactory;

import org.w3c.dom.Text;

import java.util.ArrayList;

import timber.log.Timber;

import static com.example.flights.Constants.DEPARTURE_DATE;
import static com.example.flights.Constants.DESTINATION_PLACE;
import static com.example.flights.Constants.ORIGIN_PLACE;
import static com.example.flights.Constants.PRICE;
import static com.example.flights.Constants.RETURN_DATE;

public class FavoriteFlightsDetails extends AppCompatActivity {

    FlightDateViewModel flightDateViewModel;
    ArrayList<Carrier> carrierArrayList;

    String departureDate="";
    String originPlace="";
    String price="";
    String returnDate="";
    String destinationPlace="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_flights_details);
        receiveIntent();
        setUpFlightDateViewModel();
        setUpViews();
    }

    private void receiveIntent() {

//
//        DEPARTURE_DATE="co
//        ORIGIN_PLACE="com.
//        PRICE="com.example
//        RETURN_DATE="com.e
//        DESTINATION_PLACE=

        Intent intent=getIntent();
        if(intent.hasExtra(DEPARTURE_DATE)) {
            departureDate=intent.getStringExtra(DEPARTURE_DATE);
        }
        if(intent.hasExtra(ORIGIN_PLACE)) {
            originPlace=intent.getStringExtra(ORIGIN_PLACE);
        }
        if(intent.hasExtra(PRICE)) {
            price=intent.getStringExtra(PRICE);
        }
        if(intent.hasExtra(RETURN_DATE)) {
            returnDate=intent.getStringExtra(RETURN_DATE);
        }
        if(intent.hasExtra(DESTINATION_PLACE)) {
            destinationPlace=intent.getStringExtra(DESTINATION_PLACE);
        }

    }

    private void setUpFlightDateViewModel(){
        FlightDateViewModelFactory flightDateViewModelFactory=new FlightDateViewModelFactory(getApplication());
        flightDateViewModel= ViewModelProviders.of(this, flightDateViewModelFactory).get(FlightDateViewModel.class);
    }

    private void setUpViews(){
        findViewById(R.id.separatorLine).setVisibility(View.INVISIBLE);

        TextView departureDateTV=findViewById(R.id.departureDateTV);
        TextView originPlaceTV=findViewById(R.id.originPlaceTV);
        TextView priceTV=findViewById(R.id.priceTV);
        TextView returnDateTV=findViewById(R.id.returnDateTV);
        TextView destinationPlaceTV=findViewById(R.id.destinationPlaceTV);

        departureDateTV.setText(departureDate);
        originPlaceTV.setText(originPlace);
        priceTV.setText(price);
        returnDateTV.setText(returnDate);
        destinationPlaceTV.setText(destinationPlace);

        carrierArrayList= flightDateViewModel.getCarrierDir();
        setUpCarrierRecyclerView(carrierArrayList);
    }

    private void setUpCarrierRecyclerView(ArrayList<Carrier> carrierArrayList){

        Carrier carrier=carrierArrayList.get(0);
        Timber.i("carrierName= "+carrier.getName());

            // Lookup the recyclerview in activity layout
            RecyclerView carrierRecyclerView = (RecyclerView) findViewById(R.id.carrierRecyclerView);

            FlightCarrierAdapter adapter = new FlightCarrierAdapter(this, carrierArrayList);
            carrierRecyclerView.setAdapter(adapter);
            carrierRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}