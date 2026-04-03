package com.asanali.kafka.consumer;

import com.asanali.kafka.dto.MovieReminderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMovieReminderConsumer {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = "movie-reminder",groupId = "notification")
    public void listen(MovieReminderDto dto){
        System.out.println("Сообщение " + dto);

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
        System.out.println("Сообщение отправлено по адресу " + dto.getUserEmail());
    }
}
