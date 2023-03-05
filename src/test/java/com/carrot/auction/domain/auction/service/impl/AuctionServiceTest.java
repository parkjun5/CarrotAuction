package com.carrot.auction.domain.auction.service.impl;

import com.carrot.auction.domain.auction.TestAuctionUtils;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest implements TestAuctionUtils {

    @InjectMocks
    private AuctionRoomService auctionRoomService;
    @Mock
    private UserService userService;
    @Mock
    private AuctionRoomRepository auctionRoomRepository;
    @Mock
    private AuctionRoom auctionRoom;
    @Mock
    private AuctionRequest auctionRequest;

    @Test
    @DisplayName("경매장 생성 및 저장 비지니스 로직 테스트")
    void createAuctionRoomTest() {
        //given
        given(userService.findUserById(anyLong())).willReturn(Optional.of(getTestUser()));
        given(auctionRoomRepository.save(any())).willReturn(getTestAuctionRoom());

        //when
        auctionRoomService.createAuctionRoom(getTestAuctionRequest());

        //then
        then(userService).should(times(1)).findUserById(anyLong());
        then(auctionRoomRepository).should(times(1)).save(any());
    }
    
    @Test
    @DisplayName("경매장 아이디로 찾기")
    void findAuctionRoom() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(getTestAuctionRoom()));
        //when
        auctionRoomService.findAuctionInfoById(anyLong());
        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("경매장 정보 수정")
    void updateAuction() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.of(auctionRoom));
        given(auctionRequest.name()).willReturn("testRequest");
        given(auctionRequest.beginAuctionDateTime()).willReturn(LocalDateTime.MIN);
        given(auctionRequest.closeAuctionDateTime()).willReturn(LocalDateTime.MAX);
        given(auctionRequest.item()).willReturn(Item.of("test", 10_000, "test data"));
        given(auctionRequest.category()).willReturn(Category.WTB);

        willDoNothing().given(auctionRoom).updateAuctionInfo(anyString(), any(), anyInt(), any(LocalDateTime.class), any(LocalDateTime.class));
        willDoNothing().given(auctionRoom).updateItem(anyString(), anyInt(), anyString(), any(Category.class));
        willDoNothing().given(auctionRequest).validateDateTime();

        //when
        assertThatCode(() -> auctionRoomService.updateAuctionRoom(1L, auctionRequest)).doesNotThrowAnyException();

        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
        then(auctionRoom).should(times(1)).updateAuctionInfo(anyString(), any(), anyInt(), any(LocalDateTime.class), any(LocalDateTime.class));
        then(auctionRoom).should(times(1)).updateItem(anyString(), anyInt(), anyString(), any(Category.class));
    }

    @Test
    @DisplayName("경매장 삭제")
    void deleteAuction() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(getTestAuctionRoom()));
        //when
        auctionRoomService.deleteAuctionRoom(anyLong());
        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
        then(auctionRoomRepository).should(times(1)).delete(any(AuctionRoom.class));
    }
}