package com.bootcamp.bankaccount;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static com.bootcamp.bankaccount.util.Constants.API_CLIENT_URL;


@EnableEurekaClient
@SpringBootApplication
public class BankAccountApplication {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(API_CLIENT_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }

}
