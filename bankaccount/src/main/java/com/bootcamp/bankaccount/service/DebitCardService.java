package com.bootcamp.bankaccount.service;

import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.models.dto.debitcard.DebitCardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DebitCardService {



    Mono<DebitCardDto> saveDebitCard(Mono<DebitCardDto> debitDtoMono);

    Mono<AccountDto> findPrincipalAccByDebitCard(String debitNumber);

    Flux<DebitCardDto> getByClientIdNumber(String clientIdNumber);

    public Mono<Void> deleteDebitCard(String id);
    Mono<DebitCardDto> getById(String id);
    Mono<DebitCardDto> setPrincipalAccount(String debitCard, String account);
}
