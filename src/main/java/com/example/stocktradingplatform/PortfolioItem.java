package com.example.stocktradingplatform;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PortfolioItem {
    private final SimpleStringProperty symbol;
    private final SimpleIntegerProperty quantity;

    public PortfolioItem(String symbol, int quantity) {
        this.symbol = new SimpleStringProperty(symbol);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public String getSymbol() {
        return symbol.get();
    }

    public SimpleStringProperty symbolProperty() {
        return symbol;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }
}

