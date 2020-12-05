package com.example.flights.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flights.Constants;
import com.example.flights.R;

public class ClientApiKey extends AppCompatActivity {
    EditText apiKeyET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_api_key);
        apiKeyET=findViewById(R.id.apiKeyET);

//        startBrowserIntent();

    }

//    public void startBrowserIntent(){
//        String url = getResources().getString(R.string.rapidApiKeyUrl);
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//        if (i.resolveActivity(getPackageManager()) != null) {
//            startActivity(i);
//        }
//
//    }


    public void startMainActivity(View view) {
        String apiKey=apiKeyET.getText().toString();
        if(apiKey.matches("[a-z0-9]*") && !apiKey.equals("") && apiKey.length()>45){
            Toast.makeText(this, getString(R.string.welcome_to_flights), Toast.LENGTH_SHORT).show();
            SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.API_KEY,apiKey);
            editor.putBoolean(Constants.API_KEY_BOOLEAN, false);
            editor.commit();
             Intent intent=new Intent(ClientApiKey.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//            finish();
        }
        else{
            Toast.makeText(this, getString(R.string.invalid_api_key_message), Toast.LENGTH_SHORT).show();
        }

    }

    public void signUpLaunchIntent(View view) {
        String url = getResources().getString(R.string.rapidApiKeyUrl);
        Intent i = new Intent(Intent.ACTION_VIEW);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setData(Uri.parse(url));
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }


    }

//    https://stackoverflow.com/questions/21253303/exit-android-app-on-back-pressed#:~:text=in%20back%20button%20pressed%20you,previous%20activity%20in%20the%20stack.
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}