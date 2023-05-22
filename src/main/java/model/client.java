package model;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class client extends person {
    private   StringProperty id_client = new SimpleStringProperty();
    private List<product> products= new ArrayList<>();

    public client(StringProperty id_client, StringProperty name, StringProperty mail,IntegerProperty phoneNumber,IntegerProperty id_person ) {
        super(id_person,name, mail, phoneNumber);
        this.id_client = id_client;
    }
    public client(){
        this(new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleStringProperty(""),new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
    }

    @Override
    public void imprimirInformacion() {

    }

    public String getId_client() {
        return id_client.get();
    }

    public StringProperty id_clientProperty() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client.set(id_client);
    }

    public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return id_client.getValue() + " " + name.getValue();
    }


}
