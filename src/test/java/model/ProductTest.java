package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class ProductTest {

    private static Product product;

    @BeforeAll
    public static void setup() {
        product = new Product(12348, "naranjas", 30, 1.9, 1.2);
    }

    @Test
    public void testSetters() {
        product.setId_product(12378);
        product.setDescription("manzanas");
        product.setUnit(30);
        product.setSale_price(1.7);
        product.setSupplier_price(1.2);

        assertEquals(12378, product.getId_product());
        assertEquals("manzanas", product.getDescription());
        assertEquals(30, product.getUnit());
        assertEquals(1.7, product.getSale_price(), 0.0);
        assertEquals(1.2, product.getSupplier_price(), 0.0);
    }

    @Test
    public void testGetId_client() {
        String id_client = product.getId_client();
        assertNull(id_client);
    }
}

