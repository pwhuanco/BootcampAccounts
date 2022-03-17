package com.bootcamp.bankaccount.service.Impl;

import com.bootcamp.bankaccount.controller.AccountController;
import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.bean.Client;
import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.models.dto.ClientDto;
import com.bootcamp.bankaccount.repository.AccountRepository;
import com.bootcamp.bankaccount.service.AccountService;
import com.bootcamp.bankaccount.util.AppUtils;
import com.bootcamp.bankaccount.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@AllArgsConstructor @NoArgsConstructor @Builder
@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RestTemplate restTemplate;
    public Flux<AccountDto> getAccounts() {
        return accountRepository.findAll().map(AppUtils::entityToDto);
    }


    @Value("${microservice-clients.uri}")
    private String urlClients;
    @Value("${apiclient.uri}")
    private String urlApigateway;

    @Override
    public Mono<AccountDto> getAccountById(String id) {
        return accountRepository.findById(id).map(AppUtils::entityToDto);
    }

    public Mono<AccountDto> saveAccount(AccountDto accountDto) {
        ClientDto client = obtainClient(accountDto.getClientIdNumber());
        //obtainAccountsClient();
        //return accountRepository.save(accountDto);

        return Mono.just(accountDto).map(AppUtils::dtoToEntity)
                .flatMap(accountRepository::insert)
                .map(AppUtils::entityToDto);
    }
    /*private AccountDto obtainAccountsClient(Account account, ClientDto client) {
        Flux<Account> acc = accountRepository.findByClientIdNumber(account.getClientIdNumber());
        int countAccountS = 0;
        int countAccountC = 0;
        int countAccount = 0;

        acc.flatMap((ac) ->{
            int rsp = 0;
                if(Constants.TIPO_CLIENTE_PERSONA.equalsIgnoreCase(client.getClientType().getId())
                    && Constants.TIPO_CUENTA_AHORRO.equalsIgnoreCase(ac.getAccountType())){
                    rsp = 1;
                } else{
                    rsp = 0;
                }
                return null;
        });

        if(countAccount > 1 && countAccountC > 1){

        }
        return null;
    }*/
    private ClientDto obtainClient(String clientId) {
        ClientDto clientDto = restTemplate.getForObject(urlApigateway + urlClients +"findClientCredit/"+ clientId, ClientDto.class);
        LOGGER.debug("clientDto:" + clientDto.getClientNumber());
        return clientDto;
    }

    public Mono<AccountDto> updateAccount(Mono<AccountDto> AccountDtoMono, String id) {
        return accountRepository.findById(id)
                .flatMap(p -> AccountDtoMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(accountRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteAccount(String id) {
        return accountRepository.deleteById(id);
    }

    @Override
    public Flux<Account> validateClientIdNumber(String customerIdentityNumber) {
        return accountRepository.findByClientIdNumber(customerIdentityNumber)
                .switchIfEmpty(Mono.just(Account.builder()
                        .clientIdNumber(null).build()));
    }

    @Override
    public Flux<Account> findByClientIdNumber(String clientIdNumber) {
        return accountRepository.findByClientIdNumber(clientIdNumber);
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
