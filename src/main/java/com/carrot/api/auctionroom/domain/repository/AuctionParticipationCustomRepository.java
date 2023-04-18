package com.carrot.api.auctionroom.domain.repository;

import com.carrot.api.auctionroom.domain.AuctionParticipation;

import java.util.Optional;

public interface AuctionParticipationCustomRepository {
    Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId);
}
