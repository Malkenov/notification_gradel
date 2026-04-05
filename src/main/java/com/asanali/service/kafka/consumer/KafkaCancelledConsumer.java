package com.asanali.service.kafka.consumer;


import com.asanali.service.kafka.dto.KafkaCancelledDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaCancelledConsumer {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "ticket-cancelled", groupId = "notification-group")
    public void listen(KafkaCancelledDto dto) {
        System.out.println("Получено сообщение: " + dto);


        log.info("Возврат " + dto.getRefundAmount() + " тенге,перевдено успешно!");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(dto.getUserEmail());
            message.setSubject("Билет отменен: " + dto.getMovieName());
            message.setText(
                    "Привет " + dto.getUserName() + "!\n\n" +
                            "Ваше билет на " + dto.getMovieName() + " был отменен \n" +
                            "Номер билета " + dto.getTicketId() + "\n" +
                            "Сумма возврата " + dto.getRefundAmount() + " тенге\n\n"
            );
            javaMailSender.send(message);
            log.info("Сообщение отправлено на " + dto.getUserEmail());
        } catch (Exception e) {
            log.error("Не удалось отправить сообщение на {}: {}", dto.getUserEmail(), e.getMessage());
        }
    }
}
