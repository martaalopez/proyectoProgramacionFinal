package model;
import DAO.AdminDAO;

import connections.ConnectionMySQL;

public class Admin extends Person {
    private AdminDAO adminDAO;
    private String id_client;

    public Admin( String id_client,String name, String mail, int phoneNumber, int id_person, String username, String password, String type) {
        super(id_person, name, mail, phoneNumber, username, password, type);
        this.id_client = id_client;
        ConnectionMySQL conexionBD = new ConnectionMySQL();
        this.adminDAO = new AdminDAO(conexionBD);
    }

    public Admin(String idClient, String name, String mail, int phoneNumber, String password, String username, String type) {
        this.id_client=idClient;
        this.name=name;
        this.mail=mail;
        this.phoneNumber=phoneNumber;
        this.password=password;
        this.username=username;
        this.type=type;

    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public Admin() {
        this("","","",0,0,"","","");
        initializeClientDAO();
    }

    private void initializeClientDAO() {
        try {
            ConnectionMySQL conexionBD = new ConnectionMySQL();
            this.adminDAO = new AdminDAO(conexionBD);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar la excepción, por ejemplo, mostrar un mensaje de error o lanzar una excepción personalizada
        }
    }
    public Admin(ConnectionMySQL connection) {
        ConnectionMySQL conexionBD = new ConnectionMySQL();
        this.adminDAO = new AdminDAO(conexionBD);
        initializeClientDAO();
    }

   @Override
    public void imprimirInformacion() {
        // Implementación de imprimirInformacion
    }
}
