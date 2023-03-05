package com.carrot.auction.domain.auction.domain;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.chat.domain.BaseChatRoom;
import com.carrot.auction.domain.chat.domain.Message;
import lombok.*;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Bidding  implements Message {

    private static final BigDecimal MINIMUM_BIDDING_PERCENT = BigDecimal.valueOf(0.05);

    @ManyToOne(fetch = FetchType.LAZY)
    private BaseChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User sender;

    private int price;

    /**
     * 생성 편의 메소드
     */
    public static Bidding of(BaseChatRoom chatRoom, User sender, int price) {
        return Bidding.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .price(price)
                .build();
    }
    public static boolean isCorrectSuggest(int existingPrice, int suggestPrice) {
        if (suggestPrice < 0 || existingPrice < 0) {
            throw new IllegalArgumentException("잘못된 가격 설정: " + suggestPrice);
        }

        int minimumPrice = existingPrice + MINIMUM_BIDDING_PERCENT.multiply(BigDecimal.valueOf(existingPrice)).intValue();
        return minimumPrice <= suggestPrice;
    }

}
