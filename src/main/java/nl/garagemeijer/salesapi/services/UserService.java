package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.models.User;
import nl.garagemeijer.salesapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User saveUser(User user) {
        user.setCreationDate(LocalDate.now());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userToUpdate.setUsername(user.getUsername());
        return userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
