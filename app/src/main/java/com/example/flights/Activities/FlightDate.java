package com.example.flights.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.flights.Activities.DatabaseClasses.FavoriteFlightsDatabaseActivity;
import com.example.flights.Adapters.FlightDateCurrencyAdapter;
import com.example.flights.Constants;
import com.example.flights.Fragments.DatePickerFragment;
import com.example.flights.Fragments.TimePickerFragment;
import com.example.flights.Pojos.FlightDatePojos.Carrier;
import com.example.flights.Pojos.FlightDatePojos.Currency;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.Pojos.Place;
import com.example.flights.Pojos.Places;
import com.example.flights.R;
import com.example.flights.StaticMethods.AllFlightsMethods;
import com.example.flights.ViewModels.FlightDateViewModel;
import com.example.flights.ViewModels.FlightDateViewModelFactory;
import com.example.flights.ViewModels.MainActivityViewModel;
import com.example.flights.ViewModels.MainActivityViewModelFactory;

import java.io.Serializable;
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

import static com.example.flights.Constants.CLASS_NAME;
import static com.example.flights.Constants.CURRENCIES_ARRAY;
import static com.example.flights.Constants.LOCALITY_ARRAY;

//public class FlightDate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
//        TimePickerDialog.OnTimeSetListener {
//public class FlightDate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
//        FlightDateCurrencyAdapter.OnDateCurrencyListener{

public class FlightDate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        AdapterView.OnItemSelectedListener{
    public static final int DEFAULT_POSITION=-1;
    int position=-1;

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

    String activityRecreated="activityRecreated";

    private Spinner localitySpinner;
    private Spinner currencySpinner;

    boolean departureDateButtonBool=false;
    boolean returnDateButtonBool=false;

    String dateChosen;
    String dateFormatted;
    Currency currency;

    ArrayList quoteList=null;

    Bundle savedInstanceState=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("onCreate called");
        setContentView(R.layout.activity_flight_date);
        setUpMainViewModel();
        setUpFlightDateViewModel();
        setUpViewModelOnChanged();
        getOriginFlightPosition();
        setUpViews();
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
                Intent intent=new Intent(this, FavoriteFlightsDatabaseActivity.class);
                intent.putExtra(CLASS_NAME, FlightDate.this.getClass().getSimpleName());
                startActivity(intent);
                //new RetrofitRequester().requestMovies(this);
                break;
            }
            case android.R.id.home:
                Intent intent = new Intent(FlightDate.this, DepartureFlightLocations.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
        }
        return true;
    }

    public void getOriginFlightPosition() {
        Intent intent = getIntent();
        if(intent==null){
            Timber.i("intent equals null");
        }
        position = intent.getIntExtra(Constants.FLIGHT_ORIGIN_POSITION, DEFAULT_POSITION);
        if(position==DEFAULT_POSITION){
            position=flightDateViewModel.getItemPosition();
            Timber.i("position= "+position);
            if(position==DEFAULT_POSITION){
                closeOnError();
            }
        }
//        if (intent == null) {
//            //try to get position from view model
//            position=flightDateViewModel.getItemPosition();
////            Timber.i("Error getting position");
////            closeOnError();
//        }
        else {

            //position = intent.getIntExtra(Constants.FLIGHT_ORIGIN_POSITION, DEFAULT_POSITION);

            Timber.i("setting position in flightdateViewModel");
            flightDateViewModel.setItemPosition(position);
        }

//        if (position == DEFAULT_POSITION) {
//            closeOnError();
//        }
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

    private void setUpViewModelOnChanged() {
        Observer<ArrayList<Quote>> observer = new Observer<ArrayList<Quote>>() {
            int i = 0;

            @Override
            public void onChanged(@Nullable final ArrayList<Quote> quoteArrayList) {
                if(i>0 && quoteArrayList==null) {
                    Toast.makeText(FlightDate.this, "No flights found", Toast.LENGTH_SHORT).show();
                }
                if(quoteArrayList==null){
                    return;
                }
                //FlightDate.this.quoteList = new ArrayList<Quote>(quoteArrayList);
                if (quoteArrayList == null || flightDateViewModel.getCurrencyArrayList()==null || flightDateViewModel.getCarrierArrayList()==null) {
                    if(i>0) {
                        Toast.makeText(FlightDate.this, "No flights found", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                i++;
                //i = quoteList.size();
                //mainActivityViewModel.getPlaceDir();
                // Update the cached copy of the words in the adapter.
                Timber.i("onChanged triggered");
                if (i > 0) {
                    for (Quote quote : quoteArrayList) {
                        Timber.i("setUpViewModelOnChanged quote= %s", quote.getQuoteDateTime());
                    }
                    //placeRecyclerList=placeList;
//                    for (int j = 0; j < placeList.size() ; j++) {
//                        Timber.i(placeList.get(i).getCountryName());
//                    }
                    //setUpGridAdapter();
                }

//                currency=flightDateViewModel.getCurrencyArrayList().get(0);

                //createRecyclerView();
                saveQuotesFlightDateViewModel(quoteArrayList);
                saveCurrenciesFlightDateViewModel(flightDateViewModel.getCurrencyArrayList());
                saveCarriersFlightDateViewModel(flightDateViewModel.getCarrierArrayList());
                //                sendFlightDateCurrencyIntent(currency);
                sendFlightDateCurrencyIntent();
            }
        };

        flightDateViewModel.getAllQuotes().observe(this,observer);
    }

    private void saveQuotesFlightDateViewModel(ArrayList<Quote> quoteList){
        flightDateViewModel.writeToFile(quoteList);
    }

    private void saveCurrenciesFlightDateViewModel(ArrayList<Currency>currencyList){
        flightDateViewModel.writeToCurrencyFile(currencyList);
    }
    private void saveCarriersFlightDateViewModel(ArrayList<Carrier>carrierList){
        flightDateViewModel.writeToCarrierFile(carrierList);
    }

//    private void sendFlightDateCurrencyIntent(Currency currency){
//        Intent intent = new Intent(FlightDate.this, FlightDateCurrency.class);
////        intent.putExtra(Constants.QUOTE_ARRAY_LIST, (Serializable) quoteArrayList);
//        intent.putExtra(Constants.CURRENCY,currency);
//        startActivity(intent);
//
//    }
    private void sendFlightDateCurrencyIntent(){
            Intent intent = new Intent(FlightDate.this, FlightDateCurrency.class);
    //        intent.putExtra(Constants.QUOTE_ARRAY_LIST, (Serializable) quoteArrayList);
    //        intent.putExtra(Constants.CURRENCY,currency);
            startActivity(intent);
        overridePendingTransition(R.anim.anim_right_slide_in, R.anim.anim_left_slide_out);

    }


//    private void createRecyclerView() {
//        ArrayList<Currency> currencyArrayList = flightDateViewModel.getCurrencyArrayList();
//        if (currencyArrayList != null) {
//            if (!currencyArrayList.isEmpty()) {
//                currency = currencyArrayList.get(0);
//                String currencySymbol = currencyArrayList.get(0).getSymbol();
//                Timber.i("currencySymbol= " + currencySymbol);
//            }
//        }
//
//
//        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
//        String originPlaceId = sharedpreferences.getString(Constants.KEY_PREFERENCE_PLACE_ID, "SFO-sky");
//        String destinationPlace = sharedpreferences.getString(Constants.KEY_PREFERENCE_DESTINATION_PLACE, "LAX-sky");
//        String inboundDate = sharedpreferences.getString(Constants.KEY_PREFERENCE_INBOUND_DATE, "");
//
//        Quote[] quoteArray = new Quote[quoteList.size()];
//        quoteList.toArray(quoteArray);
//
//        FlightDateCurrencyAdapter flightDateCurrencyAdapter = new FlightDateCurrencyAdapter(FlightDate.this, originPlaceId, destinationPlace, inboundDate
//                , currency, quoteArray, FlightDate.this);
//    }

    private void setUpViews(){
        View simpleAppBar = findViewById(R.id.simpleAppBar);
        Toolbar dateToolbar = (Toolbar) simpleAppBar.findViewById(R.id.toolbar);

        //Toolbar departureLocationToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(dateToolbar==null){
            Timber.i("departureLocationToolbar equals null");
        }

        setSupportActionBar(dateToolbar);
        if(getSupportActionBar()!=null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        else{
            Timber.i("support action bar is null");
        }

        setUpSpinners();

         originPlaceET=findViewById(R.id.originPlaceET);
         originCountryET=findViewById(R.id.originCountryET);
         departureDateET=findViewById(R.id.departureDate);
         destinationPlaceET=findViewById(R.id.destinationPlaceET);
         returnDateET=findViewById(R.id.returnDateET);
         dateBT=findViewById(R.id.departureDateBT);
         localeET=findViewById(R.id.localeET);
         currencyET=findViewById(R.id.currencyET);

         if(activityRecreated!=null && savedInstanceState!=null && savedInstanceState.getBoolean(activityRecreated)){
             Timber.i("savedInstanceState does not equal null");
             setInitialViewValues();
         }
         else {

//        setInitialViewValues();

             SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
//        String userCountry=sharedpreferences.getString(Constants.KEY_PREFERENCE_COUNTRY, "UK");
             String userCurrency = sharedpreferences.getString(Constants.KEY_PREFERENCE_CURRENCY, "GBP");
             String userLocale = sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALE, "en-GB");
//        String userLocalityName=sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALITY_NAME,"Stockholm");

             if (placeArrayList != null) {
                 Place place = placeArrayList.get(position);
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

    }


    private void setUpSpinners() {

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

    private void setInitialViewValues(){
        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        String userPlace=sharedpreferences.getString(Constants.KEY_PREFERENCE_PLACE,"Stockholm");
        String userCountry=sharedpreferences.getString(Constants.KEY_PREFERENCE_COUNTRY, "UK");
        String userCurrency=sharedpreferences.getString(Constants.KEY_PREFERENCE_CURRENCY, "GBP");
        String userLocale=sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALE, "en-GB");
        String destinationPlace=sharedpreferences.getString(Constants.KEY_PREFERENCE_DESTINATION_PLACE,"LAX-sky");
        String outboundDate=sharedpreferences.getString(Constants.KEY_PREFERENCE_OUTBOUND_DATE,"2021-09-01");
        String inboundDate=sharedpreferences.getString(Constants.KEY_PREFERENCE_INBOUND_DATE,"");


        originPlaceET.setText(userPlace);
        originCountryET.setText(userCountry);
        departureDateET.setText(outboundDate);
        destinationPlaceET.setText(destinationPlace);
        returnDateET.setText(inboundDate);
        localeET.setText(userLocale);
        currencyET.setText(userCurrency);
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

        //TODO add back when done testing
//        if (originPlaceId.equals("") || originCountry.equals("") || departureDate.equals("")
//                || destinationPlace.equals("") || locale.equals("") || currency.equals("")){
//            Toast.makeText(this, "Fill in all required fields", Toast.LENGTH_SHORT).show();
//            return;
//        }

        String countryAbbreviation=getCountryAbbreviationFromName(originCountry);

        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //TODO remove only for testing
        editor.putString(Constants.KEY_PREFERENCE_PLACE_ID, "LAX-sky");
        editor.putString(Constants.KEY_PREFERENCE_DESTINATION_PLACE, "SFO-sky");
        editor.putString(Constants.KEY_PREFERENCE_COUNTRY, "US");
        editor.putString(Constants.KEY_PREFERENCE_COUNTRY_ID, "US");
        editor.putString(Constants.KEY_PREFERENCE_OUTBOUND_DATE, "2020-12-12");
        editor.putString(Constants.KEY_PREFERENCE_INBOUND_DATE, "2020-12-18");
        editor.putString(Constants.KEY_PREFERENCE_LOCALE, "en_US");
        editor.putString(Constants.KEY_PREFERENCE_CURRENCY, "USD");
        //TODO add back when done testing
//        editor.putString(Constants.KEY_PREFERENCE_PLACE_ID, originPlaceId);
//        editor.putString(Constants.KEY_PREFERENCE_DESTINATION_PLACE, destinationPlace);
//        editor.putString(Constants.KEY_PREFERENCE_COUNTRY, originCountry);
//        editor.putString(Constants.KEY_PREFERENCE_COUNTRY_ID, countryAbbreviation);
//        editor.putString(Constants.KEY_PREFERENCE_OUTBOUND_DATE, departureDate);
//        editor.putString(Constants.KEY_PREFERENCE_INBOUND_DATE, returnDate);
//        editor.putString(Constants.KEY_PREFERENCE_LOCALE, locale);
//        editor.putString(Constants.KEY_PREFERENCE_CURRENCY, currency);

        editor.commit();

        if(AllFlightsMethods.getInstance().isInternetConnection(FlightDate.this)){
            flightDateViewModel.requestFlightQuotes();
        }
        else{
            Toast.makeText(FlightDate.this, R.string.need_internet_message, Toast.LENGTH_SHORT).show();
            Timber.i("No internet connection");
        }

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==R.id.localitySpinner){
            localeET.setText(LOCALITY_ARRAY[position]);
        }
        //currency spinner
        else{
            currencyET.setText(CURRENCIES_ARRAY[position]);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void saveToSharedPreferences(){

        String originPlaceId = originPlaceET.getText().toString();
        String originCountry = originCountryET.getText().toString();
        String departureDate = departureDateET.getText().toString();
        String returnDate = returnDateET.getText().toString();
        String destinationPlace = destinationPlaceET.getText().toString();
        String locale = localeET.getText().toString();
        String currency = currencyET.getText().toString();

        String countryAbbreviation=getCountryAbbreviationFromName(originCountry);

        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //TODO remove only for testing
//        editor.putString(Constants.KEY_PREFERENCE_PLACE_ID, "LAX-sky");
//        editor.putString(Constants.KEY_PREFERENCE_DESTINATION_PLACE, "SFO-sky");
//        editor.putString(Constants.KEY_PREFERENCE_COUNTRY, "US");
//        editor.putString(Constants.KEY_PREFERENCE_COUNTRY_ID, "US");
//        editor.putString(Constants.KEY_PREFERENCE_OUTBOUND_DATE, "2020-12-01");
//        editor.putString(Constants.KEY_PREFERENCE_INBOUND_DATE, "2020-12-06");
//        editor.putString(Constants.KEY_PREFERENCE_LOCALE, "en_US");
//        editor.putString(Constants.KEY_PREFERENCE_CURRENCY, "USD");
        //TODO uncomment only for testing
        editor.putString(Constants.KEY_PREFERENCE_PLACE_ID, originPlaceId);
        editor.putString(Constants.KEY_PREFERENCE_DESTINATION_PLACE, destinationPlace);
        editor.putString(Constants.KEY_PREFERENCE_COUNTRY, originCountry);
        editor.putString(Constants.KEY_PREFERENCE_COUNTRY_ID, countryAbbreviation);
        editor.putString(Constants.KEY_PREFERENCE_OUTBOUND_DATE, departureDate);
        editor.putString(Constants.KEY_PREFERENCE_INBOUND_DATE, returnDate);
        editor.putString(Constants.KEY_PREFERENCE_LOCALE, locale);
        editor.putString(Constants.KEY_PREFERENCE_CURRENCY, currency);
        editor.commit();


//        editor.putString(Constants.KEY_PREFERENCE_PLACE_ID, originPlaceId);
        //editor.putString(Constants.KEY_PREFERENCE_ORIGIN_PLACE, originPlace);
//        editor.putString(Constants.KEY_PREFERENCE_COUNTRY, originCountry);
//        editor.putString(Constants.KEY_PREFERENCE_COUNTRY_ID, countryAbbreviation);
//        editor.putString(Constants.KEY_PREFERENCE_OUTBOUND_DATE, departureDate);
//        editor.putString(Constants.KEY_PREFERENCE_INBOUND_DATE, returnDate);
//        editor.putString(Constants.KEY_PREFERENCE_DESTINATION_PLACE, destinationPlace);
//        editor.putString(Constants.KEY_PREFERENCE_LOCALE, locale);
//        editor.putString(Constants.KEY_PREFERENCE_CURRENCY, currency);
//        editor.putString(Constants.KEY_PREFERENCE_OUTBOUND_DATE, departureDate);
//        editor.putString(Constants.KEY_PREFERENCE_INBOUND_DATE, returnDate);
//        editor.commit();


    }



    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        saveToSharedPreferences();
        savedInstanceState.putBoolean(activityRecreated, true);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_left_slide_in, R.anim.anim_right_slide_out);
    }


}