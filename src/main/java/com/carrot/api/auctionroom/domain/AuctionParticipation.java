package com.carrot.api.auctionroom.domain;

import com.carrot.api.user.domain.User;
import com.carrot.api.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionParticipation extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "auction_participation_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_room_id")
    private AuctionRoom auctionRoom;

    /**
     * 연관 관계 메소드
     */

    public void setUser(User user) {
        this.user = user;
        user.getParticipatedAuctionRoom().add(this);
    }

    public void setAuctionRoom(AuctionRoom auctionRoom) {
        this.auctionRoom = auctionRoom;
        auctionRoom.getAuctionParticipation().add(this);
    }

    public static AuctionParticipation createAuctionParticipation(User user, AuctionRoom auctionRoom) {
        AuctionParticipation auctionParticipation = new AuctionParticipation();
        auctionParticipation.setUser(user);
        auctionParticipation.setAuctionRoom(auctionRoom);
        return auctionParticipation;
    }

}
