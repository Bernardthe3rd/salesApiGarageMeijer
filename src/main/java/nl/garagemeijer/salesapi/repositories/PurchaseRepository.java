package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT MAX(s.orderNumber) from Purchase s")
    Integer findLastOrderNumber();
}
