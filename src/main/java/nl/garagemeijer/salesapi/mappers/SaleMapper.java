package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.customers.CustomerOutputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.models.Sale;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaleMapper {

    public static SaleOutputDto saleTosaleOutputDto(Sale sale) {
        var dto = new SaleOutputDto();

        dto.setId(sale.getId());
        dto.setSaleDate(sale.getSaleDate());
        dto.setSalePriceEx(sale.getSalePriceEx());
        dto.setBpmPrice(sale.getBpmPrice());
        dto.setTaxPrice(sale.getTaxPrice());
        dto.setSalePriceIncl(sale.getSalePriceIncl());
        dto.setStatus(sale.getStatus());
        dto.setOrderNumber(sale.getOrderNumber());
        dto.setComment(sale.getComment());
        dto.setDiscount(sale.getDiscount());
        dto.setTypeOrder(sale.getTypeOrder());
        dto.setWarranty(sale.getWarranty());
        dto.setPaymentMethod(sale.getPaymentMethod());
        dto.setBusinessOrPrivate(sale.getBusinessOrPrivate());
        dto.setAddition(sale.getAddition());
        if (sale.getVehicle() != null) {
            dto.setVehicle(VehicleMapper.vehicleToVehicleOutputDto(sale.getVehicle()));
        }
        if (sale.getCustomer() != null) {
            var simpleCustomerDto = new CustomerOutputDto();
            simpleCustomerDto.setId(sale.getCustomer().getId());
            simpleCustomerDto.setFirstName(sale.getCustomer().getFirstName());
            simpleCustomerDto.setLastName(sale.getCustomer().getLastName());
            simpleCustomerDto.setDateOfBirth(sale.getCustomer().getDateOfBirth());
            simpleCustomerDto.setStreet(sale.getCustomer().getStreet());
            simpleCustomerDto.setPostalCode(sale.getCustomer().getPostalCode());
            simpleCustomerDto.setCity(sale.getCustomer().getCity());
            simpleCustomerDto.setCountry(sale.getCustomer().getCountry());
            simpleCustomerDto.setEmail(sale.getCustomer().getEmail());
            simpleCustomerDto.setPhoneNumber(sale.getCustomer().getPhoneNumber());
            simpleCustomerDto.setPrefferedContactMethod(sale.getCustomer().getPrefferedContactMethod());
            simpleCustomerDto.setNameLastSalesPerson(sale.getCustomer().getNameLastSalesPerson());
            dto.setCustomer(simpleCustomerDto);
        }

        return dto;
    }

    public Sale updateSaleFromSaleInputDto(SaleInputDto saleInputDto, Sale sale) {
        sale.setSalePriceIncl(saleInputDto.getSalePriceIncl());
        sale.setDiscount(saleInputDto.getDiscount());
        sale.setTypeOrder(saleInputDto.getTypeOrder());
        sale.setWarranty(saleInputDto.getWarranty());
        sale.setPaymentMethod(saleInputDto.getPaymentMethod());
        sale.setBusinessOrPrivate(saleInputDto.getBusinessOrPrivate());
        sale.setComment(saleInputDto.getComment());
        sale.setAddition(saleInputDto.getAddition());

        return sale;
    }

    public Sale saleInputDtoToSale(SaleInputDto saleInputDto) {
        var sale = new Sale();
        return updateSaleFromSaleInputDto(saleInputDto, sale);
    }

    public static List<SaleOutputDto> salesToSalesOutputDtos(List<Sale> sales) {
        List<SaleOutputDto> saleOutputDtos = new ArrayList<>();
        for (Sale sale : sales) {
            SaleOutputDto saleDto = saleTosaleOutputDto(sale);
            saleOutputDtos.add(saleDto);
        }
        return saleOutputDtos;
    }
}
