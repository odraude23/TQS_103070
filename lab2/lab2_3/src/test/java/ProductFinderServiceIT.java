import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Optional;
import org.apache.http.ParseException;
import org.junit.Test;

import lab2_3.*;

public class ProductFinderServiceIT {

    @Test
    public void findProductDetailsTest() throws ParseException, IOException, org.json.simple.parser.ParseException {

        HttpClient httpClient = new HttpClient();
        ProductFinderService productFinderService = new ProductFinderService(httpClient);
        Optional<Product> product = productFinderService.findProductDetails(3);
        Integer id = product.get().getId();

        assertTrue(product.isPresent());
        assertTrue(id == 3);
        assertTrue(product.get().getTitle().equals("Mens Cotton Jacket"));
    }
}
