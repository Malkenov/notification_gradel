package com.asanali.kafka.producer;


import com.asanali.kafka.dto.KafkaPurchasedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPurchasedProducer {

    private final KafkaTemplate<String, KafkaPurchasedDto> kafkaTemplate;
               // KafkaTemplate - инструмент от Spring для отправки сообщений в Kafka

    public void send(KafkaPurchasedDto dto){
        kafkaTemplate.send("ticket-purchased", dto);
        System.out.println("Отправлено: " + dto);
    }
}
