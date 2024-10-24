package nl.garagemeijer.salesapi.services;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.ids.IdInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserChangePasswordInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.enums.Role;
import nl.garagemeijer.salesapi.exceptions.BadRequestException;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.exceptions.UnauthorizedException;
import nl.garagemeijer.salesapi.mappers.UserMapper;
import nl.garagemeijer.salesapi.models.Profile;
import nl.garagemeijer.salesapi.models.User;
import nl.garagemeijer.salesapi.repositories.ProfileRepository;
import nl.garagemeijer.salesapi.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private String getCurrentAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public List<UserOutputDto> getUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("user " + auth.getName());
        System.out.println("role " + auth.getAuthorities());
        return userMapper.usersToUserOutputDtos(userRepository.findAll());
    }

    public UserOutputDto getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return userMapper.userToUserOutputDto(optionalUser.get());
        } else {
            throw new RecordNotFoundException("User with id: " + id + " not found");
        }
    }

    public UserOutputDto saveUser(UserInputDto user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists: " + user.getUsername());
        }

        User userToSave = userMapper.userInputDtoToUser(user);
        userToSave.setCreationDate(LocalDate.now());
        userToSave.setIsActive(true);
        userToSave.setLastLogin(LocalDate.now());

        return userMapper.userToUserOutputDto(userRepository.save(userToSave));
    }

    public UserOutputDto updatePassword(Long id, UserChangePasswordInputDto userPassword) {
        String currentUsername = getCurrentAuthenticatedUsername();
        User getUser = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User with id: " + id + " not found"));
        if (!currentUsername.equals(getUser.getUsername())) {
            throw new UnauthorizedException("You can only change your own password");
        }
        if (!passwordEncoder.matches(userPassword.getOldPassword(), getUser.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }
        User userToUpdate = userMapper.updatePasswordFromPasswordDto(userPassword, getUser);
        userToUpdate.setLastLogin(LocalDate.now());
        if (!userToUpdate.getIsActive()) {
            userToUpdate.setIsActive(true);
        }
        return userMapper.userToUserOutputDto(userRepository.save(userToUpdate));
    }

    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        String currentUsername = getCurrentAuthenticatedUsername();
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User with id: " + id + " not found");
        } else if (currentUsername.equals(optionalUser.get().getUsername())) {
            throw new UnauthorizedException("You can not delete your own user account");
        }
        userRepository.deleteById(id);
    }

    public UserOutputDto assignProfileToUser(Long id, @Valid IdInputDto profileId) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<Profile> optionalProfile = profileRepository.findById(profileId.getId());
        if (optionalUser.isPresent() && optionalProfile.isPresent()) {
            User user = optionalUser.get();
            Profile profile = optionalProfile.get();
            user.setProfile(profile);
            profile.setUser(user);
            return userMapper.userToUserOutputDto(userRepository.save(user));
        } else if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User with id: " + id + " not found");
        } else {
            throw new RecordNotFoundException("Profile with id: " + profileId.getId() + " not found");
        }
    }
}
