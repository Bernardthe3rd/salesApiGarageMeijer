package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.accounts.AccountInputDto;
import nl.garagemeijer.salesapi.dtos.accounts.AccountOutputDto;
import nl.garagemeijer.salesapi.models.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper {

    public AccountOutputDto accountToAccountOutputDto(Account account) {
        var dto = new AccountOutputDto();

        dto.setId(account.getId());
        dto.setAccountType(account.getAccountType());
        dto.setCreationDate(account.getCreationDate());
        dto.setStatus(account.getStatus());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setDateOfBirth(account.getDateOfBirth());
        dto.setStreet(account.getStreet());
        dto.setPostalCode(account.getPostalCode());
        dto.setCity(account.getCity());
        dto.setCountry(account.getCountry());
        dto.setEmail(account.getEmail());
        dto.setPhoneNumber(account.getPhoneNumber());

        return dto;
    }

    public Account updateAccountFromAccountInputDto(AccountInputDto accountInputDto, Account account) {
        account.setAccountType(accountInputDto.getAccountType());
        account.setStatus(accountInputDto.getStatus());
        account.setFirstName(accountInputDto.getFirstName());
        account.setLastName(accountInputDto.getLastName());
        account.setDateOfBirth(accountInputDto.getDateOfBirth());
        account.setStreet(accountInputDto.getStreet());
        account.setPostalCode(accountInputDto.getPostalCode());
        account.setCity(accountInputDto.getCity());
        account.setCountry(accountInputDto.getCountry());
        account.setEmail(accountInputDto.getEmail());
        account.setPhoneNumber(accountInputDto.getPhoneNumber());

        return account;
    }

    public Account accountInputDtoToAccount(AccountInputDto accountInputDto) {
        var account = new Account();
        return updateAccountFromAccountInputDto(accountInputDto, account);
    }

    public List<AccountOutputDto> accountsToAccountsOutputDtos(List<Account> accounts) {
        List<AccountOutputDto> accountOutputDtos = new ArrayList<>();
        for (Account account : accounts) {
            AccountOutputDto accountDto = accountToAccountOutputDto(account);
            accountOutputDtos.add(accountDto);
        }
        return accountOutputDtos;
    }
}
