package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.accounts.AccountInputDto;
import nl.garagemeijer.salesapi.dtos.accounts.AccountOutputDto;
import nl.garagemeijer.salesapi.models.Account;
import nl.garagemeijer.salesapi.repositories.AccountRepository;
import nl.garagemeijer.salesapi.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public ResponseEntity<List<AccountOutputDto>> getAllAccounts() {
        List<AccountOutputDto> accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountOutputDto> getAccountById(@PathVariable Long id) {
        AccountOutputDto selectedAccount = accountService.getAccount(id);
        return ResponseEntity.ok(selectedAccount);
    }

    @PostMapping
    public ResponseEntity<AccountOutputDto> createAccount(@Valid @RequestBody AccountInputDto account) {
        AccountOutputDto createdAccount = accountService.saveAccount(account);
        URI location = URI.create("/api/accounts/" + createdAccount.getId());
        return ResponseEntity.created(location).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountOutputDto> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountInputDto account) {
        AccountOutputDto updatedAccount = accountService.updateAccount(id, account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

//    postmapping voor addUserToAccount ook in postman
}
