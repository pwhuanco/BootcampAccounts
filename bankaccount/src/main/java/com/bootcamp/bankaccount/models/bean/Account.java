package com.bootcamp.bankaccount.models.bean;

import com.bootcamp.bankaccount.models.dto.ClientCommand;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "account")
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    private String id;

    private String accountNumber;

    private Double balance;

    private String currency;

    private String accountType;

    private String canBeDeposit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationDate = LocalDateTime.now();

    private ClientCommand client;

    private int movementPerMonth;

    private int maxLimitMovementPerMonth;

    private String clientIdNumber;

    private Double minimumOpeningAmount;

    private Double minimumDailyAverageAmountEachMonth;

    private int maxLimitTransaction;

    /**
     * associatedCard: tarjeta asociada a la cuenta
     */
    private String associatedCard;

    public Account(String id, String accountNumber, double balance, String currency, String accountType, String canBeDeposit, ClientCommand client, int movementPerMonth, int maxLimitMovementPerMonth, String clientIdNumber, Double minimumOpeningAmount, Double minimumDailyAverageAmountEachMonth, int maxLimitTransaction) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.accountType = accountType;
        this.canBeDeposit = canBeDeposit;
        this.client = client;
        this.movementPerMonth = movementPerMonth;
        this.maxLimitMovementPerMonth = maxLimitMovementPerMonth;
        this.clientIdNumber = clientIdNumber;
        this.minimumOpeningAmount = minimumOpeningAmount;
        this.minimumDailyAverageAmountEachMonth = minimumDailyAverageAmountEachMonth;
        this.maxLimitTransaction = maxLimitTransaction;
    }
}
