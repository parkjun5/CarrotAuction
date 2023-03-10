package com.carrot.auction.domain.auction.domain.repository;

import com.carrot.auction.domain.auction.domain.entity.AuctionParticipation;

import java.util.Optional;

public interface AuctionParticipationCustomRepository {
    Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId);
}
