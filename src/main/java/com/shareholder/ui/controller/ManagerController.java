package com.shareholder.ui.controller;

import com.shareholder.config.log.Log;
import com.shareholder.database.Database;
import com.shareholder.ui.Stock;
import com.shareholder.ui.utils.CustomAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ManagerController implements Initializable {
    private static final Logger log = Log.getLogger(Class.class);
    private Database database;
    @FXML
    private TextField tfStockRegDescription;
    @FXML
    private TextField tfStockRegName;
    @FXML
    private Label lbStockRegStatus;
    @FXML
    private Button btnStockReg;
    @FXML
    private TableColumn<Stock, Double> columnAveragePrice;
    @FXML
    private TableColumn<Stock, Integer> columnAmount;
    @FXML
    private TableColumn<Stock, Integer> columnId;
    @FXML
    private TableColumn<Stock, String> columnDescription;
    @FXML
    private TableColumn<Stock, String> columnStockName;
    @FXML
    private TableView<Stock> table;
    @FXML
    private Label lbStokSelected;
    @FXML
    private Button btnStockEdit;
    @FXML
    private Button btnStockDelete;
    @FXML
    private TextField tfSearchData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Start database */
        database = new Database();
        database.connect("admin", "admin");

        /* Configure tableView to show stocks */
        table.setPlaceholder(new Label("Sem ações cadastradas"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnStockName.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnAveragePrice.setCellValueFactory(new PropertyValueFactory<>("averagePrice"));

        /* Configure listener to when a stock is selected */
        table.getSelectionModel().selectedIndexProperty().addListener((num) -> selectedStock());

        /* Load stock data from database */
        List<Stock> list = database.readAllStocks();
        log.info("Number of stocks registered in the system: " + list.size());
        ObservableList<Stock> observableList = FXCollections.observableList(list);
        table.setItems(observableList);

        tfSearchData.setPromptText("Digite para pesquisar");

        log.info("Manager interface configured");
    }

    @FXML
    protected void onBtnStockRegClicked(ActionEvent event) throws Exception {
        lbStockRegStatus.setText("");

        String name = tfStockRegName.getText().trim();
        String description = tfStockRegDescription.getText().trim();
        if (name.equals("")) {
            CustomAlert.dialog("A nome da ação precisa ser informado.", AlertType.WARNING);
            return;
        }

        Stock stock = new Stock();
        stock.setStockName(name);
        stock.setDescription(description);

        if (database.verifyIfStockExists(name) == 0) {
            /*
             * If the stock don't exist int the database, then:
             * Adds the stock in the database and then to the tableView, with the other stocks.
             */
            log.info("Adding \"" + name + "\" in the database");
            Stock s = database.addNewStock(stock);
            if (s == null) {
                CustomAlert.dialog("Ocorreu uma falha ao tentar adicionar a ação \"" + name + "\" no sistema.", AlertType.ERROR);
                return;
            }

            table.getItems().add(s);
            lbStockRegStatus.setText("Ação cadastrada com sucesso!");
        } else {
            log.warning("The sock already exists in the database");
            CustomAlert.dialog("A ação \"" + name + "\" já encontra-se cadastrada no sistema.", AlertType.WARNING);
        }

        tfStockRegName.setText("");
        tfStockRegDescription.setText("");
    }

    @FXML
    protected void onBtnStockEditClicked(ActionEvent event) {
        if (!userSelectedStock()) {
            log.info("Stock not selected to be edited");
            CustomAlert.dialog("Selecione uma ação para prosseguir", AlertType.INFORMATION);
            return;
        }
        Stock stock = table.getSelectionModel().selectedItemProperty().get();
        log.info("Stock to be edited: " + stock.getStockName());

        // TODO: Stock edition
    }

    @FXML
    protected void onBtnStockDeleteClicked(ActionEvent event) {
        if (!userSelectedStock()) {
            log.info("Stock not selected to be deleted");
            CustomAlert.dialog("Selecione uma ação para prosseguir.", AlertType.INFORMATION);
            return;
        }
        Stock stock = table.getSelectionModel().getSelectedItem();
        String name = stock.getStockName();

        log.info("Stock to be deleted: " + name);

        if (!database.deleteStock(stock.getId())) {
            CustomAlert.dialog("Falha ao remover ação do sistema.", AlertType.ERROR);
            return;
        }

        /* Remove row from table */
        table.getItems().remove(stock);
        /* Clear selection after remove element */
        table.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onSearchRunning() {
        // TODO: onSearchRunning
    }

    /* Listener of the table */
    protected void selectedStock() {
        if (userSelectedStock()) {
            int index = table.getSelectionModel().selectedIndexProperty().get();
            Stock stock = table.getSelectionModel().selectedItemProperty().get();

            lbStokSelected.setText("Ação selecionada: " + stock.getStockName());

            log.info("Stock selected: " + stock.getStockName());
        } else {
            if (table.getItems().size() > 0) {
                lbStokSelected.setText(">> Selecione uma ação");
            } else {
                lbStokSelected.setText(">> Adcione ações ao sistema");
            }
        }
    }

    private boolean userSelectedStock() {
        return table.getSelectionModel().selectedItemProperty().isNotNull().getValue();
    }
}