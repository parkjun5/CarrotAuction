package com.carrot.core.auctionroom.domain.repository;

import com.carrot.core.auctionroom.domain.AuctionParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AuctionParticipationRepository extends JpaRepository<AuctionParticipation, Long> {
    Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId);

    @Query("""
        SELECT
            u.id
        FROM
            AuctionParticipation ap
            JOIN ap.auctionRoom ar
            JOIN ap.user u
        WHERE
            ar.id = :roomId
            AND u.id != :userId
    """)
    Set<Long> findAuctionParticipationByAuctionId(@Param("roomId") Long roomId,
                                                  @Param("userId") Long userId);

}
