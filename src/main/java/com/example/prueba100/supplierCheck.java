package com.example.prueba100;

import DAO.productDAO;
import DAO.supplierDAO;
import connections.ConnectionMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.product;
import model.supplier;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class supplierCheck implements Initializable {


    @FXML
    private ComboBox<String> chooseCity;

    @FXML
    private ComboBox<product> chooseProduct;

    @FXML
    private TextField id_supplier;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ListView<product> productList;

    private ObservableList<product> productItems;
    private supplierDAO supplierDao;
    private ConnectionMySQL conexionBD = new ConnectionMySQL();
    private productDAO productDao;

    private Connection conn = null;
    private ConnectionMySQL connection;

    public void supplierDAO(ConnectionMySQL conexionBD) {
        this.connection = conexionBD;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conexionBD.getConnect();
            productDAO productDAO = new productDAO(conexionBD);
            ObservableList<product> products = FXCollections.observableArrayList(productDAO.getAll());
            chooseProduct.setItems(products);

            conexionBD.getConnect();
            this.supplierDao = new supplierDAO(conexionBD);
            List<String> cities = getCitiesOfSpain();
            chooseCity.getItems().addAll(cities);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void insert(ActionEvent event) {

        Integer supplierValue= id_supplier.getLength();
        String nameValue = name.getText();
        String phoneNumberValue = phoneNumber.getText();
        String selectedCity = chooseCity.getValue();
        product selectedProduct = chooseProduct.getValue();


        if (nameValue.isEmpty() || phoneNumberValue.isEmpty() || selectedCity == null || selectedProduct == null || supplierValue==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Required fields");
            alert.setHeaderText(null);
            alert.setContentText("Please complete all required fields.");
            alert.showAndWait();
            return;
        }
        supplier newSupplier = new supplier();
        newSupplier.setId_supplier(supplierValue);
        newSupplier.setName(nameValue);
        newSupplier.setPhoneNumber(phoneNumberValue);
        newSupplier.setAddress(selectedCity);
        newSupplier.setId_product(selectedProduct.getId_product()); // Asignar el ID del producto seleccionado
        this.supplierDao.guardar(newSupplier);

        ObservableList<String> supplierItems = chooseCity.getItems();
        supplierItems.add(String.valueOf(newSupplier));
        chooseCity.setItems(supplierItems);

        name.clear();
        phoneNumber.clear();
        chooseCity.getSelectionModel().clearSelection();
        chooseProduct.getSelectionModel().clearSelection();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("successful insertion");
        alert.setHeaderText(null);
        alert.setContentText(
                "The provider has been inserted successfully.");
        alert.showAndWait();
    }

    private List<String> getCitiesOfSpain() {
        // Puedes obtener las ciudades desde una base de datos, archivo, o crear una lista manualmente
        return Arrays.asList("Madrid", "Barcelona", "Valencia", "Sevilla", "Zaragoza", "Málaga", "Murcia", "Palma", "Bilbao", "Alicante");
    }

    @FXML
    void gotomenu(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }
}
