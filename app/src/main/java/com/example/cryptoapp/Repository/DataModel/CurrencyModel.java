package com.example.cryptoapp.Repository.DataModel;

import java.text.DecimalFormat;

public class CurrencyModel {

    private String name,symbol;
    private String price;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public CurrencyModel(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = decimalFormat.format(price);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
