package io.hhplus.concert.concert.domain.repository;

import io.hhplus.concert.concert.domain.Seat;
import io.hhplus.concert.concert.domain.SeatStatus;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository {
    List<Seat> findByConcertIdAndStatus(Long concertId, SeatStatus status);

    Optional<Seat> findByIdForUpdate(Long id);

    void save(Seat seat);

    List<Seat> findByStatus(SeatStatus status);
}
