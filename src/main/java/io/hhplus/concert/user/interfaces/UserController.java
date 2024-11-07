package io.hhplus.concert.user.interfaces;

import io.hhplus.concert.user.domain.RedisQueueService;
import io.hhplus.concert.user.domain.UserService;
import io.hhplus.concert.user.interfaces.dto.AmountChargeRequest;
import io.hhplus.concert.user.interfaces.dto.AmountRequest;
import io.hhplus.concert.user.interfaces.dto.AmountResponse;
import io.hhplus.concert.user.interfaces.dto.QueueRequest;
import io.hhplus.concert.user.interfaces.dto.QueueResponse;
import io.hhplus.concert.user.interfaces.dto.TokenRequest;
import io.hhplus.concert.user.interfaces.dto.TokenResponse;
import io.hhplus.concert.user.interfaces.dto.TokenStatusRequest;
import io.hhplus.concert.user.interfaces.dto.TokenStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RedisQueueService redisQueueService;

    /*@Operation(
        summary = "유저 토큰 발급 api",
        description = "유저의 토큰을 발급합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "토큰이 발급되었습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
            ),

            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )})
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest request){
        return ResponseEntity.ok(userService.createToken(request.userUuid()));
    }

    @Operation(
        summary = "유저 토큰 조회 api",
        description = "유저의 토큰을 조회합니다.",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenStatusResponse.class))
            ),

            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )})
    @GetMapping("/token")
    public ResponseEntity<TokenStatusResponse> getTokenStatus(@RequestBody TokenStatusRequest request) {
        return ResponseEntity.ok(userService.getTokenStatus(request.tokenUuid()));
    }*/

    @Operation(
        summary = "유저 토큰 발급 api",
        description = "유저의 토큰을 발급합니다.")
    @PostMapping("/token")
    public ResponseEntity<QueueResponse> addToWaitQueue(@RequestBody QueueRequest request){
        return ResponseEntity.ok(redisQueueService.addToWaitQueue(request.userUuid()));
    }

    @Operation(
        summary = "유저 토큰 조회 api",
        description = "유저의 토큰을 조회합니다.")
    @GetMapping("/token")
    public ResponseEntity<QueueResponse> getQueuePosition(@RequestBody TokenStatusRequest request) {
        return ResponseEntity.ok(redisQueueService.getQueuePosition(request.tokenUuid()));
    }

    @Operation(
        summary = "유저 잔액 조회 api",
        description = "유저의 잔액을 조회합니다",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AmountResponse.class))
            ),

            @ApiResponse(responseCode = "401", description = "유효하지 액수입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )})
    @GetMapping("/amount")
    public ResponseEntity<AmountResponse> getAmount(@RequestBody AmountRequest request){
        return ResponseEntity.ok(userService.getAmount(request.userUuid()));
    }

    @Operation(
        summary = "유저 잔액 충전 api",
        description = "유저의 잔액을 충전합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "잔액이 충전되었습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AmountResponse.class))
            ),

            @ApiResponse(responseCode = "401", description = "유효하지 않은 값입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )})
    @PostMapping("/amount/charge")
    public ResponseEntity<AmountResponse> charge(@RequestBody AmountChargeRequest request){
        return ResponseEntity.ok(userService.chargeAmount(request.userUuid(), request.amount()));
    }
}
