package com.example.flights;

import com.example.flights.Pojos.Place;
import com.example.flights.Retrofit.RetrofitRequester;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RetrofitRequester.onRetrofitListener {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";

    private static final int START_LEVEL = 1;
    private int mLevel;
    private Button mNextLevelButton;
    private InterstitialAd mInterstitialAd;
    private TextView mLevelTextView;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(BuildConfig.DEBUG){
        Timber.plant(new Timber.DebugTree());
//        }

        setContentView(R.layout.activity_main);

        // Create the next level button, which tries to show an interstitial when clicked.
        mNextLevelButton = ((Button) findViewById(R.id.next_level_button));
        mNextLevelButton.setEnabled(false);
        mNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();

//                // starts and triggers GetFlightsService
//                Intent i = new Intent(MainActivity.this, GetFlightsService.class);
//// potentially add data to the intent
//                i.putExtra("KEY1", "Value to be used by the service");
//                MainActivity.this.startService(i);

                //location
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    Timber.i("permission granted");
                    getLastLocation();
                } else {
                    //permission denied
                    Timber.i("permission denied onClick");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
                }


                //airport locations
                new RetrofitRequester().requestPlaces(MainActivity.this);
            }

            ;

        });

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        // Create the text view to show the level number.
        mLevelTextView = (TextView) findViewById(R.id.level);
        mLevel = START_LEVEL;

        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
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
                goToNextLevel();
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
        if (placeList == null) {
            Timber.i("placeList equals null");
        }
        Place place = placeList.get(0);
        String placeId = place.getCityId();
        Timber.i("placeId= " + placeId);
    }


    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            Timber.i("permission needed before can access location");
            return;
        }

        // starts and triggers GetFlightsService
        Intent i = new Intent(MainActivity.this, GetFlightsService.class);
// potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        MainActivity.this.startService(i);



        Timber.i("getLastLocation called and passed permissions");
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Timber.i("onComplete for location called");
                Location location = task.getResult();
                if (location != null) {

                    Timber.i("location does not equal null");

                    Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                    Timber.i(String.valueOf(location.getAccuracy()));

                    try {
                        List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(),1);

                        Address address=addressList.get(0);
                        if(address==null){
                            Timber.i("address equals null");
                        }
                        else{
                            Timber.i("address does not equal null");
                        }
                        String countryName = address.getCountryName();
                        String locality=address.getLocality();
                        String addressLine=address.getAddressLine(0);
                        Timber.i("countryName= "+countryName);
                        Timber.i("locality= "+locality);
                        Timber.i("addressLine= "+addressLine);
                        Toast.makeText(MainActivity.this, ""+countryName, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Timber.i("error retrieving location");
                        e.printStackTrace();
                    }
                }
            }
        });
        }



    public class PlaceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Timber.i("onReceive in ResponseReceiver called");
            intent.getStringExtra(Constants.PLACE_RESPONSE);

        }
    }
    }