package tqs.backend.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tqs.backend.entities.Meal;
import java.util.List;

@Repository
public interface MealRepo extends JpaRepository<Meal, Long> {
    
    List<Meal> findByRestaurantId(Long restaurantId);
}
