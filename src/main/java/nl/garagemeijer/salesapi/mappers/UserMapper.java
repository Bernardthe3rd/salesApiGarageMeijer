package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.users.UserChangePasswordInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private final ProfileMapper profileMapper;
    private final PasswordEncoder passwordEncoder;

    public UserMapper(ProfileMapper profileMapper, PasswordEncoder passwordEncoder) {
        this.profileMapper = profileMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserOutputDto userToUserOutputDto(User user) {
        var dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setLastLogin(user.getLastLogin());
        dto.setIsActive(user.getIsActive());
        dto.setCreationDate(user.getCreationDate());
        if (user.getProfile() != null) {
            dto.setProfile(profileMapper.profileToProfileOutputDto(user.getProfile()));
        }
        return dto;
    }

    public User updatePasswordFromPasswordDto(UserChangePasswordInputDto dto, User user) {
        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(encodedPassword);

        return user;
    }

    public User userInputDtoToUser(UserInputDto userInputDto) {
        var user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        return user;
    }

    public List<UserOutputDto> usersToUserOutputDtos(List<User> users) {
        List<UserOutputDto> userOutputDtos = new ArrayList<>();
        for (User user : users) {
            UserOutputDto userDto = userToUserOutputDto(user);
            userOutputDtos.add(userDto);
        }
        return userOutputDtos;
    }
}
