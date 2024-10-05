package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.models.Account;
import nl.garagemeijer.salesapi.models.Customer;
import nl.garagemeijer.salesapi.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            throw new RuntimeException("Customer with id " + id + " not found");
        }
    }

    public Customer saveCustomer(Customer customer) {
//        Account newAccount = new Account();
//        newAccount.setFirstName(customer);
//        in customerInputDto deze informatie uitvragen
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer customerToUpdate = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
        customerToUpdate.setPrefferedContactMethod(customer.getPrefferedContactMethod());
        return customerRepository.save(customerToUpdate);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public void getLastSalesPerson() {

    }
}
