package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

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
        user.getParticipatedRoom().add(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuctionParticipation that = (AuctionParticipation) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
