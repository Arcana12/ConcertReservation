package io.hhplus.concert.payment.infrastructure;

import io.hhplus.concert.payment.infrastructure.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {

}
