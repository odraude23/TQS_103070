package tqs.backend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.backend.entities.Restaurant;
import tqs.backend.repo.RestaurantRepo;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class RestaurantControllerIT {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestaurantRepo restaurantRepo;

    private final String BASE_URL = "/api/v1/restaurants";

    @AfterEach
    void tearDown() {
        restaurantRepo.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateRestaurant() {
        Restaurant r = new Restaurant("Casa Nova", "Rua Direita");

        ResponseEntity<Restaurant> response = restTemplate.postForEntity(BASE_URL, r, Restaurant.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Restaurant> found = restaurantRepo.findAll();
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getName()).isEqualTo("Casa Nova");
    }

    @Test
    void givenRestaurants_whenGetAll_thenStatus200() {
        createTestRestaurant("O Grelhador", "Rua Central");
        createTestRestaurant("Sabores da Serra", "Av. Verde");

        ResponseEntity<List<Restaurant>> response = restTemplate.exchange(
                BASE_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Restaurant>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).extracting(Restaurant::getName)
                .containsExactlyInAnyOrder("O Grelhador", "Sabores da Serra");
    }

    @Test
    void givenRestaurant_whenGetById_thenReturnCorrectRestaurant() {
        Restaurant saved = restaurantRepo.saveAndFlush(new Restaurant("Marisqueira Azul", "Beira-Mar"));

        ResponseEntity<Restaurant> response = restTemplate.getForEntity(BASE_URL + "/" + saved.getId(), Restaurant.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Marisqueira Azul");
        assertThat(response.getBody().getLocation()).isEqualTo("Beira-Mar");
    }

    private void createTestRestaurant(String name, String location) {
        Restaurant r = new Restaurant(name, location);
        restaurantRepo.saveAndFlush(r);
    }
}

