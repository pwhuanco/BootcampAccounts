package com.bootcamp.bankaccount.message.producer;

import com.bootcamp.bankaccount.models.dto.yanki.PaymentConfirmation;

public interface KafkaSendConfirmation {
    void sendMessage (String topic, PaymentConfirmation paymentConfirmation);
}
