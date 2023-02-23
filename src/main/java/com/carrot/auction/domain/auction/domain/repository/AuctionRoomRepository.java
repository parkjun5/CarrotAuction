package com.carrot.auction.domain.auction.domain.repository;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRoomRepository extends JpaRepository<AuctionRoom, Long> {

}
