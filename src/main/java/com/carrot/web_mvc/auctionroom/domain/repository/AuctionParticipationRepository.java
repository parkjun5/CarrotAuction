package com.carrot.web_mvc.auctionroom.domain.repository;

import com.carrot.web_mvc.auctionroom.domain.AuctionParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionParticipationRepository extends JpaRepository<AuctionParticipation, Long>, AuctionParticipationCustomRepository {

}
