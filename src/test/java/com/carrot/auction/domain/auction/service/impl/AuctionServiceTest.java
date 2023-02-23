package com.carrot.auction.domain.auction.service.impl;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import com.carrot.auction.domain.auction.dto.CreateAuctionRequest;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @InjectMocks
    private AuctionRoomServiceImpl auctionRoomService;
    @Mock
    private UserService userService;
    @Mock
    private AuctionRoomRepository auctionRoomRepository;

    @Test
    @DisplayName("경매장 생성 및 저장 비지니스 로직 테스트")
    void createAuctionRoomTest() throws Exception {
        //given
        given(userService.findUserById(anyLong())).willReturn(Optional.of(getUser()));
        given(auctionRoomRepository.save(any())).willReturn(getAuctionRoom());
        CreateAuctionRequest createRequest = getCreateAuctionRequest();
        //when
        auctionRoomService.createAuctionRoom(createRequest);

        //then
        then(userService).should(times(1)).findUserById(anyLong());
        then(auctionRoomRepository).should(times(1)).save(any());
    }

    private AuctionRoom getAuctionRoom() {
        return AuctionRoom.createByRequestBuilder()
                .hostUser(getUser())
                .createAuctionRequest(getCreateAuctionRequest())
                .build();
    }

    private User getUser() {
        return User.createUser()
                .email("tester@gmail.com")
                .nickname("tester")
                .password("testPw")
                .build();
    }

    private CreateAuctionRequest getCreateAuctionRequest() {
        return CreateAuctionRequest
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