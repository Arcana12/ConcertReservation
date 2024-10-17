package io.hhplus.concert.reservation.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    public Long id;

    public Long userId;
    public Long seatId;
    public Long concertId;
    public ReservationStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;


}
