package model;
import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private String id_client;
    private List<Product> products = new ArrayList<>();

    public Client(String id_client, String name, String mail, int phoneNumber, int id_person) {
        super(id_person, name, mail, phoneNumber);
        this.id_client = id_client;
    }

    public Client() {
        this("", "", "", 0, 0);
    }

    public Client(String idClient, String name, String mail, int phoneNumber, String password, String username, String type) {
    }


    @Override
    public void imprimirInformacion() {

    }

    public String getId_client() {
        return id_client;
    }


    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
