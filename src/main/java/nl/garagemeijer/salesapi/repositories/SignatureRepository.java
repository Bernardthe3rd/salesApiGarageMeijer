package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRepository extends JpaRepository<Signature, Long> {
}
