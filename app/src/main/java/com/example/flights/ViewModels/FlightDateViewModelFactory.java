package com.example.flights.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FlightDateViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application application;
    private int position;

    public FlightDateViewModelFactory(Application application) {
        this.application=application;
        this.position = position;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FlightDateViewModel(application);
    }
}