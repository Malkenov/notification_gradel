package com.asanali.service.kafka.consumer;


import com.asanali.dto.KafkaCancelledDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.DefaultErrorHandler;
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
        log.info("Получено сообщение: {}", dto);


        log.info("Возврат {} тенге, переведено успешно!", dto.getRefundAmount());

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
        log.info("Сообщение отправлено на {}", dto.getUserEmail());
    }
    // Если тут при отправке сообщение будет ошибка, то Spring сам ловит ошибку,
    // и передает в DefaultErrorHandler
}

