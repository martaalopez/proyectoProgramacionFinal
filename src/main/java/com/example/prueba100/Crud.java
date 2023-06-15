package com.example.prueba100;

<<<<<<< HEAD:src/main/java/com/example/prueba100/crud.java
import DAO.productDAO;
import DAO.supplierDAO;
=======
import DAO.ProductDAO;
>>>>>>> final:src/main/java/com/example/prueba100/Crud.java
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
    private TableColumn<Product, String> columnDescription; // Table column for product description

    @FXML
    private TableColumn<Product, Integer> columnID; // Table column for product ID

    @FXML
    private TableColumn<Product, Integer> columnUnit; // Table column for product unit

    @FXML
    private TableColumn<Product, Double> columnSale; // Table column for product sale price

    @FXML
    private TableColumn<Product, Double> columnSupplier; // Table column for product supplier price

    @FXML
    private TableView<Product> productTableView; // Table view for displaying products

    @FXML
    private Button tbndelete; // Delete button

    @FXML
    private Button tbninsert; // Insert button

    @FXML
    private TextField txtdescription; // Text field for product description

    @FXML
    private TextField txtid; // Text field for product ID

    @FXML
    private TextField txtunit; // Text field for product unit

    @FXML
    private Button tbnedit; // Edit button

    @FXML
    private TextField txtSale; // Text field for product sale price

    @FXML
    private TextField txtSupplier; // Text field for product supplier price

    private ConnectionMySQL conexionBD = new ConnectionMySQL(); // Object for MySQL database connection
    private ProductDAO productDAO; // DAO object for products
    ObservableList<Product> listaProduct = FXCollections.observableArrayList(); // Observable list for products
    private final ObjectProperty<Product> objProduct = new SimpleObjectProperty<>(); // Selected product object

    @FXML
    void goToInicio(ActionEvent event) throws IOException {
        App.setRoot("Inicio"); // Go to "Inicio" page
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnID.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_product()).asObject());
        columnDescription.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDescription()));
        columnUnit.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getUnit()).asObject());
        columnSale.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getSale_price()).asObject());
        columnSupplier.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getSupplier_price()).asObject());
        listarProduct();
        productTableView.setItems(listaProduct);
        // Bind the selected product to the object property
        objProduct.bind(productTableView.getSelectionModel().selectedItemProperty());
    }

    @FXML
    void Delete(ActionEvent event) throws SQLException {
<<<<<<< HEAD:src/main/java/com/example/prueba100/crud.java
        /* hacemos una alerta */
=======
        // Show confirmation alert for product deletion
>>>>>>> final:src/main/java/com/example/prueba100/Crud.java
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the product?", ButtonType.YES, ButtonType.NO);
        a.setHeaderText(this.objProduct.get().getDescription());
        if (a.showAndWait().get() == ButtonType.YES) {
            ConnectionMySQL.getConnect();
<<<<<<< HEAD:src/main/java/com/example/prueba100/crud.java
            productDAO = new productDAO(conexionBD);

            int productId = objProduct.get().getId_product();
            productDAO.eliminar(productId);

=======
            productDAO = new ProductDAO(conexionBD);
            productDAO.eliminar(objProduct.get().getId_product());
>>>>>>> final:src/main/java/com/example/prueba100/Crud.java
            listarProduct();
        }
    }



    @FXML
    void Insert(ActionEvent event) {
        if (!txtid.getText().isEmpty() && !txtdescription.getText().isEmpty() && !txtunit.getText().isEmpty()) {
            String idText = txtid.getText();
            if (idText.matches("\\d{5}")) { // Verify that the ID contains exactly 5 numbers
                int id_product = Integer.parseInt(idText);
                int unit = Integer.parseInt(txtunit.getText());
                double salePrice = Double.parseDouble(txtSale.getText().replace(",", ".")); // Replace comma with dot
                double supplierPrice = Double.parseDouble(txtSupplier.getText().replace(",", ".")); // Replace comma with dot
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
<<<<<<< HEAD:src/main/java/com/example/prueba100/crud.java
=======

>>>>>>> final:src/main/java/com/example/prueba100/Crud.java
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
        // Get the selected product from the table
        Product selectedProduct = objProduct.get();
        // Create a dialog for the user to edit the product values
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Edit");
        dialog.setHeaderText("Enter the new product values:");

        // Create text fields for the user to enter the new values
        TextField idField = new TextField(String.valueOf(selectedProduct.getId_product()));
        idField.setEditable(false);
        TextField descriptionField = new TextField(selectedProduct.getDescription());
        TextField unitField = new TextField(String.valueOf(selectedProduct.getUnit()));
        TextField saleField = new TextField(String.valueOf(selectedProduct.getSale_price()));
        TextField supplierField = new TextField(String.valueOf(selectedProduct.getSupplier_price()));

        // Add the text fields to the dialog
        GridPane grid = new GridPane();
        grid.add(new Label("ID:"), 1, 1);
        grid.add(idField, 2, 1);
        grid.add(new Label("Description:"), 1, 2);
        grid.add(descriptionField, 2, 2);
        grid.add(new Label("Unit:"), 1, 3);
        grid.add(unitField, 2, 3);
        grid.add(new Label("Sale price:"), 1, 4);
        grid.add(saleField, 2, 4);
        grid.add(new Label("Supplier price:"), 1, 5);
        grid.add(supplierField, 2, 5);
        dialog.getDialogPane().setContent(grid);

        // Add "Save" and "Cancel" buttons
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // When the user clicks "Save", update the changes in the database
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

