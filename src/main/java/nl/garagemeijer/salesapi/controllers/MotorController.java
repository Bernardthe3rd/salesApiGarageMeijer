package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.motors.MotorInputDto;
import nl.garagemeijer.salesapi.dtos.motors.MotorOutputDto;
import nl.garagemeijer.salesapi.repositories.MotorRepository;
import nl.garagemeijer.salesapi.services.MotorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/motors")
public class MotorController {

    private final MotorService motorService;
    private final MotorRepository motorRepository;

    public MotorController(MotorService motorService, MotorRepository motorRepository) {
        this.motorService = motorService;
        this.motorRepository = motorRepository;
    }

    @GetMapping
    public ResponseEntity<List<MotorOutputDto>> getAllMotors() {
        List<MotorOutputDto> motors = motorService.getMotors();
        return ResponseEntity.ok(motors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotorOutputDto> getMotorById(@PathVariable Long id) {
        MotorOutputDto motor = motorService.getMotor(id);
        return ResponseEntity.ok(motor);
    }

    @PostMapping
    public ResponseEntity<MotorOutputDto> createMotor(@Valid @RequestBody MotorInputDto Motor) {
        MotorOutputDto createdMotor = motorService.saveMotor(Motor);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMotor.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdMotor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotorOutputDto> updateMotor(@PathVariable Long id, @Valid @RequestBody MotorInputDto Motor) {
        MotorOutputDto updatedMotor = motorService.updateMotor(id, Motor);
        return ResponseEntity.ok(updatedMotor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotor(@PathVariable Long id) {
        motorService.deleteMotor(id);
        return ResponseEntity.noContent().build();
    }
}
