package com.carrot.auction.domain.auction.domain;

import com.carrot.auction.domain.user.domain.entity.User;
import lombok.*;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@Builder @AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Bidding {

    private static final BigDecimal MINIMUM_BIDDING_PERCENT = BigDecimal.valueOf(0.05);
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User sender;
    private int price;

    public static boolean validatePrice(int existingPrice, int suggestPrice) {
        if (suggestPrice < 0 || existingPrice < 0) {
            throw new IllegalArgumentException("잘못된 가격 설정: " + suggestPrice);
        }
        int minimumPrice = existingPrice + MINIMUM_BIDDING_PERCENT.multiply(BigDecimal.valueOf(existingPrice)).intValue();
        return minimumPrice <= suggestPrice;
    }

}
