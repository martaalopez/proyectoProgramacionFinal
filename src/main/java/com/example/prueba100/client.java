package com.example.prueba100;
import DAO.clientDAO;
import connections.ConnectionMySQL;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.Notifications;
import util.method;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static util.method.validarDNI;

public class client implements Initializable {

    @FXML
    private TableColumn<model.client, String> column_mail;

    @FXML
    private TableColumn<model.client, Integer> column_number;


    @FXML
    private TableColumn<model.client, String> column_client;


    @FXML
    private TableColumn<model.client, String> column_idorder;



    @FXML
    private Button order_addBtn;

    @FXML
    private TableView<model.client> order_table;

    @FXML
    private TextField txtClient;


    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtNumber;

    private ConnectionMySQL conexionBD=new ConnectionMySQL();
    private DAO.clientDAO clientDAO;

    private final ObjectProperty<model.client> objOrder=new SimpleObjectProperty<>();
    ObservableList<model.client> listaOrder= FXCollections.observableArrayList();

    @FXML
    void goToInicio(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }
    @FXML
    void goToOrder(ActionEvent event) throws IOException {
        App.setRoot("shop");
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        column_client.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_idorder.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        column_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        column_number.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        listarOrder();
        order_table.setItems(listaOrder);
        /*poder hacer validaciones*/
        objOrder.bind(order_table.getSelectionModel().selectedItemProperty());
    }

    @FXML
    void Delete(ActionEvent event) throws SQLException {
      /* if(objOrder.get()==null){
            method.rotateBug(order_table);
            return;

        /* hacemos una alerta */
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar el cliente?", ButtonType.YES, ButtonType.NO);
        a.setHeaderText(this.objOrder.get().getName());
        if (a.showAndWait().get() == ButtonType.YES) {
            ConnectionMySQL.getConnect();
            clientDAO= new clientDAO(conexionBD);
            clientDAO.eliminar(objOrder.get().getId_client());
            listarOrder();
        }

    }
    @FXML
    void Insert(ActionEvent event) throws Exception {
        if (!txtId.getText().isEmpty() && !txtClient.getText().isEmpty() && !txtMail.getText().isEmpty() && !txtNumber.getText().isEmpty() ) {
            if (!validarDNI(txtId.getText())) {
                Notifications.create().title("Aviso").text("El DNI es incorrecto.").showWarning();
            } if(!method.GmailValidate(txtMail.getText())){
                Notifications.create().title("Aviso").text("Introduce de nuevo el gmail.").showWarning();
            }
            else {
                String id_client = txtId.getText();
                String client = txtClient.getText();
                String mail= txtMail.getText();
                int phoneNumber= Integer.parseInt(txtNumber.getText());
                model.client o = new model.client();
                o.setId_client(id_client);
                o.setName(client);
                o.setMail(mail);
                o.setPhoneNumber(phoneNumber);
                this.conexionBD.getConnect();
                clientDAO = new clientDAO(conexionBD);
                clientDAO.guardar(o);
                listarOrder();
            }
        } else {
            Notifications.create().title("Aviso").text("Completa todos los campos.").showWarning();
        }
    }
    public void listarOrder() {
        try {
            this.conexionBD.getConnect();
            clientDAO = new clientDAO(conexionBD);
            listaOrder.setAll(clientDAO.getAll());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void Edit(ActionEvent event) {

        // Obtenemos la orden seleccionada de la tabla
       model.client selectedOrder = objOrder.get();
        // Creamos una ventana para que el usuario pueda editar los valores de la orden
        Dialog<model.client> dialog = new Dialog<>();
        dialog.setTitle("Editar orden");
        dialog.setHeaderText("Ingrese los nuevos valores de la orden:");

        // Creamos los campos de texto para que el usuario pueda ingresar los nuevos valores
        TextField idField = new TextField(String.valueOf(selectedOrder.getId_client()));
        idField.setEditable(false);
        TextField clientField = new TextField(String.valueOf(selectedOrder.getName()));
        TextField mailField = new TextField(String.valueOf(selectedOrder.getMail()));
        TextField phoneField = new TextField(String.valueOf(selectedOrder.getPhoneNumber()));
        // Agregamos los campos de texto a la ventana
        GridPane grid = new GridPane();
        grid.add(new Label("ID:"), 1, 1);
        grid.add(idField, 2, 1);
        grid.add(new Label("Cliente:"), 1, 2);
        grid.add(clientField, 2, 2);
        grid.add(new Label("Mail:"), 1, 2);
        grid.add(mailField, 2, 2);
        grid.add(new Label("Phone Number:"), 1, 2);
        grid.add(phoneField, 2, 2);
        dialog.getDialogPane().setContent(grid);

        // Agregamos los botones de "Guardar" y "Cancelar"
        ButtonType saveButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);


        // Cuando el usuario presiona el botón de "Guardar", guardamos los cambios en la base de datos
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                selectedOrder.setName(clientField.getText());
                try {
                    this.conexionBD.getConnect();
                    clientDAO = new clientDAO(conexionBD);
                    clientDAO.actualizar(selectedOrder);
                    listarOrder();
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