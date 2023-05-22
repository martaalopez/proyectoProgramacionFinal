package com.example.prueba100;
import DAO.productDAO;
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
import model.product;
import model.product_order;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import DAO.product_orderDAO;

import java.util.Random;
import java.util.ResourceBundle;

import DAO.clientDAO;

public class shop implements Initializable {

    private boolean orderInProgress = false; // Track if an order is already in progress
    private int orderId;
    @FXML
    private Button start;
    @FXML
    private Button finish;



    @FXML
    private ComboBox<model.client> clientCombo;

    @FXML
    private ComboBox<product> productCombo;
    @FXML
    private TextField txtUnidades;
    @FXML
    private Button add;
    @FXML
    private TableColumn<product_order, Integer> colidProduct;
    @FXML
    private TableColumn<product_order, Integer> colpedido;
    @FXML
    private TableColumn<product_order, Integer> colunidades;

    @FXML
    private TableColumn<product_order,Integer> colOrder;

    @FXML
    private Button goToMenu;

    @FXML
    private Label lblTotal;

    @FXML
    private TextField idOrder;

    @FXML
    private TableView<product_order> tablaProductos;


    private ConnectionMySQL conexionBD = new ConnectionMySQL();


    private ObservableList<product_order> cart;

    private final ObjectProperty<product> objOrder=new SimpleObjectProperty<>();

    private final ObjectProperty<client> objClient=new SimpleObjectProperty<>();
    ObservableList<product> listaOrder= FXCollections.observableArrayList();

    public shop() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colidProduct.setCellValueFactory(new PropertyValueFactory<>("id_product"));
        colunidades.setCellValueFactory(new PropertyValueFactory<>("unitProduct"));
        colpedido.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        colOrder.setCellValueFactory(new PropertyValueFactory<>("id_order"));

        try {
            conexionBD.getConnect();
            productDAO productDAO = new productDAO(conexionBD);
            ObservableList<product> productList = FXCollections.observableArrayList(productDAO.getAll());
            productCombo.setItems(productList);

            clientDAO clientDAO=new clientDAO(conexionBD);
            ObservableList<model.client> clientsList=FXCollections.observableArrayList(clientDAO.getAll());
            clientCombo.setItems(clientsList);
            clientCombo.setDisable(false);

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        cart = FXCollections.observableArrayList();
        tablaProductos.setItems(cart);
    }

    @FXML
    void addToCart(ActionEvent event) {
        // Obtener producto seleccionado y cantidad
        product selectedProduct = productCombo.getSelectionModel().getSelectedItem();
        int unitOrder;
        int id_Order;

        try {
            unitOrder = Integer.parseInt(txtUnidades.getText());
            id_Order = Integer.parseInt(idOrder.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity and Order ID must be integers.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a product.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (selectedProduct.getUnit() < unitOrder || unitOrder <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are not enough units of the selected product.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        model.client selectedClient = clientCombo.getSelectionModel().getSelectedItem();

        // Crear nuevo objeto Product_Order
        product_order newOrder = new product_order(selectedProduct, unitOrder, selectedClient, id_Order);

        // Agregar a carrito y actualizar tabla
        cart.add(newOrder);
        tablaProductos.refresh();

        // Actualizar total
        updateTotal();

        // Guardar en base de datos
        product_orderDAO product_orderDAO = new product_orderDAO(conexionBD);
        product_orderDAO.guardar(newOrder);

        // Limpiar campos
        productCombo.getSelectionModel().clearSelection();
        txtUnidades.clear();
    }


    private void updateTotal() {
        // Calcular total del carrito
        double total = 0;
        for (product_order order : cart) {
            total += order.getProduct().getSale_price() * order.getUnitProduct();
        }

        // Establecer en etiqueta totalLabel
        lblTotal.setText(String.format("$%.2f", total));
    }

    @FXML
    void realizarCompra(ActionEvent event) {
        // Lógica para realizar la compra
    }
    @FXML
    private void goToMenu() throws IOException {
        App.setRoot("inicio");
    }
    @FXML
    void startOrder(ActionEvent event) {
        if (orderInProgress) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There is already an order in progress. Please complete or cancel the current order before starting a new one..", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Random rand = new Random();
        orderId = rand.nextInt(10000);
        orderInProgress = true;

        // Habilitar o deshabilitar controles según sea necesario
        productCombo.setDisable(false);
        txtUnidades.setDisable(false);
        add.setDisable(false);
        start.setDisable(true);
        finish.setDisable(false);
    }

    @FXML
    void finishOrder(ActionEvent event) {
        if (!orderInProgress) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are no orders in progress to finish.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Guardar en base de datos
        product_orderDAO product_orderDAO = new product_orderDAO(conexionBD);
        for (product_order order : cart) {
            order.setId_order(orderId);
            product_orderDAO.guardar(order);
        }

        // Limpiar y reiniciar la pantalla
        cart.clear();
        tablaProductos.refresh();
        idOrder.clear();
        updateTotal();

        // Habilitar o deshabilitar controles según sea necesario
        productCombo.setDisable(true);
        txtUnidades.setDisable(true);
        add.setDisable(true);
        start.setDisable(false);
        finish.setDisable(true);

        orderInProgress = false;
        orderId = 0;
    }
        }