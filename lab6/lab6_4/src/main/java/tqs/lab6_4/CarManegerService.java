package tqs.lab6_4;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarManegerService {
    
    final CarRepository carRepository;

    public CarManegerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long id) {
        return Optional.of(carRepository.findByCarId(id));
    }
}
