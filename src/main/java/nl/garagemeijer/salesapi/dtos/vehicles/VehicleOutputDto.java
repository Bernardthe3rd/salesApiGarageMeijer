package nl.garagemeijer.salesapi.dtos.vehicles;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.purchases.PurchaseOutputDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VehicleOutputDto {

    private Long id;
    private String vinNumber;
    private String brand;
    private String model;
    private String type;
    private Integer year;
    private String licensePlate;
    private Integer mileage;
    private String color;
    private String fuelType;
    private Double engineCapacity;
    private LocalDate firstRegistrationDate;
    private Integer amountInStock;

}
