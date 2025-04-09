package tqs.backend.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime reservationDateTime;

    @Column(nullable = false)
    private int numberOfPeople;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private boolean isCancelled;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    public Reservation() {
    }

    public Reservation(int numberOfPeople, Meal meal) {
        this.token = UUID.randomUUID().toString();
        this.reservationDateTime = LocalDateTime.now();
        this.numberOfPeople = numberOfPeople;
        this.isUsed = false;
        this.isCancelled = false;
        this.meal = meal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void used() {
        this.isUsed = true;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancelled() {
        this.isCancelled = true;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public boolean isValid() {
        return !isUsed && !isCancelled;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", reservationDateTime=" + reservationDateTime +
                ", numberOfPeople=" + numberOfPeople +
                ", isUsed=" + isUsed +
                ", isCancelled=" + isCancelled +
                ", meal=" + meal +
                '}';
    }
}
