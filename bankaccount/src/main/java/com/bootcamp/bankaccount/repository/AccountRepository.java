package com.bootcamp.bankaccount.repository;

import com.bootcamp.bankaccount.models.bean.Account;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.redis.core.RedisHash;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RedisHash
@Configuration
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

  Mono<Account> findByAccountNumber(String accountNumber);
  Flux<Account> findByClientIdNumber(String clientIdNumber);
}
