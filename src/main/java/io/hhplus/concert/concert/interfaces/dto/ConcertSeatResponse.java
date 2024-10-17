package io.hhplus.concert.concert.interfaces.dto;

import io.hhplus.concert.concert.domain.Seat;
import java.util.List;

public record ConcertSeatResponse(
    List<Seat> seatList
) {

}
