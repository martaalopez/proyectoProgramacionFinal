/**module com.example.prueba100 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.xml.bind;
    requires junit;
    requires org.testng;


    opens com.example.prueba100 to javafx.fxml;
    opens connections to java.xml.bind;

    exports com.example.prueba100;
    opens model to javafx.base;
}*/
module com.example.prueba100 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.xml.bind;
    requires org.testng;

    opens com.example.prueba100 to javafx.fxml;
    opens connections to java.xml.bind;

    opens model to javafx.base;

    exports com.example.prueba100;
}
