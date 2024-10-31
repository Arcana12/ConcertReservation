package io.hhplus.concert.user.domain;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.exception.ErrorCode;
import io.hhplus.concert.user.domain.repository.TokenRepository;
import io.hhplus.concert.user.domain.repository.UserRepository;
import io.hhplus.concert.user.infrastructure.TokenJpaRepository;
import io.hhplus.concert.user.infrastructure.UserJpaRepository;
import io.hhplus.concert.user.interfaces.dto.AmountResponse;
import io.hhplus.concert.user.interfaces.dto.TokenResponse;
import io.hhplus.concert.user.interfaces.dto.TokenStatusResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenJpaRepository tokenJpaRepository;
    private final UserJpaRepository userJpaRepository;

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
        Optional<Token> token = tokenRepository.findByTokenUuid(uuid);
        if (token.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        return new TokenStatusResponse(token.get().getTokenStatus());
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
    public AmountResponse chargeAmount(UUID uuid, Long amount) {
        User user = getUser(uuid);

        user.charge(amount);
        userRepository.save(user);

        return new AmountResponse(user.getAmount());
    }

    //잔액 사용
    public void useAmount(User user, Long amount) {
        int retryCount = 0;
        int maxRetries = 5;

        while (retryCount < maxRetries) {
            try {
                user.use(amount);
                userRepository.save(user);
                break;
            } catch (OptimisticLockingFailureException e) {
                retryCount++;
                log.info("useAmount retryCount : {}", retryCount);
                if (retryCount >= maxRetries) {
                    throw e;  // 재시도 한계를 초과한 경우 예외를 던짐
                }

                // 사용자의 정보를 새로 받아옴
                user = userRepository.findById(user.getUuid());
            }
        }
    }

    //토큰 확인
    public void checkToken(UUID uuid) {
        Optional<Token> token = tokenRepository.findByTokenUuid(uuid);
        if (token.isEmpty() || token.get().isExpired()) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    //사용자 조회
    public User getUser(UUID uuid) {
        return userRepository.findById(uuid);
    }

    //토큰 만료
    public void dropToken(Long id) {
        tokenJpaRepository.deleteByUserId(id);
    }
}
