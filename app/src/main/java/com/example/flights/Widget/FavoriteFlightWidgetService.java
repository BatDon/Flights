package com.example.flights.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.flights.FavoriteFlightsData.FavoriteFlights;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import timber.log.Timber;

import static com.example.flights.Constants.ACTION_INTENT_OPEN_FLIGHT;
import static com.example.flights.Constants.FLIGHT_DEPARTURE_DATE;
import static com.example.flights.Constants.FLIGHT_DEPARTURE_PLACE;
import static com.example.flights.Constants.FLIGHT_PRICE;

public class FavoriteFlightWidgetService extends IntentService {


    //declare this name as constant
    public FavoriteFlightWidgetService() {
        super("FavoriteFlightWidgetService");
        Timber.i("FavoriteFlightWidgetService constructor called");
        //super(this.getString(R.string.baking_widget_service));
    }


    public void updateFlightWidgets(){
        Timber.i("updateREcipeWidgets called");

        //getSharedPreferences find current recipe and place string in textview
        //        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

    }


    //3. onHandleIntent called when starting service called by startActionUpdateRecipe
    protected void onHandleIntent(Intent intent) {
        Timber.i("onHandleIntent called");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INTENT_OPEN_FLIGHT.equals(action)) {
//                int recipePosition=intent.getIntExtra(RECIPE_POSITION,0);
                //after passing intent filter start background work
//                handleActionRecipeUpdate(recipePosition);
                handleActionFlightUpdate(intent);
            }
        }
    }


//    public static void enqueueRecipeWork(Context context, Intent workIntent) {
//        Timber.i("enqueRecipeWork called");
//        enqueueWork(context, BakingWidgetService.class, JOB_ID, workIntent);
//    }

//    private void handleActionRecipeUpdate(int recipePosition) {

    //4. onHandleIntent calls this
    private void handleActionFlightUpdate(Intent flightIntent) {
        Timber.i("handleActionFlightUpdate called");
//        JSONUtility jsonUtility= JSONUtility.createJSONUtilityInstance();
//        ArrayList<String> recipeNames=jsonUtility.getRecipeNames();
//        String recipeName = recipeNames.get(recipePosition);
//        String recipe=jsonUtility.getRecipeOnlyIngredientsAsString(recipePosition);

        String departureDate="";
        String departurePlace="";
        String price="";

        if(ACTION_INTENT_OPEN_FLIGHT.equals(flightIntent.getAction())){
            departureDate=flightIntent.getStringExtra(FLIGHT_DEPARTURE_DATE);
            departurePlace=flightIntent.getStringExtra(FLIGHT_DEPARTURE_PLACE);
            price=flightIntent.getStringExtra(FLIGHT_PRICE);

        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FavoriteFlightWidgetProvider.class));
        FavoriteFlightWidgetProvider.updateFlightWidgets(this, departureDate, departurePlace, price, appWidgetManager, appWidgetIds);

    }




    //2. provider calls this method
    public static void startActionUpdateFlight(Context context){

        Timber.i("startActionUpdateRecipe called");


        DatabaseReference flightDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener flightListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //int t=dataSnapshot.getChildren();
                Iterable<DataSnapshot> dataSnapshotChildren= dataSnapshot.getChildren();
                if(dataSnapshotChildren.iterator().hasNext()){
                    DataSnapshot dataSnapshot1=dataSnapshotChildren.iterator().next();
                    FavoriteFlights favoriteFlights=dataSnapshot1.getValue(FavoriteFlights.class);
                    Timber.i("flightObject price "+favoriteFlights.getPrice());
                    String departureDate=favoriteFlights.getOutgoingDate();
                    String departurePlace=favoriteFlights.getOriginPlace();
                    String price=favoriteFlights.getPrice();
                    Intent favoriteFlightIntent = new Intent(context, FavoriteFlightWidgetService.class);
                    favoriteFlightIntent.setAction(ACTION_INTENT_OPEN_FLIGHT);
                    favoriteFlightIntent.putExtra(FLIGHT_DEPARTURE_DATE, departureDate);
                    favoriteFlightIntent.putExtra(FLIGHT_DEPARTURE_PLACE, departurePlace);
                    favoriteFlightIntent.putExtra(FLIGHT_PRICE, price);
                    Timber.i("startService before call");
                    context.startService(favoriteFlightIntent);

//                    flightObject.toString();
//                    Timber.i("flightObject= "+flightObject.toString());
//                    Timber.i("flightObject class= "+flightObject.getClass().toString());
                }
                else{
                    Intent favoriteFlightIntent = new Intent(context, FavoriteFlightWidgetService.class);
                    favoriteFlightIntent.setAction(ACTION_INTENT_OPEN_FLIGHT);
                    favoriteFlightIntent.putExtra(FLIGHT_DEPARTURE_DATE, "");
                    favoriteFlightIntent.putExtra(FLIGHT_DEPARTURE_PLACE, "");
                    favoriteFlightIntent.putExtra(FLIGHT_PRICE, "");
                    context.startService(favoriteFlightIntent);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Timber.i("reading data cancelled");
                // ...
            }
        };
        flightDatabase.addListenerForSingleValueEvent(flightListener);

    }

}
