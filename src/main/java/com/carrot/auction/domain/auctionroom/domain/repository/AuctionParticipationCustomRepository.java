package com.carrot.auction.domain.auctionroom.domain.repository;

import com.carrot.auction.domain.auctionroom.domain.entity.AuctionParticipation;

import java.util.Optional;

public interface AuctionParticipationCustomRepository {
    Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId);
}
