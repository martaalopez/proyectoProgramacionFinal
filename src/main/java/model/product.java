package model;


import javafx.beans.property.*;
import java.util.ArrayList;
import java.util.List;

public class product {
    public IntegerProperty id_product = new SimpleIntegerProperty();
    public  StringProperty description =new SimpleStringProperty();
    public IntegerProperty unit;

    public DoubleProperty sale_price;

    public  DoubleProperty supplier_price;

    public  List<client> clients = new ArrayList<>();

    public List<product> getProducts;

    public product(IntegerProperty id_product,StringProperty description,IntegerProperty unit,DoubleProperty sale_price,DoubleProperty supplier_price) {
        this.id_product = id_product;
        this.description=description;
        this.unit=unit;
        this.sale_price=sale_price;
        this.supplier_price=supplier_price;
    }

    public product() {
        this(new SimpleIntegerProperty(0), new SimpleStringProperty(""), new SimpleIntegerProperty(0),new SimpleDoubleProperty(),new SimpleDoubleProperty() );
    }



    public product(SimpleIntegerProperty idProperty, SimpleStringProperty descriptionProperty) {
        this.id_product = idProperty;
        this.description= descriptionProperty;
    }

    public product(int i, String testProduct, int i1, double v, double v1) {
        this.id_product = id_product;
        this.description=description;
        this.unit=unit;
        this.sale_price=sale_price;
        this.supplier_price=supplier_price;
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getUnit() {
        return unit.get();
    }

    public IntegerProperty unitProperty() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit.set(unit);
    }


    public double getSale_price() {
        return sale_price.get();
    }

    public DoubleProperty sale_priceProperty() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price.set(sale_price);
    }

    public double getSupplier_price() {
        return supplier_price.get();
    }

    public DoubleProperty supplier_priceProperty() {
        return supplier_price;
    }

    public void setSupplier_price(double supplier_price) {
        this.supplier_price.set(supplier_price);
    }


    public List<client> getClients() {
        return clients;
    }

    public void setClients(List<client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return  id_product.getValue()+"-->"+description.getValue() +"-->" + sale_price.getValue()+"€";
    }

}