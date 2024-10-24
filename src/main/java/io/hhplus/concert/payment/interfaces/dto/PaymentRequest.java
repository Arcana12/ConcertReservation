package io.hhplus.concert.payment.interfaces.dto;

import java.util.UUID;

public record PaymentRequest(
    Long reservationId,
    UUID tokenUuid,
    UUID userUuid,
    Long amount
) {

}
