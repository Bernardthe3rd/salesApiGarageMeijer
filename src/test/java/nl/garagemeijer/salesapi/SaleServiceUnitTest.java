package nl.garagemeijer.salesapi;

import nl.garagemeijer.salesapi.dtos.ids.IdInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.dtos.signature.SignatureOutputDto;
import nl.garagemeijer.salesapi.enums.Addition;
import nl.garagemeijer.salesapi.enums.BusinessOrPrivate;
import nl.garagemeijer.salesapi.enums.Role;
import nl.garagemeijer.salesapi.enums.Status;
import nl.garagemeijer.salesapi.exceptions.BadRequestException;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.exceptions.SignatureException;
import nl.garagemeijer.salesapi.exceptions.UnauthorizedException;
import nl.garagemeijer.salesapi.helpers.CheckAndChangeStatus;
import nl.garagemeijer.salesapi.helpers.PriceCalculator;
import nl.garagemeijer.salesapi.mappers.SaleMapper;
import nl.garagemeijer.salesapi.mappers.SignatureMapper;
import nl.garagemeijer.salesapi.models.*;
import nl.garagemeijer.salesapi.repositories.*;
import nl.garagemeijer.salesapi.services.SaleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private CheckAndChangeStatus checkAndChangeStatus;
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

    @Test
    @DisplayName("Get sale by id - found")
    void getSaleByIdFound() {
        Sale sale1 = new Sale();
        sale1.setId(1L);
        sale1.setSalePriceIncl(new BigDecimal("15.000"));
        sale1.setBusinessOrPrivate(BusinessOrPrivate.BUSINESS);
        sale1.setQuantity(1);
        sale1.setDiscount(500.00);
        sale1.setPaymentMethod("Bank");
        sale1.setTypeOrder("Order");
        sale1.setComment("Amazing");
        sale1.setAddition(Addition.DPS);
        sale1.setWarranty("2 years");

        SaleOutputDto dto1 = new SaleOutputDto();
        dto1.setId(1L);
        dto1.setSalePriceIncl(new BigDecimal("15.000"));
        dto1.setBusinessOrPrivate(BusinessOrPrivate.BUSINESS);
        dto1.setQuantity(1);
        dto1.setDiscount(500.00);
        dto1.setPaymentMethod("Bank");
        dto1.setTypeOrder("Order");
        dto1.setComment("Amazing");
        dto1.setAddition(Addition.DPS);
        dto1.setWarranty("2 years");

        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale1));
        when(saleMapper.saleTosaleOutputDto(sale1)).thenReturn(dto1);

        SaleOutputDto result = saleService.getSale(1L);

        assertNotNull(result);
        assertEquals(dto1, result);
    }

    @Test
    @DisplayName("Get sale by id - not found")
    void getSaleByIdNotFound() {
        Long saleId = 1L;
        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.getSale(saleId));

        assertEquals("Sale with id: " + saleId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Create new sale")
    void createSale() {
        SaleInputDto saleInput = new SaleInputDto();
        saleInput.setSalePriceIncl(new BigDecimal("20.000"));
        saleInput.setBusinessOrPrivate(BusinessOrPrivate.PRIVATE);
        saleInput.setQuantity(1);
        saleInput.setDiscount(500.00);
        saleInput.setPaymentMethod("Bank");
        saleInput.setTypeOrder("Order");
        saleInput.setComment("Amazing");
        saleInput.setAddition(Addition.DPS);
        saleInput.setWarranty("2 years");

        Sale sale = new Sale();
        sale.setId(1L);
        sale.setOrderNumber(0);
        sale.setSalePriceIncl(new BigDecimal("20.000"));
        sale.setBusinessOrPrivate(BusinessOrPrivate.PRIVATE);
        sale.setQuantity(1);
        sale.setDiscount(500.00);
        sale.setPaymentMethod("Bank");
        sale.setTypeOrder("Order");
        sale.setComment("Amazing");
        sale.setAddition(Addition.DPS);
        sale.setWarranty("2 years");

        List<BigDecimal> prices = Arrays.asList(new BigDecimal("3471.07"), new BigDecimal("2000"), new BigDecimal("14528.93"));
        sale.setTaxPrice(prices.get(0));
        sale.setBpmPrice(prices.get(1));
        sale.setSalePriceEx(prices.get(2));

        SaleOutputDto output = new SaleOutputDto();
        output.setId(1L);
        output.setOrderNumber(sale.getOrderNumber());
        output.setSalePriceIncl(sale.getSalePriceIncl());
        output.setTaxPrice(sale.getTaxPrice());
        output.setBpmPrice(sale.getBpmPrice());
        output.setSalePriceEx(sale.getSalePriceEx());
        output.setBusinessOrPrivate(sale.getBusinessOrPrivate());
        output.setQuantity(sale.getQuantity());
        output.setDiscount(sale.getDiscount());
        output.setPaymentMethod(sale.getPaymentMethod());
        output.setTypeOrder(sale.getTypeOrder());
        output.setComment(sale.getComment());
        output.setAddition(sale.getAddition());
        output.setWarranty(sale.getWarranty());

        when(saleMapper.saleInputDtoToSale(saleInput)).thenReturn(sale);
        when(saleRepository.findLastOrderNumber()).thenReturn(0);
        when(priceCalculator.calculatePrices(sale)).thenReturn(prices);
        when(saleRepository.save(sale)).thenReturn(sale);
        when(saleMapper.saleTosaleOutputDto(sale)).thenReturn(output);

        SaleOutputDto result = saleService.saveSale(saleInput);

        assertNotNull(result);
        assertEquals(output, result);
        assertEquals(new BigDecimal("2000"), result.getBpmPrice());
        assertEquals(0, result.getOrderNumber());
    }

    @Test
    @DisplayName("Update existing sale - found")
    void updateSaleFound() {
        Long saleId = 1L;
        SaleInputDto saleInput = new SaleInputDto();
        saleInput.setSalePriceIncl(new BigDecimal("20.000"));
        saleInput.setBusinessOrPrivate(BusinessOrPrivate.PRIVATE);
        saleInput.setQuantity(1);
        saleInput.setDiscount(500.00);
        saleInput.setPaymentMethod("Bank");
        saleInput.setTypeOrder("Order");
        saleInput.setComment("Amazing");
        saleInput.setAddition(Addition.DPS);
        saleInput.setWarranty("2 years");

        Sale existingSale = new Sale();
        existingSale.setId(1L);
        existingSale.setSalePriceIncl(new BigDecimal("18.000"));
        existingSale.setBpmPrice(new BigDecimal("1800"));
        existingSale.setTaxPrice(new BigDecimal("3123.96"));
        existingSale.setSalePriceEx(new BigDecimal("13076.04"));
        existingSale.setBusinessOrPrivate(BusinessOrPrivate.PRIVATE);
        existingSale.setQuantity(1);
        existingSale.setDiscount(1500.00);
        existingSale.setPaymentMethod("Bank");
        existingSale.setTypeOrder("Order");
        existingSale.setComment("Amazing");
        existingSale.setAddition(Addition.DPS);
        existingSale.setWarranty("3 years");
        existingSale.setStatus(Status.NEW);

        Sale updatedSale = new Sale();
        updatedSale.setId(saleId);
        updatedSale.setSalePriceIncl(saleInput.getSalePriceIncl());
        updatedSale.setBusinessOrPrivate(saleInput.getBusinessOrPrivate());
        updatedSale.setQuantity(saleInput.getQuantity());
        updatedSale.setDiscount(saleInput.getDiscount());
        updatedSale.setPaymentMethod(saleInput.getPaymentMethod());
        updatedSale.setTypeOrder(saleInput.getTypeOrder());
        updatedSale.setComment(saleInput.getComment());
        updatedSale.setAddition(saleInput.getAddition());
        updatedSale.setWarranty(saleInput.getWarranty());
        updatedSale.setStatus(existingSale.getStatus());

        List<BigDecimal> prices = new ArrayList<>();
        prices.add(new BigDecimal("3305.78"));
        prices.add(new BigDecimal("2000"));
        prices.add(new BigDecimal("14694.22"));
        updatedSale.setTaxPrice(prices.get(0));
        updatedSale.setBpmPrice(prices.get(1));
        updatedSale.setSalePriceEx(prices.get(2));

        SaleOutputDto output = new SaleOutputDto();
        output.setId(updatedSale.getId());
        output.setSalePriceIncl(updatedSale.getSalePriceIncl());
        output.setBpmPrice(updatedSale.getBpmPrice());
        output.setTaxPrice(updatedSale.getTaxPrice());
        output.setSalePriceEx(updatedSale.getSalePriceEx());
        output.setBusinessOrPrivate(updatedSale.getBusinessOrPrivate());
        output.setQuantity(updatedSale.getQuantity());
        output.setDiscount(updatedSale.getDiscount());
        output.setPaymentMethod(updatedSale.getPaymentMethod());
        output.setTypeOrder(updatedSale.getTypeOrder());
        output.setComment(updatedSale.getComment());
        output.setAddition(updatedSale.getAddition());
        output.setWarranty(updatedSale.getWarranty());

        when(saleRepository.findById(saleId)).thenReturn(Optional.of(existingSale));
        when(saleMapper.updateSaleFromSaleInputDto(saleInput, existingSale)).thenReturn(updatedSale);
        when(checkAndChangeStatus.returnNewSaleStatus(updatedSale)).thenReturn(updatedSale.getStatus());
        when(priceCalculator.calculatePrices(updatedSale)).thenReturn(prices);
        when(saleRepository.save(updatedSale)).thenReturn(updatedSale);
        when(saleMapper.saleTosaleOutputDto(updatedSale)).thenReturn(output);

        SaleOutputDto result = saleService.updateSale(saleId, saleInput);

        assertNotNull(result);
        assertEquals(output, result);
        assertEquals(new BigDecimal("14694.22"), result.getSalePriceEx());
    }

    @Test
    @DisplayName("Update existing sale - not found")
    void updateSaleNotFound() {
        Long saleId = 1L;
        SaleInputDto saleInput = new SaleInputDto();

        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.updateSale(saleId, saleInput));

        assertEquals("Sale with id: " + saleId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Delete sale - found")
    void deleteSaleFound() {
        Long saleId = 1L;
        Sale sale = new Sale();
        sale.setId(saleId);

        when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));

        saleService.deleteSale(saleId);

        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());

        assertFalse(saleRepository.findById(saleId).isPresent());
    }

    @Test
    @DisplayName("Delete sale - not found")
    void deleteSaleNotFound() {
        Long saleId = 1L;

        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.deleteSale(saleId));

        assertEquals("Sale with id: " + saleId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Create purchase if vehicle is out of stock")
    void createPurchaseIfVehicleIsOutOfStock() {
        Car car = new Car();
        car.setAmountInStock(0);

        Sale sale = new Sale();
        sale.setQuantity(2);
        sale.setSalePriceEx(new BigDecimal("8597.10"));
        sale.setBusinessOrPrivate(BusinessOrPrivate.PRIVATE);

        Purchase purchase = new Purchase();
        purchase.setVehicle(car);
        purchase.setQuantity(sale.getQuantity());
        purchase.setOrderDate(LocalDate.now());
        purchase.setStatus(Status.OPEN);
        purchase.setOrderNumber(1);
        purchase.setPurchasePriceIncl(sale.getSalePriceEx());
        purchase.setBusinessOrPrivate(sale.getBusinessOrPrivate());

        when(purchaseRepository.findLastOrderNumber()).thenReturn(1);

        saleService.checkVehicleInStockElseCreatePurchase(car, sale);

        assertNotNull(purchase);
        assertEquals(car, purchase.getVehicle());
        assertEquals(2, purchase.getQuantity());
        assertEquals(1, purchase.getOrderNumber());
        assertEquals(sale.getSalePriceEx(), purchase.getPurchasePriceIncl());

    }

    @Test
    @DisplayName("Don't create a purchase if vehicle is in stock")
    void checkVehicleInStock() {
        Car car = new Car();
        car.setAmountInStock(5);

        Sale sale = new Sale();
        sale.setQuantity(2);

        saleService.checkVehicleInStockElseCreatePurchase(car, sale);

        assertNotNull(sale);
        assertEquals(3, car.getAmountInStock());
    }

    @Test
    @DisplayName("Assign vehicle to sale - succes for private")
    void assignVehicleToSaleSuccesForPrivate() {
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setQuantity(2);
        sale.setBusinessOrPrivate(BusinessOrPrivate.PRIVATE);

        Car car = new Car();
        car.setAmountInStock(5);
        car.setLicensePlate("P-673-GD");

        IdInputDto idInputDto = new IdInputDto();
        idInputDto.setId(2L);

        SaleOutputDto output = new SaleOutputDto();
        output.setId(sale.getId());
        output.setQuantity(sale.getQuantity());
        output.setBusinessOrPrivate(sale.getBusinessOrPrivate());

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(vehicleRepository.findById(idInputDto.getId())).thenReturn(Optional.of(car));
        when(saleRepository.save(any())).thenReturn(sale);
        when(saleMapper.saleTosaleOutputDto(any())).thenReturn(output);

        SaleOutputDto result = saleService.assignVehicleToSale(sale.getId(), idInputDto);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Assign vehicle to sale - succes for business")
    void assignVehicleToSaleSuccesForBusiness() {
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setQuantity(2);
        sale.setBusinessOrPrivate(BusinessOrPrivate.BUSINESS);

        BusinessVehicle businessVehicle = new BusinessVehicle();
        businessVehicle.setAmountInStock(5);
        businessVehicle.setLicensePlate("V-673-GD");

        IdInputDto vehicleInputDto = new IdInputDto();
        vehicleInputDto.setId(2L);

        SaleOutputDto output = new SaleOutputDto();
        output.setId(sale.getId());
        output.setQuantity(sale.getQuantity());
        output.setBusinessOrPrivate(sale.getBusinessOrPrivate());

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(vehicleRepository.findById(vehicleInputDto.getId())).thenReturn(Optional.of(businessVehicle));
        when(saleRepository.save(any())).thenReturn(sale);
        when(saleMapper.saleTosaleOutputDto(any())).thenReturn(output);

        SaleOutputDto result = saleService.assignVehicleToSale(sale.getId(), vehicleInputDto);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Assign vehicle to sale - incorrect business vehicle")
    void assignVehicleToSaleIncorrectBusinessVehicle() {
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setBusinessOrPrivate(BusinessOrPrivate.BUSINESS);

        BusinessVehicle businessVehicle = new BusinessVehicle();
        businessVehicle.setAmountInStock(5);
        businessVehicle.setLicensePlate("GDD-67-D");

        IdInputDto vehicleInputDto = new IdInputDto();
        vehicleInputDto.setId(2L);

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(vehicleRepository.findById(vehicleInputDto.getId())).thenReturn(Optional.of(businessVehicle));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> saleService.assignVehicleToSale(sale.getId(), vehicleInputDto));

        assertEquals(exception.getMessage(), "You can only add a business vehicle to a order which are set for business");
    }

    @Test
    @DisplayName("Assign vehicle to sale - sale not found")
    void assignVehicleToSaleNotFound() {
        Long saleId = 1L;

        Car car = new Car();
        car.setId(2L);

        IdInputDto vehicleInputDto = new IdInputDto();
        vehicleInputDto.setId(2L);

        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());
        when(vehicleRepository.findById(vehicleInputDto.getId())).thenReturn(Optional.of(car));

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.assignVehicleToSale(saleId, vehicleInputDto));

        assertEquals("Sale with id: " + saleId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Assign vehicle to sale - vehicle not found")
    void assignVehicleToSaleNotFoundVehicle() {
        Long vehicleId = 1L;

        Sale sale = new Sale();
        sale.setId(1L);

        IdInputDto vehicleInputDto = new IdInputDto();
        vehicleInputDto.setId(1L);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());
        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.assignVehicleToSale(sale.getId(), vehicleInputDto));

        assertEquals("Vehicle with id: " + vehicleId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Assign customer to sale - both found")
    void assignCustomerToSaleFound() {
        Sale sale = new Sale();
        sale.setId(1L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setPurchaseHistory(new ArrayList<>());

        IdInputDto customerInputDto = new IdInputDto();
        customerInputDto.setId(1L);

        SaleOutputDto output = new SaleOutputDto();
        output.setId(sale.getId());

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(customerRepository.findById(customerInputDto.getId())).thenReturn(Optional.of(customer));
        when(saleRepository.save(sale)).thenReturn(sale);
        when(saleMapper.saleTosaleOutputDto(sale)).thenReturn(output);

        SaleOutputDto result = saleService.assignCustomerToSale(sale.getId(), customerInputDto);

        assertNotNull(result);
        assertTrue(customer.getPurchaseHistory().contains(sale));
    }

    @Test
    @DisplayName("Assign customer to sale - sale not found")
    void assignCustomerToSaleNotFound() {
        Long saleId = 1L;

        Customer customer = new Customer();
        customer.setId(1L);

        IdInputDto customerInputDto = new IdInputDto();
        customerInputDto.setId(2L);

        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());
        when(customerRepository.findById(customerInputDto.getId())).thenReturn(Optional.of(customer));

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.assignCustomerToSale(saleId, customerInputDto));

        assertEquals("Sale with id: " + saleId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Assign customer to sale - customer not found")
    void assignCustomerToSaleNotFoundCustomer() {
        Long customerId = 1L;

        Sale sale = new Sale();
        sale.setId(1L);

        IdInputDto customerInputDto = new IdInputDto();
        customerInputDto.setId(1L);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.assignCustomerToSale(sale.getId(), customerInputDto));

        assertEquals("Customer with id: " + customerId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Assign seller to sale - both found")
    void assignSellerToSaleFound() {
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setOrderNumber(1);

        Profile seller = new Profile();
        seller.setId(1L);
        seller.setSaleOrderNumbers(new ArrayList<>());
        seller.setRole(Role.SELLER);

        IdInputDto sellerInputDto = new IdInputDto();
        sellerInputDto.setId(1L);

        SaleOutputDto output = new SaleOutputDto();
        output.setId(sale.getId());
        output.setOrderNumber(sale.getOrderNumber());

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(profileRepository.findById(seller.getId())).thenReturn(Optional.of(seller));
        when(saleRepository.save(sale)).thenReturn(sale);
        when(saleMapper.saleTosaleOutputDto(sale)).thenReturn(output);

        SaleOutputDto result = saleService.assignSellerToSale(sale.getId(), sellerInputDto);

        assertNotNull(result);
        assertTrue(seller.getSaleOrderNumbers().contains(sale.getOrderNumber()));
    }

    @Test
    @DisplayName("Assign seller to sale - non_seller role")
    void assignSellerToSaleRoleNotSeller() {
        Sale sale = new Sale();
        sale.setId(1L);

        Profile notASeller = new Profile();
        notASeller.setId(1L);
        notASeller.setRole(Role.ADMIN);

        IdInputDto notASellerInputDto = new IdInputDto();
        notASellerInputDto.setId(1L);

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(profileRepository.findById(notASeller.getId())).thenReturn(Optional.of(notASeller));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> saleService.assignSellerToSale(sale.getId(), notASellerInputDto));

        assertEquals("The ProfileId you entered has role Admin but only profiles with role SELLER can be assigned", exception.getMessage());

    }

    @Test
    @DisplayName("Assign seller to sale - sale not found")
    void assignSellerToSaleNotFound() {
        Long saleId = 1L;

        Profile seller = new Profile();
        seller.setId(1L);

        IdInputDto sellerInputDto = new IdInputDto();
        sellerInputDto.setId(2L);

        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());
        when(profileRepository.findById(sellerInputDto.getId())).thenReturn(Optional.of(seller));

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.assignSellerToSale(saleId, sellerInputDto));

        assertEquals("Sale with id: " + saleId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Assign seller to sale - seller not found")
    void assignSellerToSaleNotFoundSeller() {
        Long sellerId = 1L;

        Sale sale = new Sale();
        sale.setId(1L);

        IdInputDto sellerInputDto = new IdInputDto();
        sellerInputDto.setId(1L);

        when(profileRepository.findById(sellerId)).thenReturn(Optional.empty());
        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> saleService.assignSellerToSale(sale.getId(), sellerInputDto));

        assertEquals("Seller with id: " + sellerId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Assign signature to sale - both found")
    void assignSignatureToSaleFound() {
        Sale sale = new Sale();
        sale.setId(1L);

        SignatureOutputDto signatureOutputDto = new SignatureOutputDto();
        signatureOutputDto.setId(1L);
        signatureOutputDto.setContentType("application/pdf");

        Signature signature = new Signature();
        signature.setId(signatureOutputDto.getId());
        signature.setContentType(signatureOutputDto.getContentType());

        SaleOutputDto saleOutputDto = new SaleOutputDto();
        saleOutputDto.setId(sale.getId());

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(signatureMapper.signatureOutputDtoToSignature(signatureOutputDto)).thenReturn(signature);
        when(saleRepository.save(sale)).thenReturn(sale);
        when(saleMapper.saleTosaleOutputDto(sale)).thenReturn(saleOutputDto);

        SaleOutputDto result = saleService.assignSignatureToSale(sale.getId(), signatureOutputDto);

        assertNotNull(result);
        assertEquals(signatureOutputDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Assign signature to sale - sale not found")
    void assignSignatureToSaleNotFound() {
        Long saleId = 1L;

        SignatureOutputDto signatureOutputDto = new SignatureOutputDto();
        signatureOutputDto.setId(1L);
        signatureOutputDto.setContentType("application/pdf");

        Signature signature = new Signature();
        signature.setId(signatureOutputDto.getId());

        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());
        when(signatureMapper.signatureOutputDtoToSignature(signatureOutputDto)).thenReturn(signature);

        assertThrows(RecordNotFoundException.class, () -> saleService.assignSignatureToSale(saleId, signatureOutputDto));
    }

    @Test
    @DisplayName("Assign signature to sale - signature type null")
    void assignSignatureToSaleSignatureTypeNull() {
        Sale sale = new Sale();
        sale.setId(1L);

        SignatureOutputDto signatureOutputDto = new SignatureOutputDto();
        signatureOutputDto.setId(1L);

        Signature signature = new Signature();
        signature.setId(signatureOutputDto.getId());

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(signatureMapper.signatureOutputDtoToSignature(signatureOutputDto)).thenReturn(signature);

        assertThrows(SignatureException.class, () -> saleService.assignSignatureToSale(sale.getId(), signatureOutputDto));
    }

    @Test
    @DisplayName("Get signature from sale - both found")
    void getSignatureFromSaleFound() {
        Sale sale = new Sale();
        sale.setId(1L);

        Signature signature = new Signature();
        signature.setId(1L);
        signature.setSale(sale);

        sale.setSignature(signature);

        SignatureOutputDto outputDto = new SignatureOutputDto();
        outputDto.setId(signature.getId());

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(signatureMapper.signatureToOutputDto(signature)).thenReturn(outputDto);

        SignatureOutputDto result = saleService.getSignatureFromSale(sale.getId());

        assertNotNull(result);
        assertEquals(outputDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Get signature from sale - sale not found")
    void getSignatureFromSaleNotFound() {
        Long saleId = 1L;

        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> saleService.getSignatureFromSale(saleId));
    }

    @Test
    @DisplayName("Get signature from sale - signature is null")
    void getSignatureFromSaleSignatureNull() {
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setSignature(null);

        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));

        assertThrows(RecordNotFoundException.class, () -> saleService.getSignatureFromSale(sale.getId()));
    }

}
