package com.bootcamp.bankaccount.repository;

import com.bootcamp.bankaccount.models.bean.yanki.YankiTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface YankiTransactionRepository extends ReactiveMongoRepository<YankiTransaction, String> {
}
