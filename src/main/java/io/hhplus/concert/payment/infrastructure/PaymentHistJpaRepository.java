package io.hhplus.concert.payment.infrastructure;

import io.hhplus.concert.payment.infrastructure.entity.PaymentHistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistJpaRepository extends JpaRepository<PaymentHistEntity, Long> {

}
