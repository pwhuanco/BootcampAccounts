package com.bootcamp.bankaccount.service;

import com.bootcamp.bankaccount.models.bean.yanki.YankiTransaction;
import com.bootcamp.bankaccount.models.dto.yanki.PaymentRequest;
import reactor.core.publisher.Mono;

public interface YankiTransactionService {
    Mono<YankiTransaction> saveTransaction(PaymentRequest paymentRequest);
}
