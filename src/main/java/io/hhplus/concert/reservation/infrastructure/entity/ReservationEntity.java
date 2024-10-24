package io.hhplus.concert.reservation.infrastructure.entity;

import io.hhplus.concert.reservation.domain.Reservation;
import io.hhplus.concert.reservation.domain.ReservationStatus;
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
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue
    public Long id;

    public Long userId;
    public Long seatId;
    public Long concertId;
    @Enumerated(EnumType.STRING)
    public ReservationStatus status;

    @CreatedDate
    public LocalDateTime createdAt;

    @LastModifiedDate
    public LocalDateTime updatedAt;

    public ReservationEntity (Long userId, Long seatId, Long concertId, ReservationStatus status) {
        this.userId = userId;
        this.seatId = seatId;
        this.concertId = concertId;
        this.status = status;
    }

    public static ReservationEntity fromDomain(Reservation reservation){
        return new ReservationEntity(reservation.getId(), reservation.getConcertId(),reservation.getSeatId(),
            reservation.getConcertId(), reservation.getStatus(), reservation.getCreatedAt(), reservation.getUpdatedAt());
    }

    public Reservation toDomain(){
        return new Reservation(id, userId, seatId, concertId, status, createdAt, updatedAt);
    }

}
