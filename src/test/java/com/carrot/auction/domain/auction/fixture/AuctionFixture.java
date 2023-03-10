package com.carrot.auction.domain.auction.fixture;

import com.carrot.auction.domain.auction.domain.Bid;
import com.carrot.auction.domain.auction.domain.entity.AuctionParticipation;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.domain.entity.AuctionStatus;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.dto.AuctionResponse;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.domain.entity.UserRole;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AuctionFixture {

    public static final User TEST_USER_1 = new User(
            1L,
            "tester@gmail.com",
            "테스터 1",
            "password",
            Set.of(UserRole.ADMIN),
            new ArrayList<>());

    public static final User TEST_USER_2 = new User(
            "jagosi@gmail.com",
            "테스터 2",
            "q1w2e3r4!"
    );

    public static final Bid TEST_BID = Bid.builder()
            .bidderId(99_999)
            .biddingPrice(3_000)
            .biddingTime(ZonedDateTime.now())
            .build();

    public static final Item TEST_ITEM = Item.of("팔고 싶은 물건", 50_000, "저렴하게 팔아요!");

    public static final ZonedDateTime BEGIN_TIME = ZonedDateTime.of(2002, 12, 2, 12, 0, 0, 0, ZoneId.of("Asia/Seoul"));
    public static final ZonedDateTime CLOSE_TIME = ZonedDateTime.of(2092, 12, 2, 12, 0, 0, 0, ZoneId.of("Asia/Seoul"));

    public static final AuctionResponse TEST_AUCTION_RESPONSE = new AuctionResponse(
            99_999,
            TEST_USER_1,
            new HashSet<>(),
            "성공적인 테스트 기원",
            "",
            3,
            TEST_BID,
            TEST_ITEM,
            Category.HOBBY_GAME_MUSIC,
            BEGIN_TIME,
            CLOSE_TIME,
            AuctionStatus.DRAFT
    );

    public static final AuctionRoom TEST_AUCTION_ROOM = AuctionRoom.builder()
            .hostUser(TEST_USER_1)
            .name("성공적인 테스트!!")
            .password("")
            .bid(TEST_BID)
            .item(TEST_ITEM)
            .category(Category.DIGITAL)
            .beginDateTime(BEGIN_TIME)
            .closeDateTime(CLOSE_TIME)
            .limitOfEnrollment(3)
            .build();

    public static final AuctionRequest TEST_AUCTION_REQUEST = AuctionRequest.builder()
                .userId(1L)
                .name("테스트 경매장")
                .item(Item.of("맥북", 500_000, "신형 맥북 급처"))
                .password(null)
                .category(Category.DIGITAL)
                .limitOfEnrollment(100)
                .bid(Bid.startPrice(10_000))
                .beginDateTime(BEGIN_TIME)
                .closeDateTime(CLOSE_TIME)
                .build();

    public static final AuctionParticipation TEST_AUCTION_PARTICIPATION = AuctionParticipation.createAuctionParticipation(TEST_USER_2, TEST_AUCTION_ROOM);
}
