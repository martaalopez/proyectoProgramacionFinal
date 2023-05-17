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
import java.util.ResourceBundle;

import DAO.clientDAO;

public class shop implements Initializable {

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
    private Button goToMenu;

    @FXML
    private Label lblTotal;

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
        int unitOrder = Integer.parseInt(txtUnidades.getText());
        if ( selectedProduct.getUnit() < unitOrder || unitOrder == 0 || unitOrder<0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No hay suficientes unidades del producto seleccionado.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        model.client selectedClient=clientCombo.getSelectionModel().getSelectedItem();


        // Crear nuevo objeto Product_Order
        product_order newOrder = new product_order(selectedProduct, unitOrder,selectedClient);

        // Agregar a carrito y actualizar tabla
        cart.add(newOrder);
        tablaProductos.refresh();

        /*Actualizar total*/
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
        }