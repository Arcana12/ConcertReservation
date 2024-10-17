package io.hhplus.concert.concert.interfaces;

import io.hhplus.concert.concert.application.ConcertFacade;
import io.hhplus.concert.concert.application.ConcertService;
import io.hhplus.concert.concert.interfaces.dto.ConcertDateRequest;
import io.hhplus.concert.concert.interfaces.dto.ConcertDateResponse;
import io.hhplus.concert.concert.interfaces.dto.ConcertSeatRequest;
import io.hhplus.concert.concert.interfaces.dto.ConcertSeatResponse;
import io.hhplus.concert.user.interfaces.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertFacade concertFacade;

    @Operation(
        summary = "예약 가능한 날짜 api",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
            ),

            @ApiResponse(responseCode = "401", description = "유효하지 않은 값입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )})
    @GetMapping("/{concertName}/date")
    public ResponseEntity<ConcertDateResponse> getConcertDate(
        @PathVariable String concertName,
        ConcertDateRequest request
    ) throws Exception {
        return ResponseEntity.ok(concertFacade.getConcertDate(request.uuid(), concertName));
    }

    @Operation(
        summary = "예약 가능한 좌석 api",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
            ),

            @ApiResponse(responseCode = "401", description = "유효하지 않은 값입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )})
    @GetMapping("{date}/seat")
    public ResponseEntity<ConcertSeatResponse> getConcertSeat(
        @PathVariable LocalDateTime date,
        ConcertSeatRequest request
    ) throws Exception {
        return ResponseEntity.ok(concertFacade.getSeat(request.uuid(), date));
    }
}
