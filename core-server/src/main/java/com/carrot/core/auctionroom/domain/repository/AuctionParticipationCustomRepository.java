package com.carrot.core.auctionroom.domain.repository;

import com.carrot.core.auctionroom.domain.AuctionParticipation;

import java.util.Optional;

public interface AuctionParticipationCustomRepository {
    Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId);
}
