package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.*;

class AuctionRoomTest {

    @Test
    @DisplayName("경매장 생성 파라미터가 널일 경우 IllegalArgumentException 발생")
    void auctionRoomNullCheck() {
        AuctionRoom.createByRequestBuilder noArgs = AuctionRoom.createByRequestBuilder();
        assertThatThrownBy(noArgs::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유저는 널일 수 없습니다.");
    }

    @Test
    @DisplayName("경매장 호스트 유저 널일 경우 IllegalArgumentException 발생")
    void auctionUserNull() {
        AuctionRoom.createByRequestBuilder noUserArg = AuctionRoom.createByRequestBuilder().auctionRequest(getAuctionRequest());
        assertThatThrownBy(noUserArg::build).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경매장 요청이 전부 널일 경우 IllegalArgumentException 발생")
    void auctionNoRequest() {
        User testUser = User.createUser().email("test").nickname("test").password("12").build();
        AuctionRoom.createByRequestBuilder noCreateAuctionRequest = AuctionRoom.createByRequestBuilder().hostUser(testUser);
        assertThatThrownBy(noCreateAuctionRequest::build).isInstanceOf(IllegalArgumentException.class);
    }

    private AuctionRequest getAuctionRequest() {
        return AuctionRequest
                .builder()
                .userId(1L)
                .name("테스트 경매장")
                .item(Item.of("맥북", 500_000, "신형 맥북 급처"))
                .password(null)
                .category(Category.DIGITAL)
                .limitOfEnrollment(100)
                .beginAuctionDateTime(LocalDateTime.of(2023, Month.of(2), 23, 10, 30))
                .closeAuctionDateTime(LocalDateTime.of(2023, Month.of(2), 23, 12, 30))
                .build();
    }
}