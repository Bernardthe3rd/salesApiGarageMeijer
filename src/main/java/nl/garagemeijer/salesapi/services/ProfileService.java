package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.ids.IdInputDto;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileInputDto;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.ProfileMapper;
import nl.garagemeijer.salesapi.models.Profile;
import nl.garagemeijer.salesapi.models.User;
import nl.garagemeijer.salesapi.repositories.ProfileRepository;
import nl.garagemeijer.salesapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.userRepository = userRepository;
    }

    public List<ProfileOutputDto> getProfiles() {
        return profileMapper.profilesToProfilesOutputDtos(profileRepository.findAll());
    }

    public ProfileOutputDto getProfile(Long id) {
        Optional<Profile> accountOptional = profileRepository.findById(id);
        if (accountOptional.isPresent()) {
            return profileMapper.profileToProfileOutputDto(accountOptional.get());
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
        profileRepository.deleteById(id);
    }

    public ProfileOutputDto assignUserToProfile(Long id, IdInputDto userId) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(userId.getId());
        if (optionalProfile.isPresent() && optionalUser.isPresent()) {
            Profile profile = optionalProfile.get();
            User user = optionalUser.get();
            user.setProfile(profile);
            profile.setUser(user);
            return profileMapper.profileToProfileOutputDto(profileRepository.save(profile));
        } else if (optionalProfile.isEmpty()) {
            throw new RecordNotFoundException("Profile with id " + id + " not found");
        } else {
            throw new RecordNotFoundException("User with id " + userId.getId() + " not found");
        }
    }
}