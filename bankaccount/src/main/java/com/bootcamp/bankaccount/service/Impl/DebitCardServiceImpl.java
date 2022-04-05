package com.bootcamp.bankaccount.service.Impl;

import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.models.dto.ClientDto;
import com.bootcamp.bankaccount.models.dto.debitcard.DebitCardDto;
import com.bootcamp.bankaccount.repository.AccountRepository;
import com.bootcamp.bankaccount.repository.DebitCardRepository;
import com.bootcamp.bankaccount.service.DebitCardService;
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
public class DebitCardServiceImpl implements DebitCardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebitCardServiceImpl.class);

    @Autowired
    private DebitCardRepository debitRepository;
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

    //@Cacheable(value = "accountsCache")
    public Flux<DebitCardDto> getByClientIdNumber(String clientIdNumber) {
        return debitRepository.getByClientIdNumber(clientIdNumber).map(AppUtils::entityDebitToDto);
    }

    @Override
    public Mono<DebitCardDto> getById(String id) {
        return debitRepository.findById(id).map(AppUtils::entityDebitToDto);
    }

    @Override
    public Mono<DebitCardDto> saveDebitCard(Mono<DebitCardDto> debitDtoMono) {

        return debitDtoMono.flatMap(d ->debitRepository.save(AppUtils.dtoToEntityDebit(d)).map(AppUtils::entityDebitToDto))
                ;
        /*if (StringUtils.equals(accountDto.getAccountType(), Constants.VIP)
                || StringUtils.equals(accountDto.getAccountType(), Constants.PYME)) {
            if (!Objects.isNull(restTemplate.getForObject(
                    urlApigateway + urlCreditCard + accountDto.getAccountType(),
                    CreditCardDto.class))) {
                if (accountDto.getBalance() >= accountDto.getMinimumOpeningAmount()) {
                    return Mono.just(accountDto).map(AppUtils::dtoToEntity)
                            .flatMap(debitRepository::insert)
                            .map(AppUtils::entityToDto);
                }
                else{
                    LOGGER.debug(Constants.MSJ_MONTO_MENOR_APERTURA);
                    return null;
                }
            }
            else{
                LOGGER.debug(Constants.MSJ_REQUIERE_TC);
                return null;
            }

        } else {
            // NO ES CUENTA VIP, NO ES CUENTA PYME
            if (accountDto.getBalance() >= accountDto.getMinimumOpeningAmount()) {
                return Mono.just(accountDto).map(AppUtils::dtoToEntity)
                        .flatMap(debitRepository::insert)
                        .map(AppUtils::entityToDto);
            }
            else{
                LOGGER.debug("CLiente no se registró porque el monto inicial no es mayor a el mínimo de apertura");
                return null;
            }
        }*/
    }

    @Override
    public Mono<AccountDto> findPrincipalAccByDebitCard(String debitNumber) {
        return debitRepository.findByNumber(debitNumber)
                .flatMap(card -> accountRepository.findByAccountNumber(card.getPrincipalAccount()))
                .map(AppUtils::entityToDto);
    }

    private ClientDto obtainClient(String clientId) {
        ClientDto clientDto = restTemplate.getForObject(urlApigateway
                + urlClients + "findClientCredit/"
                + clientId, ClientDto.class);
        LOGGER.debug("clientDto:" + clientDto.getClientIdNumber());
        return clientDto;
    }

    @Override
    public Mono<DebitCardDto> setPrincipalAccount(String debitCard, String account) {
        return debitRepository.findByNumber(debitCard)
                .doOnNext(card -> card.setPrincipalAccount(account))
                .flatMap(debitRepository::save)
                .map(AppUtils::entityDebitToDto);
    }

    public Mono<Void> deleteDebitCard(String id) {
        return debitRepository.deleteById(id);
    }

}
