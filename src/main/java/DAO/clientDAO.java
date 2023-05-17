package DAO;

import connections.ConnectionMySQL;
import model.client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class  clientDAO  implements DAO<client>{
    private ConnectionMySQL connection;

    public clientDAO(ConnectionMySQL connection) {
        this.connection = connection;
    }
    private Connection conn =null;
    public void guardar(client o) {
        String sql = null;
        if (!o.getName().equals("")) {
            sql = "INSERT INTO client (id_client, name,phoneNumber,mail) VALUES('" + o.getId_client() + "','"
                    + o.getName() + "','"+o.getPhoneNumber()+ "','"+o.getMail()+"')";
        }
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<client> getAll() throws SQLException {
        List<client> list = new ArrayList<>();
        client o = null;
        ResultSet rs = this.connection.consultar("SELECT id_client,name,phoneNumber,id_person,mail FROM client");
        try {
            while (rs.next()) {
                o = new client();
                o.setId_client(rs.getString("id_client"));
                o.setName(rs.getString("name").trim());
                o.setPhoneNumber(rs.getInt("phoneNumber"));
                o.setMail(rs.getString("mail").trim());
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void eliminar(Integer entity) throws SQLException {

    }

    @Override
    public void eliminar(String id_client) throws SQLException {
        String query = "DELETE  FROM client WHERE id_client = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(query);
            pst.setString(1, id_client);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void actualizar(client o) throws SQLException {
        String sql = "UPDATE client SET name = ?,phoneNumber=?,mail=?,id_person=? WHERE id_client = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql);
            pst.setString(1, o.getName());
            pst.setString(2, o.getId_client());
            pst.setInt(3,o.getPhoneNumber());
            pst.setString(4,o.getMail());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @Override
    public void close() throws Exception {

    }

        @Override
        public int getClientTotal() {
            String query = "SELECT COUNT(id_client) FROM client";
            int sumClients = 0;
            try {
                PreparedStatement pst = this.connection.getConnect().prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    sumClients = rs.getInt(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return sumClients;
        }

    }

