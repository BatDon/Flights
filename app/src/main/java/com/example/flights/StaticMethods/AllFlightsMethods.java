package com.example.flights.StaticMethods;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AllFlightsMethods extends AppCompatActivity {

    private static AllFlightsMethods INSTANCE = null;
    ConnectivityManager connectivityManager;

    private AllFlightsMethods() {
        //connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static AllFlightsMethods getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AllFlightsMethods();
        }
        return (INSTANCE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
