package io.hhplus.concert.user.application;

import io.hhplus.concert.user.domain.Token;
import io.hhplus.concert.user.domain.TokenStatus;
import io.hhplus.concert.user.domain.User;
import io.hhplus.concert.user.domain.repository.TokenRepository;
import io.hhplus.concert.user.domain.repository.UserRepository;
import io.hhplus.concert.user.infrastructure.TokenJpaRepository;
import io.hhplus.concert.user.interfaces.dto.AmountResponse;
import io.hhplus.concert.user.interfaces.dto.TokenResponse;
import io.hhplus.concert.user.interfaces.dto.TokenStatusResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenJpaRepository tokenJpaRepository;

    //토큰 생성
    @Transactional
    public TokenResponse createToken(UUID userUuid) {
        User user = getUser(userUuid);

        //내부 로직에서는 uuid가 아닌 id로 조회
        Optional<Token> token = tokenRepository.findByUserId(user.getId());

        //토큰이 존재할 경우 토큰 정보 반환
        if (token.isPresent()) {
            Token detailToken = token.get();
            //총 대기자 수
            Long totalWaiting = tokenJpaRepository.countByTokenStatus(TokenStatus.PENDING);
            return new TokenResponse(detailToken.getUuid(),detailToken.getExpiredAt(), totalWaiting);
        }

        //토큰 생성
        Token saveToken = Token.builder()
            .uuid(UUID.randomUUID())
            .userId(user.getId())
            .tokenStatus(TokenStatus.PENDING)
            .expiredAt(LocalDateTime.now().plusMinutes(10))
            .build();
        tokenRepository.save(saveToken);

        //총 대기자 수
        Long totalWaiting = tokenJpaRepository.countByTokenStatus(TokenStatus.PENDING);

        return new TokenResponse(saveToken.getUuid(),saveToken.getExpiredAt(), totalWaiting);
    }

    //토큰 조회
    public TokenStatusResponse getTokenStatus(UUID uuid) {
        Token token = tokenRepository.findByTokenUuid(uuid);
        return new TokenStatusResponse(token.getTokenStatus());
    }

    //대기열 토큰 만료
    @Transactional
    public void tokenExpire() {

        List<Token> tokens = tokenRepository.findByStateIssued();

        tokens.forEach(token -> {
            if (token.isExpired()) {
                tokenRepository.deleteToken(token.getId());
            }
        });
    }

    //대기열 관리
    @Transactional
    public void queueToIssuedToken() {
        //대기 상태중인 토큰 중 생성일자를 기준으로 20개를 가져옴
        List<Token> tokens = tokenRepository.findPendingStatusTokens();

        for (Token token : tokens) {

            //토큰 상태 변경 및 만료시간 갱신
            token.setTokenStatus(TokenStatus.ISSUED);
            token.setExpiredAt(LocalDateTime.now().plusMinutes(10));

            tokenRepository.save(token);
        }
    }

    //잔액 조회
    public AmountResponse getAmount(UUID uuid) {
        User user = getUser(uuid);
        return new AmountResponse(user.getAmount());
    }

    //잔액 충전
    @Transactional
    public AmountResponse chargeAmount(UUID uuid,Long amount){
        User user = getUser(uuid);

        user.charge(amount);
        userRepository.save(user);

        return new AmountResponse(user.getAmount());
    }

    //토큰 확인
    public void checkToken(UUID uuid) throws Exception {
        Token token = tokenRepository.findByTokenUuid(uuid);
        if (token == null || token.isExpired()) {
            throw new Exception("토큰이 유효하지 않습니다.");
        }
    }

    //사용자 조회
    public User getUser(UUID uuid){
        return userRepository.findById(uuid);
    }

    //토큰 만료
    public void dropToken(Long id){
        tokenJpaRepository.deleteByUserId(id);
    }
}
