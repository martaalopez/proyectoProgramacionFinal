package com.example.prueba100;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.io.IOException;

public class LoginController{

    @FXML
    private Button btn_home;

    @FXML
    private Label labelUser;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private void btnHomeValidate() throws IOException {
        if (userField.getText().equals("admin") && passField.getText().equals("admin")){
            labelUser.setText("Correct user and password!");
            labelUser.setTextFill(Color.GREEN);
            App.setRoot("inicio");
        }else {
            if (userField.getText().equals("user") && passField.getText().equals("user")){
                App.setRoot("users");
            } else {
                labelUser.setText("Wrong username or password!");
                labelUser.setTextFill(Color.RED);
            }
        }
    }

}
