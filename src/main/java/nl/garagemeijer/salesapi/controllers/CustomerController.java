package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.customers.CustomerInputDto;
import nl.garagemeijer.salesapi.dtos.customers.CustomerOutputDto;
import nl.garagemeijer.salesapi.models.Customer;
import nl.garagemeijer.salesapi.repositories.CustomerRepository;
import nl.garagemeijer.salesapi.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<List<CustomerOutputDto>> getAllCustomers() {
        List<CustomerOutputDto> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOutputDto> getCustomerById(@PathVariable Long id) {
        CustomerOutputDto selectedCustomer = customerService.getCustomer(id);
        return ResponseEntity.ok(selectedCustomer);
    }

    @PostMapping
    public ResponseEntity<CustomerOutputDto> createCustomer(@Valid @RequestBody CustomerInputDto customer) {
        CustomerOutputDto createdCustomer = customerService.saveCustomer(customer);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOutputDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerInputDto customer) {
        CustomerOutputDto updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
