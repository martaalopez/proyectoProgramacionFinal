package model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class SupplierTest {
    private Supplier supplier;

    @Before
    public void setup() {
        supplier = new Supplier(1, "Test Supplier", "Test Address", "1234567890", 1);
    }

    

    @Test
    public void testSetters() {
        supplier.setId_supplier(2);
        supplier.setName("Updated Supplier");
        supplier.setAddress("Updated Address");
        supplier.setPhoneNumber("9876543210");
        supplier.setId_product(2);

        Assert.assertEquals(2, supplier.getId_supplier());
        Assert.assertEquals("Updated Supplier", supplier.getName());
        Assert.assertEquals("Updated Address", supplier.getAddress());
        Assert.assertEquals("9876543210", supplier.getPhoneNumber());
        Assert.assertEquals(2, (int) supplier.getId_product());
    }

    @Test
    public void testGetProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product 1", 10, 100.0, 50.0));
        products.add(new Product(2, "Product 2", 5, 200.0, 100.0));

        supplier.setProducts(products);
        Assert.assertEquals(products, supplier.getProducts());
    }

}

