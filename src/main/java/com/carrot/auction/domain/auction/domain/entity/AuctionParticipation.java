package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuctionParticipation {
    @Id @GeneratedValue
    @Column(name = "auction_participation_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_room_id")
    private AuctionRoom auctionRoom;
    private ZonedDateTime lastAccessDateTime;

}
