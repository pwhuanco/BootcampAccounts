package com.bootcamp.bankaccount.models.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize
public class CreditCardDto {

    private String id;
    private String accountNumber;
    private Double creditLine;
    private Double balance;
    private String idClient;
}
