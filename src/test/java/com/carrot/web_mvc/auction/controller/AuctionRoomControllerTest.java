package com.carrot.web_mvc.auction.controller;

import com.carrot.web_mvc.auctionroom.application.dto.AuctionRoomRequest;
import com.carrot.web_mvc.auctionroom.application.AuctionRoomService;
import com.carrot.web_mvc.auctionroom.presentation.AuctionRoomController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static com.carrot.web_mvc.auction.fixture.AuctionFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuctionRoomController.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
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
        given(auctionService.createAuctionRoom(any())).willReturn(TEST_AUCTION_ROOM_RESPONSE);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/auctionRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_AUCTION_ROOM_REQUEST))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("body").exists())
                .andExpect(jsonPath("body.AuctionRoom").exists());
    }

    @Test
    @DisplayName("post /api/auctionRoom 널갑 전송")
    void createAuctionRoomWithNullValue() throws Exception {
        //given
        AuctionRoomRequest userIdNull = AuctionRoomRequest.builder()
                .limitOfEnrollment(100)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/auctionRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userIdNull))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("필수 값이 널일 경우 400에러 발생")
    void dateNullRequest() throws Exception {
        //given
        AuctionRoomRequest dateNullRequest = AuctionRoomRequest.builder()
                .name("날씨가 널인 요청")
                .limitOfEnrollment(10)
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/auctionRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dateNullRequest))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("post /api/auctionRoom/{auctionRoomId}")
    void updateAuctionRoom() throws Exception {
        //given
        given(auctionService.updateAuctionRoom(anyLong(), any(AuctionRoomRequest.class))).willReturn(TEST_AUCTION_ROOM_RESPONSE);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/auctionRoom/"+ 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_AUCTION_ROOM_REQUEST))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("body").exists())
                .andExpect(jsonPath("body.AuctionRoom").exists());
    }

}