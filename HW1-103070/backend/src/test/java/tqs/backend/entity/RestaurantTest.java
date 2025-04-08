package tqs.backend.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import tqs.backend.entities.Restaurant;

public class RestaurantTest {
    @Test
    void testGettersAndSetters() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Casa da Comida");
        restaurant.setLocation("Old Town");

        assertEquals(1L, restaurant.getId());
        assertEquals("Casa da Comida", restaurant.getName());
        assertEquals("Old Town", restaurant.getLocation());
    }

    @Test
    void testConstructorAndFields() {
        Restaurant restaurant = new Restaurant("The Tasty Spoon", "Uptown");

        assertNull(restaurant.getId()); // not set manually
        assertEquals("The Tasty Spoon", restaurant.getName());
        assertEquals("Uptown", restaurant.getLocation());
    }

    @Test
    void testToStringContainsFields() {
        Restaurant restaurant = new Restaurant("Delish Diner", "City Center");
        restaurant.setId(42L);

        String output = restaurant.toString();
        assertTrue(output.contains("Delish Diner"));
        assertTrue(output.contains("City Center"));
        assertTrue(output.contains("42"));
    }
}
