package com.carrot.auction.domain.auction.controller;

import com.carrot.auction.domain.auction.TestAuctionUtils;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
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


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuctionRoomController.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class AuctionRoomControllerTest implements TestAuctionUtils {

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
        AuctionRoom room = getTestAuctionRoom();
        given(auctionService.createAuctionRoom(any())).willReturn(auctionRoomToResponse(room));

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/auctionRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTestAuctionRequest()))
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
        AuctionRequest userIdNull = AuctionRequest.builder()
                .item(Item.of("맥북", 500_000, "신형 맥북 급처"))
                .category(Category.DIGITAL)
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
    @DisplayName("post /api/auctionRoom/{auctionRoomId}")
    void updateAuctionRoom() throws Exception {
        //given
        AuctionRoom room = getTestAuctionRoom();
        given(auctionService.updateAuctionRoom(anyLong(), any(AuctionRequest.class))).willReturn(auctionRoomToResponse(room));

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/auctionRoom/"+ 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTestAuctionRequest()))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("body").exists())
                .andExpect(jsonPath("body.AuctionRoom").exists());
    }

}