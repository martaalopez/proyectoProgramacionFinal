package DAO;
import connections.ConnectionMySQL;
import model.Admin;

import java.sql.*;

public class AdminDAO {

    private ConnectionMySQL connection;

    private Connection conn =null;
    public AdminDAO(ConnectionMySQL conexionBD) {
        this.connection = conexionBD;
    }


    public void guardar(Admin o) {
        String sql = "INSERT INTO admin (phoneNumber,mail,username,password,name,id_client,type) " +
                "VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, o.getPhoneNumber());
            pst.setString(2, o.getMail());
            pst.setString(3, o.getUsername());
            pst.setString(4, o.getPassword());
            pst.setString(5, o.getName());
            pst.setString(6, o.getId_client());
            pst.setString(7, o.getType());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public boolean validarCredenciales(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username=? AND password=? ";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // Devuelve true si se encuentra un cliente con los datos proporcionados, o false en caso contrario
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Devuelve false si ocurre una excepción
    }
}
