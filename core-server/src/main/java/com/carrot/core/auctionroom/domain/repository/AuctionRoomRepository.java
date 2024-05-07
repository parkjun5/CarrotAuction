package com.carrot.core.auctionroom.domain.repository;

import com.carrot.core.auctionroom.domain.AuctionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRoomRepository extends JpaRepository<AuctionRoom, Long> {

}
