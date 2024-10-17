package io.hhplus.concert.payment.infrastructure;

import io.hhplus.concert.payment.domain.PaymentHist;
import io.hhplus.concert.payment.domain.repository.PaymentHistRepository;
import io.hhplus.concert.payment.infrastructure.entity.PaymentHistEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentHistRepositoryImpl implements PaymentHistRepository {

    private final PaymentHistJpaRepository paymentHistJpaRepository;

    @Override
    public PaymentHist save(PaymentHist paymentHist) {
        return paymentHistJpaRepository.save(PaymentHistEntity.fromDomain(paymentHist))
            .toDomain();
    }
}
