package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierTest {
    private static Supplier supplier;

    @BeforeAll
    public static void setup() {
        supplier = new Supplier(1, "Test Supplier", "Test Address", "1234567890", 1);
    }



    @Test
    public void testSetters() {
        supplier.setId_supplier(2);
        supplier.setName("Updated Supplier");
        supplier.setAddress("Updated Address");
        supplier.setPhoneNumber("9876543210");
        supplier.setId_product(2);

        assertEquals(2, supplier.getId_supplier());
        assertEquals("Updated Supplier", supplier.getName());
        assertEquals("Updated Address", supplier.getAddress());
        assertEquals("9876543210", supplier.getPhoneNumber());
        assertEquals(2, (int) supplier.getId_product());
    }

    @Test
    public void testGetProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product 1", 10, 100.0, 50.0));
        products.add(new Product(2, "Product 2", 5, 200.0, 100.0));

        supplier.setProducts(products);
        assertEquals(products, supplier.getProducts());
    }

}

