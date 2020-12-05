package com.example.flights.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    }


    public void startMainActivity(View view) {
        String apiKey=apiKeyET.getText().toString();
        if(apiKey.matches("[a-z0-9]*") && !apiKey.equals("") && apiKey.length()>45){
            Constants.api_key=apiKey;
            Toast.makeText(this, getString(R.string.welcome_to_flights), Toast.LENGTH_SHORT).show();
            SharedPreferences sharedpreferences = getSharedPreferences(Constants.FLIGHT_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
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
}