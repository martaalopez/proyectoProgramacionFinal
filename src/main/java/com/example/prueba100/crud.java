package com.example.prueba100;

import DAO.productDAO;
import connections.ConnectionMySQL;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import org.controlsfx.control.Notifications;
import util.method;

public class crud  implements Initializable {
    @FXML
    private TableColumn<product, String> columnDescription;

    @FXML
    private TableColumn<product, Integer> columnID;

    @FXML
    private TableColumn<product, Integer> columnUnit;

    @FXML
    private TableColumn<product, Double> columnSale;

    @FXML
    private TableColumn<product, Double> columnSupplier;

    @FXML
    private TableView<product> productTableView;

    @FXML
    private Button tbndelete;

    @FXML
    private Button tbninsert;

    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtunit;

    @FXML
    private Button tbnedit;
    @FXML
    private TextField txtSale;

    @FXML
    private TextField txtSupplier;

    private ConnectionMySQL conexionBD=new ConnectionMySQL();
    private productDAO productDAO;
    ObservableList<product> listaProduct= FXCollections.observableArrayList();
    private final ObjectProperty<product> objProduct=new SimpleObjectProperty<>();
    @FXML
    void goToInicio(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnID.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_product()).asObject());
        columnDescription.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDescription()));
        columnUnit.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getUnit()).asObject());
        columnSale.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getSale_price()).asObject());
       columnSupplier.setCellValueFactory(param->new SimpleDoubleProperty(param.getValue().getSupplier_price()).asObject());
        listarProduct();
        productTableView.setItems(listaProduct);
        /*poder hacer validaciones*/
        objProduct.bind(productTableView.getSelectionModel().selectedItemProperty());

    }
    @FXML
    void Delete(ActionEvent event) throws SQLException {

        /* hacemos una alerta */
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar el producto?", ButtonType.YES, ButtonType.NO);
        a.setHeaderText(this.objProduct.get().getDescription());
        if (a.showAndWait().get() == ButtonType.YES) {
            ConnectionMySQL.getConnect();
            productDAO = new productDAO(conexionBD);
            productDAO.eliminar(objProduct.get().getId_product());
            listarProduct();
        }

    }

    @FXML
    void Insert(ActionEvent event) throws Exception {
        if (!txtid.getText().isEmpty() && !txtdescription.getText().isEmpty() && !txtunit.getText().isEmpty()) {
            int id_product = Integer.parseInt(txtid.getText());
            int unit = Integer.parseInt(txtunit.getText());
            double salePrice = Double.parseDouble(txtSale.getText());
            double supplierPrice = Double.parseDouble(txtSupplier.getText());
            product p = new product();
            p.setId_product(id_product);
            p.setDescription(txtdescription.getText());
            p.setUnit(unit);
            p.setSale_price(salePrice);
            p.setSupplier_price(supplierPrice);
            this.conexionBD.getConnect();
            productDAO = new productDAO(conexionBD);
            productDAO.guardar(p);
            listarProduct();
        } else {
            Notifications.create().title("Aviso").text("Por favor, complete todos los campos.").showWarning();
        }
    }

    public void listarProduct() {
        try {
            this.conexionBD.getConnect();
            productDAO = new productDAO(conexionBD);
            listaProduct.setAll(productDAO.getAll());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void Edit(ActionEvent event) {

        // Obtenemos el producto seleccionado de la tabla
        product selectedProduct = objProduct.get();
        // Creamos una ventana para que el usuario pueda editar los valores del producto
        Dialog<product> dialog = new Dialog<>();
        dialog.setTitle("Editar producto");
        dialog.setHeaderText("Ingrese los nuevos valores del producto:");

        // Creamos los campos de texto para que el usuario pueda ingresar los nuevos valores
        TextField idField = new TextField(String.valueOf(selectedProduct.getId_product()));
        idField.setEditable(false);
        TextField descriptionField = new TextField(selectedProduct.getDescription());
        TextField unitField = new TextField(String.valueOf(selectedProduct.getUnit()));
        TextField saleField = new TextField(String.valueOf(selectedProduct.getSale_price()));
        TextField supplierField = new TextField(String.valueOf(selectedProduct.getSupplier_price()));

        // Agregamos los campos de texto a la ventana
        GridPane grid = new GridPane();
        grid.add(new Label("ID:"), 1, 1);
        grid.add(idField, 2, 1);
        grid.add(new Label("Descripción:"), 1, 2);
        grid.add(descriptionField, 2, 2);
        grid.add(new Label("Unidad:"), 1, 3);
        grid.add(unitField, 2, 3);
        grid.add(new Label("Precio venta:"), 1, 4);
        grid.add(saleField, 2, 4);
        grid.add(new Label("Precio proveedor:"), 1, 5);
        grid.add(supplierField, 2, 5);
        dialog.getDialogPane().setContent(grid);

        // Agregamos los botones de "Guardar" y "Cancelar"
        ButtonType saveButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Cuando el usuario presiona el botón de "Guardar", guardamos los cambios en la base de datos
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                selectedProduct.setDescription(descriptionField.getText());
                selectedProduct.setUnit(Integer.parseInt(unitField.getText()));
                selectedProduct.setSale_price(Double.parseDouble(saleField.getText()));
                selectedProduct.setSupplier_price(Double.parseDouble(supplierField.getText()));
                try {
                    this.conexionBD.getConnect();
                    productDAO = new productDAO(conexionBD);
                    productDAO.actualizar(selectedProduct);
                    listarProduct();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notifications.create().title("Aviso").text("Error al intentar guardar").showError();
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


}
