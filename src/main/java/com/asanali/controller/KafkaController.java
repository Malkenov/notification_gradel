package com.asanali.controller;

<<<<<<< Updated upstream
import com.asanali.kafka.dto.KafkaPurchasedDto;
import com.asanali.kafka.producer.KafkaPurchasedProducer;
=======
import com.asanali.Service.KafkaCancelledProducer;
import com.asanali.Service.KafkaPurchasedProducer;
>>>>>>> Stashed changes
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
