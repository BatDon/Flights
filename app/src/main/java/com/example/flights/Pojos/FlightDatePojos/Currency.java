package com.example.flights.Pojos.FlightDatePojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//http://www.jsonschema2pojo.org/
public class Currency {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Symbol")
    @Expose
    private String symbol;
    @SerializedName("ThousandsSeparator")
    @Expose
    private String thousandsSeparator;
    @SerializedName("DecimalSeparator")
    @Expose
    private String decimalSeparator;
    @SerializedName("SymbolOnLeft")
    @Expose
    private Boolean symbolOnLeft;
    @SerializedName("SpaceBetweenAmountAndSymbol")
    @Expose
    private Boolean spaceBetweenAmountAndSymbol;
    @SerializedName("RoundingCoefficient")
    @Expose
    private Integer roundingCoefficient;
    @SerializedName("DecimalDigits")
    @Expose
    private Integer decimalDigits;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getThousandsSeparator() {
        return thousandsSeparator;
    }

    public void setThousandsSeparator(String thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public Boolean getSymbolOnLeft() {
        return symbolOnLeft;
    }

    public void setSymbolOnLeft(Boolean symbolOnLeft) {
        this.symbolOnLeft = symbolOnLeft;
    }

    public Boolean getSpaceBetweenAmountAndSymbol() {
        return spaceBetweenAmountAndSymbol;
    }

    public void setSpaceBetweenAmountAndSymbol(Boolean spaceBetweenAmountAndSymbol) {
        this.spaceBetweenAmountAndSymbol = spaceBetweenAmountAndSymbol;
    }

    public Integer getRoundingCoefficient() {
        return roundingCoefficient;
    }

    public void setRoundingCoefficient(Integer roundingCoefficient) {
        this.roundingCoefficient = roundingCoefficient;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

}