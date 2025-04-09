package tqs.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.backend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tqs.backend.entities.Reservation;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations/{token}")
    public Reservation getReservation(@PathVariable(value = "token") String token) {
        return reservationService.getReservationByToken(token);
    }

    @PostMapping("/reservations/{mealId}")
    public Reservation makeReservation(@PathVariable(value = "mealId") long mealId, @RequestBody int numberOfPeople) {
        return reservationService.makeReservation(mealId, numberOfPeople);
    }

    @PostMapping("/reservations/used/{token}")
    public Reservation markAsUsed(@PathVariable(value = "token") String token) {
        return reservationService.markAsUsed(token);
    }

    @PostMapping("/reservations/cancel/{token}")
    public Reservation cancelReservation(@PathVariable(value = "token") String token) {
        return reservationService.cancelReservation(token);
    }
}
