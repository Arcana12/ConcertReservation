package io.hhplus.concert.concert.infrastructure;

import aj.org.objectweb.asm.commons.Remapper;
import io.hhplus.concert.concert.domain.Seat;
import io.hhplus.concert.concert.domain.SeatStatus;
import io.hhplus.concert.concert.infrastructure.entity.SeatEntity;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findByConcertIdAndStatus(Long concertId, SeatStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SeatEntity> findById(Long id);

    List<SeatEntity> findByStatus(SeatStatus status);

}
