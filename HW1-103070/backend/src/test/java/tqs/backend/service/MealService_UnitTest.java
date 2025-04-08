package tqs.backend.service;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tqs.backend.entities.Meal;
import tqs.backend.entities.Restaurant;
import tqs.backend.repo.MealRepo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MealService_UnitTest {
    @Mock
    private MealRepo mealRepo;

    @InjectMocks
    private MealService mealService;

    private Meal testMeal;
    private Restaurant mockRestaurant;

    @BeforeEach
    void setUp() {
        // Mock restaurant setup
        mockRestaurant = Mockito.mock(Restaurant.class);
        Mockito.when(mockRestaurant.getName()).thenReturn("Mocked Restaurant");

        // Full meal setup
        testMeal = new Meal(
                "Special Menu",
                "Tomato Soup",
                "Chocolate Cake",
                "Grilled Chicken",
                "Lunch",
                LocalDate.now(),
                mockRestaurant,
                50
        );
        testMeal.setId(1L);

        // Repo behaviors
        Mockito.when(mealRepo.save(testMeal)).thenReturn(testMeal);
        Mockito.when(mealRepo.findById(1L)).thenReturn(Optional.of(testMeal));
        Mockito.when(mealRepo.findById(-1L)).thenReturn(Optional.empty());
    }

    @Test
    void whenSaveMeal_thenReturnSavedMeal() {
        Meal saved = mealService.saveMeal(testMeal);
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Special Menu");
        assertThat(saved.getMealType()).isEqualTo("Lunch");
        assertThat(saved.getReservationLimit()).isEqualTo(50);
        assertThat(saved.getRestaurant().getName()).isEqualTo("Mocked Restaurant");

        verifySaveCalledOnce(testMeal);
    }

    @Test
    void whenValidId_thenReturnMeal() {
        Meal found = mealService.getMealById(1L);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getMainCourse()).isEqualTo("Grilled Chicken");

        verifyFindByIdCalledOnce(1L);
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Meal notFound = mealService.getMealById(-1L);
        assertThat(notFound).isNull();

        verifyFindByIdCalledOnce(-1L);
    }

    @Test
    void whenGetAllMeals_thenReturnListOfMeals() {
        // Assuming mealRepo.findAll() returns a list of meals
        Mockito.when(mealRepo.findAll()).thenReturn(List.of(testMeal));

        List<Meal> meals = mealService.getAllMeals();
        assertThat(meals).isNotEmpty();
        assertThat(meals.get(0).getName()).isEqualTo("Special Menu");
    }

    @Test
    void whenGetMealsByRestaurantId_thenReturnListOfMeals() {
        // Assuming mealRepo.findByRestaurantId() returns a list of meals
        Mockito.when(mealRepo.findByRestaurantId(1L)).thenReturn(List.of(testMeal));

        List<Meal> meals = mealService.getMealsByRestaurantId(1L);
        assertThat(meals).isNotEmpty();
        assertThat(meals.get(0).getName()).isEqualTo("Special Menu");
    }

    // Verification helpers
    private void verifySaveCalledOnce(Meal meal) {
        Mockito.verify(mealRepo, VerificationModeFactory.times(1)).save(meal);
    }

    private void verifyFindByIdCalledOnce(Long id) {
        Mockito.verify(mealRepo, VerificationModeFactory.times(1)).findById(id);
    }
}
