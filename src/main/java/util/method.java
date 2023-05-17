package util;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class method<T> implements AutoCloseable {


    public static boolean validarDNI(String id_client) {
        // Expresión regular para validar el formato del DNI
        String patron = "\\d{8}[A-HJ-NP-TV-Z]";
        return id_client.matches(patron);
    }
    public  static boolean GmailValidate(String gmail) {
        // Expresión regular para validar una dirección de correo de Gmail
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        // Compilamos la expresión regular en un patrón
        Pattern pattern = Pattern.compile(regex);
        // Creamos un objeto Matcher para comparar el patrón con la dirección de correo
        Matcher matcher = pattern.matcher(gmail);
        // Retornamos verdadero si la dirección de correo cumple con el patrón
        return ((Matcher) matcher).matches();
    }

    @Override
    public void close() throws Exception {
        // no hacer nada
    }
}

