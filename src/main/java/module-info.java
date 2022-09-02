module com.stockmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.shareholder to javafx.fxml;
    exports com.shareholder.ui.controller;
    opens com.shareholder.ui.controller to javafx.fxml;
    exports com.shareholder.ui;
    opens com.shareholder.ui to javafx.fxml;
    exports com.shareholder;
}