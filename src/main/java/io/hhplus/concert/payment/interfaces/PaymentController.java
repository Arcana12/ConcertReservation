package io.hhplus.concert.payment.interfaces;

import io.hhplus.concert.payment.application.PaymentFacade;
import io.hhplus.concert.payment.interfaces.dto.PaymentRequest;
import io.hhplus.concert.payment.interfaces.dto.PaymentResponse;
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
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @Operation(
        summary = "결제 api",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))
            ),

            @ApiResponse(responseCode = "401", description = "유효하지 않은 값입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )})
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentFacade.createPayment(paymentRequest.reservationId(),
            paymentRequest.userUuid(), paymentRequest.amount()));
    }

}
