package tqs.lab6_4;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    public Car findByCarId(long carId);

    public List<Car> findAll();
}
