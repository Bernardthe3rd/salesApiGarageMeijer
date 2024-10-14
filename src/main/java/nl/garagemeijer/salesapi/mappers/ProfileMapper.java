package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.profiles.ProfileInputDto;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileOutputDto;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.enums.Role;
import nl.garagemeijer.salesapi.models.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileMapper {

    private final PurchaseMapper purchaseMapper;

    public ProfileMapper(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    public ProfileOutputDto profileToProfileOutputDto(Profile profile) {
        var dto = new ProfileOutputDto();

        dto.setId(profile.getId());
        dto.setCreationDate(profile.getCreationDate());
        dto.setRole(profile.getRole());
        dto.setFirstName(profile.getFirstName());
        dto.setLastName(profile.getLastName());
        dto.setDateOfBirth(profile.getDateOfBirth());
        dto.setStreet(profile.getStreet());
        dto.setPostalCode(profile.getPostalCode());
        dto.setCity(profile.getCity());
        dto.setCountry(profile.getCountry());
        dto.setEmail(profile.getEmail());
        dto.setPhoneNumber(profile.getPhoneNumber());
        if (profile.getUser() != null) {
            var simpleUserDto = new UserOutputDto();
            simpleUserDto.setId(profile.getUser().getId());
            simpleUserDto.setIsActive(profile.getUser().getIsActive());
            simpleUserDto.setLastLogin(profile.getUser().getLastLogin());
            simpleUserDto.setCreationDate(profile.getUser().getCreationDate());
            dto.setUser(simpleUserDto);
        }
        if (profile.getRole() != null) {
            if (profile.getRole().equals(Role.ADMIN)) {
                dto.setPurchaseOrderNumbers(profile.getPurchaseOrderNumbers());
            }
            if (profile.getRole().equals(Role.SELLER)) {
                dto.setSaleOrders(profile.getSaleOrderNumbers());
            }
        }

        return dto;
    }

    public Profile updateProfileFromProfileInputDto(ProfileInputDto profileInputDto, Profile profile) {
        profile.setRole(profileInputDto.getRole());
        profile.setFirstName(profileInputDto.getFirstName());
        profile.setLastName(profileInputDto.getLastName());
        profile.setDateOfBirth(profileInputDto.getDateOfBirth());
        profile.setStreet(profileInputDto.getStreet());
        profile.setPostalCode(profileInputDto.getPostalCode());
        profile.setCity(profileInputDto.getCity());
        profile.setCountry(profileInputDto.getCountry());
        profile.setEmail(profileInputDto.getEmail());
        profile.setPhoneNumber(profileInputDto.getPhoneNumber());

        return profile;
    }

    public Profile profileInputDtoToProfile(ProfileInputDto profileInputDto) {
        var profile = new Profile();
        return updateProfileFromProfileInputDto(profileInputDto, profile);
    }

    public List<ProfileOutputDto> profilesToProfilesOutputDtos(List<Profile> profiles) {
        List<ProfileOutputDto> profileOutputDtos = new ArrayList<>();
        for (Profile profile : profiles) {
            ProfileOutputDto profileDto = profileToProfileOutputDto(profile);
            profileOutputDtos.add(profileDto);
        }
        return profileOutputDtos;
    }
}
