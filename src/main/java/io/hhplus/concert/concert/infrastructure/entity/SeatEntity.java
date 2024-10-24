package io.hhplus.concert.concert.infrastructure.entity;

import io.hhplus.concert.concert.domain.Seat;
import io.hhplus.concert.concert.domain.SeatStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat")
public class SeatEntity {

    @Id
    @GeneratedValue
    public Long id;

    public Long concertId;
    public Long seatNum;
    @Enumerated(EnumType.STRING)
    public SeatStatus status;
    private Long price;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static SeatEntity fromDomain(Seat seat) {
        return new SeatEntity(seat.getId(), seat.getConcertId(),seat.getSeatNum(),
            seat.getStatus(), seat.getPrice(), seat.getCreatedAt(), seat.getUpdatedAt());
    }

    public Seat toDomain(){
        return new Seat(id, concertId, seatNum, status, price, createdAt, updatedAt);
    }
}
