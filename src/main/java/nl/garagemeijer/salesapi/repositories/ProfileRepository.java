package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
