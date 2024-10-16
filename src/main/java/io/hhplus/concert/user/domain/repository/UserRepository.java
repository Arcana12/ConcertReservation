package io.hhplus.concert.user.domain.repository;

import io.hhplus.concert.user.domain.User;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findById(UUID userUuid);

    void save(User user);
}
