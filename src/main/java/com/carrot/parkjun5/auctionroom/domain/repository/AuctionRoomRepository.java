package com.carrot.parkjun5.auctionroom.domain.repository;

import com.carrot.parkjun5.auctionroom.domain.AuctionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRoomRepository extends JpaRepository<AuctionRoom, Long> {

    @Query("select ar from AuctionRoom ar join fetch ar.auctionParticipation where ar.id = :roomId")
    AuctionRoom findByIdFetchParticipation(@Param("roomId") Long roomId);
}
