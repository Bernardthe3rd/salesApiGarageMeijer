package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.ids.IdInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.dtos.signature.SignatureOutputDto;
import nl.garagemeijer.salesapi.enums.BusinessOrPrivate;
import nl.garagemeijer.salesapi.enums.Role;
import nl.garagemeijer.salesapi.enums.Status;
import nl.garagemeijer.salesapi.exceptions.BadRequestException;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.helpers.GetLastOrderNumber;
import nl.garagemeijer.salesapi.helpers.PriceCalculator;
import nl.garagemeijer.salesapi.mappers.SaleMapper;
import nl.garagemeijer.salesapi.mappers.SignatureMapper;
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
    private final GetLastOrderNumber getLastOrderNumber;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final ProfileRepository profileRepository;
    private final PurchaseRepository purchaseRepository;
    private final SignatureMapper signatureMapper;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper, PriceCalculator priceCalculator, VehicleRepository vehicleRepository, CustomerRepository customerRepository, ProfileRepository profileRepository, PurchaseRepository purchaseRepository, GetLastOrderNumber getLastOrderNumber, SignatureMapper signatureMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.priceCalculator = priceCalculator;
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.profileRepository = profileRepository;
        this.purchaseRepository = purchaseRepository;
        this.getLastOrderNumber = getLastOrderNumber;
        this.signatureMapper = signatureMapper;
    }


    public void checkVehicleInStockElseCreatePurchase(Vehicle vehicle, Sale sale) {
        if (vehicle.getAmountInStock() > 0) {
            vehicle.setAmountInStock(vehicle.getAmountInStock() - sale.getQuantity());
        } else {
            Purchase purchaseFromSale = new Purchase();
            purchaseFromSale.setVehicle(vehicle);
            purchaseFromSale.setQuantity(sale.getQuantity());
            purchaseFromSale.setOrderDate(LocalDate.now());
            purchaseFromSale.setStatus(Status.OPEN);
            purchaseFromSale.setOrderNumber(getLastOrderNumber.getLastOrderNumber(purchaseFromSale));
            purchaseFromSale.setExpectedDeliveryDate(LocalDate.of(2044, 1, 1));
            purchaseFromSale.setPurchasePriceIncl(sale.getSalePriceEx());
            purchaseFromSale.setBusinessOrPrivate(sale.getBusinessOrPrivate());
            purchaseFromSale.setSupplier("Please update me");
            purchaseRepository.save(purchaseFromSale);
        }
    }


    public List<SaleOutputDto> getSales() {
        return saleMapper.salesToSalesOutputDtos(saleRepository.findAll());
    }

    public SaleOutputDto getSale(Long id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            return saleMapper.saleTosaleOutputDto(optionalSale.get());
        } else {
            throw new RecordNotFoundException("Sale with id: " + id + " not found");
        }
    }

    public SaleOutputDto saveSale(SaleInputDto sale) {
        Sale saleToSave = saleMapper.saleInputDtoToSale(sale);

        saleToSave.setSaleDate(LocalDate.now());
        saleToSave.setStatus(Status.NEW);
        saleToSave.setOrderNumber(getLastOrderNumber.getLastOrderNumber(saleToSave));

        List<BigDecimal> prices = priceCalculator.calculatePrices(saleToSave);
        saleToSave.setTaxPrice(prices.get(0));
        saleToSave.setBpmPrice(prices.get(1));
        saleToSave.setSalePriceEx(prices.get(2));

        return saleMapper.saleTosaleOutputDto(saleRepository.save(saleToSave));
    }

    public SaleOutputDto updateSale(Long id, SaleInputDto sale) {
        Sale getSale = saleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Sale with id: " + id + " not found"));
        Sale saleToUpdate = saleMapper.updateSaleFromSaleInputDto(sale, getSale);

        List<BigDecimal> prices = priceCalculator.calculatePrices(saleToUpdate);
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
        if (saleRepository.findById(id).isEmpty()) {
            throw new RecordNotFoundException("Sale with id: " + id + " not found");
        }
        saleRepository.deleteById(id);
    }

    public SaleOutputDto assignVehicleToSale(Long id, IdInputDto vehicleId) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId.getId());
        if (optionalSale.isPresent() && optionalVehicle.isPresent()) {
            Sale sale = optionalSale.get();
            Vehicle vehicle = optionalVehicle.get();
            if (sale.getBusinessOrPrivate() == BusinessOrPrivate.BUSINESS && vehicle.getLicensePlate().startsWith("V")) {
                sale.setVehicle(vehicle);
                checkVehicleInStockElseCreatePurchase(vehicle, sale);
                return saleMapper.saleTosaleOutputDto(saleRepository.save(sale));
            } else if (sale.getBusinessOrPrivate() == BusinessOrPrivate.PRIVATE) {
                sale.setVehicle(vehicle);
                checkVehicleInStockElseCreatePurchase(vehicle, sale);
                return saleMapper.saleTosaleOutputDto(saleRepository.save(sale));
            } else {
                throw new BadRequestException("You can only add a business vehicle to a order which are set for business");
            }
        } else if (optionalVehicle.isEmpty()) {
            throw new RecordNotFoundException("Vehicle with id: " + vehicleId.getId() + " not found");
        } else {
            throw new RecordNotFoundException("Sale with id: " + id + " not found");
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
        } else if (optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException("Customer with id: " + customerId.getId() + " not found");
        } else {
            throw new RecordNotFoundException("Sale with id: " + id + " not found");
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
                throw new RuntimeException("The ProfileId you entered has role Admin but only profiles with role SELLER can be assigned");
            }
        } else if (optionalSeller.isEmpty()) {
            throw new RecordNotFoundException("Seller with id: " + sellerId.getId() + " not found");
        } else {
            throw new RecordNotFoundException("Sale with id: " + id + " not found");
        }
    }

    public SaleOutputDto assignSignatureToSale(Long id, SignatureOutputDto signatureDto) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        Signature signature = signatureMapper.signatureOutputDtoToSignature(signatureDto);
        if (optionalSale.isEmpty()) {
            throw new RecordNotFoundException("Sale with id: " + id + " not found");
        }
        Sale sale = optionalSale.get();
        signature.setSale(sale);
        sale.setSignature(signature);
        return saleMapper.saleTosaleOutputDto(saleRepository.save(sale));
    }

    public SignatureOutputDto getSignatureFromSale(Long id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isEmpty()) {
            throw new RecordNotFoundException("Sale with id: " + id + " not found");
        }
        if (optionalSale.get().getSignature() == null) {
            throw new RecordNotFoundException("This sale has no signature yet");
        }
        Sale sale = optionalSale.get();
        return signatureMapper.signatureToOutputDto(sale.getSignature());
    }
}
