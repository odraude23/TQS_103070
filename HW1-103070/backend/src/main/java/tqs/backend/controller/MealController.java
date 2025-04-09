package tqs.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.backend.entities.Meal;
import tqs.backend.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class MealController {
    
    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/meals/{id}")
    public Meal getMealById(@PathVariable(value = "id") Long id) {
        return mealService.getMealById(id);
    }

    @PostMapping("/meals")
    public Meal saveMeal(@RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }

    @GetMapping("/meals/restaurant/{restaurantId}")
    public List<Meal> getMealsByRestaurantId(@PathVariable(value = "restaurantId") Long restaurantId) {
        return mealService.getMealsByRestaurantId(restaurantId);
    }
}
