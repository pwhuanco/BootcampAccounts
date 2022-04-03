package com.bootcamp.bankaccount.repository;

import com.bootcamp.bankaccount.handlers.bean.Account;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

  Mono<Account> findByAccountNumber(String accountNumber);
  Flux<Account> findByClientIdNumber(String clientIdNumber);
}
