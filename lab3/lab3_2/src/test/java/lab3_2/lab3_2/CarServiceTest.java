package lab3_2.lab3_2;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManegerService carManegerService;

    @Test
    void getAllCarsTest() {
        Car car1 = new Car("Honda", "Civic");
        Car car2 = new Car("Toyota", "Corolla");

        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        assertThat(carManegerService.getAllCars()).contains(car1, car2).hasSize(2);
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void getCarDetailsTest() {
        Car car = new Car("Honda", "Civic");
        car.setId(1L);

        when(carRepository.findByCarId(1L)).thenReturn(car);
        Optional<Car> found = carManegerService.getCarDetails(1L);

        assertThat(found.get()).isEqualTo(car);
        assertThat(found.get().hashCode()).isEqualTo(car.hashCode());
        assertThat(found.get().equals(car)).isTrue();
    }

    @Test
    void saveTest() {
        Car car = new Car("Honda", "Civic");

        when(carRepository.save(Mockito.any())).thenReturn(car);

        assertThat(carManegerService.save(car)).isEqualTo(car);
    }
}
