package io.hhplus.concert.payment.domain.repository;

import io.hhplus.concert.payment.domain.PaymentHist;

public interface PaymentHistRepository {
    PaymentHist save(PaymentHist paymentHist);
}
