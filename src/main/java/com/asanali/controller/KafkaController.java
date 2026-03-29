package com.asanali.controller;

import com.asanali.kafka.dto.KafkaPurchasedDto;
import com.asanali.kafka.producer.KafkaProducerService;
import com.asanali.kafka.producer.KafkaPurchasedProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class KafkaController {

    private final KafkaPurchasedProducer kafkaPurchasedProducer;

    @PostMapping("/ticket-purchased")
    public String sendTicket(@RequestBody KafkaPurchasedDto dto){
        kafkaPurchasedProducer.send(dto);
        return "Сообщение отправлно!";
    }
}
