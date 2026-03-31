package com.asanali.kafka.consumer;

import com.asanali.kafka.dto.KafkaCancelledDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaRefundConsumer {



    @KafkaListener(topics = "ticket-cancelled", groupId = "payment-group")
    public void listen(KafkaCancelledDto dto){
        System.out.println("Обработка возврата билета номером: " + dto.getTicketId());

        System.out.println("Возврат " + dto.getRefundAmount() + " тенге" +
                "для пользователя " + dto.getUserName() + "проведена успешно");
    }

}
