package io.hhplus.concert.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.hhplus.concert.user.domain.Token;
import io.hhplus.concert.user.domain.TokenStatus;
import io.hhplus.concert.user.domain.User;
import io.hhplus.concert.user.domain.UserService;
import io.hhplus.concert.user.domain.repository.TokenRepository;
import io.hhplus.concert.user.domain.repository.UserRepository;
import io.hhplus.concert.user.infrastructure.TokenJpaRepository;
import io.hhplus.concert.user.infrastructure.entity.UserEntity;
import io.hhplus.concert.user.interfaces.dto.AmountResponse;
import io.hhplus.concert.user.interfaces.dto.TokenResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class UserServiceTest {

    private UserEntity userEntity;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenJpaRepository tokenJpaRepository;

    private User mockedUser;
    private Token mockedToken;
    private Token newToken;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockedUser = mock(User.class);
        UUID uuid = UUID.randomUUID();
        mockedUser.setId(1L);
        mockedUser.setUuid(uuid);
        mockedUser.setName("Test");
        mockedUser.setAmount(100L);
        mockedUser.setCreatedAt(LocalDateTime.now());

        mockedToken = mock(Token.class);
        UUID uuid2 = UUID.randomUUID();
        mockedToken.setId(1L);
        mockedToken.setUuid(uuid2);
        mockedToken.setCreatedAt(LocalDateTime.now());
        mockedToken.setTokenStatus(TokenStatus.PENDING);
        mockedToken.setExpiredAt(LocalDateTime.now().plusMinutes(5));


        newToken = mock(Token.class);
        UUID uuid3 = UUID.randomUUID();
        newToken.setId(1L);
        newToken.setUuid(uuid3);
        newToken.setCreatedAt(LocalDateTime.now());
        newToken.setTokenStatus(TokenStatus.PENDING);
        newToken.setExpiredAt(LocalDateTime.now().plusMinutes(5));

        userEntity = new UserEntity(1L, UUID.randomUUID(), "Test", 1000L, null, null);
    }


    @Test
    @DisplayName("유저 조회 성공 시 toDomain 성공")
    public void toDomain_shouldReturnBeanSuccessfully() {
        assertDoesNotThrow(() -> {
            User user = userEntity.toDomain();
            assertEquals(userEntity.getId(), user.getId());
            assertEquals(userEntity.getUuid(), user.getUuid());
            assertEquals(userEntity.getName(), user.getName());
            assertEquals(userEntity.getAmount(), user.getAmount());
            assertEquals(userEntity.getCreatedAt(), user.getCreatedAt());
            assertEquals(userEntity.getUpdatedAt(), user.getUpdatedAt());
        });
    }

    @Test
    @DisplayName("유저 조회 실패 시 toDomain에서 예외 발생")
    public void toDomain_shouldThrowIllegalArgumentException_whenIdIsNull() {
        userEntity = new UserEntity();
        assertThrows(IllegalArgumentException.class, () -> userEntity.toDomain());
    }

    @Test
    @DisplayName("유저가 존재하고 토큰이 이미 생성되어 있을 때, 토큰 생성은 기존 토큰을 반환해야 합니다.")
    public void createToken_shouldReturnExistingToken_whenUserExistsAndTokenAlreadyCreated() {

        when(userRepository.findById(any(UUID.class))).thenReturn(mockedUser);
        when(tokenRepository.findByUserId(any(Long.class))).thenReturn(Optional.of(mockedToken));
        when(tokenJpaRepository.countByTokenStatus(TokenStatus.PENDING)).thenReturn(5L);

        TokenResponse response = userService.createToken(UUID.randomUUID());

        assertEquals(mockedToken.getUuid(), response.tokenUuid());
    }

    @Test
    @DisplayName("유저가 존재하지만 토큰이 아직 생성되지 않았을 때, 토큰 생성은 새 토큰을 생성하고 반환해야 합니다.")
    public void createToken_shouldCreateAndReturnToken_whenUserExistsAndTokenNotYetCreated() {

        when(userRepository.findById(any(UUID.class))).thenReturn(mockedUser);
        when(tokenRepository.findByUserId(any(Long.class))).thenReturn(Optional.empty());
        doNothing().when(tokenRepository).save(any(Token.class));
        when(tokenJpaRepository.countByTokenStatus(TokenStatus.PENDING)).thenReturn(5L);
        when(mockedUser.getUuid()).thenReturn(UUID.randomUUID());

        TokenResponse response = userService.createToken(mockedUser.getUuid());

        assertNotNull(response.tokenUuid());
    }

    @Test
    @DisplayName("대기열 토큰 만료시킨다")
    public void whenTokenExpire_thenTokensShouldBeDeleted(){
        // Given
        Token unexpiredToken = new Token();
        unexpiredToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));

        Token expiredToken = new Token();
        expiredToken.setExpiredAt(LocalDateTime.now().minusMinutes(15));

        List<Token> tokens = Arrays.asList(unexpiredToken, expiredToken);

        doReturn(tokens).when(tokenRepository).findByStateIssued();

        // When
        userService.tokenExpire();

        // Then
        verify(tokenRepository).findByStateIssued();
        verify(tokenRepository).deleteToken(expiredToken.getId());
    }

    @Test
    @DisplayName("대기 상태의 토큰을 발행 상태로 변경한다")
    public void queueToIssuedToken() {
        // Given
        Token pendingToken = new Token();
        pendingToken.setTokenStatus(TokenStatus.PENDING);
        Page<Token> tokenPage = new PageImpl<>(List.of(pendingToken));


        // When
        userService.queueToIssuedToken();

        // Then
        verify(tokenRepository).save(pendingToken);
        assertEquals(TokenStatus.ISSUED, pendingToken.getTokenStatus());
        assertTrue(pendingToken.getExpiredAt().isAfter(LocalDateTime.now()));
    }

    @Test
    @DisplayName("특정 사용자의 잔액을 가져온다")
    public void getAmount() {
        // Given
        UUID uuid = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setAmount(1000L);

        doReturn(mockUser).when(userRepository).findById(uuid);

        // When
        AmountResponse response = userService.getAmount(uuid);

        // Then
        verify(userRepository).findById(uuid);
        assertEquals(mockUser.getAmount(), response.amount());
    }

    @Test
    @DisplayName("특정 사용자의 잔액을 충전한다")
    public void chargeAmount() {
        // Given
        UUID uuid = UUID.randomUUID();
        Long rechargeAmount = 1000L;

        User user = new User();
        user.setId(1L);
        user.setAmount(2000L);  // 초기 잔액

        doReturn(user).when(userRepository).findById(uuid);
        Long expectedAmount = user.getAmount() + rechargeAmount;

        // When
        AmountResponse response = userService.chargeAmount(uuid, rechargeAmount);

        // Then
        verify(userRepository).findById(uuid);
        assertEquals(expectedAmount, response.amount());
        verify(userRepository).save(user);
    }
}
