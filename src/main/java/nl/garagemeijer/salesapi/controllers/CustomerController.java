package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.customers.CustomerInputDto;
import nl.garagemeijer.salesapi.dtos.customers.CustomerOutputDto;
import nl.garagemeijer.salesapi.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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
    public ResponseEntity<CustomerOutputDto> createCustomer(@Valid @RequestBody CustomerInputDto customerInput) {
        CustomerOutputDto createdCustomer = customerService.saveCustomer(customerInput);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOutputDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerInputDto customerInput) {
        CustomerOutputDto updatedCustomer = customerService.updateCustomer(id, customerInput);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
