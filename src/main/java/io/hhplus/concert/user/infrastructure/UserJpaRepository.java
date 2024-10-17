package io.hhplus.concert.user.infrastructure;


import io.hhplus.concert.user.infrastructure.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUuid(UUID uuid);

}
