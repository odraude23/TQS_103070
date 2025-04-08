package tqs.backend.repository;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tqs.backend.entities.Meal;
import tqs.backend.entities.Reservation;
import tqs.backend.entities.Restaurant;
import tqs.backend.repo.MealRepo;
import tqs.backend.repo.ReservationRepo;
import tqs.backend.repo.RestaurantRepo;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReservationRepoTest {
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private MealRepo mealRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    private Meal createAndSaveTestMeal(String mealName) {
        Restaurant restaurant = new Restaurant("Testaurant", "Somewhere");
        restaurantRepo.save(restaurant);

        Meal meal = new Meal(
                mealName,
                "Soup",
                "Dessert",
                "Main",
                "lunch",
                LocalDate.now().plusDays(1),
                restaurant,
                50
        );
        return mealRepo.save(meal);
    }

    @Test
    @DisplayName("Find reservation by token")
    public void testFindByToken() {
        Meal meal = createAndSaveTestMeal("Meal A");

        Reservation reservation = new Reservation(2, meal);
        reservationRepo.save(reservation);

        Reservation found = reservationRepo.findByToken(reservation.getToken());

        assertThat(found).isNotNull();
        assertThat(found.getToken()).isEqualTo(reservation.getToken());
    }

    @Test
    @DisplayName("Find reservations by meal ID")
    public void testFindByMealId() {
        Meal meal = createAndSaveTestMeal("Meal B");

        Reservation r1 = new Reservation(1, meal);
        Reservation r2 = new Reservation(2, meal);
        reservationRepo.saveAll(List.of(r1, r2));

        List<Reservation> found = reservationRepo.findByMealId(meal.getId());

        assertThat(found).hasSize(2);
        assertThat(found).allMatch(r -> r.getMeal().getId().equals(meal.getId()));
    }

    @Test
    @DisplayName("Return null for unknown token")
    public void testFindByTokenNotFound() {
        Reservation found = reservationRepo.findByToken("non-existent-token");
        assertThat(found).isNull();
    }
}
