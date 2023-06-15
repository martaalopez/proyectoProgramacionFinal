package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connections.ConnectionMySQL;

import model.Product;
import model.Supplier;

public class ProductDAO implements DAO<Product>{
    private final static String FINDBYID ="SELECT * FROM product WHERE id_product=?";
    private ConnectionMySQL connection;

    public ProductDAO(ConnectionMySQL connection) {
        this.connection = connection;
    }

    private Connection conn;

    public ProductDAO() {
        this.conn=ConnectionMySQL.getConnect();
    }

    public void guardar(Product p) {
        String sql = null;
        if (!p.getDescription().equals("")) {
            sql = "INSERT INTO product(id_product, description, unit, supplier_price, sale_price) VALUES(?,?,?,?,?)";
        }
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, p.getId_product());
            pst.setString(2, p.getDescription());
            pst.setInt(3, p.getUnit());
            pst.setDouble(4, p.getSupplier_price());
            pst.setDouble(5, p.getSale_price());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<Product> getAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        Product p = null;
        ResultSet rs = this.connection.consultar("SELECT id_product, description, unit,sale_price,supplier_price FROM product");
        try {
            while (rs.next()) {
                p = new Product();
                p.setId_product(rs.getInt("id_product"));
                p.setDescription(rs.getString("description").trim());
                p.setUnit(rs.getInt("unit"));
                p.setSale_price(rs.getDouble("sale_price"));
                p.setSupplier_price(rs.getDouble("supplier_price"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }



    public void eliminar(int  id_product) throws SQLException {
        String query = "DELETE FROM product WHERE id_product = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(query);
            pst.setInt(1, id_product);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizar(Product p) throws SQLException {
        String sql = "UPDATE product SET description = ?, unit = ?, sale_price = ?, supplier_price = ? WHERE id_product = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql);
            pst.setString(1, p.getDescription());
            pst.setInt(2, p.getUnit());
            pst.setDouble(3, p.getSale_price());
            pst.setDouble(4, p.getSupplier_price());
            pst.setInt(5, p.getId_product());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
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
