package model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.List;

public class supplier {
private  IntegerProperty id_supplier= new SimpleIntegerProperty();

private StringProperty name;
private StringProperty address;
private StringProperty phoneNumber;
private IntegerProperty id_product;

private List<product> products=null;


    public supplier(IntegerProperty id_supplier, StringProperty name,  StringProperty address,StringProperty phoneNumber,IntegerProperty id_product) {
        this.id_supplier = id_supplier;
        this.name = name;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.id_product=id_product;


    }
    public supplier() {
        this(new SimpleIntegerProperty(0), new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleIntegerProperty(0));

    }


    public supplier(SimpleStringProperty nameProperty, SimpleStringProperty addressProperty, SimpleStringProperty phoneNumberProperty) { this.name = nameProperty;
        this.name = nameProperty;
        this.address = addressProperty;
        this.phoneNumber = phoneNumberProperty;
    }


    public int getId_supplier() {
        return id_supplier.get();
    }

    public IntegerProperty id_supplierProperty() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier.set(id_supplier);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }


    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public int getId_product() {
        return id_product.get();
    }

    public IntegerProperty id_productProperty() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product.set(id_product);
    }

    public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }
}
