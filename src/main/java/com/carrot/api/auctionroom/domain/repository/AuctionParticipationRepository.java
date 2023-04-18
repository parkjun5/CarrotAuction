package com.carrot.api.auctionroom.domain.repository;

import com.carrot.api.auctionroom.domain.AuctionParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionParticipationRepository extends JpaRepository<AuctionParticipation, Long>, AuctionParticipationCustomRepository {

}
