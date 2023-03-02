package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.auction.TestAuctionUtils;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

class AuctionRoomTest implements TestAuctionUtils {

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
        AuctionRoom.createByRequestBuilder noUserArg = AuctionRoom.createByRequestBuilder().auctionRequest(getTestAuctionRequest());
        assertThatThrownBy(noUserArg::build).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경매장 요청이 전부 널일 경우 IllegalArgumentException 발생")
    void auctionNoRequest() {
        User testUser = getTestUser();
        AuctionRoom.createByRequestBuilder noCreateAuctionRequest = AuctionRoom.createByRequestBuilder().hostUser(testUser);
        assertThatThrownBy(noCreateAuctionRequest::build).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경매장 참가자 추가 테스트")
    void testAddParticipants() {
        AuctionRoom auctionRoom = getTestAuctionRoom();
        assertThat(auctionRoom.getParticipants()).hasSize(1);
        User testUser = User.createUser().nickname("참가자").email("참가자@gmail.com").password("pass").build();
        assertThat(testUser.getAuctionRooms()).isEmpty();

        auctionRoom.addParticipants(testUser);

        assertThat(auctionRoom.getParticipants()).hasSize(2);
        assertThat(auctionRoom.getParticipants().get(1).getNickname()).isEqualTo("참가자");
        assertThat(testUser.getAuctionRooms()).hasSize(1);
        assertThat(testUser.getAuctionRooms().get(0).getName()).isEqualTo("테스트 경매장");
    }
    
    @Test
    @DisplayName("경매장 정보 변경 테스트")
    void testChangeInfo() {
        //given
        AuctionRoom auctionRoom = getTestAuctionRoom();
        String oldName = auctionRoom.getName();
        Category notChangeCategory = auctionRoom.getCategory();
        Item galaxyBook = Item.of("갤럭시 북", 100_000, "맥북보다 훨씬 싼 갤럭시 북 안드로이드 개발에 좋아요");
        AuctionRequest changeRequest = AuctionRequest.builder().name("이름 변경").item(galaxyBook).build();
        //when 
        auctionRoom.changeInfoByRequest(changeRequest);

        //then
        assertThat(auctionRoom.getName()).isNotEqualTo(oldName);
        assertThat(auctionRoom.getName()).isEqualTo("이름 변경");
        assertThat(auctionRoom.getItem().getTitle()).isEqualTo(galaxyBook.getTitle());
        assertThat(auctionRoom.getItem().getPrice()).isEqualTo(galaxyBook.getPrice());
        assertThat(auctionRoom.getItem().getContent()).isEqualTo(galaxyBook.getContent());
        assertThat(auctionRoom.getCategory()).isEqualTo(notChangeCategory);
    }
}