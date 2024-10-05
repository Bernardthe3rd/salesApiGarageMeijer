package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.models.Motor;
import nl.garagemeijer.salesapi.repositories.MotorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorService {

    private final MotorRepository motorRepository;

    public MotorService(MotorRepository motorRepository) {
        this.motorRepository = motorRepository;
    }

    public List<Motor> getMotors() {
        return motorRepository.findAll();
    }

    public Motor getMotor(Long id) {
        Optional<Motor> motorOptional = motorRepository.findById(id);
        if (motorOptional.isPresent()) {
            return motorOptional.get();
        } else {
            throw new RuntimeException("Motor with id " + id + " not found");
        }
    }

    public Motor saveMotor(Motor motor) {
        return motorRepository.save(motor);
    }

    public Motor updateMotor(Long id, Motor motor) {
        Motor motorToUpdate = motorRepository.findById(id).orElseThrow(() -> new RuntimeException("Motor with id " + id + " not found"));
        motorToUpdate.setHandlebarType(motor.getHandlebarType());
        return motorRepository.save(motorToUpdate);
    }

    public void deleteMotor(Long id) {
        motorRepository.deleteById(id);
    }
}
