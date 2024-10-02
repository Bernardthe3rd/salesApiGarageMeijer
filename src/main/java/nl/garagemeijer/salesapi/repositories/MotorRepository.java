package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Motor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorRepository extends JpaRepository<Motor, Long> {
}
