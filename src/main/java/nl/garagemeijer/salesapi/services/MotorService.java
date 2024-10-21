package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.motors.MotorInputDto;
import nl.garagemeijer.salesapi.dtos.motors.MotorOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.MotorMapper;
import nl.garagemeijer.salesapi.models.Motor;
import nl.garagemeijer.salesapi.repositories.MotorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorService {

    private final MotorRepository motorRepository;
    private final MotorMapper motorMapper;

    public MotorService(MotorRepository motorRepository, MotorMapper motorMapper) {
        this.motorRepository = motorRepository;
        this.motorMapper = motorMapper;
    }

    public List<MotorOutputDto> getMotors() {
        return motorMapper.motorsTomotorsOutputDtos(motorRepository.findAll());
    }

    public MotorOutputDto getMotor(Long id) {
        Optional<Motor> optionalMotor = motorRepository.findById(id);
        if (optionalMotor.isPresent()) {
            return motorMapper.motorTomotorOutputDto(optionalMotor.get());
        } else {
            throw new RecordNotFoundException("Motor with id " + id + " not found");
        }
    }

    public MotorOutputDto saveMotor(MotorInputDto motor) {
        Motor motorToSave = motorMapper.motorInputDtoTomotor(motor);
        motorToSave.setAmountInStock(0);
        return motorMapper.motorTomotorOutputDto(motorRepository.save(motorToSave));
    }

    public MotorOutputDto updateMotor(Long id, MotorInputDto motor) {
        Motor getMotor = motorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Motor with id " + id + " not found"));
        Motor motorToUpdate = motorMapper.updatemotorFrommotorInputDto(motor, getMotor);
        return motorMapper.motorTomotorOutputDto(motorRepository.save(motorToUpdate));
    }

    public void deleteMotor(Long id) {
        if (motorRepository.findById(id).isEmpty()) {
            throw new RecordNotFoundException("Motor with id " + id + " not found");
        }
        motorRepository.deleteById(id);
    }
}
