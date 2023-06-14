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

    private final String adminPassword = "123"; // Reemplaza con la contraseña proporcionada por la empresa


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
        adminDao = new AdminDAO(conexionBD); // Agregar esta línea para configurar el AdminDAO

        ObservableList<String> typeOptions = FXCollections.observableArrayList("admin", "client");
        type_up.setItems(typeOptions);
        type_up.getSelectionModel().selectFirst();
    }

    @FXML
    void signUp(ActionEvent event) {
        // Obtener los valores de los campos de entrada
        String name = txt_name_up.getText();
        int phoneNumber = Integer.parseInt(txt_number_up.getText());
        String mail = txt_mail_up.getText();
        String username = txt_username_up.getText();
        String password = txt_password_up.getText();
        String type = type_up.getValue();
        String idClient = txt_dni_up.getText();

        if (type.equals("admin")) {
            // Crear un nuevo objeto Admin con los valores proporcionados
            model.Admin admin = new model.Admin(idClient, name, mail, phoneNumber, password, username, type);
            // Establecer el campo id_client del objeto Admin
            admin.setId_client(idClient);
            admin.setType(type);
            admin.setPassword(password);
            admin.setUsername(username);
            admin.setPhoneNumber(phoneNumber);
            admin.setName(name);

            showAdminPasswordDialog(admin);
        } else {
            // Crear un nuevo objeto Client con los valores proporcionados
            model.Client client = new model.Client(idClient, name, mail, phoneNumber, password, username, type);
            // Establecer el campo id_client del objeto Client
            client.setId_client(idClient);
            client.setType(type);
            client.setPassword(password);
            client.setUsername(username);
            client.setPhoneNumber(phoneNumber);
            client.setName(name);

            // El cliente no es un administrador, guardar en ClientDAO
            clientDao.guardar(client);
        }
    }

    private void showAdminPasswordDialog(model.Admin admin) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Validación de contraseña de administrador");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingrese la contraseña de administrador proporcionada por la empresa:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(password -> {
            if (password.equals(adminPassword)) {
                // Contraseña de administrador correcta, guardar en AdminDAO
                AdminDAO adminDao = new AdminDAO(conexionBD);
                adminDao.guardar(admin);
            } else {
                // Contraseña de administrador incorrecta, mostrar mensaje de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de validación de contraseña de administrador");
                alert.setHeaderText(null);
                alert.setContentText("¡Contraseña de administrador incorrecta!");
                alert.showAndWait();
            }
        });
    }

}
