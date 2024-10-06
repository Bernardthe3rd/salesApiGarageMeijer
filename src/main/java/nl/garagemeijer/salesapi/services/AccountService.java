package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.accounts.AccountInputDto;
import nl.garagemeijer.salesapi.dtos.accounts.AccountOutputDto;
import nl.garagemeijer.salesapi.mappers.AccountMapper;
import nl.garagemeijer.salesapi.models.Account;
import nl.garagemeijer.salesapi.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public List<AccountOutputDto> getAccounts() {
        return accountMapper.accountsToAccountsOutputDtos(accountRepository.findAll());
    }

    public AccountOutputDto getAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return accountMapper.accountToAccountOutputDto(accountOptional.get());
        } else {
            throw new RuntimeException("Account with id " + id + " not found");
        }
    }

    public AccountOutputDto saveAccount(AccountInputDto account) {
        Account accountToSave = accountMapper.accountInputDtoToAccount(account);
        accountToSave.setCreationDate(LocalDate.now());
        accountToSave.setStatus("NEW");

        return accountMapper.accountToAccountOutputDto(accountRepository.save(accountToSave));
    }

    public AccountOutputDto updateAccount(Long id, AccountInputDto account) {
        Account getAccount = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account with id " + id + " not found"));
        Account accountToUpdate = accountMapper.updateAccountFromAccountInputDto(account, getAccount);
        if (accountToUpdate.getStatus().contains("NEW")) {
            accountToUpdate.setStatus("OPEN");
        }
        return accountMapper.accountToAccountOutputDto(accountRepository.save(accountToUpdate));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public void addUserToAccount(Long id, Long userId) {

    }
}
