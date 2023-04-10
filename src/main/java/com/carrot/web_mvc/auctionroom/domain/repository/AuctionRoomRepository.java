package com.carrot.web_mvc.auctionroom.domain.repository;

import com.carrot.web_mvc.auctionroom.domain.AuctionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRoomRepository extends JpaRepository<AuctionRoom, Long> {

}
