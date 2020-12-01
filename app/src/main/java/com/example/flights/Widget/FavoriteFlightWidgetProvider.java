package com.example.flights.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.content.Context;

import com.example.flights.Activities.DatabaseClasses.FavoriteFlightsDatabaseActivity;
import com.example.flights.Activities.FavoriteFlightsDetails;
import com.example.flights.Activities.MainActivity;
import com.example.flights.FavoriteFlightsData.FavoriteFlights;
import com.example.flights.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

public class FavoriteFlightWidgetProvider extends AppWidgetProvider {
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
////        super.onUpdate(context, appWidgetManager, appWidgetIds);
//
//        for (int appWidgetId : appWidgetIds) {
//            Intent favoriteFlightIntent = new Intent(context, FavoriteFlightsDatabaseActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, favoriteFlightIntent, 0);
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.flight_widget);
//            remoteViews.setOnClickPendingIntent(R.layout.flight_widget, pendingIntent);
//            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
//        }
//    }


    private static void updateFlightAppWidgets(Context context, String departureDate, String departurePlace, String price, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        Timber.i("updateFlightAppWidgets called");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.flight_widget);

//        SharedPreferences sharedPreferences=context.getApplicationContext().getSharedPreferences(RECIPE_PREFERENCE_FILE, MODE_PRIVATE);
//        int recipePosition=sharedPreferences.getInt(RECIPE_POSITION, 0);


        Intent intent = new Intent(context, FavoriteFlightsDetails.class);
//        Intent intent = new Intent(context, FavoriteFlightsDatabaseActivity.class);
//        intent.putExtra(RECIPE_POSITION, recipePosition);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        if(departureDate==null){
            departureDate="";
        }

        if(departurePlace==null){
            departurePlace="";
        }

        if(price==null) {
            price = "";
        }


        views.setTextViewText(R.id.favoriteOutgoingDate, departureDate);
        views.setTextViewText(R.id.favoriteOutgoingPlace, departurePlace);
        views.setTextViewText(R.id.favoriteOutgoingPrice, price);

        //pending intent when text view in widget clicked opens RecipeIngredientsSteps activity
//        views.setOnClickPendingIntent(R.layout.flight_widget, pendingIntent);
        views.setOnClickPendingIntent(R.id.widgetLinearLayout, pendingIntent);


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    //1. onUpdate is called when widget is placed on homescreen
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timber.i("onUpdate called");
        FavoriteFlightWidgetService.startActionUpdateFlight(context);

//        DatabaseReference flightDatabase = FirebaseDatabase.getInstance().getReference();
//
//        ValueEventListener flightListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                FavoriteFlights favoriteFlights = dataSnapshot.getValue(FavoriteFlights.class);
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Timber.i("reading data cancelled");
//                // ...
//            }
//        };
//        flightDatabase.addListenerForSingleValueEvent(flightListener);

// ...

//        FavoriteFlightsDatabaseActivity favoriteFlightsDatabaseActivity=new FavoriteFlightsDatabaseActivity();
    }


    public static void updateFlightWidgets(Context context, String departureDate , String departurePlace, String price, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds){

        Timber.i("departureDate= "+departureDate);
        Timber.i("departurePlace= "+departurePlace);
        Timber.i("price= "+price);

        Timber.i("updateFlightWidgets called for loop to start");


        //There can be more one appWidget placed on users screen
        //all must be updated
        for (int appWidgetId : appWidgetIds) {
            updateFlightAppWidgets(context, departureDate, departurePlace, price, appWidgetManager, appWidgetId);
        }
    }


    //when widget is first created
    @Override
    public void onEnabled(Context context) {
    }

    //when widget is deleted
    @Override
    public void onDisabled(Context context) {
    }
}

