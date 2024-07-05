module com.example.stocktradingplatform {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stocktradingplatform to javafx.fxml;
    exports com.example.stocktradingplatform;
}