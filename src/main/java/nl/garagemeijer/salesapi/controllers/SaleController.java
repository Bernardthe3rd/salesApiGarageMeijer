package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.ids.IdInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleInputDto;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;
import nl.garagemeijer.salesapi.dtos.signature.SignatureInputDto;
import nl.garagemeijer.salesapi.dtos.signature.SignatureOutputDto;
import nl.garagemeijer.salesapi.exceptions.SignatureException;
import nl.garagemeijer.salesapi.services.SaleService;
import nl.garagemeijer.salesapi.services.SignatureService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;
    private final SignatureService signatureService;

    public SaleController(SaleService saleService, SignatureService signatureService) {
        this.saleService = saleService;
        this.signatureService = signatureService;
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

    @GetMapping("/{id}/signature")
    public ResponseEntity<byte[]> getSaleSignature(@PathVariable Long id) {
        SignatureOutputDto signature = saleService.getSignatureFromSale(id);
        MediaType mediaType;

        try {
            mediaType = MediaType.parseMediaType(signature.getContentType());
        } catch (InvalidMediaTypeException ignored) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + signature.getFileName())
                .body(signature.getContents());
    }

    @PostMapping
    public ResponseEntity<SaleOutputDto> createSale(@Valid @RequestBody SaleInputDto sale) {
        SaleOutputDto createdSale = saleService.saveSale(sale);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSale.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdSale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleOutputDto> updateSale(@PathVariable Long id, @Valid @RequestBody SaleInputDto sale) {
        SaleOutputDto updatedSale = saleService.updateSale(id, sale);
        return ResponseEntity.ok(updatedSale);
    }

    @PutMapping("/{id}/vehicle")
    public ResponseEntity<SaleOutputDto> addVehicleToSale(@PathVariable Long id, @Valid @RequestBody IdInputDto vehicleId) {
        SaleOutputDto updatedSale = saleService.assignVehicleToSale(id, vehicleId);
        return ResponseEntity.ok(updatedSale);
    }

    @PutMapping("/{id}/customer")
    public ResponseEntity<SaleOutputDto> addCustomerToSale(@PathVariable Long id, @Valid @RequestBody IdInputDto customerId) {
        SaleOutputDto updatedSale = saleService.assignCustomerToSale(id, customerId);
        return ResponseEntity.ok(updatedSale);
    }

    @PutMapping("/{id}/seller")
    public ResponseEntity<SaleOutputDto> addSellerToSale(@PathVariable Long id, @Valid @RequestBody IdInputDto sellerId) {
        SaleOutputDto updatedSale = saleService.assignSellerToSale(id, sellerId);
        return ResponseEntity.ok(updatedSale);
    }

    @PutMapping("/{id}/signature")
    public ResponseEntity<SaleOutputDto> addSignatureToSale(@PathVariable Long id, @Valid @RequestParam("file") MultipartFile file) {
        try {
            SignatureInputDto input = new SignatureInputDto();
            input.setOriginalFileName(file.getOriginalFilename());
            input.setContentType(file.getContentType());
            input.setContents(file.getBytes());
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/sales/")
                    .path(Objects.requireNonNull(id.toString()))
                    .path("/signature")
                    .toUriString();
            SignatureOutputDto signature = signatureService.storeSignature(input, url);
            SaleOutputDto updatedSale = saleService.assignSignatureToSale(id, signature);
            return ResponseEntity.created(URI.create(url)).body(updatedSale);
        } catch (IOException e) {
            throw new SignatureException(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
