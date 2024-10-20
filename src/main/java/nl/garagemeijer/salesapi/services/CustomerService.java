package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.customers.CustomerInputDto;
import nl.garagemeijer.salesapi.dtos.customers.CustomerOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.CustomerMapper;
import nl.garagemeijer.salesapi.models.Customer;
import nl.garagemeijer.salesapi.models.Profile;
import nl.garagemeijer.salesapi.models.Sale;
import nl.garagemeijer.salesapi.repositories.CustomerRepository;
import nl.garagemeijer.salesapi.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ProfileRepository profileRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, ProfileRepository profileRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.profileRepository = profileRepository;
    }

    public List<CustomerOutputDto> getCustomers() {
        return customerMapper.customersToCustomersOutputDtos(customerRepository.findAll());
    }

    public CustomerOutputDto getCustomer(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            return customerMapper.customerTocustomerOutputDto(optionalCustomer.get());
        } else {
            throw new RecordNotFoundException("Customer with id " + id + " not found");
        }
    }

    public CustomerOutputDto saveCustomer(CustomerInputDto customer) {
        Customer customerToSave = customerMapper.customerInputDtoTocustomer(customer);

        return customerMapper.customerTocustomerOutputDto(customerRepository.save(customerToSave));
    }

    public CustomerOutputDto updateCustomer(Long id, CustomerInputDto customer) {
        Customer getCustomer = customerRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Customer with id " + id + " not found"));
        Customer customerToUpdate = customerMapper.updateCustomerFromCustomerInputDto(customer, getCustomer);
        Long getSellerId = customerToUpdate.getPurchaseHistory().getLast().getSellerId();
        Optional<Profile> optionalSalePerson = profileRepository.findById(getSellerId);
        if (optionalSalePerson.isPresent()) {
            Profile salePerson = optionalSalePerson.get();
            customerToUpdate.setNameLastSalesPerson(salePerson.getFirstName());
        }
        return customerMapper.customerTocustomerOutputDto(customerRepository.save(customerToUpdate));
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException("Customer with id " + id + " not found");
        }
        List<Sale> purchaseHistory = optionalCustomer.get().getPurchaseHistory();
        for (Sale sale : purchaseHistory) {
            sale.setCustomer(null);
        }
        customerRepository.deleteById(id);
    }


}
