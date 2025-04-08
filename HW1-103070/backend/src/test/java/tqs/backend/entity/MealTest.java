package tqs.backend.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import tqs.backend.entities.Meal;
import tqs.backend.entities.Restaurant;

public class MealTest {
    @Test
    void testGettersAndSetters() {
        Restaurant restaurant = new Restaurant("Bistro", "Downtown");

        Meal meal = new Meal();
        meal.setId(1L);
        meal.setName("Special Lunch");
        meal.setSoup("Pumpkin Soup");
        meal.setDessert("Apple Pie");
        meal.setMainCourse("Roast Beef");
        meal.setMealType("lunch");
        meal.setDate(LocalDate.of(2025, 4, 5));
        meal.setReservationLimit(25);
        meal.setRestaurant(restaurant);

        assertEquals(1L, meal.getId());
        assertEquals("Special Lunch", meal.getName());
        assertEquals("Pumpkin Soup", meal.getSoup());
        assertEquals("Apple Pie", meal.getDessert());
        assertEquals("Roast Beef", meal.getMainCourse());
        assertEquals("lunch", meal.getMealType());
        assertEquals(LocalDate.of(2025, 4, 5), meal.getDate());
        assertEquals(25, meal.getReservationLimit());
        assertEquals(restaurant, meal.getRestaurant());
    }

    @Test
    void testToStringContainsFields() {
        Restaurant restaurant = new Restaurant("Café Java", "Center");
        Meal meal = new Meal("Combo Meal", "Minestrone", "Tiramisu", "Spaghetti", "dinner", LocalDate.of(2025, 4, 5), restaurant, 40);
        meal.setId(999L);

        String output = meal.toString();
        assertTrue(output.contains("Combo Meal"));
        assertTrue(output.contains("Minestrone"));
        assertTrue(output.contains("Tiramisu"));
        assertTrue(output.contains("Spaghetti"));
        assertTrue(output.contains("dinner"));
        assertTrue(output.contains("2025-04-05"));
        assertTrue(output.contains("999"));
    }
}
