package tqs.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tqs.backend.entities.Meal;
import tqs.backend.entities.Reservation;
import tqs.backend.repo.ReservationRepo;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReservationService_UnitTest {

    @Mock
    private ReservationRepo reservationRepo;

    @Mock
    private MealService mealService;

    @InjectMocks
    private ReservationService reservationService;

    private Meal meal;
    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    void setUp() {
        meal = new Meal("Lunch A", "Tomato soup", "Apple pie", "Grilled chicken",
                "lunch", LocalDate.now(), null, 10);
        meal.setId(1L);

        reservation1 = new Reservation(3, meal);
        reservation2 = new Reservation(2, meal);

        when(mealService.getMealById(1L)).thenReturn(meal);
        when(mealService.getMealById(-1L)).thenReturn(null);
        when(reservationRepo.findByMealId(1L)).thenReturn(Arrays.asList(reservation1, reservation2));
    }

    @Test
    void whenValidReservation_thenItShouldBeSaved() {
        Reservation newRes = new Reservation(4, meal);
        when(reservationRepo.save(any(Reservation.class))).thenReturn(newRes);

        Reservation saved = reservationService.makeReservation(1L, 4);
        assertThat(saved).isNotNull();
        assertThat(saved.getNumberOfPeople()).isEqualTo(4);

        verify(mealService, times(1)).getMealById(1L);
        verify(reservationRepo, times(1)).findByMealId(1L);
        verify(reservationRepo, times(1)).save(any(Reservation.class));
    }

    @Test
    void whenMealNotFound_thenReservationFails() {
        Reservation result = reservationService.makeReservation(-1L, 2);
        assertThat(result).isNull();

        verify(mealService, times(1)).getMealById(-1L);
        verify(reservationRepo, never()).save(any());
    }

    @Test
    void whenReservationExceedsLimit_thenFails() {
        Reservation result = reservationService.makeReservation(1L, 6);
        assertThat(result).isNull();

        verify(mealService, times(1)).getMealById(1L);
        verify(reservationRepo, times(1)).findByMealId(1L);
        verify(reservationRepo, never()).save(any());
    }

    @Test
    void whenValidToken_thenGetReservation() {
        String token = UUID.randomUUID().toString();
        Reservation r = new Reservation(2, meal);
        when(reservationRepo.findByToken(token)).thenReturn(r);

        Reservation found = reservationService.getReservationByToken(token);
        assertThat(found).isNotNull();

        verify(reservationRepo, times(1)).findByToken(token);
    }

    @Test
    void whenMarkAsUsed_thenReservationUpdated() {
        String token = UUID.randomUUID().toString();
        Reservation r = new Reservation(2, meal);
        when(reservationRepo.findByToken(token)).thenReturn(r);
        when(reservationRepo.save(r)).thenReturn(r);

        Reservation updated = reservationService.markAsUsed(token);
        assertThat(updated.isUsed()).isTrue();

        verify(reservationRepo).findByToken(token);
        verify(reservationRepo).save(r);
    }

    @Test
    void whenCancelReservation_thenStatusUpdated() {
        String token = UUID.randomUUID().toString();
        Reservation r = new Reservation(2, meal);
        when(reservationRepo.findByToken(token)).thenReturn(r);
        when(reservationRepo.save(r)).thenReturn(r);

        Reservation cancelled = reservationService.cancelReservation(token);
        assertThat(cancelled.isCancelled()).isTrue();

        verify(reservationRepo).findByToken(token);
        verify(reservationRepo).save(r);
    }

    @Test
    void whenCheckValidity_thenReturnTrueOrFalse() {
        String token = UUID.randomUUID().toString();
        Reservation r = new Reservation(2, meal);
        when(reservationRepo.findByToken(token)).thenReturn(r);

        Boolean valid = reservationService.isReservationValid(token);
        assertThat(valid).isTrue();

        r.cancelled(); 
        Boolean afterCancel = reservationService.isReservationValid(token);
        assertThat(afterCancel).isFalse();
    }
}

