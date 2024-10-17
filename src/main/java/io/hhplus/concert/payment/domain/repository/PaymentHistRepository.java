package io.hhplus.concert.payment.domain.repository;

import io.hhplus.concert.payment.domain.PaymentHist;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistRepository {
    PaymentHist save(PaymentHist paymentHist);
}
