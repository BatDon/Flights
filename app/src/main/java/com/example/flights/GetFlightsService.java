package com.example.flights;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

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
    }
}
