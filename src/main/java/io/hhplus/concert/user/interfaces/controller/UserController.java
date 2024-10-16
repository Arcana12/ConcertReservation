package io.hhplus.concert.user.interfaces.controller;

import io.hhplus.concert.user.domain.UserService;
import io.hhplus.concert.user.interfaces.dto.AmountRequest;
import io.hhplus.concert.user.interfaces.dto.AmountResponse;
import io.hhplus.concert.user.interfaces.dto.TokenRequest;
import io.hhplus.concert.user.interfaces.dto.TokenResponse;
import io.hhplus.concert.user.interfaces.dto.TokenStatusRequest;
import io.hhplus.concert.user.interfaces.dto.TokenStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> createToken(TokenRequest request){
        return ResponseEntity.ok(userService.createToken(request.userUuid()));
    }

    @GetMapping("/token")
    public ResponseEntity<TokenStatusResponse> getTokenStatus(TokenStatusRequest request){
        return ResponseEntity.ok(userService.getTokenStatus(request.uuid()));
    }

    @GetMapping("/amount")
    public ResponseEntity<AmountResponse> getAmount(AmountRequest request){
        return ResponseEntity.ok(userService.getAmount(request.userUuid()));
    }

    @PostMapping("/amount/charge")
    public ResponseEntity<AmountResponse> charge(AmountRequest request){
        return ResponseEntity.ok(userService.chargeAmount(request.userUuid(), request.amount()));
    }
}
