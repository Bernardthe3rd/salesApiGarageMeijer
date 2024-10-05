package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.sales.SaleInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.models.Sale;
import nl.garagemeijer.salesapi.repositories.SaleRepository;
import nl.garagemeijer.salesapi.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;
    private final SaleRepository saleRepository;

    public SaleController(SaleService saleService, SaleRepository saleRepository) {
        this.saleService = saleService;
        this.saleRepository = saleRepository;
    }

    @GetMapping
    public ResponseEntity<List<SaleOutputDto>> getAllSales() {
        List<SaleOutputDto> sales = saleService.getSales();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleOutputDto> getSaleById(@PathVariable Long id) {
        SaleOutputDto selectedSale = saleService.getSale(id);
        return ResponseEntity.ok(selectedSale);
    }

    @PostMapping
    public ResponseEntity<SaleOutputDto> createSale(@Valid @RequestBody SaleInputDto sale) {
        SaleOutputDto createdSale = saleService.saveSale(sale);
        URI location = URI.create("/api/sales/" + createdSale.getId());
        return ResponseEntity.created(location).body(createdSale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleOutputDto> updateSale(@PathVariable Long id, @Valid @RequestBody SaleInputDto sale) {
        SaleOutputDto updatedSale = saleService.updateSale(id, sale);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
