package com.carrot.core.auctionroom.domain.repository;

import com.carrot.core.auctionroom.domain.AuctionParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionParticipationRepository extends JpaRepository<AuctionParticipation, Long> {
    Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId);
}
