package com.example.prueba100;

import DAO.ProductDAO;
import DAO.SupplierDAO;
import connections.ConnectionMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Product;
import model.Supplier;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierCheck implements Initializable {

    @FXML
    private ComboBox<String> chooseCity;

    @FXML
    private ComboBox<Product> chooseProduct;

    @FXML
    private TextField id_supplier;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ListView<Product> productList;

    private ObservableList<Product> productItems;
    private SupplierDAO supplierDao;
    private ConnectionMySQL conexionBD = new ConnectionMySQL();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conexionBD.getConnect();
            ProductDAO productDAO = new ProductDAO(conexionBD);
            ObservableList<Product> products = FXCollections.observableArrayList(productDAO.getAll());
            chooseProduct.setItems(products);

            conexionBD.getConnect();
            this.supplierDao = new SupplierDAO(conexionBD);
            List<String> cities = getCitiesOfSpain();
            chooseCity.getItems().addAll(cities);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void insert(ActionEvent event) {

        Integer supplierValue = id_supplier.getLength();
        String nameValue = name.getText();
        String phoneNumberValue = phoneNumber.getText();
        String selectedCity = chooseCity.getValue();
        Product selectedProduct = chooseProduct.getValue();

        if (nameValue.isEmpty() || phoneNumberValue.isEmpty() || selectedCity == null || selectedProduct == null || supplierValue == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Required fields");
            alert.setHeaderText(null);
            alert.setContentText("Please complete all required fields.");
            alert.showAndWait();
            return;
        }

        Supplier newSupplier = new Supplier();
        newSupplier.setId_supplier(supplierValue);
        newSupplier.setName(nameValue);
        newSupplier.setPhoneNumber(phoneNumberValue);
        newSupplier.setAddress(selectedCity);
        newSupplier.setId_product(selectedProduct.getId_product()); // Assign the ID of the selected product
        this.supplierDao.guardar(newSupplier);

        ObservableList<String> supplierItems = chooseCity.getItems();
        supplierItems.add(String.valueOf(newSupplier));
        chooseCity.setItems(supplierItems);

        name.clear();
        phoneNumber.clear();
        chooseCity.getSelectionModel().clearSelection();
        chooseProduct.getSelectionModel().clearSelection();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful insertion");
        alert.setHeaderText(null);
        alert.setContentText("The provider has been inserted successfully.");
        alert.showAndWait();
    }

    private List<String> getCitiesOfSpain() {
        // You can obtain the cities from a database, file, or manually create a list
        return Arrays.asList("Madrid", "Barcelona", "Valencia", "Sevilla", "Zaragoza", "Málaga", "Murcia", "Palma", "Bilbao", "Alicante");
    }

    @FXML
    void gotomenu(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }
}
