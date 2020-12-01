package com.example.flights.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flights.Pojos.FlightDatePojos.Carrier;
import com.example.flights.Pojos.FlightDatePojos.Currency;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.R;

import java.util.ArrayList;

import timber.log.Timber;

public class FlightCarrierAdapter extends RecyclerView.Adapter<FlightCarrierAdapter.FlightCarrierViewHolder> {

    Context mContext;
    ArrayList<Carrier> carrierArrayList;

    public FlightCarrierAdapter(Context context, ArrayList<Carrier> carrierArrayList) {
        mContext=context;
        this.carrierArrayList=carrierArrayList;
    }

    @NonNull
    @Override
    public FlightCarrierAdapter.FlightCarrierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View carrierItemView = inflater.inflate(R.layout.flight_carrier_item, parent, false);

        // Return a new holder instance
        //DateCurrencyViewHolder departuresViewHolder = new DateCurrencyViewHolder(departureItemView);
        return new FlightCarrierViewHolder(carrierItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightCarrierAdapter.FlightCarrierViewHolder holder, int position) {
        if(carrierArrayList !=null && carrierArrayList.size()>0){
            Carrier carrier=carrierArrayList.get(position);
            String contentDescription="Flight carrier id is "+carrier.getCarrierId()+" carrier name is "+carrier.getName();
            holder.constraintLayoutCarrierItem.setContentDescription(contentDescription);
            holder.carrierIdTV.setText(carrier.getCarrierId().toString());
            holder.carrierNameTV.setText(carrier.getName());
        }

    }

    @Override
    public int getItemCount() {
        if (carrierArrayList != null) {
            return carrierArrayList.size();
        }
        return 0;
    }

    public class FlightCarrierViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayoutCarrierItem;
        public TextView carrierIdTV;
        public TextView carrierNameTV;

        public FlightCarrierViewHolder(View itemView) {
            super(itemView);

            constraintLayoutCarrierItem=(ConstraintLayout) itemView.findViewById(R.id.constraintLayoutCarrierItem);
            carrierIdTV = (TextView) itemView.findViewById(R.id.carrierId);
            carrierNameTV = (TextView) itemView.findViewById(R.id.carrierName);
        }
    }
}
