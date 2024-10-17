package io.hhplus.concert.concert.infrastructure;

import io.hhplus.concert.concert.infrastructure.entity.ConcertEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
    List<ConcertEntity> findByName(String name);

    List<ConcertEntity> findByConcertAt(LocalDateTime concertAt);

}
