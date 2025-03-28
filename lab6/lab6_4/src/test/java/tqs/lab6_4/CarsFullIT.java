package tqs.lab6_4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.junit.jupiter.api.Order;

import static org.hamcrest.Matchers.is;

@SpringBootTest(classes = Lab64Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers 
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
public class CarsFullIT {

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12")
    .withUsername("test")
    .withPassword("test")
    .withDatabaseName("test");


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @Order(1)
    public void testCreateCar() {
        Car car = new Car("Tesla", "Model S");

        RestAssuredMockMvc.given().contentType("application/json")
                .body(car)
                .when().post("/api/cars")
                .then().statusCode(201);
    }

    @Test
    @Order(2)
    public void testGetAllCars() {
        RestAssuredMockMvc.given().contentType("application/json")
                .when().get("/api/cars")
                .then().statusCode(200)
                .body("[0].maker", is("Ford"))
                .body("[0].model", is("Puma"));
    }

    @Test
    @Order(3)
    void testGetCarByID(){
        RestAssuredMockMvc.given().contentType("application/json")
                .when().get("/api/cars/3")
                .then().statusCode(200)
                .body("maker", is("Toyota"));
    }
}
