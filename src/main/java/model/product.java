package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    public int id_product ;
     public String description;
    public int unit;

    public Double sale_price;

    public  Double  supplier_price;

    public  List<Client> clients = new ArrayList<>();
    public List<Product> getProducts;

    public Product(int  id_product, String description, int  unit, Double sale_price, Double supplier_price) {
        this.id_product = id_product;
        this.description=description;
        this.unit=unit;
        this.sale_price=sale_price;
        this.supplier_price=supplier_price;
    }

    public Product() {
        this(0, "", 0,0.0,0.0 );
    }


    public Product(int i, String testProduct, int i1, double v, double v1) {
        this.id_product = id_product;
        this.description=description;
        this.unit=unit;
        this.sale_price=sale_price;
        this.supplier_price=supplier_price;
    }

    public Product(int id, String description) {
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public Double getSupplier_price() {
        return supplier_price;
    }

    public void setSupplier_price(Double supplier_price) {
        this.supplier_price = supplier_price;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Product> getGetProducts() {
        return getProducts;
    }

    public void setGetProducts(List<Product> getProducts) {
        this.getProducts = getProducts;
    }

    @Override
    public String toString() {
        return "description='" + description + '\'' +
                ", sale_price=" + sale_price + ", id_product=" + id_product +
                '}';
    }

    public String getId_client() {
        String id_client = null;
        return id_client;
    }
}
