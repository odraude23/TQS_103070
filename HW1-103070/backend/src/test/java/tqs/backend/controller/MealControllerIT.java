package tqs.backend.controller;

import tqs.backend.entities.Meal;
import tqs.backend.entities.Restaurant;
import tqs.backend.repo.MealRepo;
import tqs.backend.repo.RestaurantRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class MealControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MealRepo mealRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    private final String baseUrl = "/api/v1/meals";

    @AfterEach
    void tearDown() {
        mealRepo.deleteAll();
        restaurantRepo.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateMeal() {
        Restaurant restaurant = new Restaurant("Testaurant", "Aveiro");
        restaurantRepo.saveAndFlush(restaurant);

        Meal meal = new Meal(
                "Daily Meal",
                "Tomato Soup",
                "Chocolate Cake",
                "Grilled Chicken",
                "lunch",
                LocalDate.now(),
                restaurant,
                50
        );

        ResponseEntity<Meal> response = restTemplate.postForEntity(baseUrl, meal, Meal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Meal> allMeals = mealRepo.findAll();
        assertThat(allMeals).hasSize(1);
        assertThat(allMeals.get(0).getName()).isEqualTo("Daily Meal");
    }

    @Test
    void givenMeals_whenGetAllMeals_thenStatus200() {
        Restaurant r1 = restaurantRepo.saveAndFlush(new Restaurant("ResA", "Aveiro"));
        createTestMeal("Meal 1", r1);
        createTestMeal("Meal 2", r1);

        ResponseEntity<List<Meal>> response = restTemplate.exchange(
                baseUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Meal>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).extracting(Meal::getName).containsExactlyInAnyOrder("Meal 1", "Meal 2");
    }

    @Test
    void givenMealId_whenGetMealById_thenReturnMeal() {
        Restaurant restaurant = restaurantRepo.saveAndFlush(new Restaurant("SoloRes", "Aveiro"));
        Meal saved = mealRepo.saveAndFlush(createMeal("Unique Meal", restaurant));

        ResponseEntity<Meal> response = restTemplate.getForEntity(baseUrl + "/" + saved.getId(), Meal.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Unique Meal");
    }

    @Test
    void givenMeals_whenGetByRestaurant_thenFilterCorrectly() {
        Restaurant res1 = restaurantRepo.saveAndFlush(new Restaurant("Res1", "Aveiro"));
        Restaurant res2 = restaurantRepo.saveAndFlush(new Restaurant("Res2", "Aveiro"));

        createTestMeal("Meal A", res1);
        createTestMeal("Meal B", res1);
        createTestMeal("Meal C", res2);

        ResponseEntity<List<Meal>> response = restTemplate.exchange(
                baseUrl + "/restaurant/" + res1.getId(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Meal>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).extracting(Meal::getName).containsExactlyInAnyOrder("Meal A", "Meal B");
    }

    // Helper methods

    private void createTestMeal(String name, Restaurant restaurant) {
        mealRepo.saveAndFlush(createMeal(name, restaurant));
    }

    private Meal createMeal(String name, Restaurant restaurant) {
        return new Meal(
                name,
                "Soup of " + name,
                "Dessert of " + name,
                "Main Course of " + name,
                "lunch",
                LocalDate.now(),
                restaurant,
                30
        );
    }
}

