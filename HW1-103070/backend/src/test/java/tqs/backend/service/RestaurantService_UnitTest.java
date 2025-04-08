package tqs.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tqs.backend.entities.Restaurant;
import tqs.backend.repo.RestaurantRepo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestaurantService_UnitTest {

    @Mock
    private RestaurantRepo restaurantRepo;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant r1;
    private Restaurant r2;

    @BeforeEach
    void setUp() {
        r1 = new Restaurant();
        r1.setId(1L);
        r1.setName("The Hungry Bear");

        r2 = new Restaurant();
        r2.setId(2L);
        r2.setName("Ocean Bites");

        List<Restaurant> allRestaurants = Arrays.asList(r1, r2);

        Mockito.when(restaurantRepo.save(r1)).thenReturn(r1);
        Mockito.when(restaurantRepo.findById(1L)).thenReturn(Optional.of(r1));
        Mockito.when(restaurantRepo.findById(-1L)).thenReturn(Optional.empty());
        Mockito.when(restaurantRepo.findAll()).thenReturn(allRestaurants);
    }

    @Test
    void whenSaveRestaurant_thenItShouldBeSaved() {
        Restaurant saved = restaurantService.saveRestaurant(r1);
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("The Hungry Bear");

        verifySaveCalledOnce(r1);
    }

    @Test
    void whenValidId_thenReturnRestaurant() {
        Restaurant found = restaurantService.getRestaurantById(1L);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getName()).isEqualTo("The Hungry Bear");

        verifyFindByIdCalledOnce(1L);
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Restaurant notFound = restaurantService.getRestaurantById(-1L);
        assertThat(notFound).isNull();

        verifyFindByIdCalledOnce(-1L);
    }

    @Test
    void given2Restaurants_whenGetAll_thenReturnAll() {
        List<Restaurant> all = restaurantService.getAllRestaurants();
        assertThat(all).hasSize(2).extracting(Restaurant::getName)
            .contains("The Hungry Bear", "Ocean Bites");

        verifyFindAllCalledOnce();
    }

    // Verification helpers
    private void verifySaveCalledOnce(Restaurant restaurant) {
        Mockito.verify(restaurantRepo, VerificationModeFactory.times(1)).save(restaurant);
    }

    private void verifyFindByIdCalledOnce(Long id) {
        Mockito.verify(restaurantRepo, VerificationModeFactory.times(1)).findById(id);
    }

    private void verifyFindAllCalledOnce() {
        Mockito.verify(restaurantRepo, VerificationModeFactory.times(1)).findAll();
    }
}

