package DAO;

import connections.ConnectionMySQL;
import model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierDAO implements DAO<Supplier>{

    private ConnectionMySQL connection;

    public SupplierDAO(ConnectionMySQL connection) {
        this.connection = connection;
    }

    private Connection conn;

    public SupplierDAO() {
        this.conn=ConnectionMySQL.getConnect();
    }


    public void guardar(Supplier s) {
        String sql = "INSERT INTO supplier (id_supplier, name, address, phoneNumber, id_product) VALUES (?, ?, ?, ?, ?)";

<<<<<<< HEAD:src/main/java/DAO/supplierDAO.java
    public void guardar(supplier s) {
            String sql = "INSERT INTO supplier (id_supplier, name, address, phoneNumber, id_product) VALUES (?, ?, ?, ?, ?)";

            try {
                PreparedStatement pst = this.connection.getConnect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, s.getId_supplier());
                pst.setString(2, s.getName());
                pst.setString(3, s.getAddress());
                pst.setString(4, s.getPhoneNumber());
                pst.setInt(5, s.getId_product());
                pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(supplierDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
=======
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, s.getId_supplier());
            pst.setString(2, s.getName());
            pst.setString(3, s.getAddress());
            pst.setString(4, s.getPhoneNumber());
            pst.setInt(5, s.getId_product());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
>>>>>>> final:src/main/java/DAO/SupplierDAO.java
        }
    }


    @Override
    public List<Supplier> getAll() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        Supplier p = null;
        ResultSet rs = this.connection.consultar("SELECT id_supplier,name,address ,phoneNumber,id_product FROM supplier");
        try {
            while (rs.next()) {
                p = new Supplier();
                p.setId_product(rs.getInt("id_product"));
                p.setName(rs.getString("name"));
                p.setAddress(rs.getString("address"));
                p.setPhoneNumber(rs.getString("phone number"));
                p.setId_supplier(rs.getInt("id_suppler"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void eliminar(int id_supplier) throws SQLException {
        String query = "DELETE FROM supplier WHERE id_supplier= ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(query);
            pst.setInt(1, id_supplier);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actualizar(Supplier p) throws SQLException {
        String sql = "UPDATE supplier SET name = ?, address = ?, phoneNumber = ? WHERE id_supplier = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql);
            pst.setString(1, p.getName());
            pst.setString(2, p.getAddress());
            pst.setString(3, p.getPhoneNumber());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar(String entity) throws SQLException {

    }
    @Override
    public int getClientTotal() {
        return 0;
    }

    @Override
    public void close() throws Exception {

    }
}