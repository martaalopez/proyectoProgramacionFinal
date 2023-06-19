package test;
import model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


    public class ProductTest {

        private Product product;

        @Before
        public void setup() {
            product = new Product(12348, "naranjas", 30, 1.9, 1.2);
        }

        @Test
        public void testSetters() {
            product.setId_product(12378);
            product.setDescription("manzanas");
            product.setUnit(30);
            product.setSale_price(1.7);
            product.setSupplier_price(1.2);

            Assert.assertEquals(12378, product.getId_product());
            Assert.assertEquals("manzanas", product.getDescription());
            Assert.assertEquals(30, product.getUnit());
            Assert.assertEquals(1.7, product.getSale_price(), 0.0);
            Assert.assertEquals(1.2, product.getSupplier_price(), 0.0);
        }

        @Test
        public void testGetId_client() {
            String id_client = product.getId_client();
            Assert.assertNull(id_client);
        }
    }

