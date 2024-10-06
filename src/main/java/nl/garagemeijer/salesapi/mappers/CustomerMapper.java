package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.customers.CustomerInputDto;
import nl.garagemeijer.salesapi.dtos.customers.CustomerOutputDto;
import nl.garagemeijer.salesapi.models.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {

    public CustomerOutputDto customerTocustomerOutputDto(Customer customer) {
        var dto = new CustomerOutputDto();
//        Account account = customer.getAccount;

        dto.setId(customer.getId());
        dto.setAccountType(customer.getPrefferedContactMethod());
        dto.setNameLastSalesPerson(customer.getNameLastSalesPerson());

//        if (account != null) {
//            dto.setAccountType(account.getAccounttype);
//            ..
//            ..
//            ..
//        }

        return dto;
    }

    public Customer updateCustomerFromCustomerInputDto(CustomerInputDto customerInputDto, Customer customer) {
//        Account account = customer.getAccount;
        customer.setPrefferedContactMethod(customerInputDto.getPrefferedContactMethod());

//        if (account != null) {
//            dto.setAccountType(account.getAccounttype);
//            ..
//            ..
//            ..
//        }

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
