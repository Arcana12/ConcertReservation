package io.hhplus.concert.concert.infrastructure.entity;

import io.hhplus.concert.concert.domain.Concert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class ConcertEntity {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public LocalDateTime concertAt;
    public Long maxSeats;

    @CreatedDate
    public LocalDateTime createdAt;

    @LastModifiedDate
    public LocalDateTime updatedAt;

    public Concert toDomain() {
        return new Concert(id, name, concertAt, maxSeats, createdAt, updatedAt);
    }

}
