package com.example.flights.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flights.Pojos.FlightDatePojos.Currency;
import com.example.flights.Pojos.FlightDatePojos.Quote;
import com.example.flights.Pojos.Place;
import com.example.flights.R;

import timber.log.Timber;

public class FlightDateCurrencyAdapter extends RecyclerView.Adapter<FlightDateCurrencyAdapter.DateCurrencyViewHolder> {

    //originPlaceId   KEY_PREFERENCE_PLACE_ID
    //destinationPlace   KEY_PREFERENCE_DESTINATION_PLACE
    Context context;
    Quote[] quoteArray;
    private LayoutInflater mInflater;
    int quoteArraySize;

    ConstraintLayout constraintLayoutFlightCurrencyItem;
    String originPlaceId;
    String destinationPlaceId;
    String returnDate;
    Currency currency;


    private OnDateCurrencyListener onDateCurrencyListener;

    public interface OnDateCurrencyListener {
        void onFlightDepartureClick(int quoteId, String originPlaceId,String  departureDate,String  formattedPrice,
                                    String destinationPlaceId, String returnDate, int position);
    }

    public FlightDateCurrencyAdapter(Context context, String originPlaceId, String destinationPlaceId,
                                     String returnDate, Currency currency, Quote[] quotes,
                                     OnDateCurrencyListener onDateCurrencyListener) {

        if(currency==null){
            Timber.i("currency equals null");
        }
        Timber.i("FlightDateCurrencyAdapter constructor adapter created");

        this.context = context;
        this.originPlaceId = originPlaceId;
        this.destinationPlaceId = destinationPlaceId;
        this.mInflater = LayoutInflater.from(context);
        this.quoteArray = quotes;
        quoteArraySize = quoteArray.length;
        this.returnDate = returnDate;
        this.currency = currency;
        this.onDateCurrencyListener = onDateCurrencyListener;
//        this.onReviewMovieListener=onReviewMovieListener;
    }


    @NonNull
    @Override
    public DateCurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View departureItemView = inflater.inflate(R.layout.flight_date_currency_item, parent, false);

        // Return a new holder instance
        //DateCurrencyViewHolder departuresViewHolder = new DateCurrencyViewHolder(departureItemView);
        return new DateCurrencyViewHolder(departureItemView, onDateCurrencyListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DateCurrencyViewHolder holder, int position) {

        if(quoteArray.length==0){
            holder.departureDateTV.setText("");
            holder.priceTV.setText("");

            String contentDescription="Flight origin is "+originPlaceId+" destination is "+destinationPlaceId;
            holder.constraintLayoutFlightCurrencyItem.setContentDescription(contentDescription);

            holder.originPlaceTV.setText(originPlaceId);
            holder.destinationPlaceTV.setText(destinationPlaceId);
            holder.returnDateTV.setText(returnDate);

        }
        else{
            Quote quote = quoteArray[position];
            holder.originPlaceTV.setText(originPlaceId);
//            holder.departureDateTV.setText(quote.getOutboundLeg().getDepartureDate());
            holder.destinationPlaceTV.setText(destinationPlaceId);
            holder.returnDateTV.setText(returnDate);

            String departureDate=formatDepartureDate(quote.getOutboundLeg().getDepartureDate());
            holder.departureDateTV.setText(departureDate);

            String formattedPrice = formatPriceCurrency(quote.getMinPrice().toString());
            holder.priceTV.setText(formattedPrice);

            String contentDescription="Flight is from "+originPlaceId+" and leaves on "+departureDate;
            holder.constraintLayoutFlightCurrencyItem.setContentDescription(contentDescription);
        }


//        holder.originPlaceTV.setText(originPlaceId);
//        holder.departureDateTV.setText(quote.getOutboundLeg().getDepartureDate());
//        holder.destinationPlaceTV.setText(destinationPlaceId);
//        holder.returnDateTV.setText(returnDate);
//
//        String formattedPrice = formatPriceCurrency(quote.getMinPrice().toString());
//        holder.priceTV.setText(formattedPrice);
    }

    @Override
    public int getItemCount() {
        if (quoteArray != null) {
            return quoteArray.length;
        }
        return 0;
    }

    public class DateCurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout constraintLayoutFlightCurrencyItem;
        public TextView originPlaceTV;
        public TextView departureDateTV;
        public TextView destinationPlaceTV;
        public TextView returnDateTV;
        public TextView priceTV;

        OnDateCurrencyListener onDateCurrencyListener;

        public DateCurrencyViewHolder(View itemView, OnDateCurrencyListener onDateCurrencyListener) {
            super(itemView);

            constraintLayoutFlightCurrencyItem=(ConstraintLayout) itemView.findViewById(R.id.constraintLayoutFlightCurrencyItem);
            originPlaceTV = (TextView) itemView.findViewById(R.id.originPlaceTV);
            departureDateTV = (TextView) itemView.findViewById(R.id.departureDateTV);
            destinationPlaceTV = (TextView) itemView.findViewById(R.id.destinationPlaceTV);
            returnDateTV = (TextView) itemView.findViewById(R.id.returnDateTV);
            priceTV = (TextView) itemView.findViewById(R.id.priceTV);

            this.onDateCurrencyListener = onDateCurrencyListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (onDateCurrencyListener != null) {

                Quote quote = quoteArray[getAdapterPosition()];
                //adding quote id for id in database
                int quoteId=quote.getQuoteId();
                String departureDate=formatDepartureDate(quote.getOutboundLeg().getDepartureDate());
                String formattedPrice = formatPriceCurrency(quote.getMinPrice().toString());


                onDateCurrencyListener.onFlightDepartureClick(quoteId, originPlaceId, departureDate, formattedPrice,
                        destinationPlaceId, returnDate, getAdapterPosition());

//                onDateCurrencyListener.onFlightDepartureClick(originPlaceId, departureDate, formattedPrice,
//                        destinationPlaceId, returnDate, getAdapterPosition());
            }
        }

    }

    public String formatPriceCurrency(String priceOfFlight) {
        if(currency==null){
            return "";
        }
        String code = currency.getCode();
        String symbol = currency.getSymbol();
        String thousandsSeparator = currency.getThousandsSeparator();
        String decimalSeparator = currency.getDecimalSeparator();
        boolean symbolOnLeft = currency.getSymbolOnLeft();
        boolean spaceBetweenAmountAndSymbol = currency.getSpaceBetweenAmountAndSymbol();
        int roundingCoefficient = currency.getRoundingCoefficient();
        int decimalDigits = currency.getDecimalDigits();


        StringBuilder priceSB = new StringBuilder();
        priceSB.append(code);
        priceSB.append(" ");


        String thousandsString = getThousands(priceOfFlight, thousandsSeparator);

//        priceSB.append(thousandsString);

        if (symbolOnLeft) {
//            priceSB.insert(0, symbol);
            thousandsString=symbol+thousandsString;
        } else {
            thousandsString=thousandsString+symbol;
//            priceSB.append(symbol);
        }
        priceSB.append(thousandsString);

        String formattedPrice = priceSB.toString();
        return formattedPrice;
    }

    public String getThousands(String priceOfFlight, String thousandsSeparator) {
        int priceLength = priceOfFlight.length();
        String currencyAdder = "";

        int u=0;

        for (int i = priceLength; i > 0; i--) {
            currencyAdder += priceOfFlight.charAt(u);
            if (i % 3 == 0 && priceLength>3) {
                currencyAdder += thousandsSeparator;
            }
            u++;
//            currencyAdder += priceOfFlight.charAt(u);
        }
        return currencyAdder;

    }

    public String formatDepartureDate(String departureDate){

        int endOfIndex = departureDate.indexOf("T");

        String formattedDate=departureDate;
        //found T character
        if (endOfIndex != -1)
        {
            formattedDate= departureDate.substring(0 , endOfIndex);
        }
        return formattedDate;
    }


//        if (priceOfFlight.matches("[0-9]+")) {
//            int priceLength=priceOfFlight.length();
//            if(priceLength>3){
//                String priceCommaDecimal=priceOfFlight
//            }
//        }


}
