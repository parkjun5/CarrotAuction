package com.carrot.parkjun5.auction.fixture;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.auction.application.dto.AuctionRequest;
import com.carrot.parkjun5.auction.application.dto.AuctionResponse;
import com.carrot.parkjun5.auctionroom.domain.AuctionParticipation;
import com.carrot.parkjun5.auctionroom.domain.AuctionRoom;
import com.carrot.parkjun5.auction.domain.AuctionStatus;
import com.carrot.parkjun5.auctionroom.application.dto.AuctionRoomRequest;
import com.carrot.parkjun5.auctionroom.application.dto.AuctionRoomResponse;
import com.carrot.parkjun5.bid.domain.Bid;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.item.domain.Category;
import com.carrot.parkjun5.item.domain.Item;
import com.carrot.parkjun5.user.domain.User;
import com.carrot.parkjun5.user.domain.UserRole;
import com.carrot.parkjun5.user.application.dto.UserResponse;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AuctionFixture {

    public static final User TEST_USER_1 = User.builder()
            .id(1L)
            .email("tester@gmail.com")
            .nickname("테스터 1")
            .password("password")
            .roles(Set.of(UserRole.ADMIN))
            .participatedRoom(new ArrayList<>())
            .build();

    public static final User TEST_USER_2 = User.builder()
            .id(2L)
            .email("jagosi@gmail.com")
            .nickname("테스터 2")
            .password("q1w2e3r4")
            .roles(Set.of(UserRole.ADMIN))
            .participatedRoom(new ArrayList<>())
            .build();

    public static final Item TEST_ITEM = Item.of("팔고 싶은 물건", 50_000, "저렴하게 팔아요!");

    public static final ZonedDateTime BEGIN_TIME = ZonedDateTime.of(2002, 12, 2, 12, 0, 0, 0, ZoneId.of("Asia/Seoul"));
    public static final ZonedDateTime CLOSE_TIME = ZonedDateTime.of(2092, 12, 2, 12, 0, 0, 0, ZoneId.of("Asia/Seoul"));

    public static final UserResponse TEST_USER_RESPONSE = new UserResponse(
            "tester@gmail.com",
            "테스터 1",
            BEGIN_TIME
    );

    public static final AuctionRoomResponse TEST_AUCTION_ROOM_RESPONSE = new AuctionRoomResponse(
            99_999,
            TEST_USER_RESPONSE,
            new HashSet<>(),
            "성공적인 테스트 기원",
            "",
            3
    );

    public static final AuctionResponse TEST_AUCTION_RESPONSE = new AuctionResponse(
            1L,
            5_000,
            TEST_ITEM,
            Category.HOBBY_GAME_MUSIC,
            BEGIN_TIME,
            CLOSE_TIME,
            AuctionStatus.DRAFT,
            null
    );

    public static final AuctionRoom TEST_AUCTION_ROOM = AuctionRoom.builder()
            .id(999L)
            .hostUser(TEST_USER_1)
            .name("성공적인 테스트!!")
            .password("")
            .limitOfEnrollment(10)
            .build();

    public static final AuctionRoom TEST_UPDATE_ROOM = AuctionRoom.builder()
            .id(500L)
            .hostUser(TEST_USER_2)
            .name("성공적인 테스트 기원!!")
            .limitOfEnrollment(5)
            .build();
    public static final Auction TEST_AUCTION_1 = Auction.builder()
            .id(1L)
            .bidStartPrice(5_000)
            .item(TEST_ITEM)
            .category(Category.DIGITAL)
            .beginDateTime(BEGIN_TIME)
            .closeDateTime(CLOSE_TIME)
            .auctionRoom(TEST_AUCTION_ROOM)
            .build();

    public static final Bid TEST_BID = Bid.builder()
            .bidderId(2L)
            .auction(TEST_AUCTION_1)
            .biddingPrice(3_000)
            .biddingTime(ZonedDateTime.now())
            .build();

    public static final AuctionRoomRequest TEST_AUCTION_ROOM_REQUEST = AuctionRoomRequest.builder()
            .userId(1L)
            .name("테스트 경매장")
            .password(null)
            .build();

    public static final AuctionRequest TEST_AUCTION_REQUEST = AuctionRequest.builder()
            .bidStartPrice(5_000)
            .category(Category.DIGITAL)
            .beginDateTime(BEGIN_TIME)
            .closeDateTime(CLOSE_TIME)
            .build();

    public static final BidRequest TEST_BID_REQUEST = new BidRequest(2L, 1L, 50000, ZonedDateTime.now());
    public static final AuctionParticipation TEST_AUCTION_PARTICIPATION_1 = AuctionParticipation.createAuctionParticipation(TEST_USER_1, TEST_AUCTION_ROOM);
    public static final AuctionParticipation TEST_AUCTION_PARTICIPATION_2 = AuctionParticipation.createAuctionParticipation(TEST_USER_2, TEST_AUCTION_ROOM);

}
