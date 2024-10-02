package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
