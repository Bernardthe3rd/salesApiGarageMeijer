package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
