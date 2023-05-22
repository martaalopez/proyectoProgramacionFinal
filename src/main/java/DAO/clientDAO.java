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
    /**
     * Guarda un objeto client en la base de datos.
     *
     * @param o El objeto client a guardar.
     */
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

    /**
     * Obtiene una lista de todos los objetos client de la base de datos.
     *
     * @return Una lista de objetos client.
     * @throws SQLException Si ocurre un error de SQL al consultar la base de datos.
     */
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

    /**
     * Elimina un objeto client de la base de datos por su id_client.
     *
     * @param id_client El id_client del objeto client a eliminar.
     * @throws SQLException Si ocurre un error de SQL al eliminar el objeto de la base de datos.
     */
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

    /**
     * Actualiza un objeto client en la base de datos.
     *
     * @param o El objeto client actualizado.
     * @throws SQLException Si ocurre un error de SQL al actualizar el objeto en la base de datos.
     */
    @Override
    public void actualizar(client o) throws SQLException {
        String sql = "UPDATE client SET name = ?, phoneNumber = ?, mail = ? WHERE id_client = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql);
            pst.setString(1, o.getName());
            pst.setInt(2, o.getPhoneNumber());
            pst.setString(3, o.getMail());
            pst.setString(4, o.getId_client());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(clientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    @Override
    public void close() throws Exception {

    }

    /**
     * Obtiene el número total de clientes en la base de datos.
     *
     * @return El número total de clientes.
     */
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

