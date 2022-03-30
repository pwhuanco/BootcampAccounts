package com.bootcamp.bankaccount.service;

import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.dto.AccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Flux<AccountDto> getAccounts();

    Mono<AccountDto> getAccountById(String id);

    Mono<AccountDto> saveAccount(AccountDto accountDtoMono);

    Mono<AccountDto> updateAccount(Mono<AccountDto> accountDtoMono, String id);

    Mono<Void> deleteAccount(String id);

    Flux<Account> validateClientIdNumber(String clientIdNumber);

    Flux<Account> findByClientIdNumber(String clientIdNumber);

    Mono<Account> findByAccountNumber(String accountNumber);

    Flux<Account> getAccountByClientId(String clientId);
}
