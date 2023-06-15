package DAO;

import connections.ConnectionMySQL;
import model.Client;
import model.Product_order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO implements DAO<Client> {
    private ConnectionMySQL connection;

    public ClientDAO() {
        this.connection = connection;
    }

    private Connection conn = null;

    public ClientDAO(ConnectionMySQL conexionBD) {
        this.connection = connection;
    }

    @Override
    public void guardar(Client o) {
        // Prepare the SQL statement to insert a client into the database
        String sql = "INSERT INTO client (id_client, name, phoneNumber, mail, username, password, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            // Prepare the statement with the SQL and retrieve the generated keys (auto-generated ID)
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, o.getId_client());
            pst.setString(2, o.getName());
            pst.setInt(3, o.getPhoneNumber());
            pst.setString(4, o.getMail());
            pst.setString(5, o.getUsername());
            pst.setString(6, o.getPassword());
            pst.setString(7, o.getType());

            // Execute the statement to insert the client into the database
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves a list of all client objects from the database.
     *
     * @return A list of client objects.
     * @throws SQLException If an SQL error occurs while querying the database.
     */
    @Override
    public List<Client> getAll() throws SQLException {
        List<Client> list = new ArrayList<>();
        Client o = null;
        ResultSet rs = this.connection.consultar("SELECT id_client, name, phoneNumber, id_person, mail, username, password, type FROM client");
        try {
            while (rs.next()) {
                o = new Client();
                o.setId_client(rs.getString("id_client"));
                o.setName(rs.getString("name"));
                o.setPhoneNumber(rs.getInt("phoneNumber"));
                o.setMail(rs.getString("mail"));
                o.setUsername(rs.getString("username"));
                o.setPassword(rs.getString("password"));
                o.setType(rs.getString("type"));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void eliminar(int entity) throws SQLException {
        // Implementation not provided
    }

    /**
     * Deletes a client object from the database by its id_client.
     *
     * @param id_client The id_client of the client object to be deleted.
     * @throws SQLException If an SQL error occurs while deleting the object from the database.
     */
    @Override
    public void eliminar(String id_client) throws SQLException {
        String query = "DELETE FROM client WHERE id_client = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(query);
            pst.setString(1, id_client);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates a client object in the database.
     *
     * @param o The updated client object.
     * @throws SQLException If an SQL error occurs while updating the object in the database.
     */
    @Override
    public void actualizar(Client o) throws SQLException {
        String sql = "UPDATE client SET name = ?, phoneNumber = ?, mail = ? WHERE id_client = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql);
            pst.setString(1, o.getName());
            pst.setInt(2, o.getPhoneNumber());
            pst.setString(3, o.getMail());
            pst.setString(4, o.getId_client());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void close() throws Exception {
        // Implementation not provided
    }

    /**
     * Retrieves the total number of clients in the database.
     *
     * @return The total number of clients.
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
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sumClients;
    }

    public List<Product_order> getOrdersByClientName(String clientName) throws SQLException {
        List<Product_order> orders = new ArrayList<>();
        String sql = "SELECT po.* " +
                "FROM client c " +
                "JOIN product_order po ON c.id_client = po.id_client " +
                "WHERE c.name = ?";
        try (Connection conn = connection.getConnect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, clientName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Product_order order = new Product_order();
                    // Set order properties here using rs.getXXX() methods
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public boolean validarCredenciales(String username, String password) throws SQLException {
        String sql = "SELECT * FROM client WHERE username=? AND password=?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // Returns true if a client is found with the provided data, false otherwise
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Returns false if an exception occurs
    }

    public int guardarPedido(int clientId) throws SQLException {
        int generatedOrderId = -1;
        String query = "INSERT INTO product_order (id_client) VALUES (?)";

        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(query);
            pst.setInt(1, clientId);
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedOrderId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return generatedOrderId;
    }
    public String getClientNameByUsername(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String clientName = null;

        try {
            conn = connection.getConnect(); // Obtener la conexión

            // Preparar y ejecutar la consulta
            String sql = "SELECT id_client FROM client WHERE username = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            // Obtener el nombre del cliente
            if (resultSet.next()) {
                clientName = resultSet.getString("id_client");
            }

            return clientName;
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


    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM client WHERE username = ?";
        try {
            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

