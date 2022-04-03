package com.bootcamp.bankaccount.models.dto.yanki;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class PaymentRequest {
    private String associatedAccount;
    private Double amount;
    private String operationNumber;
    private String sendTo;
}
