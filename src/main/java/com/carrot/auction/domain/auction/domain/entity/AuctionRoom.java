package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.auction.domain.Bid;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.Assert.*;

@Entity
@Getter
@Builder @AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper=false)
public class AuctionRoom extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="auction_room_id")
    private Long id;
    private String name;
    private String password;
    private int limitOfEnrollment;
    @Embedded private Bid bid;
    private ZonedDateTime beginAuctionDateTime;
    private ZonedDateTime closeAuctionDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User hostUser;
    @OneToMany
    @Builder.Default
    private List<User> participants = new ArrayList<>();
    @Embedded private Item item;
    @Enumerated(EnumType.STRING) private Category category;
    @Builder.Default
    @Enumerated(EnumType.STRING) private AuctionStatus auctionStatus = AuctionStatus.DRAFT;

    public void addParticipants(User user) {
        notNull(user,  "유저는 필수입니다.");
        user.getAuctionRooms().add(this);
        participants.add(user);
    }
    
    public void updateAuctionInfo(String name, String password, int limitOfEnrollment, int biddingPrice, ZonedDateTime beginAuctionDateTime, ZonedDateTime closeAuctionDateTime) {
        this.name = name;
        this.password = password;
        this.limitOfEnrollment = limitOfEnrollment;
        this.bid.changeStartPrice(biddingPrice);
        this.beginAuctionDateTime = beginAuctionDateTime;
        this.closeAuctionDateTime = closeAuctionDateTime;
    }

    public void updateItem(String title, int price, String content, Category category) {
            item.changeInfo(title, price, content);
            this.category = category;
    }
}
