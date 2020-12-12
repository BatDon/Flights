package com.example.flights.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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
        Timber.i("carrierArrayList class type= "+flightDateViewModel.getCarrierDir().getClass());
        if(carrierArrayList==null) {
            Timber.i("carrierArrayList equals null");
        }else{
            setUpCarrierRecyclerView(carrierArrayList);
        }


        final CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));

        final Toolbar favoriteToolbar =findViewById(R.id.toolbar);
        setSupportActionBar(favoriteToolbar);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Timber.i("onOffestChanged called");
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
//                   favoriteToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    setTitleColor(R.color.colorPrimaryDark);
                } else if (isShow) {
                    Timber.i("onOffsetChanged isShow true");
                    isShow = false;
//                    favoriteToolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
                    setTitleColor(R.color.colorAccent);
                }
            }
        });
    }


    private void setUpCarrierRecyclerView(ArrayList<Carrier> carrierArrayList){
        Timber.i("setUpCarrierRecyclerView carrierArrayList size "+carrierArrayList.size());

        Timber.i("carrier class= "+carrierArrayList.get(0).getClass());
        Carrier carrier=carrierArrayList.get(0);
        Timber.i("carrierName= "+carrier.getName());

            // Lookup the recyclerview in activity layout
            RecyclerView carrierRecyclerView = (RecyclerView) findViewById(R.id.carrierRecyclerView);

            FlightCarrierAdapter adapter = new FlightCarrierAdapter(this, carrierArrayList);
            carrierRecyclerView.setAdapter(adapter);
            carrierRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_left_slide_in, R.anim.anim_right_slide_out);
    }

}