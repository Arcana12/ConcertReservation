package io.hhplus.concert.payment.interfaces.dto;

import io.hhplus.concert.payment.domain.PaymentStatus;

public record PaymentResponse(
    PaymentStatus paymentStatus
) {

}
