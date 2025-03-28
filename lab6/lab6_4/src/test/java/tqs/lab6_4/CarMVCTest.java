package tqs.lab6_4;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;

@WebMvcTest(CarController.class)
public class CarMVCTest {

    @MockBean
    private CarManegerService carService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void postCar() {
        Car car = new Car("BMW", "X5");

        when(carService.save(Mockito.any(Car.class))).thenReturn(car);

        RestAssuredMockMvc
            .given()
            .contentType("application/json")
            .body(car)
            .when()
            .post("api/cars")
            .then()
            .statusCode(201);
    }

    @Test
    public void getCars() {
        Car car = new Car("BMW", "X5");
        Car car2 = new Car("Mercedes", "C-Class");

        when(carService.getAllCars()).thenReturn(List.of(car, car2));

        RestAssuredMockMvc
            .given()
            .contentType("application/json")
            .when()
            .get("api/cars")
            .then()
            .statusCode(200)
            .body("[0].maker", is(car.getMaker()))
            .body("[1].maker", is(car2.getMaker()));
    }

    @Test
    public void getCarById() {
        Car car = new Car("BMW", "X5");
        car.setId(1L);

        when(carService.getCarDetails(1L)).thenReturn(java.util.Optional.of(car));

        RestAssuredMockMvc
            .given()
            .contentType("application/json")
            .when()
            .get("api/cars/1")
            .then()
            .statusCode(200)
            .body("maker", is(car.getMaker()));
    }
}
