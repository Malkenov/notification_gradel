package com.asanali.service.kafka.consumer;


import com.asanali.dto.MovieReminderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMovieReminderConsumer {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = "movie-reminder", groupId = "notification")
    public void listen(MovieReminderDto dto) {
        log.info("Сообщение {}", dto);


        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(dto.getUserEmail());
            message.setSubject("Напоминание " + dto.getMovieName());
            message.setText(
                    "Привет " + dto.getUserName() + "\n" +
                            "сегодня " + dto.getShowTime() + "\n" +
                            "в кинотеатре " + dto.getCinemaName() + "\n" +
                            "будет показ фильма " + dto.getMovieName()
            );

            mailSender.send(message);
            log.error("Сообщение отправлено по адресу {}", dto.getUserEmail());
        } catch (Exception e) {
            log.error("Не удалось отправить сообщение на {}: {}", dto.getUserEmail(), e.getMessage());
        }
    }
}
