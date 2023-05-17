package model;

import javafx.beans.property.*;

public class product_order {
    private IntegerProperty id_product = new SimpleIntegerProperty();
    private StringProperty id_client = new SimpleStringProperty();

    private IntegerProperty unitProduct = new SimpleIntegerProperty();

    private product product;
    private client client;


    public product_order() {
        this(new SimpleIntegerProperty(0), new SimpleStringProperty(""), new SimpleIntegerProperty(0));
    }


    public product_order(IntegerProperty id_product, StringProperty id_client, IntegerProperty unitProduct) {
        this.id_product.bindBidirectional(id_product);
        this.id_client.bindBidirectional(id_client);
        this.unitProduct.bindBidirectional(unitProduct);
    }

    public product_order(product selectedProduct, int unitOrder, client selectedClient) {
        this(new SimpleIntegerProperty(selectedProduct.getId_product()), new SimpleStringProperty(selectedClient.getId_client()), new SimpleIntegerProperty(unitOrder));
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

    @Override
    public String toString() {
        return "product_order{" +
                "id_product=" + id_product +
                ", id_client=" + id_client +
                ", unitProduct=" + unitProduct +
                ", product=" + product +
                '}';
    }

    public product getProduct() {
        return product;
    }

    public client getClient() {
        return client;
    }

}
