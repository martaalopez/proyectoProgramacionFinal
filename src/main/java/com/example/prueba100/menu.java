package com.example.prueba100;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class menu {
    @FXML
    private Button products;
    @FXML
    private Button orders;
    @FXML
    private Button profit;
    @FXML
    private Button sale;

    @FXML
    private void goToProducts() throws IOException {
        App.setRoot("crud");
    }

    @FXML
    private void goToOrders() throws IOException {
        App.setRoot("orders");
    }

    @FXML
    private void goToProfit() throws  IOException {
        App.setRoot("shop");
    }

    @FXML
    private void goToSale() throws IOException {
        App.setRoot("Sales");
    }
    @FXML
    private void goToSuppliers()throws  IOException{
        App.setRoot("crudSupplier");
    }
}
