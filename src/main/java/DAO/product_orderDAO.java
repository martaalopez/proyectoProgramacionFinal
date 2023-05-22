package DAO;

import connections.ConnectionMySQL;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import model.product;
import model.product_order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class product_orderDAO  implements DAO<product_order>{
    private static final String FINDBYID = "SELECT * FROM product_order WHERE id_product=?";
    private ConnectionMySQL connection;

    public product_orderDAO(ConnectionMySQL connection) {
        this.connection = connection;
    }
    private Connection conn =null;

    @Override
    public void guardar(product_order p) {
        String sql = null;
        if (!Objects.equals(p.getUnitProduct(), "")) {
            sql = "INSERT INTO product_order(id_product, id_client, unitProduct ,id_order) VALUES('" + p.getId_product() + "','"
                    + p.getId_client() + "','" + p.getUnitProduct() + "','"+p.getId_order()+"')";
            // subtract the purchased units from the units available for the product
            String updateSql = "UPDATE product SET unit = unit - " + p.getUnitProduct() + " WHERE id_product = " + p.getId_product();
            try {
                conn = connection.getConnect();
                conn.setAutoCommit(false); // start transaction
                PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.executeUpdate();
                PreparedStatement updatePst = conn.prepareStatement(updateSql);
                updatePst.executeUpdate();
                conn.commit(); // commit transaction
            } catch (SQLException ex) {
                try {
                    if (conn != null) {
                        conn.rollback(); // rollback transaction
                    }
                } catch (SQLException e) {
                    Logger.getLogger(product_orderDAO.class.getName()).log(Level.SEVERE, null, e);
                }
                Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (conn != null) {
                        conn.setAutoCommit(true);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(product_orderDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public List<product_order> getAll() throws SQLException {
        return null;
    }

    @Override
    public void eliminar(Integer entity) throws SQLException {

    }


    @Override
    public void actualizar(product_order entity) throws SQLException {

    }

    @Override
    public void eliminar(String entity) throws SQLException {

    }

    @Override
    public int getClientTotal() {
        return 0;
    }

    public List<XYChart.Data<String, Integer>> getProductOrderChartDataWithNames() {
        List<XYChart.Data<String, Integer>> chartDataList = new ArrayList<>();
        String sql = "SELECT p.description, SUM(po.unitProduct) " +
                "FROM product_order po " +
                "JOIN product p ON po.id_product = p.id_product " +
                "GROUP BY po.id_product";
        PreparedStatement pst = null;
        try {
            pst = this.connection.getConnect().prepareStatement(sql);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                String productName = res.getString(1);
                int unitProduct= res.getInt(2);
                chartDataList.add(new XYChart.Data<>(productName, unitProduct));
            }
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(product_orderDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return chartDataList;
    }


    public  product_order findById(Integer id_product) throws SQLException {
        product_order result ;
        try (Connection conn = ConnectionMySQL.getConnect();
             PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id_product);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    product_order l = new product_order();
                    l.setId_product(res.getInt("id_product"));
                    l.setId_client(res.getString("id_client"));
                    l.setUnitProduct(res.getInt("unidades producto"));
                    result = l;
                }
            }
        }
        return result=null;
    }

    @Override
    public void close() throws Exception {

    }
}
