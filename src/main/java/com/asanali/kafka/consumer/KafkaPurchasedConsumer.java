package com.asanali.kafka.producer;


import com.asanali.kafka.dto.KafkaPurchasedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPurchasedConsumer {

    private final JavaMailSender mailSender;
    // JavaMailSender - инструмент spring для отправки email

    @KafkaListener(topics = "ticket-purchased", groupId = "notification-group")
    public void listen(KafkaPurchasedDto dto){
        System.out.println("Сообщение " + dto);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(dto.getUserEmail());
    message.setSubject("Билет купле " + dto.getMovieName());
    message.setText(
            "Привет " + dto.getUserName() + "!\n\n" +
            "Вы купили билет на " + dto.getMovieName() + "\n" +
            "Ваш билет " + dto.getTicketId()
    );

    mailSender.send(message);
    System.out.println("Сообщение отправлена на адрес " + dto.getUserEmail());
    }
}
