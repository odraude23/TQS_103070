package tqs.backend;

import tqs.backend.entities.Meal;
import tqs.backend.entities.Restaurant;
import tqs.backend.repo.MealRepo;
import tqs.backend.repo.RestaurantRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
public class GenerateEntries implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(GenerateEntries.class);

    private final RestaurantRepo restaurantRepo;
    private final MealRepo mealRepo;

    @Autowired
    public GenerateEntries(RestaurantRepo restaurantRepo, MealRepo mealRepo) {
        this.restaurantRepo = restaurantRepo;
        this.mealRepo = mealRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Generating initial meal and restaurant data...");

        // Create restaurants
        Restaurant r1 = new Restaurant("Joe's Tavern", "Aveiro");
        Restaurant r2 = new Restaurant("Good Food", "Aveiro");
        Restaurant r3 = new Restaurant("Neighborhood Restaurant", "Aveiro");

        restaurantRepo.save(r1);
        restaurantRepo.save(r2);
        restaurantRepo.save(r3);

        String[][] sampleMeals = {
            {"Vegetable Soup", "Grilled Chicken", "Chocolate Mousse"},
            {"Green Soup", "Codfish à Brás", "Pudding"},
            {"Fish Soup", "Duck Rice", "Fruit"},
            {"Pumpkin Cream", "Lasagna", "Jelly"},
            {"Tomato Soup", "Beef Stew", "Ice Cream"}
        };

        Restaurant[] restaurants = {r1, r2, r3};
        String[] mealTypes = {"lunch", "dinner"};

        for (Restaurant restaurant : restaurants) {
            for (int dayOffset = 0; dayOffset < 5; dayOffset++) {
                LocalDate date = LocalDate.now().plusDays(dayOffset);
                for (int i = 0; i < mealTypes.length; i++) {
                    String type = mealTypes[i];
                    int mealIndex = (dayOffset + i) % sampleMeals.length;
                    String[] mealData = sampleMeals[mealIndex];

                    Meal meal = new Meal(
                        String.format("Menu %s - %s", type.toUpperCase(), date),
                        mealData[0],
                        mealData[2],
                        mealData[1],
                        type,
                        date,
                        restaurant,
                        20 + (int)(Math.random() * 11)  // Random capacity between 20 and 30
                    );
                    mealRepo.save(meal);
                }
            }
        }

        logger.info("Sample data generated successfully.");
    }
}
