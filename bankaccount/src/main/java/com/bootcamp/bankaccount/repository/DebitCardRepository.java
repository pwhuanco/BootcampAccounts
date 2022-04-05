package com.bootcamp.bankaccount.repository;

import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.bean.debitcard.DebitCard;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.redis.core.RedisHash;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public interface DebitCardRepository extends ReactiveMongoRepository<DebitCard, String> {

  Mono<DebitCard> findByPrincipalAccount(String debitNumber);
  Mono<DebitCard> findByNumber(String debitNumber);
  Flux<DebitCard> getByClientIdNumber(String clientIdNumber);
}
