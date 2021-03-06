package com.example.flights.Activities;

import com.example.flights.Activities.DatabaseClasses.FavoriteFlightsDatabaseActivity;
import com.example.flights.Constants;
import com.example.flights.GetFlightsService;
import com.example.flights.R;
import com.example.flights.StaticMethods.AllFlightsMethods;
import com.example.flights.ViewModels.DatabaseViewModel;
import com.example.flights.ViewModels.DatabaseViewModelFactory;
import com.example.flights.ViewModels.MainActivityViewModel;
import com.example.flights.ViewModels.MainActivityViewModelFactory;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;

import timber.log.Timber;

import static com.example.flights.Constants.CLASS_NAME;
import static com.example.flights.Constants.CURRENCIES_ARRAY;
import static com.example.flights.Constants.LOCALITY_ARRAY;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//public class MainActivity extends AppCompatActivity implements RetrofitRequester.onRetrofitListener {

    //public class MainActivity extends AppCompatActivity implements RetrofitRequester.onRetrofitListener,
//        AdapterView.OnItemSelectedListener {
    // Remove the below line after defining your own ad unit ID.


    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";

    private static final int START_LEVEL = 1;
    private int mLevel;
    private Button mNextLevelButton;
    private InterstitialAd mInterstitialAd;
    private EditText countryEditText;
    private EditText placeEditText;
    private EditText currencyEditText;
    private EditText localityEditText;

    private Spinner localitySpinner;
    private Spinner currencySpinner;



    //private Spinner countrySpinner;
    private MainActivityViewModel mainActivityViewModel;

    String[] countryArray;

    SharedPreferences sharedpreferences;

    FusedLocationProviderClient fusedLocationProviderClient;

    String userSelectedCountryName;
    String userSelectedLocalityName;

    DatabaseViewModel databaseViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(BuildConfig.DEBUG){
        Timber.plant(new Timber.DebugTree());
        sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);

        boolean firstTime=sharedpreferences.getBoolean(Constants.API_KEY_BOOLEAN, true);

        if(firstTime){
            Intent clientApiKeyIntent=new Intent(MainActivity.this, ClientApiKey.class);
//            clientApiKeyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(clientApiKeyIntent);
        }else {

            setUpViews();

            setUpViewModel();

            setUpDatabaseViewModel();

            //databaseViewModel.getDatabaseData();


            MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

            // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
            mInterstitialAd = newInterstitialAd();
            loadInterstitial();

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
            Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();

            initializeBroadcastReceiver();

//        sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);

            //TODO add back in after testing
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putString(Constants.KEY_PREFERENCE_COUNTRY, "UK");
//            editor.putString(Constants.KEY_PREFERENCE_CURRENCY, "GBP");
//            editor.putString(Constants.KEY_PREFERENCE_LOCALE, "en-GB");
//            editor.putString(Constants.KEY_PREFERENCE_PLACE, "Stockholm");





//        editor.putString("country", "UK");
//        editor.putString("currency", "GBP");
//        editor.putString("locale", "en-GB");
//        editor.putString("localityName","Stockholm");
//            editor.commit();
        }
    }

    private void setUpViews() {
        setContentView(R.layout.activity_main);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);

        setUpSpinners();


        countryEditText = findViewById(R.id.countryEditText);
        placeEditText = findViewById(R.id.placeEditText);
        currencyEditText = findViewById(R.id.currencyEditText);
        localityEditText = findViewById(R.id.localeEditText);

        //setInitialViewValues();


        // Create the text view to show the level number.

        // Create the next level button, which tries to show an interstitial when clicked.
        mNextLevelButton = ((Button) findViewById(R.id.flight_origin_location_button));
        mNextLevelButton.setEnabled(false);

        //returns location fills in for origin flight
        Intent i = new Intent(MainActivity.this, GetFlightsService.class);
// potentially add data to the intent
//                i.putExtra("KEY1", "Value to be used by the service");
        MainActivity.this.startService(i);

        mNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO uncomment after done testing
                //correct but blocked out for testing

                if(countryEditText.getText().toString().equals("") || placeEditText.getText().toString().equals("") ||
                        currencyEditText.getText().toString().equals("") || localityEditText.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please fill in all flight information", Toast.LENGTH_LONG).show();
                    return;
                }
                showInterstitial();


                //TODO unblock when done testing
                //correct but blocked out for testing
                saveToSharedPreferences();

                if(AllFlightsMethods.getInstance().isInternetConnection(MainActivity.this)){
                    mainActivityViewModel.requestFlightDestinations();
                }
                else{
                    Toast.makeText(MainActivity.this, R.string.need_internet_message, Toast.LENGTH_SHORT).show();
                    Timber.i("No internet connection");
                }

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

    //TODO remove only for testing
//    private void setInitialViewValues(){
//        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
//        String userCountry=sharedpreferences.getString(Constants.KEY_PREFERENCE_COUNTRY, "UK");
//        String userCurrency=sharedpreferences.getString(Constants.KEY_PREFERENCE_CURRENCY, "GBP");
//        String userLocale=sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALE, "en-GB");
//        String userLocalityName=sharedpreferences.getString(Constants.KEY_PREFERENCE_PLACE,"Stockholm");

//        placeEditText.setText(userLocalityName);
//        countryEditText.setText(userCountry);
//        localityEditText.setText(userLocale);
//        currencyEditText.setText(userCurrency);




    public void setUpSpinners() {

        localitySpinner = findViewById(R.id.localitySpinner);
        localitySpinner.setOnItemSelectedListener(this);
        ArrayAdapter localityArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,LOCALITY_ARRAY);
        localityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        localitySpinner.setAdapter(localityArrayAdapter);
        currencySpinner = findViewById(R.id.currencySpinner);
        currencySpinner.setOnItemSelectedListener(this);
        ArrayAdapter currencyArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,CURRENCIES_ARRAY);
        currencyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        currencySpinner.setAdapter(currencyArrayAdapter);
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
                //TODO change all intents to activities
//                Intent intent=new Intent(this, FavoriteFlightsActivity.class);
                Intent intent = new Intent(this, FavoriteFlightsDatabaseActivity.class);
                intent.putExtra(CLASS_NAME, MainActivity.this.getClass().getSimpleName());
                startActivity(intent);

                //new RetrofitRequester().requestMovies(this);
                break;
            }
        }
        return true;
    }

    private void setUpViewModel() {
        MainActivityViewModelFactory mainActivityViewModelFactory = new MainActivityViewModelFactory(getApplication());
        mainActivityViewModel = ViewModelProviders.of(this, mainActivityViewModelFactory).get(MainActivityViewModel.class);
        //context=movieDetailsViewModel.getApplication();
    }

    private void setUpDatabaseViewModel() {
        DatabaseViewModelFactory databaseViewModelFactory = new DatabaseViewModelFactory(getApplication());
        databaseViewModel = ViewModelProviders.of(this, databaseViewModelFactory).get(DatabaseViewModel.class);
    }

    private void initializeBroadcastReceiver() {

        IntentFilter filter = new IntentFilter(Constants.PLACE_BROADCAST_ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        PlaceReceiver placeReceiver = new PlaceReceiver();
        registerReceiver(placeReceiver, filter);
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
                Intent intent = new Intent(MainActivity.this, DepartureFlightLocations.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_right_slide_in, R.anim.anim_left_slide_out);

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
        mInterstitialAd = newInterstitialAd();
//        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        loadInterstitial();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==R.id.localitySpinner){
            localityEditText.setText(LOCALITY_ARRAY[position]);
        }
        //currency spinner
        else{
            currencyEditText.setText(CURRENCIES_ARRAY[position]);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


//    @Override
//    public void onRetrofitFinished(List<Place> placeList) {
//        Timber.i("onPopularRetrofitFinished called");
//
//        Timber.i("userSelectedCountryName= "+userSelectedCountryName);
//        Timber.i("userSelectedLocalityName= "+userSelectedLocalityName);
//        if (placeList == null) {
//            Timber.i("placeList equals null");
//        }
//        else {
//            for (Place place : placeList) {
//                String countryName = place.getCountryName();
//                Timber.i("countryName= " + countryName);
//                String cityName = place.getPlaceName();
//                Timber.i("cityName= " + cityName);
//
//                if (countryName.equals(userSelectedCountryName)) {
//                    if (cityName.equals(userSelectedLocalityName)) {
//                        Timber.i("match found");
//                    } else {
//                        Timber.i("countryName match but not localityName Match");
//                    }
//                } else {
//                    Timber.i("wrong country returned");
//                }
//
//
//                //        Place place = placeList.get(0);
//                //        String placeId = place.getCityId();
//                //        Timber.i("placeId= " + placeId);
//            }
//        }
//        Timber.i("for each loop finished onPopularRetrofitFinished");
//    }

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
            String countryName = "";
            String localityName = "";
            SharedPreferences.Editor editor = sharedpreferences.edit();
            if (intent.hasExtra(Constants.PLACE_COUNTRY)) {
                countryName = intent.getStringExtra(Constants.PLACE_COUNTRY);
                userSelectedCountryName = countryName;
                Timber.i("countryName= " + countryName);

//                editor.putString(Constants.KEY_PREFERENCE_COUNTRY, userSelectedCountryName);
//                editor.commit();
                countryEditText.setText(userSelectedCountryName);
            }
            if (intent.hasExtra(Constants.PLACE_LOCALITY)) {
                localityName = intent.getStringExtra(Constants.PLACE_LOCALITY);
                userSelectedLocalityName = localityName;
                Timber.i("localityName " + localityName);
                //Remove just for testing
                if (localityName.equals("Bogotá")) {
                    localityName = "Bogota";
                    userSelectedLocalityName = "Bogota";
//                    editor.putString(Constants.KEY_PREFERENCE_LOCALITY_NAME, userSelectedLocalityName);
//                    editor.commit();
                    placeEditText.setText(userSelectedLocalityName);
                }
                placeEditText.setText(userSelectedLocalityName);
//                editor.putString(Constants.KEY_PREFERENCE_LOCALITY_NAME, userSelectedLocalityName);


//                new RelativeUrlCreator().createURI("Colombia","COP","es-CO","Bogota");

                //}


//            if(!countryName.equals("")&&!localityName.equals("")){
//                new RetrofitRequester(localityName, countryName).requestPlaces(MainActivity.this);
//            }

            }
//            new RetrofitRequester(MainActivity.this).requestPlaces(MainActivity.this);
        }
    }

    public void saveToSharedPreferences(){
        String place=placeEditText.getText().toString();
        String country=countryEditText.getText().toString();
        String locality=localityEditText.getText().toString();
        String currency=currencyEditText.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Constants.KEY_PREFERENCE_COUNTRY, country);
        editor.putString(Constants.KEY_PREFERENCE_PLACE, place);
        editor.putString(Constants.KEY_PREFERENCE_LOCALE, locality);
        editor.putString(Constants.KEY_PREFERENCE_CURRENCY, currency);
        editor.commit();
    }

//    private boolean isInternetConnection() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
//    }



}


//  OutgoingFlight outgoingFlight=new OutgoingFlight("USD","59","2018-05-11T00:00:00");
//                ReturnFlight returnFlight=new ReturnFlight("USD","59","2018-06-22T00:00:00");

// Write a message to the database
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference outgoingFlightReference = database.getReference("outgoingFlight");

//        outgoingFlightReference.child("flight2").setValue(outgoingFlight);