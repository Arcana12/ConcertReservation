package io.hhplus.concert.user.domain.repository;

import io.hhplus.concert.user.domain.User;
import java.util.UUID;

public interface UserRepository {

    User findById(UUID userUuid);

    void save(User user);
}
