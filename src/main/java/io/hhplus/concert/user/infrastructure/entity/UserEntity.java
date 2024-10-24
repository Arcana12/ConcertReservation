package io.hhplus.concert.user.infrastructure.entity;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.exception.ErrorCode;
import io.hhplus.concert.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;
    private String name;
    private Long amount;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User toDomain() {
        validateId(id);
        return new User(id, uuid, name, amount, createdAt, updatedAt);
    }

    public UserEntity(Long id, UUID uuid, String name, Long amount) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.amount = amount;
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(user.getId(), user.getUuid(), user.getName(), user.getAmount());
    }

    private void validateId(Long id){
        if (id == null) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
    }

}
