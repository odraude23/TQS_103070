package tqs.backend.service;

import org.springframework.stereotype.Service;

import tqs.backend.entities.Meal;
import tqs.backend.entities.Reservation;
import tqs.backend.repo.ReservationRepo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReservationService {
    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);
    private ReservationRepo reservationRepo;
    private MealService mealService;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo, MealService mealService) {
        this.reservationRepo = reservationRepo;
        this.mealService = mealService;
    }

    public Reservation makeReservation(long mealId, int numberOfPeople) {
        log.info("Making reservation for meal ID: {} with number of people: {}", mealId, numberOfPeople);

        if (numberOfPeople <= 0) {
            log.error("Number of people must be greater than zero");
            return null;
        }
        
        Meal meal = mealService.getMealById(mealId);

        if (meal == null) {
            log.error("Meal with id {} not found", mealId);
            return null;
        }

        List<Reservation> existingReservations = reservationRepo.findByMealId(mealId);
        int totalReserved = existingReservations.stream()
                .mapToInt(Reservation::getNumberOfPeople)
                .sum();

        if (numberOfPeople + totalReserved > meal.getReservationLimit()) {
            log.error("Number of people exceeds reservation limit for meal ID: {}", mealId);
            return null;
        }

        Reservation reservation = new Reservation(numberOfPeople, meal);
        log.info("Reservation made successfully with token: {}", reservation.getToken());
        return reservationRepo.save(reservation);
    }

    public Reservation getReservationByToken(String token) {
        log.info("Getting reservation by token: {}", token);
        return reservationRepo.findByToken(token);
    }

    public Reservation markAsUsed(String token) {
        log.info("Marking reservation as used with token: {}", token);
        Reservation reservation = getReservationByToken(token);

        if (reservation != null) {
            reservation.used();
            return reservationRepo.save(reservation);
        }

        return null;
    }

    public Reservation cancelReservation(String token) {
        log.info("Cancelling reservation with token: {}", token);
        Reservation reservation = getReservationByToken(token);

        if (reservation != null) {
            reservation.cancelled();
            return reservationRepo.save(reservation);
        }

        return null;
    }

    public Boolean isReservationValid(String token) {
        log.info("Checking if reservation is valid with token: {}", token);
        Reservation reservation = getReservationByToken(token);
        return reservation.isValid();
    }
}
