package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.bid.domain.entity.Bid;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
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
    private int bidStartPrice;
    private int limitOfEnrollment;
    private ZonedDateTime beginDateTime;
    private ZonedDateTime closeDateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    //TODO UserDTO 생성이전까지만
    @JoinColumn(name = "user_id")
    private User hostUser;
    @Enumerated(EnumType.STRING) private Category category;
    @OneToMany(mappedBy = "auctionRoom")
    @Builder.Default
    private Set<AuctionParticipation> auctionParticipation = new HashSet<>();
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bid_id")
    private Bid bid;
    @Embedded private Item item;
    @Builder.Default
    @Enumerated(EnumType.STRING) private AuctionStatus auctionStatus = AuctionStatus.DRAFT;

    public void updateAuctionInfo(String name, String password, int limitOfEnrollment, int bidStartPrice, ZonedDateTime beginDateTime, ZonedDateTime closeDateTime) {
        this.name = name;
        this.password = password;
        this.bidStartPrice = bidStartPrice;
        this.limitOfEnrollment = limitOfEnrollment;
        this.beginDateTime = beginDateTime;
        this.closeDateTime = closeDateTime;
    }

    public void updateItem(String title, int price, String content, Category category) {
        item.changeInfo(title, price, content);
        this.category = category;
    }

    public void createBid(Bid bid) {
        this.bid = bid;
        bid.setAuctionRoom(this);
    }

    public Set<String> getParticipantsNicknames() {
        return getAuctionParticipation()
                .stream()
                .map(AuctionParticipation::getUser)
                .map(User::getNickname)
                .collect(Collectors.toSet());
    }
}
