package com.shareholder.ui.controller;

import com.shareholder.ui.Stock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShareHistoryController implements Initializable {
    @FXML
    protected TableView<Stock> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
