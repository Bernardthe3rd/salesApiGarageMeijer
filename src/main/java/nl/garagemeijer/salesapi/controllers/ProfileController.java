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
    public ResponseEntity<List<ProfileOutputDto>> getAllProfiles() {
        List<ProfileOutputDto> profiles = profileService.getProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileOutputDto> getProfileById(@PathVariable Long id) {
        ProfileOutputDto selectedProfile = profileService.getProfile(id);
        return ResponseEntity.ok(selectedProfile);
    }

    @PostMapping
    public ResponseEntity<ProfileOutputDto> createProfile(@Valid @RequestBody ProfileInputDto profileInput) {
        ProfileOutputDto createdProfile = profileService.saveProfile(profileInput);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProfile.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdProfile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileOutputDto> updateProfile(@PathVariable Long id, @Valid @RequestBody ProfileInputDto profileInput) {
        ProfileOutputDto updatedProfile = profileService.updateProfile(id, profileInput);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

}
