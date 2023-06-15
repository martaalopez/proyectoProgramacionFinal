package com.example.prueba100;

import DAO.ClientDAO;
import DAO.ProductDAO;
import DAO.Product_orderDAO;
import connections.ConnectionMySQL;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Client;
import model.Product;
import model.Product_order;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    private boolean orderInProgress = false; // Track if an order is already in progress
    private int orderId = 0;

    @FXML
    private Button finish;
    @FXML
    private ComboBox<Product> productCombo;
    @FXML
    private ComboBox<Integer> txtUnidades; // Changed to ComboBox<Integer>
    @FXML
    private Button add;
    @FXML
    private TextField txt_username;
    @FXML
    private TableColumn<Product_order, Integer> colidProduct;
    @FXML
    private TableColumn<Product_order, Integer> colunidades;
    @FXML
    private Button goToMenu;
    @FXML
    private TableView<Product_order> tablaProductos;
    @FXML
    private TextField idClientText;

    private ConnectionMySQL conexionBD = new ConnectionMySQL();
    private ObservableList<Product_order> cart;
    private final ObjectProperty<Product> objOrder = new SimpleObjectProperty<>();
    private final ObjectProperty<Client> objClient = new SimpleObjectProperty<>();
    ObservableList<Product> listaOrder = FXCollections.observableArrayList();

    private ClientDAO clientDAO; // Add the ClientDAO object to the controller

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colidProduct.setCellValueFactory(new PropertyValueFactory<>("id_product"));
        colunidades.setCellValueFactory(new PropertyValueFactory<>("unitProduct"));

        try {
            conexionBD.getConnect();
            ProductDAO productDAO = new ProductDAO(conexionBD);
            ObservableList<Product> productList = FXCollections.observableArrayList(productDAO.getAll());
            productCombo.setItems(productList);

            clientDAO = new ClientDAO(conexionBD); // Initialize the ClientDAO object
            txt_username.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    idClientText.setText(clientDAO.getClientNameByUsername(newValue));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            // Configure numbers 1 to 10 in the txtUnidades ComboBox
            ObservableList<Integer> unidadesList = FXCollections.observableArrayList();
            for (int i = 1; i <= 10; i++) {
                unidadesList.add(i);
            }
            txtUnidades.setItems(unidadesList);

            Random random = new Random();
            orderId = random.nextInt(1000);  // Generate a random order ID
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        cart = FXCollections.observableArrayList();
        tablaProductos.setItems(cart);
    }

    @FXML
    void addToCart(ActionEvent event) throws SQLException {
        Random random = new Random();
        orderId = generateRandomOrderId();
        // Get selected product and quantity
        Product selectedProduct = productCombo.getSelectionModel().getSelectedItem();
        Integer unitOrder = txtUnidades.getValue(); // Get the selected value from the ComboBox

        String idClient = idClientText.getText();

        if (unitOrder == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity and Order ID cannot be empty.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        // Check if the product already exists in the cart
        for (Product_order order : cart) {
            if (order.getId_product() == selectedProduct.getId_product()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product already added to the cart.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }

        // Create a new Product_order object
        Product_order newOrder = new Product_order();
        newOrder.setId_product(selectedProduct.getId_product());
        newOrder.setUnitProduct(unitOrder);
        newOrder.setId_client(idClient);
        newOrder.setId_order(orderId);  // Set the order ID for the product

        cart.add(newOrder);

        // Save the new object to the database
        Product_orderDAO product_orderDAO = new Product_orderDAO(conexionBD);
        product_orderDAO.guardar(newOrder);
    }


    @FXML
    void finishOrder(ActionEvent event) {
        if (cart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cart is empty.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Generate a unique random ID for the order
        orderId = generateRandomOrderId();

        // Assign the random ID to each product in the cart
        for (Product_order order : cart) {
            order.setId_order(orderId);
        }

        // Reset values and clear the cart
        cart.clear();
        productCombo.getSelectionModel().clearSelection();
        txtUnidades.getSelectionModel().clearSelection();
        orderInProgress = false;
    }

    private int generateRandomOrderId() {
        // Generate a unique random ID for the order only if there is no order in progress
        if (!orderInProgress) {
            Random random = new Random();
            orderId = random.nextInt(1000000); // Range of random IDs
            orderInProgress = true;
        }

        return orderId;
    }


    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

}

