package lab2_2;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductFinderServiceTest {

    @Mock
    private ISimpleHttpClient httpClient;

    @InjectMocks
    private ProductFinderService productFinderService;
    
    @DisplayName("Test find product details")
    @Test
    void findProductDetailsTest() throws IOException, org.json.simple.parser.ParseException {
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn("{\"id\":3,\"title\":\"Mens Cotton Jacket\",\"price\":10.0,\"description\":\"Something\",\"category\":\"Something\",\"image\":\"Something\"}");
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/300")).thenReturn("{\"id\":4,\"title\":\"Mens Cotton Jacket\",\"price\":10.0,\"description\":\"Something\",\"category\":\"Something\"}");
        Optional<Product> product = productFinderService.findProductDetails(3);
        Optional<Product> product2 = productFinderService.findProductDetails(300);

        assertEquals(product.get().getId(), 3);
        assertEquals(product.get().getTitle(), "Mens Cotton Jacket");
        assertEquals(product.get().getPrice(), 10.0);
        verify(httpClient, times(2)).doHttpGet(anyString());

        assertTrue(product2.isEmpty());
    }
}
