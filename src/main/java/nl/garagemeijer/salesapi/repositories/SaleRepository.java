package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT MAX(s.orderNumber) from Sale s")
    Integer findLastOrderNumber();
}
