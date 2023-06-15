package com.example.prueba100;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
public class Menu {
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
        App.setRoot("Crud");
    }

    @FXML
    private void goToOrders() throws IOException {
        App.setRoot("Record");
    }

    @FXML
    private void goToProfit() throws  IOException {
        App.setRoot("Shop");
    }

    @FXML
    private void goToSale() throws IOException {
        App.setRoot("Sales");
    }

    @FXML
    private void goToSuppliers()throws  IOException{
        App.setRoot("CrudSupplier");
    }
    @FXML
    void goToLogin(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

    @FXML
    void goToSIgnUp(ActionEvent event) throws IOException {
        App.setRoot("Orders");
    }


}
