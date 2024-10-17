package io.hhplus.concert.reservation.interfaces.dto;

import java.util.UUID;

public record ReservationRequest (
    UUID tokenUuid,
    UUID userUuid,
    Long concertId,
    Long seatId

){

}
