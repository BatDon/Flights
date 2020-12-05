package com.example.flights.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flights.Api.TravelApi;
import com.example.flights.Constants;
import com.example.flights.Pojos.FlightDatePojos.Airports;
import com.example.flights.Pojos.FlightDatePojos.Carrier;
import com.example.flights.Pojos.FlightDatePojos.Currency;
import com.example.flights.Pojos.FlightDatePojos.OutboundDate;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.Pojos.FlightDatePojos.Quotes;
import com.example.flights.Pojos.Place;
import com.example.flights.Pojos.Places;
import com.example.flights.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RetrofitRequesterQuote extends AppCompatActivity {

    String userLocality;
    String userCountry;

    Context mContext;
    Map<String, String> headersMap;

    Call<Airports> call;

    public RetrofitRequesterQuote(Context context){

        mContext=context;
        headersMap=createHeadersMap();

    }

//    public RetrofitRequesterQuote(String userLocality, String userCountry){
//        this.userLocality=userLocality;
//        this.userCountry=userCountry;
//    }

//    public interface onRetrofitListener {
//        public void onRetrofitFinished(List<Quote> quoteList);
//    }
    public interface onRetrofitListener {
//        public void onRetrofitQuoteFinished(List<Object> quoteCurrencyList);
public void onRetrofitQuoteFinished(ArrayList<Quote> quoteArrayList, ArrayList<Currency> currencyArrayList, ArrayList<Carrier> carrierArrayList);
    }

    private onRetrofitListener onRetrofitListener;


    public void requestQuotes(onRetrofitListener onRetrofitListener) {

        this.onRetrofitListener = onRetrofitListener;

        TravelApi travelApi = RetrofitClientQuote.getRetrofitInstance().create(TravelApi.class);

        if(travelApi==null){
            Timber.i("travelApi equals null");
        }

//        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        String originPlaceId=sharedpreferences.getString(Constants.KEY_PREFERENCE_PLACE_ID,"SFO-sky");
        String userCountry=sharedpreferences.getString(Constants.KEY_PREFERENCE_COUNTRY, "UK");
        String userCountryId=sharedpreferences.getString(Constants.KEY_PREFERENCE_COUNTRY_ID,"UK");
        String userCurrency=sharedpreferences.getString(Constants.KEY_PREFERENCE_CURRENCY, "GBP");
        String userLocale=sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALE, "en-GB");
        //String userLocalityName=sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALITY_NAME,"Stockholm");
        String originPlace=sharedpreferences.getString(Constants.KEY_PREFERENCE_ORIGIN_PLACE,"SFO-sky");
        String destinationPlace=sharedpreferences.getString(Constants.KEY_PREFERENCE_DESTINATION_PLACE,"LAX-sky");
        String outboundDate=sharedpreferences.getString(Constants.KEY_PREFERENCE_OUTBOUND_DATE,"2021-09-01");
        String inboundDate=sharedpreferences.getString(Constants.KEY_PREFERENCE_INBOUND_DATE,"");

        Timber.i("originPlace= "+originPlace);
        Timber.i("originPlaceId= "+originPlaceId);
        Timber.i("inboundDate= "+inboundDate);


//        Call<List<Quote>> call = travelApi.getQuotes(userCountry,userCurrency,userLocale, originPlace, destinationPlace,
//                outboundDate, inboundDate);
//        if(inboundDate.equals("")){
//            Call<Quotes> call = travelApi.getQuotesNoInboundDate(userCountryId,userCurrency,userLocale, originPlaceId, destinationPlace,
//                    outboundDate);
//        }


        if(inboundDate.equals("")){
            call = travelApi.getAirportNoInboundDate(headersMap, userCountryId,userCurrency,userLocale, originPlaceId, destinationPlace,
                    outboundDate);
        }
        else{
            call = travelApi.getAirport(headersMap, userCountryId,userCurrency,userLocale, originPlaceId, destinationPlace,
                    outboundDate, inboundDate);
        }


//        if(inboundDate.equals("")){
//            call = travelApi.getAirportNoInboundDate(userCountryId,userCurrency,userLocale, originPlaceId, destinationPlace,
//                    outboundDate);
//        }
//        else{
//            call = travelApi.getAirport(userCountryId,userCurrency,userLocale, originPlaceId, destinationPlace,
//                    outboundDate, inboundDate);
//        }

//        Call<Airports> call = travelApi.getAirport(userCountryId,userCurrency,userLocale, originPlaceId, destinationPlace,
//                outboundDate, inboundDate);
//       Stockholm will be replaced by user selection
//        Call<Places> call = travelApi.getPlaces("Stockholm");
//        Call<Places> call = travelApi.getPlaces("Bogotá");
//        Call<Places> call = travelApi.getPlaces(this.userLocality);
//        Call<Places> call = travelApi.getPlaces();

        Timber.i("requestQuote called");


        call.enqueue(new Callback<Airports>() {
            @Override
            public void onResponse(Call<Airports> call, Response<Airports> response) {
                Timber.i("onResponse called");
                Timber.i("response= %s", response);

                ArrayList<Carrier> carrierArrayList;
                if(response==null){
                    Timber.i("call equals null");
                    //     Toast.makeText(RetrofitRequesterQuote.this, "No flights found", Toast.LENGTH_SHORT).show();
                    if(onRetrofitListener !=null){
                        onRetrofitListener.onRetrofitQuoteFinished(null, null, null);
                    }
                    return;
                }

                if(response.body()==null){
                    Timber.i("airports equals null");
                    if(onRetrofitListener !=null){
                        onRetrofitListener.onRetrofitQuoteFinished(null, null, null);
                    }
                    return;
                }
                Airports airports=response.body();
                Timber.i("airports= "+airports.toString());

                List<Quote> quoteList=airports.getQuotes();
                List<Carrier> carrierList=airports.getCarriers();
               // List<Quote> quoteList=response.body().getQuotes();
                //Quotes quotes= (Quotes) response.body().getQuotes();
                //List<Quote> quoteList=quotes.getQuotes();
                List<Currency> currencyList=airports.getCurrencies();
                if(currencyList==null){
                    Timber.i("currencyList equals null");
                    return;
                }
                if(currencyList.isEmpty()){
                    Timber.i("currencyList is empty");
                    return;
                }
                //List<Quote> quoteList = generateDataList(response.body());
                if(quoteList==null){
                    Timber.i("quoteList equals null");
               //     Toast.makeText(RetrofitRequesterQuote.this, "No flights found", Toast.LENGTH_SHORT).show();
                    if(onRetrofitListener !=null){
                        onRetrofitListener.onRetrofitQuoteFinished(null, null, null);
                    }
                    return;
                }
                if(quoteList.size()==0){
                    Timber.i("quoteList is empty");
                    if(onRetrofitListener !=null){
                        onRetrofitListener.onRetrofitQuoteFinished(null, null, null);
                    }
            //        Toast.makeText(RetrofitRequesterQuote.this, "No flights found", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(carrierList==null){
                    Timber.i("carrierList is null");
                    carrierArrayList=null;
                }

                else if(carrierList.size()==0){
                    Timber.i("carrierList is empty");
                    carrierArrayList=null;
                }
                else{
                    carrierArrayList=new ArrayList<Carrier>(carrierList);
                    Carrier carrier=carrierArrayList.get(0);
                    String carrierName=carrier.getName();

                    Timber.i("onResponse carrierPrice= "+carrierName);
                }
//                Quote firstQuote=quoteList.get(0);
//                Integer minPrice=firstQuote.getMinPrice();
//                Timber.i("minPrice= %s", minPrice);
//                Currency currency=currencyList.get(0);

//                if(currency!=null) {
//                    Timber.i("currency does not equal null");
//                }

//                ArrayList<<ArrayList<Quote><Currency>,<ArrayList>><ArrayList<Currency>> quoteCurrencyList=new ArrayList<Object>();
//                ArrayList<Object> quoteCurrencyList=new ArrayList<Object>();
//                quoteCurrencyList.add(0,quoteList);
//                quoteCurrencyList.add(1,currencyList);
                //List<Quote> quoteList=response.body();
//                if (onRetrofitListener != null) {
//                    onRetrofitListener.onRetrofitQuoteFinished(quoteCurrencyList);
//                }

                ArrayList<Quote> quoteArrayList=new ArrayList<Quote>(quoteList);
                Timber.i("onResponse quoteArrayListSize= "+quoteArrayList.size());
                //quoteArrayList.addAll(quoteList);

                ArrayList<Currency> currencyArrayList=new ArrayList<Currency>(currencyList);
//                ArrayList<String> arrlistofOptions = new ArrayList<String>(list);
//                currencyArrayList.addAll(currencyList);
                if (onRetrofitListener != null) {
                    onRetrofitListener.onRetrofitQuoteFinished(quoteArrayList, currencyArrayList, carrierArrayList);
                }
            }

            @Override
            public void onFailure(Call<Airports> call, Throwable t) {
                Toast.makeText(RetrofitRequesterQuote.this, R.string.problem_retrieving_data, Toast.LENGTH_SHORT).show();

                Timber.e("R.string.problem_retrieving_data");

                if (t instanceof IOException) {
                    Timber.i("network failure retry requesting data");
                }
                else {
                    Timber.i("conversion error unable to convert data");
                }

            }
        });
    }

    public List<Quote> generateDataList(Quotes quotes) {
        Timber.i("generateDataList called");
        List<Quote> quoteList;

        if (quotes == null) {
            Timber.i("generateDataList quotes equals null");
            //           Toast.makeText(this, R.string.parsing_problem, Toast.LENGTH_SHORT).show();
            quoteList = null;
            return quoteList;
        }
        else {
            quoteList = quotes.getQuotes();
            if(quoteList.isEmpty()){
                Timber.i("generateDataList quoteList is empty");
                return null;
            }
        }
        return quoteList;
    }


    public Map<String, String> createHeadersMap(){
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        String apiKey=sharedpreferences.getString(Constants.API_KEY,"");
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("x-rapidapi-key",apiKey);
        headers.put("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com");
        headers.put("useQueryString", "true");
        return headers;
    }

}
