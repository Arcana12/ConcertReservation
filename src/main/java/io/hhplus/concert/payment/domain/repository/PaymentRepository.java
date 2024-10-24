package io.hhplus.concert.payment.domain.repository;

import io.hhplus.concert.payment.domain.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);
}
