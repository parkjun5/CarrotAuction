package com.carrot.auction.domain.auctionroom.domain.repository;

import com.carrot.auction.domain.auctionroom.domain.entity.AuctionParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionParticipationRepository extends JpaRepository<AuctionParticipation, Long>, AuctionParticipationCustomRepository {

}
