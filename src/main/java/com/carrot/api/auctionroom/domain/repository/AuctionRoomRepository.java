package com.carrot.api.auctionroom.domain.repository;

import com.carrot.api.auctionroom.domain.AuctionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRoomRepository extends JpaRepository<AuctionRoom, Long> {

}
