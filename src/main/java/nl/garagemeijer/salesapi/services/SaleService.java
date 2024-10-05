package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.sales.SaleInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.mappers.SaleMapper;
import nl.garagemeijer.salesapi.models.Sale;
import nl.garagemeijer.salesapi.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
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

    public Integer getLastOrderNumber() {
        Integer lastOrderNumber = saleRepository.findLastOrderNumber();
        return (lastOrderNumber != null) ? lastOrderNumber : 0;
    }

    public SaleOutputDto saveSale(SaleInputDto sale) {
        Sale saleToSave = saleMapper.saleInputDtoToSale(sale);

        saleToSave.setSaleDate(LocalDate.now());
        saleToSave.setStatus("Open");

        if (saleToSave.getBusinessOrPrivate().contains("business")) {
            saleToSave.setBpmPrice(new BigDecimal("0.00"));
            saleToSave.setTaxPrice(new BigDecimal("0.00"));
            saleToSave.setSalePriceEx(new BigDecimal(String.valueOf(saleToSave.getSalePriceIncl())));
        } else if (saleToSave.getBusinessOrPrivate().contains("private")) {
            BigDecimal taxRate = new BigDecimal("21.00");
            BigDecimal taxOne = saleToSave.getSalePriceIncl().divide(new BigDecimal("121.00"), 2, RoundingMode.HALF_UP);
            BigDecimal taxTwo = taxOne.multiply(taxRate);

            saleToSave.setTaxPrice(taxTwo);
            saleToSave.setBpmPrice(new BigDecimal(String.valueOf(saleToSave.getSalePriceIncl().multiply(new BigDecimal("0.1")))));
            BigDecimal saleExOne = saleToSave.getSalePriceIncl().subtract(saleToSave.getBpmPrice());
            saleToSave.setSalePriceEx(saleExOne.subtract(saleToSave.getTaxPrice()));
            if (saleToSave.getDiscount() != null) {
                saleToSave.setSalePriceEx(new BigDecimal(String.valueOf(saleToSave.getSalePriceEx().subtract(BigDecimal.valueOf(saleToSave.getDiscount())))));
            }
        }

        saleToSave.setOrderNumber(getLastOrderNumber() + 1);
        return saleMapper.saleTosaleOutputDto(saleRepository.save(saleToSave));
    }

    public SaleOutputDto updateSale(Long id, SaleInputDto sale) {
        Sale getSale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));
        Sale saleToUpdate = saleMapper.updateSaleFromSaleInputDto(sale, getSale);
        return saleMapper.saleTosaleOutputDto(saleRepository.save(saleToUpdate));
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
