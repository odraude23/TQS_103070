package tqs.backend.service;

import org.springframework.stereotype.Service;
import tqs.backend.repo.MealRepo;
import tqs.backend.entities.Meal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealService.class);
    private MealRepo mealRepo;

    @Autowired
    public MealService(MealRepo mealRepo) {
        this.mealRepo = mealRepo;
    }

    public Meal saveMeal(Meal meal) {
        log.info("Saving meal: {}", meal);
        return mealRepo.save(meal);
    }

    public Meal getMealById(Long id) {
        log.info("Getting meal by id: {}", id);
        return mealRepo.findById(id).orElse(null);
    }

    public List<Meal> getAllMeals() {
        log.info("Getting all meals");
        return mealRepo.findAll();
    }

    public List<Meal> getMealsByRestaurantId(Long restaurantId) {
        log.info("Getting meals by restaurant id: {}", restaurantId);
        return mealRepo.findByRestaurantId(restaurantId);
    }
}
