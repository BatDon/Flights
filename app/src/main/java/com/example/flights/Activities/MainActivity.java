package com.example.flights.Activities;

import com.example.flights.Constants;
import com.example.flights.GetFlightsService;
import com.example.flights.Pojos.Place;
import com.example.flights.R;
import com.example.flights.RelativeUrlCreator;
import com.example.flights.Retrofit.RetrofitRequester;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;

import java.util.List;
import java.util.Timer;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RetrofitRequester.onRetrofitListener {

//public class MainActivity extends AppCompatActivity implements RetrofitRequester.onRetrofitListener,
//        AdapterView.OnItemSelectedListener {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";

    private static final int START_LEVEL = 1;
    private int mLevel;
    private Button mNextLevelButton;
    private InterstitialAd mInterstitialAd;
    private TextView mLevelTextView;
    //private Spinner countrySpinner;

    String[] countryArray;

    SharedPreferences sharedpreferences;

    FusedLocationProviderClient fusedLocationProviderClient;

    String userSelectedCountryName;
    String userSelectedLocalityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(BuildConfig.DEBUG){
        Timber.plant(new Timber.DebugTree());
//        }
        setUpViews();


        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();

        initializeBroadcastReceiver();

        sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("country", "UK");
        editor.putString("currency", "GBP");
        editor.putString("locale", "en-GB");
        editor.putString("localityName","Stockholm");
        editor.commit();
    }

    private void setUpViews(){
        setContentView(R.layout.activity_main);


        // Create the text view to show the level number.
        mLevelTextView = (TextView) findViewById(R.id.level);
        mLevel = START_LEVEL;

        // Create the next level button, which tries to show an interstitial when clicked.
        mNextLevelButton = ((Button) findViewById(R.id.next_level_button));
        mNextLevelButton.setEnabled(false);

        mNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
                new RetrofitRequester(MainActivity.this).requestPlaces(MainActivity.this);

                // starts and triggers GetFlightsService
//                Intent i = new Intent(MainActivity.this, GetFlightsService.class);
// potentially add data to the intent
//                i.putExtra("KEY1", "Value to be used by the service");
//                MainActivity.this.startService(i);

//                new RetrofitRequester().requestPlaces(MainActivity.this);

            }

        });

//        countryArray = new String[]{"India", "USA", "China", "Japan", "Other"};
//
//        countrySpinner = (Spinner) findViewById(R.id.currencySpinner);
//        countrySpinner.setOnItemSelectedListener(this);
//
//        //Creating the ArrayAdapter instance having the country list
//        ArrayAdapter countryArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,countryArray);
//        countryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        countrySpinner.setAdapter(countryArrayAdapter);
    }

    private void initializeBroadcastReceiver() {

        IntentFilter filter = new IntentFilter(Constants.PLACE_BROADCAST_ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        PlaceReceiver placeReceiver = new PlaceReceiver();
        registerReceiver(placeReceiver, filter);
    }

    ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                Intent intent=new Intent(MainActivity.this, FlightToDestination.class);
                startActivity(intent);

                //goToNextLevel();

            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it"s ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        mNextLevelButton.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
//                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        mLevelTextView.setText("Level " + (++mLevel));
        mInterstitialAd = newInterstitialAd();
//        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        loadInterstitial();
    }



    @Override
    public void onPopularRetrofitFinished(List<Place> placeList) {
        Timber.i("onPopularRetrofitFinished called");

        Timber.i("userSelectedCountryName= "+userSelectedCountryName);
        Timber.i("userSelectedLocalityName= "+userSelectedLocalityName);
        if (placeList == null) {
            Timber.i("placeList equals null");
        }
        for (Place place : placeList) {
            String countryName = place.getCountryName();
            Timber.i("countryName= "+countryName);
            String cityName = place.getPlaceName();
            Timber.i("cityName= "+cityName);

            if(countryName.equals(userSelectedCountryName)){
                if(cityName.equals(userSelectedLocalityName)){
                    Timber.i("match found");
                }
                else{
                    Timber.i("countryName match but not localityName Match");
                }
            }else{
                Timber.i("wrong country returned");
            }


//        Place place = placeList.get(0);
//        String placeId = place.getCityId();
//        Timber.i("placeId= " + placeId);
        }
        Timber.i("for each loop finished onPopularRetrofitFinished");
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(),countryArray[position] , Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }


//    public void getLastLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//            Timber.i("permission needed before can access location");
//            return;
//        }
//
//        // starts and triggers GetFlightsService
//        Intent i = new Intent(MainActivity.this, GetFlightsService.class);
//// potentially add data to the intent
//        i.putExtra("KEY1", "Value to be used by the service");
//        MainActivity.this.startService(i);
//
//    }



    public class PlaceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Timber.i("onReceive in ResponseReceiver called");
            String countryName="";
            String localityName="";
//            if(intent.hasExtra(Constants.PLACE_COUNTRY)){
//                countryName=intent.getStringExtra(Constants.PLACE_COUNTRY);
//                userSelectedCountryName=countryName;
//                Timber.i("countryName= "+countryName);
//            }
//            if(intent.hasExtra(Constants.PLACE_LOCALITY)){
//                localityName=intent.getStringExtra(Constants.PLACE_LOCALITY);
//                userSelectedLocalityName=localityName;
//                Timber.i("localityName "+localityName);
//                //Remove just for testing
//                if(localityName.equals("Bogotá")){
//                    localityName="Bogota";
//                    userSelectedLocalityName="Bogota";
//                }

//                new RelativeUrlCreator().createURI("Colombia","COP","es-CO","Bogota");

//                new RetrofitRequester().requestPlaces(MainActivity.this);


                //new RetrofitRequester(localityName).requestPlaces(MainActivity.this);
            //}
            new RetrofitRequester(MainActivity.this).requestPlaces(MainActivity.this);

//            if(!countryName.equals("")&&!localityName.equals("")){
//                new RetrofitRequester(localityName, countryName).requestPlaces(MainActivity.this);
//            }

//            new RetrofitRequester(localityName).requestPlaces(MainActivity.this);

        }
    }


}