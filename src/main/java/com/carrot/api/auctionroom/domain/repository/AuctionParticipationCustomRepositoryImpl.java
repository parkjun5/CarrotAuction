package com.carrot.api.auctionroom.domain.repository;


import com.carrot.api.auctionroom.domain.AuctionParticipation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.carrot.api.auctionroom.domain.QAuctionParticipation.*;

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
