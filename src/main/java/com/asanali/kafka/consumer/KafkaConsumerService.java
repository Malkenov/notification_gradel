package com.asanali.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    // аннотация, говорит слушать Kafka и вызывать метод при получении сообщения
    @KafkaListener(topics = "notification-topic", // канал на который надо подписаться
                   groupId = "notification-group")// группа потребителей
        public void listen(String message){
        System.out.println("полученное сообщение: " + message);
    }
}
