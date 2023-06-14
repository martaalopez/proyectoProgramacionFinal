package com.example.prueba100;

import DAO.Product_orderDAO;
import connections.ConnectionMySQL;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Product_order;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class RecordController implements Initializable {
    @FXML
    private TableColumn<Product_order, Integer> columnUnit;

    @FXML
    private TableColumn<Product_order, String> columnidclient;

    @FXML
    private TableColumn<Product_order, Integer> columnidorder;

    @FXML
    private TableColumn<Product_order, Integer> columnidproduct;

    @FXML
    private TableView<Product_order> productTableView;

    @FXML
    private TextField search;

    private ConnectionMySQL conexionBD = new ConnectionMySQL();
    private Product_orderDAO product_orderDAO;
    private ObservableList<Product_order> listaProduct = FXCollections.observableArrayList();
    private final ObjectProperty<Product_order> objProduct = new SimpleObjectProperty<>();

    @FXML
    void goToInicio(ActionEvent event) throws IOException {
        App.setRoot("Inicio");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnUnit.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_product()).asObject());
        columnidclient.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId_client()));
        columnidorder.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getUnitProduct()).asObject());
        columnidproduct.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_product()).asObject());
        listarProduct();
        productTableView.setItems(listaProduct);
        objProduct.bind(productTableView.getSelectionModel().selectedItemProperty());

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterOrders(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void listarProduct() {
        try {
            conexionBD.getConnect();
            product_orderDAO = new Product_orderDAO(conexionBD);
            listaProduct.setAll(product_orderDAO.getAll());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void searchOrder(ActionEvent event) throws SQLException {
        String clientName = search.getText();
        filterOrders (clientName);
    }

    private void filterOrders(String clientName) throws SQLException {
        product_orderDAO = new Product_orderDAO(conexionBD);
        List<Product_order> filteredOrders = product_orderDAO.getOrderByClientName(clientName);
        listaProduct.setAll(filteredOrders);
    }
}
