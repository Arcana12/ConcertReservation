package io.hhplus.concert.concert.domain.repository;

import io.hhplus.concert.concert.domain.Concert;
import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository {
    List<Concert> findByName(String name);

    List<Concert> findByConcertAt(LocalDateTime concertAt);

}
