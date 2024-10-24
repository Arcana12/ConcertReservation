package io.hhplus.concert.reservation.interfaces;

import io.hhplus.concert.reservation.application.ReservationFacade;
import io.hhplus.concert.reservation.interfaces.dto.ReservationRequest;
import io.hhplus.concert.reservation.interfaces.dto.ReservationResponse;
import io.hhplus.concert.user.interfaces.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Operation(
        summary = "콘서트 예약 api",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))
            ),

            @ApiResponse(responseCode = "401", description = "유효하지 않은 값입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )})
    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationFacade.createReservation(reservationRequest.tokenUuid(), reservationRequest.userUuid(),
            reservationRequest.concertId(), reservationRequest.seatId()));
    }
}
