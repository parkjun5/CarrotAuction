package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.carrot.auction.domain.auction.fixture.AuctionFixture.TEST_AUCTION_ROOM;
import static org.assertj.core.api.Assertions.assertThat;

class AuctionRoomTest {

    @Test
    @DisplayName("경매장 정보 변경 테스트")
    void testChangeInfo() {
        //given
        AuctionRoom auctionRoom = TEST_AUCTION_ROOM;
        String oldName = auctionRoom.getName();
        Category notChangeCategory = auctionRoom.getCategory();
        Item galaxyBook = Item.of("갤럭시 북", 100_000, "맥북보다 훨씬 싼 갤럭시 북 안드로이드 개발에 좋아요");
        AuctionRequest changeRequest = AuctionRequest.builder().name("이름 변경").item(galaxyBook).bidStartPrice(20_000).category(Category.DIGITAL).build();
        //when
        auctionRoom.updateAuctionInfo(changeRequest.name(), changeRequest.password(), changeRequest.limitOfEnrollment(),
                changeRequest.bidStartPrice(), changeRequest.beginDateTime(), changeRequest.closeDateTime());
        auctionRoom.updateItem(changeRequest.item().getTitle(), changeRequest.item().getPrice(), changeRequest.item().getContent(), changeRequest.category());
        //then
        assertThat(auctionRoom.getName()).isNotEqualTo(oldName);
        assertThat(auctionRoom.getName()).isEqualTo("이름 변경");
        assertThat(auctionRoom.getItem().getTitle()).isEqualTo(galaxyBook.getTitle());
        assertThat(auctionRoom.getItem().getPrice()).isEqualTo(galaxyBook.getPrice());
        assertThat(auctionRoom.getItem().getContent()).isEqualTo(galaxyBook.getContent());
        assertThat(auctionRoom.getCategory()).isEqualTo(notChangeCategory);
    }

}