package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.motors.MotorInputDto;
import nl.garagemeijer.salesapi.dtos.motors.MotorOutputDto;
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
        Optional<Motor> motorOptional = motorRepository.findById(id);
        if (motorOptional.isPresent()) {
            return motorMapper.motorTomotorOutputDto(motorOptional.get());
        } else {
            throw new RuntimeException("Motor with id " + id + " not found");
        }
    }

    public MotorOutputDto saveMotor(MotorInputDto motor) {
        Motor motorToSave = motorMapper.motorInputDtoTomotor(motor);
        return motorMapper.motorTomotorOutputDto(motorRepository.save(motorToSave));
    }

    public MotorOutputDto updateMotor(Long id, MotorInputDto motor) {
        Motor getMotor = motorRepository.findById(id).orElseThrow(() -> new RuntimeException("Motor with id " + id + " not found"));
        Motor motorToUpdate = motorMapper.updatemotorFrommotorInputDto(motor, getMotor);
        return motorMapper.motorTomotorOutputDto(motorRepository.save(motorToUpdate));
    }

    public void deleteMotor(Long id) {
        motorRepository.deleteById(id);
    }
}
