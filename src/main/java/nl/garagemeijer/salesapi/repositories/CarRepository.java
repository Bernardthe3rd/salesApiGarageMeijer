package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
