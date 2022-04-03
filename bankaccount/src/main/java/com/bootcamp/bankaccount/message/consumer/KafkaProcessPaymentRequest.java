package com.bootcamp.bankaccount.message.consumer;

import com.bootcamp.bankaccount.handlers.bean.YankiTransaction;
import com.bootcamp.bankaccount.message.producer.KafkaSendConfirmation;
import com.bootcamp.bankaccount.models.dto.yanki.PaymentConfirmation;
import com.bootcamp.bankaccount.models.dto.yanki.PaymentRequest;
import com.bootcamp.bankaccount.service.YankiTransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Stream;

@Log4j2
@Component
public class KafkaProcessPaymentRequest {

    @Autowired
    private YankiTransactionService yankiTransactionService;

    @Autowired
    private KafkaSendConfirmation kafkaSendConfirmation;

    @KafkaListener(id = "hi", topics = "paymentRequest", groupId = "groupId", containerFactory = "factory")
    public YankiTransaction listenSendTopic (PaymentRequest process) {
        log.info("processing payment");
        YankiTransaction result = yankiTransactionService.saveTransaction(process).block();
        if(result!=null){
            PaymentConfirmation payCon = new PaymentConfirmation();
            payCon.setOperationNumber(result.getOperationNumber());
            payCon.setSendTo(result.getSendTo());
            payCon.setConfirmation(true);
            payCon.setAmount(result.getAmount());
            kafkaSendConfirmation.sendMessage("payment-confirmation",payCon);
        }
        return result;
    }
}
