package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileInputDto;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileOutputDto;
import nl.garagemeijer.salesapi.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileOutputDto>> getAllAccounts() {
        List<ProfileOutputDto> profiles = profileService.getProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileOutputDto> getAccountById(@PathVariable Long id) {
        ProfileOutputDto selectedProfile = profileService.getProfile(id);
        return ResponseEntity.ok(selectedProfile);
    }

    @PostMapping
    public ResponseEntity<ProfileOutputDto> createAccount(@Valid @RequestBody ProfileInputDto account) {
        ProfileOutputDto createdAccount = profileService.saveProfile(account);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAccount.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileOutputDto> updateAccount(@PathVariable Long id, @Valid @RequestBody ProfileInputDto account) {
        ProfileOutputDto updatedProfile = profileService.updateProfile(id, account);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

}
