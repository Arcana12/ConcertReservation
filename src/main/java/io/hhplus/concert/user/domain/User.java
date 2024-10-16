package io.hhplus.concert.user.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private Long id;
    private UUID uuid;
    private String name;
    private Long version;
    private Long amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void charge(Long chargeAmount){
        this.amount += chargeAmount;
        if(this.amount < 0){
            throw new IllegalArgumentException("금액이 잘못 입력되었습니다.");
        }
        this.updatedAt = LocalDateTime.now();
    }
}
