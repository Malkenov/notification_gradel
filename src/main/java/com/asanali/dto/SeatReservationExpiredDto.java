package com.asanali.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatReservationExpiredDto {

    private Long seatId;         // какое место освободить
    private String movieId;        // какой сеанс
    private String userId;         // кто бронировал
    private LocalDateTime expiredAt; // когда истекло
}