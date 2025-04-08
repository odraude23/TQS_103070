package tqs.backend.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import tqs.backend.entities.Meal;
import tqs.backend.entities.Reservation;
import tqs.backend.entities.Restaurant;

public class ReservationTest {
    private Meal createDummyMeal() {
        Restaurant restaurant = new Restaurant("Sample Restaurant", "Test Location");
        return new Meal(
                "Test Meal",
                "Soup",
                "Dessert",
                "Main",
                "lunch",
                java.time.LocalDate.now().plusDays(1),
                restaurant,
                100
        );
    }

    @Test
    void testConstructorInitializesFieldsCorrectly() {
        Meal meal = createDummyMeal();
        Reservation reservation = new Reservation(4, meal);

        assertNotNull(reservation.getToken());
        assertNotNull(reservation.getReservationDateTime());
        assertEquals(4, reservation.getNumberOfPeople());
        assertFalse(reservation.isUsed());
        assertFalse(reservation.isCancelled());
        assertEquals(meal, reservation.getMeal());
    }

    @Test
    void testGettersAndSetters() {
        Meal meal = createDummyMeal();
        Reservation reservation = new Reservation(2, meal);
        reservation.setId(99L);
        reservation.setToken("test-token");
        reservation.setReservationDateTime(LocalDateTime.of(2025, 4, 5, 12, 30));
        reservation.setNumberOfPeople(3);
        reservation.setMeal(meal);

        assertEquals(99L, reservation.getId());
        assertEquals("test-token", reservation.getToken());
        assertEquals(LocalDateTime.of(2025, 4, 5, 12, 30), reservation.getReservationDateTime());
        assertEquals(3, reservation.getNumberOfPeople());
        assertEquals(meal, reservation.getMeal());
    }

    @Test
    void testUsedAndCancelledMethods() {
        Reservation reservation = new Reservation(2, createDummyMeal());

        assertFalse(reservation.isUsed());
        reservation.used();
        assertTrue(reservation.isUsed());

        assertFalse(reservation.isCancelled());
        reservation.cancelled();
        assertTrue(reservation.isCancelled());
    }

    @Test
    void testIsValid_TrueCase() {
        Reservation reservation = new Reservation(2, createDummyMeal());
        reservation.setReservationDateTime(LocalDateTime.now().plusHours(2)); // in future

        assertTrue(reservation.isValid());
    }

    @Test
    void testIsValid_FalseIfUsed() {
        Reservation reservation = new Reservation(2, createDummyMeal());
        reservation.setReservationDateTime(LocalDateTime.now().plusHours(2));
        reservation.used();

        assertFalse(reservation.isValid());
    }

    @Test
    void testIsValid_FalseIfCancelled() {
        Reservation reservation = new Reservation(2, createDummyMeal());
        reservation.setReservationDateTime(LocalDateTime.now().plusHours(2));
        reservation.cancelled();

        assertFalse(reservation.isValid());
    }

    @Test
    void testToStringContainsKeyFields() {
        Meal meal = createDummyMeal();
        Reservation reservation = new Reservation(2, meal);
        reservation.setId(555L);
        reservation.setToken("xyz-token");
        reservation.setReservationDateTime(LocalDateTime.of(2025, 4, 5, 12, 0));

        String result = reservation.toString();
        assertTrue(result.contains("555"));
        assertTrue(result.contains("xyz-token"));
        assertTrue(result.contains("2025-04-05"));
        assertTrue(result.contains("2"));
    }
}
