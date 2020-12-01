package com.example.flights.Adapters.firebaseAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flights.FavoriteFlightsData.FavoriteFlights;
import com.example.flights.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;

import timber.log.Timber;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class FirebaseFavoriteFlightsAdapter extends FirebaseRecyclerAdapter<FavoriteFlights, FirebaseFavoriteFlightsAdapter.FavoriteFlightsHolder> {

    private FirebaseFavoriteFlightsAdapter.OnFavoriteFlightsListener onFavoriteFlightsListener;

    public interface OnFavoriteFlightsListener{
//        void onFavoriteFlightsClick(int position);
        void onFavoriteFlightsClick(FavoriteFlights favoriteFlights);
    }


    FavoriteFlightsHolder favoriteFlightsHolder;
        //public class personAdapter extends FirebaseRecyclerAdapter<
//    person, personAdapter.personsViewholder> {
//
//    public personAdapter(
//        @NonNull FirebaseRecyclerOptions<person> options)
//    {
//        super(options);
//    }
    public FirebaseFavoriteFlightsAdapter(@NonNull FirebaseRecyclerOptions<FavoriteFlights> options,
                                          FirebaseFavoriteFlightsAdapter.OnFavoriteFlightsListener onFavoriteFlightsListener) {
        super(options);
        this.onFavoriteFlightsListener=onFavoriteFlightsListener;
    }





    @Override
    protected void onBindViewHolder(@NonNull FavoriteFlightsHolder holder, int position, @NonNull FavoriteFlights favoriteFlight) {
        String contentDescriptionString="Flight origin= "+favoriteFlight.getOriginPlace()+" for "+favoriteFlight.getPrice()+" destination is "+favoriteFlight.getDestinationAirportName();
        holder.constraintLayoutFlightCurrencyItem.setContentDescription(contentDescriptionString);
        holder.originPlaceTV.setText(favoriteFlight.getOriginPlace());
        holder.departureDateTV.setText(favoriteFlight.getOutgoingDate());
        holder.priceTV.setText(favoriteFlight.getPrice());
        holder.destinationPlaceTV.setText(favoriteFlight.getDestinationAirportName());
        holder.returnDateTV.setText(favoriteFlight.getReturnDate());
    }


//    @Override
//    protected void onBindViewHolder(@NonNull FavoriteFlightsHolder holder, int position, @NonNull FavoriteFlights favoriteFlight) {
//        holder.originPlaceTV.setText(favoriteFlight.getOriginPlace());
//        holder.departureDateTV.setText(favoriteFlight.getOutgoingDate());
//        holder.priceTV.setText(favoriteFlight.getPrice());
//        holder.destinationPlaceTV.setText(favoriteFlight.getDestinationAirportName());
//        holder.returnDateTV.setText(favoriteFlight.getReturnDate());
//    }



    @NonNull
    @Override
    public FavoriteFlightsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_date_currency_item,
                parent, false);
        favoriteFlightsHolder=new FirebaseFavoriteFlightsAdapter.FavoriteFlightsHolder(v, onFavoriteFlightsListener);
//        return new FirebaseFavoriteFlightsAdapter.FavoriteFlightsHolder(v);
        return favoriteFlightsHolder;
    }



    public class FavoriteFlightsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnFavoriteFlightsListener onFavoriteFlightsListener;

        public ConstraintLayout constraintLayoutFlightCurrencyItem;
        public TextView originPlaceTV;
        public TextView departureDateTV;
        public TextView destinationPlaceTV;
        public TextView returnDateTV;
        public TextView priceTV;

        public FavoriteFlightsHolder(View itemView, FirebaseFavoriteFlightsAdapter.OnFavoriteFlightsListener onFavoriteFlightsListener) {
            super(itemView);

            this.onFavoriteFlightsListener=onFavoriteFlightsListener;
            itemView.setOnClickListener(this);
            constraintLayoutFlightCurrencyItem=(ConstraintLayout) itemView.findViewById(R.id.constraintLayoutFlightCurrencyItem);
            originPlaceTV = (TextView) itemView.findViewById(R.id.originPlaceTV);
            departureDateTV = (TextView) itemView.findViewById(R.id.departureDateTV);
            destinationPlaceTV = (TextView) itemView.findViewById(R.id.destinationPlaceTV);
            returnDateTV = (TextView) itemView.findViewById(R.id.returnDateTV);
            priceTV = (TextView) itemView.findViewById(R.id.priceTV);
        }

        @Override
        public void onClick(View view) {
            Timber.i("onClick called in adapter");
            if(onFavoriteFlightsListener!=null){
//                onFavoriteFlightsListener.onFavoriteFlightsClick(getAdapterPosition());
                int adapterPosition=favoriteFlightsHolder.getAdapterPosition();
                ObservableSnapshotArray<FavoriteFlights> observableSnapshotArray=getSnapshots();
                FavoriteFlights favoriteFlights=observableSnapshotArray.get(adapterPosition);
                Timber.i("price in adapter= "+favoriteFlights.getPrice());
                onFavoriteFlightsListener.onFavoriteFlightsClick(favoriteFlights);

            }
            else{
                Timber.i("onFavoriteFlightsListener equals null");
            }
        }
    }

    @NonNull
    @Override
    public ObservableSnapshotArray<FavoriteFlights> getSnapshots() {
        return super.getSnapshots();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public int getPosition(int position){
        return position;
    }

//    public void deleteItem(){
//        int adapterPosition=favoriteFlightsHolder.getAdapterPosition();
//        ObservableSnapshotArray<FavoriteFlights> observableSnapshotArray=getSnapshots();
//        FavoriteFlights favoriteFlights=observableSnapshotArray.get(adapterPosition);
//        String idCurrencySign=favoriteFlights.getPrice();
//        String idNumber= idCurrencySign.replaceAll("[^\\d]", "");
//        String flightToDelete="Flights"+idNumber;
//
//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        String pathToDelete=firebaseDatabase.getReference().child(flightToDelete).toString();
//        Timber.i("pathToDelete= "+pathToDelete);
//        firebaseDatabase.getReference().child(flightToDelete).removeValue();
//        Timber.i("deleteItem in Adapter called");
//
//
////        if(snapshot.child("msg").getValue() != null){
////            String msg = snapshot.child("msg").getValue().toString();
////            return;
////        }
//    }




}

