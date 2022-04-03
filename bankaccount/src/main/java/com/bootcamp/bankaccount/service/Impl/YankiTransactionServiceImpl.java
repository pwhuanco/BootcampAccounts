package com.bootcamp.bankaccount.service.Impl;

import com.bootcamp.bankaccount.handlers.bean.Account;
import com.bootcamp.bankaccount.handlers.bean.YankiTransaction;
import com.bootcamp.bankaccount.models.dto.yanki.PaymentRequest;
import com.bootcamp.bankaccount.repository.YankiTransactionRepository;
import com.bootcamp.bankaccount.service.AccountService;
import com.bootcamp.bankaccount.service.YankiTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class YankiTransactionServiceImpl implements YankiTransactionService {

    @Autowired
    private YankiTransactionRepository yankiTransactionRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public Mono<YankiTransaction> saveTransaction(PaymentRequest paymentRequest) {
        return updateBalanceOfAccount(paymentRequest)
                .filter(account -> account.getAccountNumber()!=null)
                .then(yankiTransactionRepository.save(new YankiTransaction(paymentRequest.getAmount(),paymentRequest.getAssociatedAccount(),
                        paymentRequest.getOperationNumber(),paymentRequest.getSendTo())));
    }

    private Mono<Account> updateBalanceOfAccount (PaymentRequest paymentRequest) {
        return accountService.findByAccountNumber(paymentRequest.getAssociatedAccount())
                .filter(balance -> balance.getBalance() >= paymentRequest.getAmount() && balance.getAccountNumber()!=null && balance.getAccountNumber().equals(paymentRequest.getAssociatedAccount()))
                .map(account -> {
                    account.setBalance(account.getBalance() - paymentRequest.getAmount());
                    return account;
                })
                .flatMap(accountService::nativeAccountUpdate);
    }

}
