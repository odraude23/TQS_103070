package lab2_3;

import java.util.Optional;
import org.apache.http.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;

public class ProductFinderService {
    private static final String API_PRODUCTS = "https://fakestoreapi.com/products/";
    private final ISimpleHttpClient httpClient;

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetails(Integer id) throws ParseException, IOException, org.json.simple.parser.ParseException {
        String response = httpClient.doHttpGet(API_PRODUCTS + id);

        JSONObject obj = (JSONObject) new JSONParser().parse(response);

        if (!obj.containsKey("id") || !obj.containsKey("image") || !obj.containsKey("title") ||
            !obj.containsKey("price") || !obj.containsKey("description") || !obj.containsKey("category")) {
            return Optional.empty();
        }

        if (obj.get("id") == null || obj.get("image") == null || obj.get("title") == null ||
            obj.get("price") == null || obj.get("description") == null || obj.get("category") == null) {
            return Optional.empty();
        }

        return Optional.of(new Product(
            Integer.parseInt(obj.get("id").toString()),
            obj.get("image").toString(),
            obj.get("description").toString(),
            Double.parseDouble(obj.get("price").toString()),
            obj.get("title").toString(),
            obj.get("category").toString()
        ));
    }
}

