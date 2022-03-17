package com.bootcamp.bankaccount.models.bean;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "clientType")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientType {

    @Id
    private String id;
    @Indexed(unique = true)
    private String code;
    @Indexed(unique = true)
    private String name;
}
