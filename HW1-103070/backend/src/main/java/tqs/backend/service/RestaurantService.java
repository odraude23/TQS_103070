package tqs.backend.service;

import org.springframework.stereotype.Service;
import tqs.backend.entities.Restaurant;
import tqs.backend.repo.RestaurantRepo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RestaurantService {
    private static final Logger log = LoggerFactory.getLogger(RestaurantService.class);
    private RestaurantRepo restaurantRepo;

    @Autowired
    public RestaurantService(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        log.info("Saving restaurant: {}", restaurant);
        return restaurantRepo.save(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {
        log.info("Getting restaurant by id: {}", id);
        return restaurantRepo.findById(id).orElse(null);
    }

    public List<Restaurant> getAllRestaurants() {
        log.info("Getting all restaurants");
        return restaurantRepo.findAll();
    }
}
