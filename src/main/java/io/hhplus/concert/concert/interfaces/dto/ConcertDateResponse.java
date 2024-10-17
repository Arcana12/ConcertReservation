package io.hhplus.concert.concert.interfaces.dto;


import java.time.LocalDateTime;
import java.util.List;

public record ConcertDateResponse(
    List<LocalDateTime> concerts
) {

}
