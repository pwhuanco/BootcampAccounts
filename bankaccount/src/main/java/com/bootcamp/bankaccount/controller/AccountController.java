package com.bootcamp.bankaccount.controller;

import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.service.AccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/accounts")
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @CircuitBreaker(name = "getAccountCB", fallbackMethod = "fallbackGetAccount")
    @TimeLimiter(name = "getAccountCB")
    @GetMapping
    public Flux<AccountDto> getAccount() {
        LOGGER.debug("Getting Accounts!");
        return accountService.getAccounts();
    }

    @GetMapping("/{id}")
    public Mono<AccountDto> getAccount(@PathVariable String id) {
        LOGGER.debug("Getting a account!");
        return accountService.getAccountById(id);
    }

    @GetMapping("/findByClientId/{clientId}")
    public Flux<Account> getAccountByClientId(@PathVariable String clientId) {
        LOGGER.info("Getting a ClientId!");
        return accountService.getAccountByClientId(clientId);
    }

    @CircuitBreaker(name = "saveAccountCB",
            fallbackMethod = "fallbackSaveAccount")
    @TimeLimiter(name = "saveAccountCB")
    @PostMapping()
    public Mono<AccountDto> saveAccount(@RequestBody AccountDto accountDtoMono) {
        LOGGER.debug("Saving accounts!");
        return accountService.saveAccount(accountDtoMono);

    }

    @PutMapping("/{id}")
    public Mono<AccountDto> updateAccount(
            @RequestBody Mono<AccountDto> accountDtoMono,
            @PathVariable String id) {
        LOGGER.debug("Updating accounts!");
        return accountService.updateAccount(accountDtoMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAccount(@PathVariable String id) {
        LOGGER.debug("Deleting accounts!");
        return accountService.deleteAccount(id);
    }

    private Mono<AccountDto> fallbackSaveAccount(AccountDto accountDto, RuntimeException re ) {
        LOGGER.debug("Respondiendo con fallbackSaveAccount");
        return Mono.just(new AccountDto());
    }

    private Flux<AccountDto> fallbackGetAccount(RuntimeException re ) {
        LOGGER.debug("Respondiendo con fallbackGetAccount");
        return Flux.just(new AccountDto());
    }
    private Flux<AccountDto> fallbackGetAccountTime(RuntimeException re ) {
        LOGGER.debug("Respondiendo con fallbackGetAccountTime");
        return Flux.just(new AccountDto());
    }

}
