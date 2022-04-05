package com.bootcamp.bankaccount.controller.debitcard;

import com.bootcamp.bankaccount.controller.AccountController;
import com.bootcamp.bankaccount.models.bean.debitcard.DebitCard;
import com.bootcamp.bankaccount.models.dto.debitcard.DebitCardDto;
import com.bootcamp.bankaccount.service.DebitCardService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/debitcard")
public class DebitCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private DebitCardService debitCardService;

    @PostMapping()
    public Mono<DebitCardDto> saveDebitCard(@RequestBody Mono<DebitCardDto> debitCardMono){
        return debitCardService.saveDebitCard(debitCardMono);
    }
    @PutMapping("/principal/{card}/{account}")
    public Mono<DebitCardDto> setPrincipalAccount(@PathVariable String card, @PathVariable String account){

        return debitCardService.setPrincipalAccount(card, account);
    }
}
