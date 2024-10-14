package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.users.UserInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.models.User;
import nl.garagemeijer.salesapi.repositories.UserRepository;
import nl.garagemeijer.salesapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        List<UserOutputDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDto> getUserById(@PathVariable Long id) {
        UserOutputDto selectedUser = userService.getUser(id);
        return ResponseEntity.ok(selectedUser);
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto user) {
        UserOutputDto createdUser = userService.saveUser(user);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutputDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserInputDto user) {
        UserOutputDto updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
