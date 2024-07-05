package com.example.stocktradingplatform;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockTradingPlatform extends Application {

    private TableView<Stock> marketTable;
    private TableView<PortfolioItem> portfolioTable;
    private TextField stockSymbolField;
    private TextField quantityField;
    private Label balanceLabel;

    private double balance = 10000.00;
    private Map<String, Double> stockPrices = new HashMap<>();
    private Map<String, Integer> portfolio = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stock Trading Platform");

        // Initialize stock prices
        initializeStockPrices();

        // Create UI elements
        balanceLabel = new Label("Balance: $" + balance);

        stockSymbolField = new TextField();
        stockSymbolField.setPromptText("Stock Symbol");

        quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        Button buyButton = new Button("Buy");
        Button sellButton = new Button("Sell");

        buyButton.setOnAction(e -> buyStock());
        sellButton.setOnAction(e -> sellStock());

        marketTable = createMarketTable();
        portfolioTable = createPortfolioTable();

        // Create layout and add UI elements
        VBox inputBox = new VBox(10, stockSymbolField, quantityField, buyButton, sellButton);
        inputBox.setAlignment(Pos.CENTER);

        HBox mainBox = new HBox(20, marketTable, inputBox, portfolioTable);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));

        VBox root = new VBox(20, balanceLabel, mainBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Create scene and show stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeStockPrices() {
        stockPrices.put("AAPL", 150.00);
        stockPrices.put("GOOGL", 2800.00);
        stockPrices.put("AMZN", 3400.00);
        stockPrices.put("MSFT", 300.00);
    }

    private TableView<Stock> createMarketTable() {
        TableView<Stock> table = new TableView<>();
        TableColumn<Stock, String> symbolColumn = new TableColumn<>("Symbol");
        TableColumn<Stock, Double> priceColumn = new TableColumn<>("Price");

        symbolColumn.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        table.getColumns().add(symbolColumn);
        table.getColumns().add(priceColumn);

        ArrayList<Stock> stockList = new ArrayList<>();
        stockPrices.forEach((symbol, price) -> stockList.add(new Stock(symbol, price)));
        table.getItems().addAll(stockList);

        return table;
    }

    private TableView<PortfolioItem> createPortfolioTable() {
        TableView<PortfolioItem> table = new TableView<>();
        TableColumn<PortfolioItem, String> symbolColumn = new TableColumn<>("Symbol");
        TableColumn<PortfolioItem, Integer> quantityColumn = new TableColumn<>("Quantity");

        symbolColumn.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        table.getColumns().add(symbolColumn);
        table.getColumns().add(quantityColumn);

        return table;
    }

    private void buyStock() {
        String symbol = stockSymbolField.getText().toUpperCase();
        int quantity;

        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity. Please enter a valid number.");
            return;
        }

        if (!stockPrices.containsKey(symbol)) {
            showAlert("Invalid stock symbol. Please enter a valid symbol.");
            return;
        }

        double price = stockPrices.get(symbol);
        double totalCost = price * quantity;

        if (totalCost > balance) {
            showAlert("Insufficient balance. Please enter a lower quantity.");
            return;
        }

        balance -= totalCost;
        balanceLabel.setText("Balance: $" + balance);

        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
        updatePortfolioTable();
        stockSymbolField.clear();
        quantityField.clear();
    }

    private void sellStock() {
        String symbol = stockSymbolField.getText().toUpperCase();
        int quantity;

        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity. Please enter a valid number.");
            return;
        }

        if (!portfolio.containsKey(symbol) || portfolio.get(symbol) < quantity) {
            showAlert("Insufficient stock quantity. Please enter a valid quantity.");
            return;
        }

        double price = stockPrices.get(symbol);
        double totalRevenue = price * quantity;

        balance += totalRevenue;
        balanceLabel.setText("Balance: $" + balance);

        portfolio.put(symbol, portfolio.get(symbol) - quantity);
        if (portfolio.get(symbol) == 0) {
            portfolio.remove(symbol);
        }
        updatePortfolioTable();
        stockSymbolField.clear();
        quantityField.clear();
    }

    private void updatePortfolioTable() {
        portfolioTable.getItems().clear();
        portfolio.forEach((symbol, quantity) -> portfolioTable.getItems().add(new PortfolioItem(symbol, quantity)));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

