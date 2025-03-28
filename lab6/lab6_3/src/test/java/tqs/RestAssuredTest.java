package tqs;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static io.restassured.RestAssured.given;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class RestAssuredTest {
    
    @Test
    public void testGetAllEmployees() {
        given().when().get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200);
        given().when().get("https://jsonplaceholder.typicode.com/todos/4").then().body("title", is("et porro tempora"));
        given().when().get("https://jsonplaceholder.typicode.com/todos").then().body("id", hasItems(198, 199));
        given().when().get("https://jsonplaceholder.typicode.com/todos").then().time(lessThan(2L), TimeUnit.SECONDS);
    }
}
