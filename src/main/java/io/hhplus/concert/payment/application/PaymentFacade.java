package io.hhplus.concert.payment.application;

import io.hhplus.concert.concert.domain.ConcertService;
import io.hhplus.concert.concert.domain.SeatService;
import io.hhplus.concert.payment.domain.Payment;
import io.hhplus.concert.payment.domain.PaymentService;
import io.hhplus.concert.payment.domain.PaymentStatus;
import io.hhplus.concert.payment.interfaces.dto.PaymentResponse;
import io.hhplus.concert.reservation.domain.ReservationService;
import io.hhplus.concert.reservation.domain.Reservation;
import io.hhplus.concert.user.domain.UserService;
import io.hhplus.concert.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentFacade {

    private final PaymentService paymentService;
    private final UserService userService;
    private final ReservationService reservationService;
    private final SeatService seatService;


    //결제
    @Transactional
    public PaymentResponse createPayment(Long reservationId, UUID userUuid, Long amount) {

        User user = userService.getUser(userUuid);
        
        //예약 확인
        Reservation reservation = reservationService.checkReservation(reservationId);
        
        //결제 처리
        Payment payment = paymentService.createPayment(reservationId, amount);
        
        //결제 내역 저장
        paymentService.savePaymentHist(payment, user.getId());

        userService.useAmount(user, amount);

        //좌석 상태 변경
        seatService.changeStatus(reservation.getSeatId());

        //예약 상태 변경
        reservationService.changeReservationStatus(reservationId);

        //대기열 토큰 만료
        userService.dropToken(user.getId());

        return new PaymentResponse(PaymentStatus.SUCCESS);
    }

}
