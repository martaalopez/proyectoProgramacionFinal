package model;

import java.util.List;

public class Supplier {
    private int id_supplier;
    private String name;
    private String address;
    private String phoneNumber;
    private Integer id_product;

    private List<Product> products = null;


    public Supplier(int id_supplier, String name, String address, String phoneNumber, int id_product) {
        this.id_supplier = id_supplier;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id_product = id_product;


    }

    public Supplier() {
        this(0, "", "", "", 0);

    }

    public Supplier(String name, String address, String phoneNumber) {
    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getId_product() {
        return id_product;
    }

    public void setId_product(Integer id_product) {
        this.id_product = id_product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
