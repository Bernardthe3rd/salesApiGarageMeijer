package nl.garagemeijer.salesapi;

import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.helpers.PriceCalculator;
import nl.garagemeijer.salesapi.mappers.SaleMapper;
import nl.garagemeijer.salesapi.mappers.SignatureMapper;
import nl.garagemeijer.salesapi.models.Sale;
import nl.garagemeijer.salesapi.repositories.*;
import nl.garagemeijer.salesapi.services.SaleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceUnitTest {

    @Mock
    private SaleRepository saleRepository;
    @Mock
    private SaleMapper saleMapper;
    @Mock
    private PriceCalculator priceCalculator;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private SignatureMapper signatureMapper;

    @InjectMocks
    private SaleService saleService;

    @Test
    @DisplayName("Get all sale orders")
    public void getAllSaleOrders() {
        Sale sale1 = new Sale();
        sale1.setId(1L);
        sale1.setSalePriceIncl(new BigDecimal("10.000"));
        Sale sale2 = new Sale();
        sale2.setId(2L);
        sale2.setSalePriceIncl(new BigDecimal("20.000"));

        SaleOutputDto dto1 = new SaleOutputDto();
        dto1.setId(1L);
        dto1.setSalePriceIncl(new BigDecimal("10.000"));
        SaleOutputDto dto2 = new SaleOutputDto();
        dto2.setId(2L);
        dto2.setSalePriceIncl(new BigDecimal("20.000"));

        List<Sale> saleList = new ArrayList<>();
        saleList.add(sale1);
        saleList.add(sale2);
        List<SaleOutputDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);

        when(saleRepository.findAll()).thenReturn(saleList);
        when(saleMapper.salesToSalesOutputDtos(saleList)).thenReturn(dtoList);

        List<SaleOutputDto> result = saleService.getSales();

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    
}
