package com.carrot.core.auction.domain.entity;

import com.carrot.core.auctionroom.application.dto.AuctionRoomRequest;
import com.carrot.core.auctionroom.domain.AuctionRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;


import static com.carrot.core.auction.fixture.AuctionFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class AuctionRoomTest {

    @Test
    @Rollback
    @DisplayName("경매장 정보 변경 테스트")
    void testChangeInfo() {
        //given
        AuctionRoom auctionRoom = TEST_UPDATE_ROOM;
        String oldName = auctionRoom.getName();
        AuctionRoomRequest changeRequest = AuctionRoomRequest.builder()
                .name("이름 변경")
                .password(null)
                .limitOfEnrollment(8)
                .build();
        //when
        auctionRoom.changeAuctionRoom(changeRequest.name(), changeRequest.password(), changeRequest.limitOfEnrollment());
        //then
        assertThat(auctionRoom.getName()).isNotEqualTo(oldName);
        assertThat(auctionRoom.getName()).isEqualTo("이름 변경");
        assertThat(auctionRoom.getLimitOfEnrollment()).isEqualTo(8);
    }

}