package io.hhplus.concert.reservation.infrastructure;

import io.hhplus.concert.reservation.infrastructure.entity.ReservationEntity;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ReservationEntity> findById(Long id);
}
