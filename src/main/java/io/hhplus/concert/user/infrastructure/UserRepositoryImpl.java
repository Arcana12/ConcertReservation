package io.hhplus.concert.user.infrastructure;

import io.hhplus.concert.user.domain.User;
import io.hhplus.concert.user.domain.repository.UserRepository;
import io.hhplus.concert.user.infrastructure.entity.UserEntity;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findById(UUID userUuid) {
        return userJpaRepository.findByUuid(userUuid).toDomain();
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(UserEntity.fromDomain(user));
    }

}
