package com.bootcamp.bankaccount.handlers.bean;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "yanki-payments")
public class YankiTransaction {
    @Id
    private String idPayment;

    private Double amount;
    private String account;
    private String operationNumber;
    private String sendTo;

    public YankiTransaction(Double amount, String account, String operationNumber, String sendTo) {
        this.amount = amount;
        this.account = account;
        this.operationNumber = operationNumber;
        this.sendTo = sendTo;
    }

    @Builder.Default
    private LocalDate date = LocalDate.now();
}
