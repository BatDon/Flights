package com.example.flights.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flights.Activities.FavoriteFlightsActivity;
import com.example.flights.FavoriteFlightsData.FavoriteFlights;
import com.example.flights.Pojos.FlightDatePojos.Currency;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.R;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

//public class FlightDateCurrencyAdapter extends RecyclerView.Adapter<FlightDateCurrencyAdapter.DateCurrencyViewHolder> {
public class FavoriteFlightsAdapter extends RecyclerView.Adapter<FavoriteFlightsAdapter.FavoriteFlightsViewHolder>{
    //originPlaceId   KEY_PREFERENCE_PLACE_ID
    //destinationPlace   KEY_PREFERENCE_DESTINATION_PLACE
    Context context;
    FavoriteFlights[] favoriteFlightsArray;
    private LayoutInflater mInflater;
    int favoriteFlightArraySize;

    String originPlaceId;
    String destinationPlaceId;
    String returnDate;
    Currency currency;


//    private OnDateCurrencyListener onDateCurrencyListener;

//    public interface OnDateCurrencyListener {
//        void onFlightDepartureClick(String originPlaceId,String  departureDate,String  formattedPrice,
//                                    String destinationPlaceId, String returnDate, int position);
//    }

//    public FlightDateCurrencyAdapter(Context context, String originPlaceId, String destinationPlaceId,
//                                     String returnDate, Currency currency, Quote[] quotes,
//                                     OnDateCurrencyListener onDateCurrencyListener) {

        public FavoriteFlightsAdapter(Context context, FavoriteFlights[] favoriteFlightsArray) {

    //        if(currency==null){
    //            Timber.i("currency equals null");
    //        }
    //        Timber.i("FlightDateCurrencyAdapter constructor adapter created");

            this.context = context;
            this.favoriteFlightsArray = favoriteFlightsArray;
    //        this.destinationPlaceId = destinationPlaceId;
    //        this.mInflater = LayoutInflater.from(context);
    //        this.quoteArray = quotes;
    //            favoriteFlightArraySize = favoriteFlightsArray.length;
    //        this.returnDate = returnDate;
    //        this.currency = currency;
    //        this.onDateCurrencyListener = onDateCurrencyListener;
    //        this.onReviewMovieListener=onReviewMovieListener;
    }


    @NonNull
    @Override
    public FavoriteFlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View departureItemView = inflater.inflate(R.layout.flight_date_currency_item, parent, false);

        // Return a new holder instance
        //DateCurrencyViewHolder departuresViewHolder = new DateCurrencyViewHolder(departureItemView);
        return new FavoriteFlightsViewHolder(departureItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFlightsViewHolder holder, int position) {

        FavoriteFlights favoriteFlight = favoriteFlightsArray[position];
        holder.originPlaceTV.setText(favoriteFlight.getOriginPlace());
        holder.departureDateTV.setText(favoriteFlight.getOutgoingDate());
        holder.priceTV.setText(favoriteFlight.getPrice());
        holder.destinationPlaceTV.setText(favoriteFlight.getDestinationAirportName());
        holder.returnDateTV.setText(favoriteFlight.getReturnDate());


    }

    @Override
    public int getItemCount() {
        if (favoriteFlightsArray != null) {
            return favoriteFlightsArray.length;
        }
        return 0;
    }

    public FavoriteFlights getFavoriteFlightAt(int position) {
        return favoriteFlightsArray[position];
    }

    public class FavoriteFlightsViewHolder extends RecyclerView.ViewHolder {
        public TextView originPlaceTV;
        public TextView departureDateTV;
        public TextView destinationPlaceTV;
        public TextView returnDateTV;
        public TextView priceTV;

//        OnDateCurrencyListener onDateCurrencyListener;

        public FavoriteFlightsViewHolder(View itemView) {
            super(itemView);

            originPlaceTV = (TextView) itemView.findViewById(R.id.originPlaceTV);
            departureDateTV = (TextView) itemView.findViewById(R.id.departureDateTV);
            destinationPlaceTV = (TextView) itemView.findViewById(R.id.destinationPlaceTV);
            returnDateTV = (TextView) itemView.findViewById(R.id.returnDateTV);
            priceTV = (TextView) itemView.findViewById(R.id.priceTV);

        }
    }

    public void updateData(FavoriteFlights[] favoriteFlightsArray) {
        this.favoriteFlightsArray=favoriteFlightsArray;
        new FavoriteFlightsAdapter(context, favoriteFlightsArray);
        notifyDataSetChanged();
    }




}





















































//public class FavoriteFlightsAdapter extends RecyclerView.Adapter<FavoriteFlightsAdapter.FavoriteFlightsViewHolder>{
//public class FavoriteFlightsAdapter extends ListAdapter<FavoriteFlights, FavoriteFlightsAdapter.FavoriteFlightsViewHolder> {


//    protected FavoriteFlightsAdapter(@NonNull DiffUtil.ItemCallback<FavoriteFlights> diffCallback) {
//        super(diffCallback);
//    }


//    public FavoriteFlightsAdapter() {
//        super(DIFF_CALLBACK);
//    }
//
//    private static final DiffUtil.ItemCallback<FavoriteFlights> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteFlights>() {
//        @Override
//        public boolean areItemsTheSame(FavoriteFlights oldFlight, FavoriteFlights newFlight) {
//            return (oldFlight.getId()).equals(newFlight.getId());
//        }
//        @Override
//        public boolean areContentsTheSame(FavoriteFlights oldFlight, FavoriteFlights newFlight) {
//            return oldFlight.getOriginPlace().equals(newFlight.getOriginPlace()) &&
//                    oldFlight.getCurrency().equals(newFlight.getCurrency()) &&
//                    oldFlight.getPrice().equals(newFlight.getPrice()) &&
//                    oldFlight.getOutgoingDate().equals(newFlight.getOutgoingDate()) &&
//                    oldFlight.getDestinationAirportName().equals(newFlight.getDestinationAirportName()) &&
//                    oldFlight.getReturnDate().equals(newFlight.getReturnDate());
//        }
//    };
//
//
////    protected FavoriteFlightsAdapter(@NonNull AsyncDifferConfig<FavoriteFlights> config) {
////        super(config);
////    }
//
//    @NonNull
//    @Override
//    public FavoriteFlightsAdapter.FavoriteFlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Timber.i("onCreateViewHolder called");
//        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
//        View favoriteFlightViewItem=inflater.inflate(R.layout.flight_date_currency_item, parent, false);
//
////        FavoriteFlightsViewHolder favoriteFlightsViewHolder=new FavoriteFlightsViewHolder(favoriteFlightViewItem);
////        return favoriteFlightsViewHolder;
//        return new FavoriteFlightsViewHolder(favoriteFlightViewItem);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull FavoriteFlightsViewHolder holder, int position) {
//        Timber.i("onBindViewHolder called");
//        FavoriteFlights favoriteFlights = getItem(position);
//        holder.originPlaceTV.setText(favoriteFlights.getOriginPlace());
//        holder.departureDateTV.setText(String.valueOf(favoriteFlights.getOriginPlace()));
//        holder.destinationPlaceTV.setText(favoriteFlights.getDestinationAirportName());
//        holder.returnDateTV.setText(favoriteFlights.getReturnDate());
//        holder.priceTV.setText(String.valueOf(favoriteFlights.getPrice()));
//
////        holder.destinationAirportName.setText("");
////        holder.returnDate.setText("");
////
////        holder.originPlaceTV.setText(originPlaceId);
////        holder.destinationPlaceTV.setText(destinationPlaceId);
////        holder.returnDateTV.setText(returnDate);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public FavoriteFlights getFavoriteFlightAt(int position) {
//        return getItem(position);
//    }
//
//    public class FavoriteFlightsViewHolder extends RecyclerView.ViewHolder{
//        public TextView originPlaceTV;
//        public TextView departureDateTV;
//        public TextView destinationPlaceTV;
//        public TextView returnDateTV;
//        public TextView priceTV;
//
//        public FavoriteFlightsViewHolder(View itemView) {
//            super(itemView);
//
//            Timber.i("FavoriteFlightsViewHolder called");
//
//            originPlaceTV = (TextView) itemView.findViewById(R.id.originPlaceTV);
//            departureDateTV = (TextView) itemView.findViewById(R.id.departureDateTV);
//            destinationPlaceTV = (TextView) itemView.findViewById(R.id.destinationPlaceTV);
//            returnDateTV = (TextView) itemView.findViewById(R.id.returnDateTV);
//            priceTV = (TextView) itemView.findViewById(R.id.priceTV);
//
//        }
//
//    }

//    public void updateList(ArrayList<FavoriteFlights> favoriteFlights) {
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(this.persons, favoriteFlights));
//        diffResult.dispatchUpdatesTo(this);
//    }























//        @NonNull
//    @Override
//    public DateCurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        // Inflate the custom layout
//        View departureItemView = inflater.inflate(R.layout.flight_date_currency_item, parent, false);
//
//        // Return a new holder instance
//        //DateCurrencyViewHolder departuresViewHolder = new DateCurrencyViewHolder(departureItemView);
//        return new DateCurrencyViewHolder(departureItemView, onDateCurrencyListener);
//    }




//        @NonNull
//    @Override
//    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.note_item, parent, false);
//        return new NoteHolder(itemView);
//    }

//}
