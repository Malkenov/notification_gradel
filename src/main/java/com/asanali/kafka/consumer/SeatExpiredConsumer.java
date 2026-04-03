package com.asanali.kafka.consumer;

import com.asanali.entity.Seat;
import com.asanali.enums.SeatStatus;
import com.asanali.kafka.dto.SeatReservationExpiredDto;
import com.asanali.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatExpiredConsumer {

    private final SeatRepository seatRepository;

    @KafkaListener(topics = "seat-reservation-expired", groupId = "seat-group")
    public void listen(SeatReservationExpiredDto dto){
        System.out.println("Освобождаем место: " + dto);

        Seat seat = seatRepository.findById(Long.parseLong(dto.getSeatId())).orElseThrow();
        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setReservedUntil(null);
        seat.setUserId(null);
        seatRepository.save(seat);

        System.out.println("Место: " + dto.getSeatId() + " освобождено");
    }
}
