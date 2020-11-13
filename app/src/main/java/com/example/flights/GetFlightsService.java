package com.example.flights;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class GetFlightsService extends IntentService {

    public static final String getFlightsService="GetFlightsService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GetFlightsService(String name) {
        super(name);
    }

    public GetFlightsService(){
        super(getFlightsService);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Timber.i("onHandleIntent called");

//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                Timber.i("onComplete for location called");
//                Location location = task.getResult();
//                if (location != null) {
//
//                    Timber.i("location does not equal null");
//
//                    Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
//                    Timber.i(String.valueOf(location.getAccuracy()));
//
//                    try {
//                        List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),
//                                location.getLongitude(),1);
//
//                        Address address=addressList.get(0);
//                        if(address==null){
//                            Timber.i("address equals null");
//                        }
//                        else{
//                            Timber.i("address does not equal null");
//                        }
//                        String countryName = address.getCountryName();
//                        String locality=address.getLocality();
//                        String addressLine=address.getAddressLine(0);
//                        Timber.i("countryName= "+countryName);
//                        Timber.i("locality= "+locality);
//                        Timber.i("addressLine= "+addressLine);
//                        Toast.makeText(MainActivity.this, ""+countryName, Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        Timber.i("error retrieving location");
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });


        Intent i = new Intent();
    }
}
