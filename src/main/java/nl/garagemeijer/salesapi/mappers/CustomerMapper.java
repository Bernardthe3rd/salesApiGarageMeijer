package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.customers.CustomerInputDto;
import nl.garagemeijer.salesapi.dtos.customers.CustomerOutputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.models.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {

    public CustomerOutputDto customerTocustomerOutputDto(Customer customer) {
        var dto = new CustomerOutputDto();

        dto.setId(customer.getId());
        dto.setCreationDate(customer.getCreationDate());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setStreet(customer.getStreet());
        dto.setPostalCode(customer.getPostalCode());
        dto.setCity(customer.getCity());
        dto.setCountry(customer.getCountry());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setPrefferedContactMethod(customer.getPrefferedContactMethod());
        dto.setNameLastSalesPerson(customer.getNameLastSalesPerson());
        if (customer.getPurchaseHistory() != null) {
            dto.setPurchaseHistory(SaleMapper.salesToSalesOutputDtos(customer.getPurchaseHistory()));
        }

        return dto;
    }

    public Customer updateCustomerFromCustomerInputDto(CustomerInputDto customerInputDto, Customer customer) {
        customer.setFirstName(customerInputDto.getFirstName());
        customer.setLastName(customerInputDto.getLastName());
        customer.setStreet(customerInputDto.getStreet());
        customer.setPostalCode(customerInputDto.getPostalCode());
        customer.setCity(customerInputDto.getCity());
        customer.setCountry(customerInputDto.getCountry());
        customer.setPhoneNumber(customerInputDto.getPhoneNumber());
        customer.setEmail(customerInputDto.getEmail());
        customer.setDateOfBirth(customerInputDto.getDateOfBirth());
        customer.setPrefferedContactMethod(customerInputDto.getPrefferedContactMethod());

        return customer;
    }

    public Customer customerInputDtoTocustomer(CustomerInputDto customerInputDto) {
        var customer = new Customer();
        return updateCustomerFromCustomerInputDto(customerInputDto, customer);
    }

    public List<CustomerOutputDto> customersToCustomersOutputDtos(List<Customer> customers) {
        List<CustomerOutputDto> customerOutputDtos = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerOutputDto customerDto = customerTocustomerOutputDto(customer);
            customerOutputDtos.add(customerDto);
        }
        return customerOutputDtos;
    }

}
