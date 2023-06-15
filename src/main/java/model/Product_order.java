package model;

public class Product_order {
    private int id_product ;
    private String id_client ;

    private int  unitProduct;

    private int id_order ;

    private Product product;
    private Client client;


    public Product_order() {
        this(0,"",0,0);
    }


    public Product_order(int id_product, String  id_client, int unitProduct, int id_order) {
        this.id_product=(id_product);
        this.id_client=(id_client);
        this.unitProduct=(unitProduct);
        this.id_order=(id_order);
    }



    public Product_order(int idProduct, int unitOrder, String idClient, int idOrder) {
        this.id_product = idProduct;
        this.unitProduct = unitOrder;
        this.id_client = idClient;
        this.id_order = idOrder;
    }


    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public int getUnitProduct() {
        return unitProduct;
    }

    public void setUnitProduct(int unitProduct) {
        this.unitProduct = unitProduct;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "product_order{" +
                "id_product=" + id_product +
                ", id_client='" + id_client + '\'' +
                ", unitProduct=" + unitProduct +
                ", id_order=" + id_order +
                ", product=" + product +
                ", client=" + client +
                '}';
    }
}
