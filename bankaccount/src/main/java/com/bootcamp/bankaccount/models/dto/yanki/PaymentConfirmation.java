package com.bootcamp.bankaccount.models.dto.yanki;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentConfirmation {
    private String operationNumber;
    private String sendTo;
    private Boolean confirmation;
    private Double amount;
}
