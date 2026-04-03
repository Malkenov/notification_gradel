package com.asanali.scheduler;

import com.asanali.entity.Seat;
import com.asanali.enums.SeatStatus;
import com.asanali.kafka.dto.SeatReservationExpiredDto;
import com.asanali.kafka.producer.SeatExpiredProducer;
import com.asanali.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatReservationScheduler {

    private final SeatRepository seatRepository;
    private final SeatExpiredProducer producer;


    @Scheduled(fixedRate = 60000)
    // Планировщик который проверяет запись в бд с истекишим бронью и отправляет в producer
    public void checkExpiredReservations(){
        List<Seat> expiredSeat = seatRepository
                .findByStatusAndReservedUntilBefore(SeatStatus.RESERVED, LocalDateTime.now());

        for(Seat seat: expiredSeat) {
            SeatReservationExpiredDto dto = new SeatReservationExpiredDto();
            dto.setSeatId(String.valueOf(seat.getId()));
            dto.setMovieId(seat.getMovieId());
            dto.setUserId(seat.getUserId());
            dto.setExpiredAt(LocalDateTime.now());

            producer.reminder(dto);
            System.out.println("Найдена истекшая бронь: " + seat.getId());
        }
    }
}
