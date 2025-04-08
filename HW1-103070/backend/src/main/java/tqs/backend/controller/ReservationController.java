package tqs.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.backend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tqs.backend.entities.Reservation;

@RestController
@RequestMapping("/api/v1")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public Reservation getReservation(@RequestBody String token) {
        return reservationService.getReservationByToken(token);
    }

    @PostMapping("/reservations/{mealId}")
    public Reservation makeReservation(@PathVariable(value = "mealId") long mealId, @RequestBody int numberOfPeople) {
        return reservationService.makeReservation(mealId, numberOfPeople);
    }

    @PostMapping("/reservations/used")
    public Reservation markAsUsed(@RequestBody String token) {
        return reservationService.markAsUsed(token);
    }

    @PostMapping("/reservations/cancel")
    public Reservation cancelReservation(@RequestBody String token) {
        return reservationService.cancelReservation(token);
    }
}
