package com.example.flights.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flights.Pojos.Place;
import com.example.flights.R;

public class FlightDeparturesAdapter extends RecyclerView.Adapter<FlightDeparturesAdapter.DeparturesViewHolder> {

    Context context;
    Place[] placeArray;
    private LayoutInflater mInflater;
    int placeArraySize;


    private OnFlightDepartureListener onFlightDepartureListener;

    public interface OnFlightDepartureListener{
        void onFlightDepartureClick(int position);
    }

    public FlightDeparturesAdapter(Context context, Place[] places, OnFlightDepartureListener onFlightDepartureListener){
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.placeArray=places;
        placeArraySize=places.length;
        this.onFlightDepartureListener=onFlightDepartureListener;
//        this.onReviewMovieListener=onReviewMovieListener;
    }


    @NonNull
    @Override
    public DeparturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View departureItemView = inflater.inflate(R.layout.flight_departure_item, parent, false);

        // Return a new holder instance
        //DeparturesViewHolder departuresViewHolder = new DeparturesViewHolder(departureItemView);
        return new DeparturesViewHolder(departureItemView, onFlightDepartureListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeparturesViewHolder holder, int position) {

        Place place=placeArray[position];

        holder.placeIdTV.setText(place.getPlaceId());
        holder.placeNameTV.setText(place.getPlaceName());
        holder.countryIdTV.setText(place.getCountryId());
        holder.regiondIdTV.setText(place.getRegionId());
        holder.cityIdTV.setText(place.getCityId());
        holder.countryNameTv.setText(place.getCountryName());
    }

    @Override
    public int getItemCount() {
        if(placeArray!=null){
            return placeArray.length;
        }
        return 0;
    }

    public class DeparturesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView placeIdTV;
        public TextView placeNameTV;
        public TextView countryIdTV;
        public TextView regiondIdTV;
        public TextView cityIdTV;
        public TextView countryNameTv;

        OnFlightDepartureListener onFlightDepartureListener;

        public DeparturesViewHolder(View itemView, OnFlightDepartureListener onFlightDepartureListener){
            super(itemView);

            placeIdTV=(TextView) itemView.findViewById(R.id.placeId);
            placeNameTV=(TextView) itemView.findViewById(R.id.placeName);
            countryIdTV=(TextView) itemView.findViewById(R.id.countryId);
            regiondIdTV=(TextView) itemView.findViewById(R.id.regionId);
            cityIdTV=(TextView) itemView.findViewById(R.id.cityId);
            countryNameTv=(TextView) itemView.findViewById(R.id.countryName);

            this.onFlightDepartureListener=onFlightDepartureListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (onFlightDepartureListener != null){
                onFlightDepartureListener.onFlightDepartureClick(getAdapterPosition());
            }
        }

    }

}