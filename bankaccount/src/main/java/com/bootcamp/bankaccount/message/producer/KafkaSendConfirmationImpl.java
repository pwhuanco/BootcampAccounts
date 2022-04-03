package com.bootcamp.bankaccount.message.producer;

import com.bootcamp.bankaccount.models.dto.yanki.PaymentConfirmation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Log4j2
@Service
public class KafkaSendConfirmationImpl implements KafkaSendConfirmation {

    @Autowired
    KafkaTemplate<String, PaymentConfirmation> kafkaTemplate;

    @Override
    public void sendMessage(String topic, PaymentConfirmation paymentConfirmation) {
        ListenableFuture<SendResult<String, PaymentConfirmation>> future = kafkaTemplate.send(topic, paymentConfirmation);
        future.addCallback(new ListenableFutureCallback<SendResult<String, PaymentConfirmation>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("fail topic submission" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, PaymentConfirmation> result) {
                log.info("successful topic submission");
            }
        });

    }
}
