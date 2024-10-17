package io.hhplus.concert.payment.domain.repository;

import io.hhplus.concert.payment.domain.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {

    Payment save(Payment payment);
}
