package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.BusinessVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessVehicleRepository extends JpaRepository<BusinessVehicle, Long> {
}
