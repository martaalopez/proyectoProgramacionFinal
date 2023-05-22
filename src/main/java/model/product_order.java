package model;

import javafx.beans.property.*;

public class product_order {
    private IntegerProperty id_product = new SimpleIntegerProperty();
    private StringProperty id_client = new SimpleStringProperty();

    private IntegerProperty unitProduct = new SimpleIntegerProperty();

    private IntegerProperty id_order = new SimpleIntegerProperty();

    private product product;
    private client client;


    public product_order() {
        this(new SimpleIntegerProperty(0), new SimpleStringProperty(""), new SimpleIntegerProperty(0),new SimpleIntegerProperty(0));
    }


    public product_order(IntegerProperty id_product, StringProperty id_client, IntegerProperty unitProduct,IntegerProperty id_order) {
        this.id_product.bindBidirectional(id_product);
        this.id_client.bindBidirectional(id_client);
        this.unitProduct.bindBidirectional(unitProduct);
        this.id_order.bindBidirectional(id_order);
    }

    public product_order(product selectedProduct, int unitOrder, client selectedClient,int id_order) {
        this(new SimpleIntegerProperty(selectedProduct.getId_product()), new SimpleStringProperty(selectedClient.getId_client()), new SimpleIntegerProperty(unitOrder),new SimpleIntegerProperty(id_order));
        product = selectedProduct;
        client = selectedClient;
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


    public int getUnitProduct() {
        return unitProduct.get();
    }

    public IntegerProperty unitProductProperty() {
        return unitProduct;
    }

    public void setUnitProduct(int unitProduct) {
        this.unitProduct.set(unitProduct);
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

    public int getId_order() {
        return id_order.get();
    }

    public IntegerProperty id_orderProperty() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order.set(id_order);
    }

    @Override
    public String toString() {
        return "product_order{" +
                "id_product=" + id_product +
                ", id_client=" + id_client +
                ", unitProduct=" + unitProduct +
                ", id_order=" + id_order +
                ", product=" + product +
                ", client=" + client +
                '}';
    }

    public product getProduct() {
        return product;
    }

    public client getClient() {
        return client;
    }

}
