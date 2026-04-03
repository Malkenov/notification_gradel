package com.asanali.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic tickedPurchasedTopic(){
        return TopicBuilder
                .name("ticket-purchased")    // название топика в kafka
                .partitions(1) // на сколько частей делить
                .replicas(1)    // солько копии
                .build();
    }

    @Bean
    public NewTopic ticketCancelledTopic(){
        return TopicBuilder
                .name("ticket-cancelled")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic movieReminder(){
        return TopicBuilder
                .name("movie-reminder")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic seatReservation(){
        return TopicBuilder
                .name("seat-reservation-expired")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
