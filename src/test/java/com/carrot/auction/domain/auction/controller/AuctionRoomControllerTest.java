package com.carrot.auction.domain.auction.controller;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.dto.CreateAuctionRequest;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.global.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuctionRoomController.class)
@AutoConfigureMockMvc
class AuctionRoomControllerTest {

    @MockBean
    private AuctionRoomService auctionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @DisplayName("post /api/auctionRoom 경매장 생성 리퀘스트 매핑")
    void createAuctionRoomTest() throws Exception {
        //given
        CreateAuctionRequest testCreateRequest = getCreateAuctionRequest();
        User testUser = getAccount();
        AuctionRoom room = AuctionRoom
                .createByRequestBuilder()
                .hostUser(testUser)
                .createAuctionRequest(testCreateRequest)
                .build();

        room.addParticipants(testUser);
        room.addParticipants(testUser);

        ApiResponse<Object> response = ApiResponse.success("data", room);
        given(auctionService.createAuctionRoom(any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/auctionRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateRequest))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("body").exists())
                .andExpect(jsonPath("body.data").exists());
    }

    private User getAccount() {
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