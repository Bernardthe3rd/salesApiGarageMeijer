package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.IdInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.enums.Role;
import nl.garagemeijer.salesapi.enums.Status;
import nl.garagemeijer.salesapi.helpers.PriceCalculator;
import nl.garagemeijer.salesapi.mappers.SaleMapper;
import nl.garagemeijer.salesapi.models.*;
import nl.garagemeijer.salesapi.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final PriceCalculator priceCalculator;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final ProfileRepository profileRepository;
    private final PurchaseRepository purchaseRepository;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper, PriceCalculator priceCalculator, VehicleRepository vehicleRepository, CustomerRepository customerRepository, ProfileRepository profileRepository, PurchaseRepository purchaseRepository) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.priceCalculator = priceCalculator;
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.profileRepository = profileRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Integer getLastOrderNumber() {
        Integer lastOrderNumber = saleRepository.findLastOrderNumber();
        return (lastOrderNumber != null) ? lastOrderNumber : 0;
    }

    public void checkVehicleInStockElseCreatePurchase(Vehicle vehicle, int saleQuantity) {
        if (vehicle.getAmountInStock() > 0) {
            vehicle.setAmountInStock(vehicle.getAmountInStock() - saleQuantity);
        } else {
            Purchase purchaseFromSale = new Purchase();
            purchaseFromSale.setVehicle(vehicle);
            purchaseFromSale.setQuantity(saleQuantity);
            purchaseFromSale.setOrderDate(LocalDate.now());
            purchaseFromSale.setStatus(Status.PENDING);
            purchaseFromSale.setOrderNumber(helper);
            purchaseRepository.save(purchaseFromSale);
        }
    }


    public List<SaleOutputDto> getSales() {
        return saleMapper.salesToSalesOutputDtos(saleRepository.findAll());
    }

    public SaleOutputDto getSale(Long id) {
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if (saleOptional.isPresent()) {
            return saleMapper.saleTosaleOutputDto(saleOptional.get());
        } else {
            throw new RuntimeException("Sale not found");
        }
    }

    public SaleOutputDto saveSale(SaleInputDto sale) {
        Sale saleToSave = saleMapper.saleInputDtoToSale(sale);

        saleToSave.setSaleDate(LocalDate.now());
        saleToSave.setStatus(Status.NEW);
        saleToSave.setOrderNumber(getLastOrderNumber() + 1);

        List<BigDecimal> prices = priceCalculator.calculatePricesSales(saleToSave);
        saleToSave.setTaxPrice(prices.get(0));
        saleToSave.setBpmPrice(prices.get(1));
        saleToSave.setSalePriceEx(prices.get(2));

        return saleMapper.saleTosaleOutputDto(saleRepository.save(saleToSave));
    }

    public SaleOutputDto updateSale(Long id, SaleInputDto sale) {
        Sale getSale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));
        Sale saleToUpdate = saleMapper.updateSaleFromSaleInputDto(sale, getSale);

        List<BigDecimal> prices = priceCalculator.calculatePricesSales(saleToUpdate);
        saleToUpdate.setTaxPrice(prices.get(0));
        saleToUpdate.setBpmPrice(prices.get(1));
        saleToUpdate.setSalePriceEx(prices.get(2));

        if (saleToUpdate.getStatus() == Status.NEW) {
            saleToUpdate.setStatus(Status.PENDING);
        } else if (saleToUpdate.getTypeOrder().contains("order") && saleToUpdate.getCustomer() != null) {
            saleToUpdate.setStatus(Status.CLOSED);
        } else {
            saleToUpdate.setStatus(Status.OPEN);
        }

        return saleMapper.saleTosaleOutputDto(saleRepository.save(saleToUpdate));
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    public SaleOutputDto assignVehicleToSale(Long id, IdInputDto vehicleId) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        Optional<Vehicle> optionalVehicl = vehicleRepository.findById(vehicleId.getId());
        if (optionalSale.isPresent() && optionalVehicl.isPresent()) {
            Sale sale = optionalSale.get();
            Vehicle vehicle = optionalVehicl.get();
            sale.setVehicle(vehicle);
            checkVehicleInStockElseCreatePurchase(vehicle, sale.getQuantity());
            return saleMapper.saleTosaleOutputDto(saleRepository.save(sale));
        } else {
            throw new RuntimeException("Sale not found");
        }
    }

    public SaleOutputDto assignCustomerToSale(Long id, IdInputDto customerId) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId.getId());
        if (optionalSale.isPresent() && optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<Sale> customerPurchaseList = customer.getPurchaseHistory();
            Sale sale = optionalSale.get();
            sale.setCustomer(customer);
            customerPurchaseList.add(sale);
            return saleMapper.saleTosaleOutputDto(saleRepository.save(sale));
        } else {
            throw new RuntimeException("Sale not found");
        }
    }

    public SaleOutputDto assignSellerToSale(Long id, IdInputDto sellerId) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        Optional<Profile> optionalSeller = profileRepository.findById(sellerId.getId());
        if (optionalSale.isPresent() && optionalSeller.isPresent()) {
            Sale sale = optionalSale.get();
            Profile seller = optionalSeller.get();
            List<Integer> sellerListOfOrderNumbers = seller.getSaleOrderNumbers();
            if (seller.getRole().equals(Role.SELLER)) {
                sellerListOfOrderNumbers.add(sale.getOrderNumber());
                sale.setSellerId(seller.getId());
                return saleMapper.saleTosaleOutputDto(saleRepository.save(sale));
            } else {
                throw new RuntimeException("Only profiles with role SELLER can be assigned");
            }
        } else {
            throw new RuntimeException("Sale not found");
        }
    }
}
