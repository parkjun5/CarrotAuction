package com.carrot.web_mvc.auctionroom.domain.repository;

import com.carrot.web_mvc.auctionroom.domain.AuctionParticipation;

import java.util.Optional;

public interface AuctionParticipationCustomRepository {
    Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId);
}
