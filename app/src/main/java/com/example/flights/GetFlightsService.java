package com.example.flights;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class GetFlightsService extends IntentService {

    public static final String getFlightsService = "GetFlightsService";

    FusedLocationProviderClient fusedLocationProviderClient;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GetFlightsService(String name) {
        super(name);
    }

    public GetFlightsService() {
        super(getFlightsService);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Timber.i("onHandleIntent called");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Timber.i("onComplete for location called");
                Location location = task.getResult();
                if (location != null) {

                    Timber.i("location does not equal null");

                    Geocoder geocoder = new Geocoder(GetFlightsService.this, Locale.getDefault());
                    Timber.i(String.valueOf(location.getAccuracy()));

                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);

                        Address address = addressList.get(0);
                        if (address == null) {
                            Timber.i("address equals null");
                        } else {
                            Timber.i("address does not equal null");
                        }
                        String countryName = address.getCountryName();
                        String locality = address.getLocality();
                        String addressLine = address.getAddressLine(0);
                        Timber.i("countryName= " + countryName);
                        Timber.i("locality= " + locality);
                        Timber.i("addressLine= " + addressLine);


                        Intent broadcastIntent = new Intent();

//                        IntentFilter filter = new IntentFilter(Constants.PLACE_BROADCAST_ACTION);
                        broadcastIntent.setAction(Constants.PLACE_BROADCAST_ACTION);
                        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        broadcastIntent.putExtra(Constants.PLACE_COUNTRY, countryName);
                        broadcastIntent.putExtra(Constants.PLACE_LOCALITY,locality);
                        sendBroadcast(broadcastIntent);
                        //Toast.makeText(MainActivity.this, "" + countryName, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Timber.i("error retrieving location");
                        e.printStackTrace();
                    }
                }
            }
        });


        Intent i = new Intent();
    }
}
