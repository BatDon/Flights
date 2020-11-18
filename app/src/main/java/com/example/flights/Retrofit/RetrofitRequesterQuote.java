package com.example.flights.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flights.Api.TravelApi;
import com.example.flights.Constants;
import com.example.flights.Pojos.FlightDatePojos.OutboundDate;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.Pojos.FlightDatePojos.Quotes;
import com.example.flights.Pojos.Place;
import com.example.flights.Pojos.Places;
import com.example.flights.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RetrofitRequesterQuote extends AppCompatActivity {

    String userLocality;
    String userCountry;

    Context mContext;

    public RetrofitRequesterQuote(Context context){
        mContext=context;
    }

//    public RetrofitRequesterQuote(String userLocality, String userCountry){
//        this.userLocality=userLocality;
//        this.userCountry=userCountry;
//    }

//    public interface onRetrofitListener {
//        public void onRetrofitFinished(List<Quote> quoteList);
//    }
    public interface onRetrofitListener {
        public void onRetrofitFinished(List<Quote> quoteList);
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
        if(inboundDate.equals("")){
            Call<Quotes> call = travelApi.getQuotesNoInboundDate(userCountryId,userCurrency,userLocale, originPlaceId, destinationPlace,
                    outboundDate);
        }

        Call<Quotes> call = travelApi.getQuotes(userCountryId,userCurrency,userLocale, originPlaceId, destinationPlace,
                outboundDate, inboundDate);
//       Stockholm will be replaced by user selection
//        Call<Places> call = travelApi.getPlaces("Stockholm");
//        Call<Places> call = travelApi.getPlaces("Bogotá");
//        Call<Places> call = travelApi.getPlaces(this.userLocality);
//        Call<Places> call = travelApi.getPlaces();

        Timber.i("requestQuote called");


        call.enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {
                Timber.i("onResponse called");
                Timber.i("response= %s", response);
                List<Quote> quoteList = generateDataList(response.body());
                if(quoteList==null){
                    Timber.i("quoteList equals null");
               //     Toast.makeText(RetrofitRequesterQuote.this, "No flights found", Toast.LENGTH_SHORT).show();
                    if(onRetrofitListener !=null){
                        onRetrofitListener.onRetrofitFinished(null);
                    }
                    return;
                }
                if(quoteList.isEmpty()){
                    Timber.i("quoteList is empty");
                    if(onRetrofitListener !=null){
                        onRetrofitListener.onRetrofitFinished(null);
                    }
            //        Toast.makeText(RetrofitRequesterQuote.this, "No flights found", Toast.LENGTH_SHORT).show();
                    return;
                }
                Quote firstQuote=quoteList.get(0);
                Integer minPrice=firstQuote.getMinPrice();
                Timber.i("minPrice= %s", minPrice);
                //List<Quote> quoteList=response.body();
                if (onRetrofitListener != null) {
                    onRetrofitListener.onRetrofitFinished(quoteList);
                }
            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {
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
}
