package com.example.flights.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flights.Api.TravelApi;
import com.example.flights.Constants;
import com.example.flights.Pojos.Place;
import com.example.flights.Pojos.Places;
import com.example.flights.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

//Stockholm is a test

public class RetrofitRequester extends AppCompatActivity {

    String userLocality;
    String userCountry;

    Context mContext;

    Map<String, String> headersMap;

    public RetrofitRequester(Context context){
        mContext=context;
        headersMap=createHeadersMap();
    }

    public RetrofitRequester(String userLocality, String userCountry){
        this.userLocality=userLocality;
        this.userCountry=userCountry;
    }

    public interface onRetrofitListener {
        public void onRetrofitFinished(List<Place> placeList);
    }

    private onRetrofitListener onRetrofitListener;


    public void requestPlaces(onRetrofitListener onRetrofitListener) {

        this.onRetrofitListener = onRetrofitListener;


        TravelApi travelApi = RetrofitClient.getRetrofitInstance().create(TravelApi.class);

        if(travelApi==null){
            Timber.i("travelApi equals null");
        }

//        SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
        String userCountry=sharedpreferences.getString(Constants.KEY_PREFERENCE_COUNTRY, "UK");
        String userCurrency=sharedpreferences.getString(Constants.KEY_PREFERENCE_CURRENCY, "GBP");
        String userLocale=sharedpreferences.getString(Constants.KEY_PREFERENCE_LOCALE, "en-GB");
        String userLocalityName=sharedpreferences.getString(Constants.KEY_PREFERENCE_PLACE,"Stockholm");
        //Call<Places> call = travelApi.getPlaces(userCountry,userCurrency,userLocale, userLocalityName);
        Call<Places> call = travelApi.getPlaces(headersMap,userCountry,userCurrency,userLocale, userLocalityName);
//       Stockholm will be replaced by user selection
//        Call<Places> call = travelApi.getPlaces("Stockholm");
//        Call<Places> call = travelApi.getPlaces("Bogotá");
//        Call<Places> call = travelApi.getPlaces(this.userLocality);
//        Call<Places> call = travelApi.getPlaces();

        Timber.i("requestPlaces called");


        call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {
                List<Place> placeList = generateDataList(response.body());
                if (onRetrofitListener != null) {
                    onRetrofitListener.onRetrofitFinished(placeList);
                }
            }

            @Override
            public void onFailure(Call<Places> call, Throwable t) {
                Toast.makeText(RetrofitRequester.this, R.string.problem_retrieving_data, Toast.LENGTH_SHORT).show();

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

    public List<Place> generateDataList(Places places) {
        List<Place> placeList;

        if (places == null) {
            //           Toast.makeText(this, R.string.parsing_problem, Toast.LENGTH_SHORT).show();
            placeList = null;
        } else {
            placeList = places.getPlaces();
        }
        return placeList;
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


