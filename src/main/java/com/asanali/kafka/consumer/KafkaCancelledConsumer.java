package com.asanali.kafka.producer;


import com.asanali.kafka.dto.KafkaCancelledDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaCancelledConsumer {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "ticket-canelled",groupId = "notification-group")
    public void listen(KafkaCancelledDto dto){
        System.out.println("Получено сообщение: " + dto);


    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(dto.getUserEmail());
    message.setSubject("Билет отменен: " + dto.getMovieName());
    message.setText(
            "Привет" + dto.getUserName() + "!\n\n" +
            "Ваше билет на " + dto.getMovieName() + "был отменен \n" +
            "Номер билета " + dto.getTicketId() + "\n" +
            "Сумма возврата " + dto.getRefundAmount() + "тенге\n\n"
            );
    javaMailSender.send(message);
    System.out.println("Сообщение отправлено на " + dto.getUserEmail());
    }
}
