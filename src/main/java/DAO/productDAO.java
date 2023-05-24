package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import connections.ConnectionMySQL;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.product;
import model.supplier;

public class productDAO  implements DAO<product>{
    private final static String FINDBYID ="SELECT * FROM product WHERE id_product=?";
    private ConnectionMySQL connection;

    public productDAO(ConnectionMySQL connection) {
        this.connection = connection;
    }

    private Connection conn;

    public productDAO() {
        this.conn=ConnectionMySQL.getConnect();
    }

    public void guardar(product p) {
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
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<product> getAll() throws SQLException {
        List<product> list = new ArrayList<>();
        product p = null;
        ResultSet rs = this.connection.consultar("SELECT id_product, description, unit,sale_price,supplier_price FROM product");
        try {
            while (rs.next()) {
                p = new product();
                p.setId_product(rs.getInt("id_product"));
                p.setDescription(rs.getString("description").trim());
                p.setUnit(rs.getInt("unit"));
                p.setSale_price(rs.getDouble("sale_price"));
                p.setSupplier_price(rs.getDouble("supplier_price"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    public void eliminar(Integer id_product) throws SQLException {
        String queryDelete = "DELETE p, s FROM product p LEFT JOIN supplier s ON p.id_product = s.id_product WHERE p.id_product = ?";

        try {
            PreparedStatement pstDelete = this.connection.getConnect().prepareStatement(queryDelete);
            pstDelete.setInt(1, id_product);
            pstDelete.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void actualizar(product p) throws SQLException {
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
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public static product findById(Integer id) throws SQLException {
        product result ;
        try (Connection conn = ConnectionMySQL.getConnect();
             PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    product l = new product();
                    l.setDescription(res.getString("description"));
                    l.setSale_price(res.getDouble("sale_price"));
                    l.setSupplier_price(res.getDouble("supplier_price"));
                    result = l;
                }
            }
        }
        return result=null;
    }
    public static List<product> getOutOfStockProducts() throws SQLException {
        List<product> outOfStockProducts = new ArrayList<>();

        String query = "SELECT p.id_product, p.description, s.name, s.address, s.phoneNumber " +
                "FROM product p " +
                "JOIN supplier s ON p.id_product = s.id_product " +
                "WHERE p.unit = 0";

        try (Connection conn = ConnectionMySQL.getConnect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SimpleStringProperty nameProperty = new SimpleStringProperty(rs.getString("name"));
                SimpleStringProperty addressProperty = new SimpleStringProperty(rs.getString("address"));
                SimpleStringProperty phoneNumberProperty = new SimpleStringProperty(rs.getString("phone_number"));

                supplier supplier = new supplier(nameProperty, addressProperty, phoneNumberProperty);

                SimpleIntegerProperty idProperty = new SimpleIntegerProperty(rs.getInt("id_product"));
                SimpleStringProperty descriptionProperty = new SimpleStringProperty(rs.getString("description"));

                product product = new product(idProperty, descriptionProperty);
                supplier.getProducts().add(product);

                outOfStockProducts.add(product);
            }
        }

        return outOfStockProducts;
    }


}

