package com.asanali.service.kafka.consumer;


import com.asanali.entity.Seat;
import com.asanali.enums.SeatStatus;
import com.asanali.service.kafka.dto.SeatReservationExpiredDto;
import com.asanali.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatExpiredConsumer {

    private final SeatRepository seatRepository;

    @KafkaListener(topics = "seat-reservation-expired", groupId = "seat-group")
    public void listen(SeatReservationExpiredDto dto){
        log.info("Освобождаем место: {}", dto);

        Seat seat = seatRepository.findById(dto.getSeatId()).orElseThrow();
        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setReservedUntil(null);
        seat.setUserId(null);
        seatRepository.save(seat);

        log.info("Место: " + dto.getSeatId() + " освобождено");
    }
}
