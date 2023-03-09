package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private ZonedDateTime beginDateTime;
    private ZonedDateTime closeDateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    //TODO UserDTO 생성이전까지만
    @JoinColumn(name = "user_id")
    private User hostUser;
    @Enumerated(EnumType.STRING) private Category category;

    @OneToMany(mappedBy = "auctionRoom")
    @Builder.Default
    private Set<AuctionParticipation> participants = new HashSet<>();

    public void updateAuctionInfo(String name, String password, int limitOfEnrollment, ZonedDateTime beginDateTime, ZonedDateTime closeDateTime, Category category) {
        this.name = name;
        this.password = password;
        this.limitOfEnrollment = limitOfEnrollment;
        this.beginDateTime = beginDateTime;
        this.closeDateTime = closeDateTime;
        this.category = category;
    }
        public void addParticipants(User user) {
        notNull(user,  "유저는 필수입니다.");
            AuctionParticipation build = AuctionParticipation.builder().user(user)
                    .auctionRoom(this).build();
            participants.add(build);
        user.addAuctionParticipation(build);
    }

}
