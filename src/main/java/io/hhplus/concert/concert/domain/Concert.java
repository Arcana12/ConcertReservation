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
public class Concert {

    public Long id;
    public String name;
    public LocalDateTime concertAt;
    public Long maxSeats;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

}
