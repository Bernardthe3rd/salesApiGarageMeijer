package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.users.UserInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private final ProfileMapper profileMapper;

    public UserMapper(ProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }

    public UserOutputDto userToUserOutputDto(User user) {
        var dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setLastLogin(user.getLastLogin());
        dto.setIsActive(user.getIsActive());
        dto.setCreationDate(user.getCreationDate());
//        if (user.getProfile() != null) {
//            var simpleProfileDto = new ProfileOutputDto();
//            simpleProfileDto.setId(user.getProfile().getId());
//            simpleProfileDto.setCreationDate(user.getProfile().getCreationDate());
//            simpleProfileDto.setRole(user.getProfile().getRole());
//            simpleProfileDto.setFirstName(user.getProfile().getFirstName());
//            simpleProfileDto.setLastName(user.getProfile().getLastName());
//            simpleProfileDto.setDateOfBirth(user.getProfile().getDateOfBirth());
//            simpleProfileDto.setStreet(user.getProfile().getStreet());
//            simpleProfileDto.setPostalCode(user.getProfile().getPostalCode());
//            simpleProfileDto.setCity(user.getProfile().getCity());
//            simpleProfileDto.setCountry(user.getProfile().getCountry());
//            simpleProfileDto.setEmail(user.getProfile().getEmail());
//            simpleProfileDto.setPhoneNumber(user.getProfile().getPhoneNumber());
//            simpleProfileDto.setSaleOrders(user.getProfile().getSaleOrders());
//            simpleProfileDto.setPurchaseOrders(user.getProfile().getPurchaseOrders());
//            dto.setProfile(simpleProfileDto);
//        }
        if (user.getProfile() != null) {
            dto.setProfile(profileMapper.profileToProfileOutputDto(user.getProfile()));
        }
        return dto;
    }

    public User updateUserFromUserInputDto(UserInputDto dto, User user) {
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        return user;
    }

    public User userInputDtoToUser(UserInputDto userInputDto) {
        var user = new User();
        return updateUserFromUserInputDto(userInputDto, user);
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
