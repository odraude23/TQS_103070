package tqs.lab6_4;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository CarRepository;

    @BeforeEach
    void setUp() {
        CarRepository.deleteAll();
    }

    @Test
    void finfById_thenReturnCar() {
        Car car = new Car("Honda", "Civic");
        entityManager.persistAndFlush(car);

        Car found = CarRepository.findById(car.getId()).get();

        assertThat(found.getMaker())
                .isEqualTo(car.getMaker());
    }
    
    @Test
    void findAll_thenReturnAllCars() {
        Car car1 = new Car("Honda", "Civic");
        entityManager.persistAndFlush(car1);

        Car car2 = new Car("Toyota", "Corolla");
        entityManager.persistAndFlush(car2);

        Car car3 = new Car("Ford", "Focus");
        entityManager.persistAndFlush(car3);

        assertThat(CarRepository.findAll())
                .hasSize(3)
                .contains(car1, car2, car3);
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Car fromDb = CarRepository.findByCarId(-111L);
        assertThat(fromDb).isNull();
    }
}
