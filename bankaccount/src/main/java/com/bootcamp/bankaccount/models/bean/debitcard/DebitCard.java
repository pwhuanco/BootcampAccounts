package com.bootcamp.bankaccount.models.bean.debitcard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("debitCard")
public class DebitCard {

    @Id
    private String id;
    private String number;
    private String clientIdNumber;
    private String clientId;
    /**
     * principalAccount: cuenta principal asociadad a la tarjeta
     */
    private String principalAccount;
}
