package nl.garagemeijer.salesapi.repositories;

import nl.garagemeijer.salesapi.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
