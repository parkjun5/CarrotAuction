//package com.carrot.core.auction.application;
//
//import auctions.Auctions;
//import com.carrot.core.auction.application.dto.AuctionRequest;
//import com.carrot.core.auction.application.dto.AuctionResponse;
//import com.carrot.core.auction.domain.repository.AuctionRepository;
//import com.carrot.core.auctionroom.application.AuctionRoomService;
//import com.carrot.core.auctionroom.application.dto.AuctionRoomRequest;
//import com.carrot.core.auctionroom.application.dto.AuctionRoomResponse;
//import com.carrot.core.bidrule.application.BidRuleService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static com.carrot.core.auction.fixture.AuctionFixture.*;
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.Mockito.times;
//
//
//@SpringBootTest
//class AuctionServiceTest {
//
//    @Autowired
//    private AuctionService auctionService;
//    @Autowired
//    private AuctionRepository auctionRepository;
//    @Autowired
//    private AuctionRoomService auctionRoomService;
//    @Autowired
//    private BidRuleService bidRuleService;
//
//    @DisplayName("경매 생성")
//    @Test
//    void createAuctionToRoomTest() {
//        //given
//        AuctionRoomResponse auctionRoom = auctionRoomService.createAuctionRoom(TEST_AUCTION_ROOM_REQUEST);
//        AuctionResponse auctionResponse = auctionService.createAuctionToRoom(auctionRoom.auctionRoomId(), TEST_AUCTION_REQUEST);
//
//
//        assertThat(auctionResponse).isNotNull();
//    }
//
//}