package io.hhplus.concert.concert.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    public Long id;
    public Long concertId;
    public Long seatNum;
    public SeatStatus status;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public boolean isExpire(Seat seat){
        return updatedAt.plusMinutes(5).isBefore(LocalDateTime.now());
    }
}
