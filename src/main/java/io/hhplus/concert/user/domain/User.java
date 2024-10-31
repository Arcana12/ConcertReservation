package io.hhplus.concert.user.domain;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private UUID uuid;
    private String name;
    private Long amount;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void charge(Long chargeAmount){
        this.amount += chargeAmount;
        if(this.amount < 0){
            throw new CustomException(ErrorCode.INVALID_AMOUNT);
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void use(Long useAmount){
        this.amount -= useAmount;
        if(this.amount < 0){
            throw new CustomException(ErrorCode.SHORTAGE_AMOUNT);
        }
    }
}
