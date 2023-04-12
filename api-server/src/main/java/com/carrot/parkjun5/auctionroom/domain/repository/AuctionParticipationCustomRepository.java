package com.carrot.parkjun5.auctionroom.domain.repository;

import com.carrot.parkjun5.auctionroom.domain.AuctionParticipation;

import java.util.Optional;

public interface AuctionParticipationCustomRepository {
    Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId);
}
