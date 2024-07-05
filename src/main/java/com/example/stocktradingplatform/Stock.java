package com.example.stocktradingplatform;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Stock {
    private final SimpleStringProperty symbol;
    private final SimpleDoubleProperty price;

    public Stock(String symbol, double price) {
        this.symbol = new SimpleStringProperty(symbol);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getSymbol() {
        return symbol.get();
    }

    public SimpleStringProperty symbolProperty() {
        return symbol;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }
}
