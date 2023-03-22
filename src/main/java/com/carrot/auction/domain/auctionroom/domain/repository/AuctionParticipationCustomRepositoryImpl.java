package com.carrot.auction.domain.auctionroom.domain.repository;


import com.carrot.auction.domain.auctionroom.domain.entity.AuctionParticipation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.carrot.auction.domain.auctionroom.domain.entity.QAuctionParticipation.*;

@RequiredArgsConstructor
public class AuctionParticipationCustomRepositoryImpl implements AuctionParticipationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<AuctionParticipation> findOneByUserIdAndAuctionRoomId(Long userId, Long roomId) {
        AuctionParticipation auctionParticipant = queryFactory.selectFrom(auctionParticipation)
                .where(auctionParticipation.user.id.eq(userId)
                        .and(auctionParticipation.auctionRoom.id.eq(roomId)))
                .fetchOne();

        return Optional.ofNullable(auctionParticipant);
    }
}
