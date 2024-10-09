package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.users.UserInputDto;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserOutputDto userToUserOutputDto(User user) {
        var dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setLastLogin(user.getLastLogin());
        dto.setIsActive(user.getIsActive());
        dto.setCreationDate(user.getCreationDate());

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
