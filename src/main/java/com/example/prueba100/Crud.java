package com.example.prueba100;

import DAO.ProductDAO;
import connections.ConnectionMySQL;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import org.controlsfx.control.Notifications;

public class Crud implements Initializable {
    @FXML
    private TableColumn<Product, String> columnDescription;

    @FXML
    private TableColumn<Product, Integer> columnID;

    @FXML
    private TableColumn<Product, Integer> columnUnit;

    @FXML
    private TableColumn<Product, Double> columnSale;

    @FXML
    private TableColumn<Product, Double> columnSupplier;

    @FXML
    private TableView<Product> productTableView;

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
    private ProductDAO productDAO;
    ObservableList<Product> listaProduct= FXCollections.observableArrayList();
    private final ObjectProperty<Product> objProduct=new SimpleObjectProperty<>();
    @FXML
    void goToInicio(ActionEvent event) throws IOException {
        App.setRoot("Inicio");
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
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the product?", ButtonType.YES, ButtonType.NO);
        a.setHeaderText(this.objProduct.get().getDescription());
        if (a.showAndWait().get() == ButtonType.YES) {
            ConnectionMySQL.getConnect();
            productDAO = new ProductDAO(conexionBD);
            productDAO.eliminar(objProduct.get().getId_product());
            listarProduct();
        }

    }

    @FXML
    void Insert(ActionEvent event) {
        if (!txtid.getText().isEmpty() && !txtdescription.getText().isEmpty() && !txtunit.getText().isEmpty()) {
            String idText = txtid.getText();
            if (idText.matches("\\d{5}")) { // Verificar que el ID tenga exactamente 5 números
                int id_product = Integer.parseInt(idText);
                int unit = Integer.parseInt(txtunit.getText());
                double salePrice = Double.parseDouble(txtSale.getText().replace(",", ".")); // Reemplazar coma por punto
                double supplierPrice = Double.parseDouble(txtSupplier.getText().replace(",", ".")); // Reemplazar coma por punto
                Product p = new Product();
                p.setId_product(id_product);
                p.setDescription(txtdescription.getText());
                p.setUnit(unit);
                p.setSale_price(salePrice);
                p.setSupplier_price(supplierPrice);
                this.conexionBD.getConnect();
                productDAO = new ProductDAO(conexionBD);
                productDAO.guardar(p);
                listarProduct();
            } else {
                Notifications.create().title("Warning").text("The ID must contain exactly 5 numbers.").showWarning();
            }
        } else {
            Notifications.create().title("Warning").text("Please complete all fields.").showWarning();
        }
    }



    public void listarProduct() {
        try {
            this.conexionBD.getConnect();
            productDAO = new ProductDAO(conexionBD);
            listaProduct.setAll(productDAO.getAll());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void Edit(ActionEvent event) {

        // Obtenemos el producto seleccionado de la tabla
        Product selectedProduct = objProduct.get();
        // Creamos una ventana para que el usuario pueda editar los valores del producto
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Edit");
        dialog.setHeaderText("Enter the new product values:");

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
        grid.add(new Label("Description:"), 1, 2);
        grid.add(descriptionField, 2, 2);
        grid.add(new Label("Unit:"), 1, 3);
        grid.add(unitField, 2, 3);
        grid.add(new Label("Sale price:"), 1, 4);
        grid.add(saleField, 2, 4);
        grid.add(new Label(
                "supplier price:"), 1, 5);
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
                    productDAO = new ProductDAO(conexionBD);
                    productDAO.actualizar(selectedProduct);
                    listarProduct();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notifications.create().title("Warning").text("Error trying to save").showError();
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


}

