package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.models.Account;
import nl.garagemeijer.salesapi.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new RuntimeException("Account with id " + id + " not found");
        }
    }

    public Account saveAccount(Account account) {
        account.setCreationDate(LocalDate.now());
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account account) {
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account with id " + id + " not found"));
        accountToUpdate.setFirstName(account.getFirstName());
        return accountRepository.save(accountToUpdate);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public void addUserToAccount(Long id, Long userId) {

    }
}
