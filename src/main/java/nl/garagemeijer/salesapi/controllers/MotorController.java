package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.models.Motor;
import nl.garagemeijer.salesapi.repositories.MotorRepository;
import nl.garagemeijer.salesapi.services.MotorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Motor>> getAllMotors() {
        List<Motor> motors = motorService.getMotors();
        return ResponseEntity.ok(motors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motor> getMotorById(@PathVariable Long id) {
        Motor motor = motorService.getMotor(id);
        return ResponseEntity.ok(motor);
    }

    @PostMapping
    public ResponseEntity<Motor> createMotor(@Valid @RequestBody Motor Motor) {
        Motor createdMotor = motorService.saveMotor(Motor);
        URI location = URI.create("/api/motors/" + createdMotor.getId());
        return ResponseEntity.created(location).body(createdMotor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motor> updateMotor(@PathVariable Long id, @Valid @RequestBody Motor Motor) {
        Motor updatedMotor = motorService.updateMotor(id, Motor);
        return ResponseEntity.ok(updatedMotor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotor(@PathVariable Long id) {
        motorService.deleteMotor(id);
        return ResponseEntity.noContent().build();
    }
}
