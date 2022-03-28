package com.bootcamp.bankaccount.service.Impl;

import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.bean.Client;
import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.models.dto.ClientDto;
import com.bootcamp.bankaccount.models.dto.CreditCardDto;
import com.bootcamp.bankaccount.repository.AccountRepository;
import com.bootcamp.bankaccount.service.AccountService;
import com.bootcamp.bankaccount.util.AppUtils;
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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${microservice-clients.uri}")
    private String urlClients;

    @Value("${microservice-creditcard.uri}")
    private String urlCreditCard;

    @Value("${apiclient.uri}")
    private String urlApigateway;

    public Flux<AccountDto> getAccounts() {
        return accountRepository.findAll().map(AppUtils::entityToDto);
    }

    @Override
    public Mono<AccountDto> getAccountById(String id) {
        return accountRepository.findById(id).map(AppUtils::entityToDto);
    }

    @Override
    public Mono<AccountDto> saveAccount(AccountDto accountDto) {
        if(accountDto.getAccountType()=="VIP"||accountDto.getAccountType()=="PYME"){
            CreditCardDto haveCreditCard = restTemplate.getForObject(urlApigateway + urlCreditCard + accountDto.getAccountType(), CreditCardDto.class);
            if(haveCreditCard!=null){
                if(accountDto.getBalance()>=accountDto.getMinimumOpeningAmount()){
                    ClientDto dto = obtainClient(accountDto.getClientIdNumber());
                    return Mono.just(accountDto).map(AppUtils::dtoToEntity)
                            .flatMap(accountRepository::insert)
                            .map(AppUtils::entityToDto);
                }
                else{
                    LOGGER.debug("Cliente no se registró porque el monto inicial no es mayor a el mínimo de apertura");
                    return null;
                }
            }
            else{
                LOGGER.debug("Cliente no se registró porque según el tipo de producto requiere tener una tarjeta de crédito");
                return null;
            }
        }
        else{
            if(accountDto.getBalance()>=accountDto.getMinimumOpeningAmount()){
                ClientDto dto = obtainClient(accountDto.getClientIdNumber());
                return Mono.just(accountDto).map(AppUtils::dtoToEntity)
                        .flatMap(accountRepository::insert)
                        .map(AppUtils::entityToDto);
            }
            else{
                LOGGER.debug("CLiente no se registró porque el monto inicial no es mayor a el mínimo de apertura");
                return null;
            }
        }
    }

    private ClientDto obtainClient(String clientId) {
        ClientDto clientDto = restTemplate.getForObject(urlApigateway + urlClients + "findClientCredit/" + clientId, ClientDto.class);

        LOGGER.debug("clientDto:" + clientDto.getClientIdNumber());
        return clientDto;
    }

    @Override
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
