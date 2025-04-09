package tqs.backend.entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String soup;

    @Column(nullable = false)
    private String dessert;

    @Column(nullable = false)
    private String mainCourse;

    @Column(nullable = false)
    private String mealType;      // lunch or dinner

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int reservationLimit;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(String name, String soup, String dessert, String mainCourse, String mealType, LocalDate date, Restaurant restaurant, int reservationLimit) {
        this.name = name;
        this.soup = soup;
        this.dessert = dessert;
        this.mainCourse = mainCourse;
        this.mealType = mealType;
        this.date = date;
        this.restaurant = restaurant;
        this.reservationLimit = reservationLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoup() {
        return soup;
    }

    public void setSoup(String soup) {
        this.soup = soup;
    }

    public String getDessert() {
        return dessert;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    public String getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public int getReservationLimit() {
        return reservationLimit;
    }

    public void setReservationLimit(int reservationLimit) {
        this.reservationLimit = reservationLimit;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", soup='" + soup + '\'' +
                ", dessert='" + dessert + '\'' +
                ", mainCourse='" + mainCourse + '\'' +
                ", mealType='" + mealType + '\'' +
                ", date=" + date +
                ", reservationLimit=" + reservationLimit +
                ", restaurant=" + restaurant +
                '}';
    }
}
