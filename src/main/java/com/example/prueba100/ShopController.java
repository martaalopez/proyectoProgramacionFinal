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
    private ComboBox<Integer> txtUnidades; // Cambiado a ComboBox<Integer>
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

    private ClientDAO clientDAO; // Agrega el objeto ClientDAO al controlador

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colidProduct.setCellValueFactory(new PropertyValueFactory<>("id_product"));
        colunidades.setCellValueFactory(new PropertyValueFactory<>("unitProduct"));

        try {
            conexionBD.getConnect();
            ProductDAO productDAO = new ProductDAO(conexionBD);
            ObservableList<Product> productList = FXCollections.observableArrayList(productDAO.getAll());
            productCombo.setItems(productList);

            clientDAO = new ClientDAO(conexionBD); // Inicializa el objeto ClientDAO
            txt_username.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    idClientText.setText(clientDAO.getClientNameByUsername(newValue));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            // Configurar los números del 1 al 10 en el ComboBox txtUnidades
            ObservableList<Integer> unidadesList = FXCollections.observableArrayList();
            for (int i = 1; i <= 10; i++) {
                unidadesList.add(i);
            }
            txtUnidades.setItems(unidadesList);

            Random random = new Random();
            orderId = random.nextInt(1000);  // Genera un ID de pedido aleatorio
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
        // Obtener producto seleccionado y cantidad
        Product selectedProduct = productCombo.getSelectionModel().getSelectedItem();
        Integer unitOrder = txtUnidades.getValue(); // Obtener el valor seleccionado del ComboBox

        String idClient = idClientText.getText();

        if (unitOrder == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity and Order ID cannot be empty.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        // Verificar si el producto ya existe en el carrito
        for (Product_order order : cart) {
            if (order.getId_product() == selectedProduct.getId_product()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product already added to the cart.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }

        // Crear nuevo objeto Product_Order
        Product_order newOrder = new Product_order();
        newOrder.setId_product(selectedProduct.getId_product());
        newOrder.setUnitProduct(unitOrder);
        newOrder.setId_client(idClient);
        newOrder.setId_order(orderId);  // Establece el ID de pedido para el producto

        cart.add(newOrder);

        // Guardar el nuevo objeto en la base de datos
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

        // Generar un ID aleatorio único para el pedido
        orderId = generateRandomOrderId();

        // Asignar el ID aleatorio a cada producto en el carrito
        for (Product_order order : cart) {
            order.setId_order(orderId);
        }

        // Restablecer los valores y limpiar el carrito
        cart.clear();
        productCombo.getSelectionModel().clearSelection();
        txtUnidades.getSelectionModel().clearSelection();
        orderInProgress = false;
    }

    private int generateRandomOrderId() {
        // Generar un ID aleatorio único para el pedido solo si no hay un pedido en progreso
        if (!orderInProgress) {
            Random random = new Random();
            orderId = random.nextInt(1000000); // Rango de IDs aleatorios
            orderInProgress = true;
        }

        return orderId;
    }


    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

}
