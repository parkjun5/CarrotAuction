package com.carrot.auction.domain.auction.service.impl;

import com.carrot.auction.domain.auction.TestAuctionUtils;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest implements TestAuctionUtils {

    @InjectMocks
    private AuctionRoomServiceImpl auctionRoomService;
    @Mock
    private UserService userService;
    @Mock
    private AuctionRoomRepository auctionRoomRepository;

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

}