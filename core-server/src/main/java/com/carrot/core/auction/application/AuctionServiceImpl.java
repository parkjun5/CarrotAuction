package com.carrot.core.auction.application;

import auctions.AuctionItemServiceGrpc;
import auctions.Auctions;
import com.carrot.core.auction.domain.Auction;
import com.carrot.core.auctionroom.application.AuctionRoomService;
import com.carrot.core.bidrule.domain.BidRule;
import com.carrot.core.item.domain.Item;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuctionServiceImpl extends AuctionItemServiceGrpc.AuctionItemServiceImplBase {

    private final AuctionService auctionService;
    private final AuctionRoomService auctionRoomService;

    public AuctionServiceImpl(AuctionService auctionService, AuctionRoomService auctionRoomService) {
        this.auctionService = auctionService;
        this.auctionRoomService = auctionRoomService;
    }

    @Override
    public void getAuctionInfo(Auctions.AuctionRequest request, StreamObserver<Auctions.AuctionResponse> responseObserver) {
        Auction auction = auctionService.findAuctionByIdFetchBidRules(request.getAuctionRoomId());
        Set<BidRule> bidRules = auction.getBidRules();
        Item item = auction.getItem();

        var itemResponse = Auctions.ItemResponse.newBuilder()
                .setTitle(item.getTitle())
                .setPrice(item.getPrice())
                .setContent(item.getContent())
                .build();

        var responseBuilder = Auctions.AuctionResponse.newBuilder()
                .setBidStartPrice(auction.getBidStartPrice())
                .setItemResponses(itemResponse)
                .setBeginDateTime(convertLocalDateTimeToProtoTimestamp(auction.getBeginDateTime()))
                .setCloseDateTime(convertLocalDateTimeToProtoTimestamp(auction.getCloseDateTime()));

        AtomicInteger index = new AtomicInteger();

        bidRules.forEach(it -> {
            int andIncrement = index.getAndIncrement();
            var bidRulebuilder = Auctions.BidRuleResponse
                    .newBuilder()
                    .setName(it.getName())
                    .setDescription(it.getDescription())
                    .setRuleValue(it.getRuleValue());

            responseBuilder.setBidRuleResponses(andIncrement, bidRulebuilder);
        });

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAllNotActiveUserInChatRoom(Auctions.ParticipationRequest request, StreamObserver<Auctions.ParticipationResponse> responseObserver) {
        Set<Long> userIds = auctionRoomService.findAuctionParticipationByAuctionId(request.getRoomId(), request.getSenderId());

        var response = Auctions.ParticipationResponse.newBuilder()
                .addAllWriterId(userIds)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Timestamp convertLocalDateTimeToProtoTimestamp(ZonedDateTime localDateTime) {
        long seconds = localDateTime.toEpochSecond();
        int nanos = localDateTime.getNano();

        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }
}
