package com.example.flights.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.flights.Constants;
import com.example.flights.Fragments.DatePickerFragment;
import com.example.flights.Fragments.TimePickerFragment;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.Pojos.Place;
import com.example.flights.Pojos.Places;
import com.example.flights.R;
import com.example.flights.ViewModels.FlightDateViewModel;
import com.example.flights.ViewModels.FlightDateViewModelFactory;
import com.example.flights.ViewModels.MainActivityViewModel;
import com.example.flights.ViewModels.MainActivityViewModelFactory;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import timber.log.Timber;

//public class FlightDate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
//        TimePickerDialog.OnTimeSetListener {
public class FlightDate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final int DEFAULT_POSITION=-1;
    int position;

    MainActivityViewModel mainActivityViewModel;
    ArrayList<Place> placeArrayList;

    FlightDateViewModel flightDateViewModel;

    //Views
    EditText originPlaceET;
    EditText originCountryET;
    EditText departureDateET;
    EditText destinationPlaceET;
    EditText returnDateET;
    Button dateBT;
    EditText localeET;
    EditText currencyET;

    boolean departureDateButtonBool=false;
    boolean returnDateButtonBool=false;

    String dateChosen;
    String dateFormatted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_date);
        setUpMainViewModel();
        setUpFlightDateViewModel();
        setUpViewModelOnChanged();
        getOriginFlightPosition();
        setUpViews();
    }

    public void getOriginFlightPosition() {
        Intent intent = getIntent();
        if (intent == null) {
            Timber.i("Error getting position");
            closeOnError();
        }

        position = intent.getIntExtra(Constants.FLIGHT_ORIGIN_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            closeOnError();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void setUpMainViewModel(){
        MainActivityViewModelFactory mainActivityViewModelFactory=new MainActivityViewModelFactory(getApplication());
        mainActivityViewModel = ViewModelProviders.of(this, mainActivityViewModelFactory).get(MainActivityViewModel.class);
        placeArrayList=mainActivityViewModel.getPlaceDir();
    }


    private void setUpFlightDateViewModel(){
        FlightDateViewModelFactory flightDateViewModelFactory=new FlightDateViewModelFactory(getApplication());
        flightDateViewModel=ViewModelProviders.of(this, flightDateViewModelFactory).get(FlightDateViewModel.class);
    }

    private void setUpViewModelOnChanged(){
        Observer<List<Quote>> observer=new Observer<List<Quote>>() {
            int i=0;
            @Override
            public void onChanged(@Nullable final List<Quote> quoteList) {
                if(quoteList==null){
                    Toast.makeText(FlightDate.this, "No flights found", Toast.LENGTH_SHORT).show();
                    return;
                }
                i=quoteList.size();
                //mainActivityViewModel.getPlaceDir();
                // Update the cached copy of the words in the adapter.
                Timber.i("onChanged triggered");
                if(i>0){
                    //placeRecyclerList=placeList;
//                    for (int j = 0; j < placeList.size() ; j++) {
//                        Timber.i(placeList.get(i).getCountryName());
//                    }
                    //setUpGridAdapter();
                }
//                i++;
//                //mainViewModel.requestMovies();
//                resultList=movies;
//                if(mainViewModel.getAllMovies().getValue()!=null){
//                    mainViewModel.requestMovies();
//                }
            }
        };

        flightDateViewModel.getAllQuotes().observe(this,observer);
    }


    private void setUpViews(){
         originPlaceET=findViewById(R.id.originPlaceET);
         originCountryET=findViewById(R.id.originCountryET);
         departureDateET=findViewById(R.id.departureDate);
         destinationPlaceET=findViewById(R.id.destinationPlaceET);
         returnDateET=findViewById(R.id.returnDateET);
         dateBT=findViewById(R.id.departureDateBT);
         localeET=findViewById(R.id.localeET);
         currencyET=findViewById(R.id.currencyET);

        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        String userCountry=sharedpreferences.getString(Constants.KEY_PREFERENCE_COUNTRY, "UK");
        String userCurrency=sharedpreferences.getString(Constants.KEY_PREFERENCE_CURRENCY, "GBP");
        String userLocale=sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALE, "en-GB");
        String userLocalityName=sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALITY_NAME,"Stockholm");

         if(placeArrayList!=null){
             Place place=placeArrayList.get(position);
//             originPlaceET.setText(place.getPlaceName());
             originPlaceET.setText(place.getPlaceId());
             originCountryET.setText(place.getCountryName());
             String todaysDate = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date()));
             departureDateET.setText(todaysDate);
//             destinationPlaceET.setText()
//             returnDateET.setText()
             localeET.setText(userLocale);
             currencyET.setText(userCurrency);

             //TODO REMOVE only used for testing
             destinationPlaceET.setText("NYO-sky");
         }



    }

    public void departureButtonClicked(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "Date Picker");
        departureDateButtonBool=true;
    }

    public void returnButtonClicked(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "Date Picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Timber.i("onDateSet called");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //Timber.i("calendar.getTime() "+calendar.getTime());
        dateFormatted=""+year+"-"+(month+1)+"-"+dayOfMonth;
        //dateChosen= DateFormat.getInstance().format(calendar.getTime());
        //dateChosen = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Toast.makeText(this, ""+dateFormatted, Toast.LENGTH_SHORT).show();

        if(departureDateButtonBool){
            departureDateET.setText(dateFormatted);
            departureDateButtonBool=false;
        }
        else{
            returnDateET.setText(dateFormatted);
        }
//        DialogFragment timePicker = new TimePickerFragment();
//        timePicker.show(getSupportFragmentManager(), "Time Picker");
    }

//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
////        https://stackoverflow.com/questions/7527138/timepicker-how-to-get-am-or-pm
//
//        String AM_PM ;
//        if(hourOfDay < 12) {
//            AM_PM = "AM";
//        } else {
//            AM_PM = "PM";
//        }
//
////        String time="Hour: " + hourOfDay + " Minute: " + minute+" ";
//        String time=hourOfDay+" : "+minute+" "+AM_PM;
//        String completeDate=dateChosen+"\n"+time;
//
//        if(departureDateButtonBool){
//            departureDateET.setText(completeDate);
//            departureDateButtonBool=false;
//        }
//        else{
//            returnDateET.setText(completeDate);
//        }
//    }


    public void searchFlights(View view) {
        //search flights using new api
        //returnDateET is not a required parameter
//        String originPlace = originPlaceET.getText().toString();
        String originPlaceId = originPlaceET.getText().toString();
        String originCountry = originCountryET.getText().toString();
        String departureDate = departureDateET.getText().toString();
        String returnDate = returnDateET.getText().toString();
        String destinationPlace = destinationPlaceET.getText().toString();
        String locale = localeET.getText().toString();
        String currency = currencyET.getText().toString();

//        if (originPlace.equals("") || originCountry.equals("") || departureDate.equals("")
//         || destinationPlace.equals("") || locale.equals("") || currency.equals("")){
//            return;
//        }

        if (originPlaceId.equals("") || originCountry.equals("") || departureDate.equals("")
                || destinationPlace.equals("") || locale.equals("") || currency.equals("")){
            Toast.makeText(this, "Fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String countryAbbreviation=getCountryAbbreviationFromName(originCountry);

        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Constants.KEY_PREFERENCE_PLACE_ID, originPlaceId);
        //editor.putString(Constants.KEY_PREFERENCE_ORIGIN_PLACE, originPlace);
        editor.putString(Constants.KEY_PREFERENCE_COUNTRY, originCountry);
        editor.putString(Constants.KEY_PREFERENCE_COUNTRY_ID, countryAbbreviation);
        editor.putString(Constants.KEY_PREFERENCE_OUTBOUND_DATE, departureDate);
        editor.putString(Constants.KEY_PREFERENCE_INBOUND_DATE, returnDate);
        editor.putString(Constants.KEY_PREFERENCE_DESTINATION_PLACE, destinationPlace);
        editor.putString(Constants.KEY_PREFERENCE_LOCALE, locale);
        editor.putString(Constants.KEY_PREFERENCE_CURRENCY, currency);
        editor.putString(Constants.KEY_PREFERENCE_OUTBOUND_DATE, departureDate);
        editor.putString(Constants.KEY_PREFERENCE_INBOUND_DATE, returnDate);
        editor.commit();

        flightDateViewModel.requestFlightQuotes();

    }

//    https://stackoverflow.com/questions/14155049/iso2-country-code-from-country-name

    public String getCountryAbbreviationFromName(String countryName){
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }

        return countries.get(countryName);

    }


}