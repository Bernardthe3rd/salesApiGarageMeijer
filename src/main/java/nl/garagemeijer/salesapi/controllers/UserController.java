package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.ids.IdInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserChangePasswordInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @PutMapping("/{id}/password")
    public ResponseEntity<UserOutputDto> updatePassword(@PathVariable Long id, @Valid @RequestBody UserChangePasswordInputDto passwordInputDto) {
        UserOutputDto updatedUser = userService.updatePassword(id, passwordInputDto);
        return ResponseEntity.ok(updatedUser);
    }


    @PutMapping("/{id}/profile")
    public ResponseEntity<UserOutputDto> addProfileToUser(@PathVariable Long id, @Valid @RequestBody IdInputDto profileId) {
        UserOutputDto updatedUser = userService.assignProfileToUser(id, profileId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
