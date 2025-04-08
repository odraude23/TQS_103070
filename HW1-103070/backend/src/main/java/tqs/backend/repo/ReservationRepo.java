package tqs.backend.repo;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.backend.entities.Reservation;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    
    Reservation findByToken(String token);
    List<Reservation> findByMealId(Long mealId);
    
}
