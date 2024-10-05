package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.customers.CustomerInputDto;
import nl.garagemeijer.salesapi.dtos.customers.CustomerOutputDto;
import nl.garagemeijer.salesapi.mappers.CustomerMapper;
import nl.garagemeijer.salesapi.models.Customer;
import nl.garagemeijer.salesapi.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerOutputDto> getCustomers() {
        return customerMapper.customersToCustomersOutputDtos(customerRepository.findAll());
    }

    public CustomerOutputDto getCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            return customerMapper.customerTocustomerOutputDto(customerOptional.get());
        } else {
            throw new RuntimeException("Customer with id " + id + " not found");
        }
    }

    public CustomerOutputDto saveCustomer(CustomerInputDto customer) {
        Customer customerToSave = customerMapper.customerInputDtoTocustomer(customer);
//        Account newAccount = new Account();
//        newAccount.setFirstName(customer);
//        newaccount.setaccounttype("customer");
//        in customerInputDto deze informatie uitvragen
        return customerMapper.customerTocustomerOutputDto(customerRepository.save(customerToSave));
    }

    public CustomerOutputDto updateCustomer(Long id, CustomerInputDto customer) {
        Customer getCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
        Customer customerToUpdate = customerMapper.updateCustomerFromCustomerInputDto(customer, getCustomer);
        return customerMapper.customerTocustomerOutputDto(customerRepository.save(customerToUpdate));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public void getLastSalesPerson() {
//        check in de laatste salesorder gekoppeld aan de klant wat daarvan de salesperson is geweest
    }
}
