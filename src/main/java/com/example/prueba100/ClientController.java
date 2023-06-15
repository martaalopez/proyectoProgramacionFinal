package com.example.prueba100;

import DAO.AdminDAO;
import DAO.ClientDAO;
import connections.ConnectionMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private Button btn_signup;

    @FXML
    private TextField txt_dni_up;

    @FXML
    private TextField txt_mail_up;

    @FXML
    private TextField txt_name_up;

    @FXML
    private TextField txt_number_up;

    @FXML
    private TextField txt_password_up;

    @FXML
    private TextField txt_username_up;

    @FXML
    private ComboBox<String> type_up;

    private ClientDAO clientDao;
    private AdminDAO adminDao;

    private ConnectionMySQL conexionBD = new ConnectionMySQL();

    private final String adminPassword = "123";


    @FXML
    void goToInicio(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

    @FXML
    void goToOrder(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexionBD.getConnect();
        clientDao = new ClientDAO(conexionBD);
        adminDao = new AdminDAO(conexionBD);

        ObservableList<String> typeOptions = FXCollections.observableArrayList("admin", "client");
        type_up.setItems(typeOptions);
        type_up.getSelectionModel().selectFirst();
    }

    @FXML
    void signUp(ActionEvent event) {
        String name = txt_name_up.getText();
        int phoneNumber = Integer.parseInt(txt_number_up.getText());
        String mail = txt_mail_up.getText();
        String username = txt_username_up.getText();
        String password = txt_password_up.getText();
        String type = type_up.getValue();
        String idClient = txt_dni_up.getText();

        if (type.equals("admin")) {
            model.Admin admin = new model.Admin(idClient, name, mail, phoneNumber, password, username, type);
            admin.setId_client(idClient);
            admin.setType(type);
            admin.setPassword(password);
            admin.setUsername(username);
            admin.setPhoneNumber(phoneNumber);
            admin.setName(name);

            showAdminPasswordDialog(admin);
        } else {
            model.Client client = new model.Client(idClient, name, mail, phoneNumber, password, username, type);
            client.setId_client(idClient);
            client.setType(type);
            client.setPassword(password);
            client.setUsername(username);
            client.setPhoneNumber(phoneNumber);
            client.setName(name);

            if (clientDao.usernameExists(username)) {
                showErrorDialog("The username is already available");
                return;
            }

            clientDao.guardar(client);
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("The username is already available");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private void showAdminPasswordDialog(model.Admin admin) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Administrator password validation");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the admin password provided by the company:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(password -> {
            if (password.equals(adminPassword)) {

                AdminDAO adminDao = new AdminDAO(conexionBD);
                adminDao.guardar(admin);
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin password validation error");
                alert.setHeaderText(null);
                alert.setContentText("Wrong admin password");
                alert.showAndWait();
            }
        });
    }

}
