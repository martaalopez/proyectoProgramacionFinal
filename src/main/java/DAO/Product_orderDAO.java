package DAO;

import connections.ConnectionMySQL;
import javafx.scene.chart.XYChart;
import model.Product_order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Product_orderDAO implements DAO<Product_order>{
    private static final String FINDBYID = "SELECT * FROM product_order WHERE id_product=?";
    private ConnectionMySQL connection;

    public Product_orderDAO(ConnectionMySQL connection) {
        this.connection = connection;
    }
    private Connection conn =null;

    @Override
    public void guardar(Product_order p) {
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
                    Logger.getLogger(Product_orderDAO.class.getName()).log(Level.SEVERE, null, e);
                }
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (conn != null) {
                        conn.setAutoCommit(true);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Product_orderDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public List<Product_order> getAll() throws SQLException {
        List<Product_order> list = new ArrayList<>();
        Product_order p = null;
        ResultSet rs = this.connection.consultar("SELECT id_product, id_client, unitProduct,id_order FROM product_order");
        try {
            while (rs.next()) {
                p = new Product_order();
                p.setId_product(rs.getInt("id_product"));
                p.setId_client(rs.getString("id_client"));
                p.setId_order(rs.getInt("id_order"));
                p.setUnitProduct(rs.getInt("unitProduct"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void eliminar(int entity) throws SQLException {

    }


    @Override
    public void actualizar(Product_order entity) throws SQLException {

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
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Product_orderDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return chartDataList;
    }


  /*  public Product_order findById(int id_product) throws SQLException {
        Product_order result ;
        try (Connection conn = ConnectionMySQL.getConnect();
             PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id_product);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Product_order l = new Product_order();
                    l.setId_product(res.getInt("id_product"));
                    l.setId_client(res.getString("id_client"));
                    l.setUnitProduct(res.getInt("unidades producto"));
                    result = l;
                }
            }
        }
        return result=null;
    }*/

    @Override
    public void close() throws Exception {

    }


    public List<Product_order> getOrderByClientName(String clientName) throws SQLException {
        List<Product_order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = connection.getConnect(); // Obtener la conexión

            // Preparar y ejecutar la consulta
            String sql = "SELECT po.id_product, po.id_client, po.unitProduct, po.id_order " +
                    "FROM client c " +
                    "JOIN product_order po ON c.id_client = po.id_client " +
                    "WHERE c.name = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, clientName);
            resultSet = statement.executeQuery();

            // Procesar los resultados y crear objetos Product_order
            while (resultSet.next()) {
                Product_order order = new Product_order();
                order.setId_product(resultSet.getInt("id_product"));
                order.setId_client(resultSet.getString("id_client"));
                order.setUnitProduct(resultSet.getInt("unitProduct"));
                order.setId_order(resultSet.getInt("id_order"));
                orders.add(order);
            }

            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Cerrar los recursos (resultset y statement) en el orden inverso
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            // No cierres la conexión aquí, déjala abierta para futuras operaciones
        }
    }


}