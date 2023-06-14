package connections;


import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ConnectionMySQL {

    private String file = "conexion.xml";
    private static ConnectionMySQL _newInstance;
    public static Connection connection;
    private static Statement statement;

    public ConnectionMySQL() {
        ConnectionData dc = loadXML();

        try {
            connection = DriverManager.getConnection(dc.getServer() + "/" + dc.getDatabase(), dc.getUsername(), dc.getPassword());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            connection = null;
            e.printStackTrace();
        }
    }

    public static Connection getConnect() {
        if (_newInstance== null) {
            _newInstance= new ConnectionMySQL();
        }
        return connection;
    }

    /**
     * Método para leer los datos de la conexion guardados en el fichero file
     * @return objeto ConnectionData con los datos leidos
     */
    public  ConnectionData loadXML() {
        ConnectionData con = new ConnectionData();
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ConnectionData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ConnectionData newR = (ConnectionData) jaxbUnmarshaller.unmarshal(new File(file));
            con = newR;
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }


    public static ResultSet consultar(String sql) throws SQLException {
        ResultSet rs = null;
        if (ConnectionMySQL.statement == null) {
            ConnectionMySQL.statement = connection.createStatement();
        }
        rs = ConnectionMySQL.statement.executeQuery(sql);
        System.out.println(sql);
        return rs;
    }

    public static void cerrar() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean GUARDAR(String sql) throws SQLException {
        return (statement.executeUpdate(sql)>0);
    }


    public void close() {
    }
}
