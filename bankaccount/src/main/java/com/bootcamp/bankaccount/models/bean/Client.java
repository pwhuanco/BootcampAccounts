package com.bootcamp.bankaccount.models.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "client")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Client {
    @Id
    private String id;
    private String name;

    private String clientIdType;

    private String clientIdNumber;

    private String email;
    private String phone;
    private String address;

}
