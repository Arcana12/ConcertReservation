package io.hhplus.concert.user.infrastructure;


import io.hhplus.concert.user.infrastructure.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUuid(UUID uuid);

}
