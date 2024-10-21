package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.profiles.ProfileInputDto;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.ProfileMapper;
import nl.garagemeijer.salesapi.models.Profile;
import nl.garagemeijer.salesapi.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public List<ProfileOutputDto> getProfiles() {
        return profileMapper.profilesToProfilesOutputDtos(profileRepository.findAll());
    }

    public ProfileOutputDto getProfile(Long id) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isPresent()) {
            return profileMapper.profileToProfileOutputDto(optionalProfile.get());
        } else {
            throw new RecordNotFoundException("Profile with id " + id + " not found");
        }
    }

    public ProfileOutputDto saveProfile(ProfileInputDto profile) {
        Profile profileToSave = profileMapper.profileInputDtoToProfile(profile);
        profileToSave.setCreationDate(LocalDate.now());
        return profileMapper.profileToProfileOutputDto(profileRepository.save(profileToSave));
    }

    public ProfileOutputDto updateProfile(Long id, ProfileInputDto profile) {
        Profile getProfile = profileRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Profile with id " + id + " not found"));
        Profile profileToUpdate = profileMapper.updateProfileFromProfileInputDto(profile, getProfile);
        return profileMapper.profileToProfileOutputDto(profileRepository.save(profileToUpdate));
    }

    public void deleteProfile(Long id) {
        if (profileRepository.findById(id).isEmpty()) {
            throw new RecordNotFoundException("Profile with id " + id + " not found");
        }
        profileRepository.deleteById(id);
    }

}