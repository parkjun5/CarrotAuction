package com.carrot.parkjun5.auctionroom.domain.repository;

import com.carrot.parkjun5.auctionroom.domain.AuctionParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionParticipationRepository extends JpaRepository<AuctionParticipation, Long>, AuctionParticipationCustomRepository {

}
