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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;

        Reservation that = (Reservation) o;

        if (numberOfPeople != that.numberOfPeople) return false;
        if (isUsed != that.isUsed) return false;
        if (isCancelled != that.isCancelled) return false;
        if (!id.equals(that.id)) return false;
        if (!token.equals(that.token)) return false;
        if (!reservationDateTime.equals(that.reservationDateTime)) return false;
        return meal.equals(that.meal);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + token.hashCode();
        result = 31 * result + reservationDateTime.hashCode();
        result = 31 * result + numberOfPeople;
        result = 31 * result + (isUsed ? 1 : 0);
        result = 31 * result + (isCancelled ? 1 : 0);
        result = 31 * result + meal.hashCode();
        return result;
    }
}
