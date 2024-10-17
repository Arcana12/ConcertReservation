package io.hhplus.concert.payment.infrastructure;

import io.hhplus.concert.payment.domain.Payment;
import io.hhplus.concert.payment.domain.repository.PaymentRepository;
import io.hhplus.concert.payment.infrastructure.entity.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.fromDomain(payment)).toDomain();
    }
}
