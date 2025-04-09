package tqs.backend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import tqs.backend.entities.*;
import tqs.backend.repo.*;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource( locations = "classpath:application-integrationtest.properties")
class ReservationControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private MealRepo mealRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    private final String BASE_URL = "/api/v1/reservations";

    @AfterEach
    void cleanUp() {
        reservationRepo.deleteAll();
        mealRepo.deleteAll();
        restaurantRepo.deleteAll();
    }

    @Test
    void whenValidMealId_thenMakeReservation() {
        Meal meal = createTestMeal();
        int numberOfPeople = 2;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer> entity = new HttpEntity<>(numberOfPeople, headers);

        ResponseEntity<Reservation> response = restTemplate.postForEntity(
                BASE_URL + "/" + meal.getId(), entity, Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNumberOfPeople()).isEqualTo(numberOfPeople);
    }

    @Test
    void givenReservationToken_whenGetReservation_thenReturnReservation() {
        Reservation reservation = createTestReservation(4);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Reservation> response = restTemplate.exchange(
                BASE_URL + "/" + reservation.getToken(), HttpMethod.GET, null, Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isEqualTo(reservation.getToken());
    }

    @Test
    void givenReservation_whenMarkAsUsed_thenIsUsedTrue() {
        Reservation reservation = createTestReservation(2);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Reservation> response = restTemplate.postForEntity(
                BASE_URL + "/used/" + reservation.getToken(), null, Reservation.class);

        //get the reservation from the database
        Reservation updatedReservation = reservationRepo.findByToken(reservation.getToken());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedReservation.isUsed()).isTrue();
    }

    @Test
    void givenReservation_whenCancel_thenIsCancelledTrue() {
        Reservation reservation = createTestReservation(2);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Reservation> response = restTemplate.postForEntity(
                BASE_URL + "/cancel/" + reservation.getToken(), null, Reservation.class);

        //get the reservation from the database
        Reservation updatedReservation = reservationRepo.findByToken(reservation.getToken());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedReservation.isCancelled()).isTrue();
    }

    // Methods to create test data

    private Meal createTestMeal() {
        Restaurant r = new Restaurant("Testaurant", "Downtown");
        restaurantRepo.saveAndFlush(r);

        Meal meal = new Meal(
                "Special",
                "Tomato Soup",
                "Cheesecake",
                "Steak",
                "lunch",
                LocalDate.now(),
                r,
                20
        );

        return mealRepo.saveAndFlush(meal);
    }

    private Reservation createTestReservation(int numPeople) {
        Meal meal = createTestMeal();
        Reservation reservation = new Reservation(numPeople, meal);
        return reservationRepo.saveAndFlush(reservation);
    }
}
