package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.models.User;
import nl.garagemeijer.salesapi.repositories.UserRepository;
import nl.garagemeijer.salesapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User selectedUser = userService.getUser(id);
        return ResponseEntity.ok(selectedUser);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.saveUser(user);
        URI location = URI.create("/api/users/" + createdUser.getId());
        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
