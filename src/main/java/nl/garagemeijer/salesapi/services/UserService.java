package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.users.UserInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.UserMapper;
import nl.garagemeijer.salesapi.models.User;
import nl.garagemeijer.salesapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserOutputDto> getUsers() {
        return userMapper.usersToUserOutputDtos(userRepository.findAll());
    }

    public UserOutputDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.userToUserOutputDto(user.get());
        } else {
            throw new RecordNotFoundException("User with id: " + id + " not found");
        }
    }

    public UserOutputDto saveUser(UserInputDto user) {
        User userToSave = userMapper.userInputDtoToUser(user);
        userToSave.setCreationDate(LocalDate.now());
        userToSave.setIsActive(true);
        userToSave.setLastLogin(LocalDate.now());

        return userMapper.userToUserOutputDto(userRepository.save(userToSave));
    }

    public UserOutputDto updateUser(Long id, UserInputDto user) {
        User getUser = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User with id: " + id + " not found"));
        User userToUpdate = userMapper.updateUserFromUserInputDto(user, getUser);
        userToUpdate.setLastLogin(LocalDate.now());
        if (!userToUpdate.getIsActive()) {
            userToUpdate.setIsActive(true);
        }
        return userMapper.userToUserOutputDto(userRepository.save(userToUpdate));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
