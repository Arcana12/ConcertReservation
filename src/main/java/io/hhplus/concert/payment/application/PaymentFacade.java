package io.hhplus.concert.payment.application;

import io.hhplus.concert.concert.application.ConcertService;
import io.hhplus.concert.payment.domain.Payment;
import io.hhplus.concert.payment.domain.PaymentHist;
import io.hhplus.concert.payment.domain.PaymentStatus;
import io.hhplus.concert.payment.interfaces.dto.PaymentResponse;
import io.hhplus.concert.reservation.application.ReservationService;
import io.hhplus.concert.reservation.domain.Reservation;
import io.hhplus.concert.reservation.domain.ReservationStatus;
import io.hhplus.concert.user.application.UserService;
import io.hhplus.concert.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;
    private final UserService userService;
    private final ReservationService reservationService;
    private final ConcertService concertService;


    //결제
    @Transactional
    public PaymentResponse createPayment(Long reservationId, UUID tokenId, UUID userUuid, Long amount)
        throws Exception {

        userService.checkToken(tokenId);

        User user = userService.getUser(userUuid);

        //예약 확인
        Reservation reservation = reservationService.checkReservation(reservationId);

        //결제 처리
        Payment payment = paymentService.createPayment(reservationId, amount);

        //결제 내역 저장
        paymentService.savePaymentHist(payment, user.getId());

        //좌석 상태 변경
        concertService.changeStatus(reservation.getSeatId());

        //대기열 토큰 만료
        userService.dropToken(user.getId());

        return new PaymentResponse(PaymentStatus.SUCCESS);
    }

}
