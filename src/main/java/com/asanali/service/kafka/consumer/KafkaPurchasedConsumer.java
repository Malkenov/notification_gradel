package com.asanali.service.kafka.consumer;


import com.asanali.dto.KafkaPurchasedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPurchasedConsumer {

    private final JavaMailSender mailSender;
    // JavaMailSender - инструмент spring для отправки email

    @KafkaListener(topics = "ticket-purchased", groupId = "notification-group")
    public void listen(KafkaPurchasedDto dto){
        log.info("Сообщение " + dto);


        try {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(dto.getUserEmail());
    message.setSubject("Билет куплен " + dto.getMovieName());
    message.setText(
            "Привет " + dto.getUserName() + "!\n\n" +
            "Вы купили билет на " + dto.getMovieName() + "\n" +
            "Ваш билет " + dto.getTicketId()
    );

    mailSender.send(message);
        log.info("Сообщение отправлена на адрес " + dto.getUserEmail());
    }catch (Exception e) {
            log.error("Не удалось отправить сообщение на {}: {}", dto.getUserEmail(), e.getMessage());
        }
}
}
