package com.bootcamp.bankaccount.models.dto;

import com.bootcamp.bankaccount.models.bean.ClientType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {

    private String id;
    private String name;

    private String clientIdNumber;

    private String email;
    private String phone;
    private String address;

    private ClientType clientType;
}
