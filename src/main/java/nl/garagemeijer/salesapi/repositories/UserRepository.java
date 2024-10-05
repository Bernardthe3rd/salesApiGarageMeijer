package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
