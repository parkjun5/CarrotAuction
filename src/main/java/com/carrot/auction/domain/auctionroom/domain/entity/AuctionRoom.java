package com.carrot.auction.domain.auctionroom.domain.entity;

import com.carrot.auction.domain.auction.domain.entity.Auction;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder @AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionRoom extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="auction_room_id")
    private Long id;
    private String name;
    private String password;
    private int limitOfEnrollment;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User hostUser;
    @OneToMany(mappedBy = "auctionRoom")
    @Builder.Default
    private Set<AuctionParticipation> auctionParticipation = new HashSet<>();
    @OneToMany(mappedBy = "auctionRoom")
    @Builder.Default
    private List<Auction> auctions = new ArrayList<>();

    public void addAuction(Auction auction) {
        auction.setAuctionRoom(this);
        this.auctions.add(auction);
    }

    public void changeAuctionRoom(String name, String password, int limitOfEnrollment) {
        this.name = name;
        this.password = password;
        this.limitOfEnrollment = limitOfEnrollment;
    }

    public Set<String> getParticipantsNicknames() {
        return getAuctionParticipation()
                .stream()
                .map(AuctionParticipation::getUser)
                .map(User::getNickname)
                .collect(Collectors.toSet());
    }
}
