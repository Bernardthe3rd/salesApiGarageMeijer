package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.ids.IdInputDto;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileInputDto;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileOutputDto;
import nl.garagemeijer.salesapi.repositories.ProfileRepository;
import nl.garagemeijer.salesapi.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileService profileService, ProfileRepository profileRepository) {
        this.profileService = profileService;
        this.profileRepository = profileRepository;
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
        URI location = URI.create("/api/profiles/" + createdAccount.getId());
        return ResponseEntity.created(location).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileOutputDto> updateAccount(@PathVariable Long id, @Valid @RequestBody ProfileInputDto account) {
        ProfileOutputDto updatedProfile = profileService.updateProfile(id, account);
        return ResponseEntity.ok(updatedProfile);
    }

    @PutMapping("/{id}/user")
    public ResponseEntity<ProfileOutputDto> addUserToProfile(@PathVariable Long id, @Valid @RequestBody IdInputDto profileId) {
        ProfileOutputDto updatedProfile = profileService.assignUserToProfile(id, profileId);
        return ResponseEntity.ok(updatedProfile);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

//    put mapping voor addUserToAccount ook in postman
}
