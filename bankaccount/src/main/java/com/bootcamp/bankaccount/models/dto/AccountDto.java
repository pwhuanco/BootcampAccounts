package com.bootcamp.bankaccount.models.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String accountNumber;

    private double balance;

    private String currency;

    private String accountType;

    private String canBeDeposit;

    //private LocalDateTime operationDate = LocalDateTime.now(ZoneId.of("America/Lima"));

    private ClientCommand client;

    private int movementPerMonth;

    private int maxLimitMovementPerMonth;

    private String clientIdNumber;

    private Double minimumOpeningAmount;

    private Double minimumDailyAverageAmountEachMonth;

    private int maxLimitTransaction;



}
