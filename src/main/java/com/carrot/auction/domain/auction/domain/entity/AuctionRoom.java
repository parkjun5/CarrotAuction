package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.serailizer.UserSerializer;
import com.carrot.auction.domain.auction.dto.CreateAuctionRequest;
import com.carrot.auction.domain.chat.domain.BaseChatRoom;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.global.domain.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionRoom extends BaseEntity implements BaseChatRoom {

    @Id @GeneratedValue
    @Column(name="auction_room_id")
    private Long id;
    private String name;
    private String password;
    private int limitOfEnrollment;
    private LocalDateTime beginAuctionDateTime;
    private LocalDateTime closeAuctionDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonSerialize(using = UserSerializer.class)
    private User hostUser;
    @OneToMany
    @JsonSerialize(contentUsing = UserSerializer.class)
    private List<User> participants = new ArrayList<>();
    @Embedded
    private Item item;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private AuctionStatus auctionStatus = AuctionStatus.DRAFT;

    @Builder(builderClassName = "createByRequest", builderMethodName = "createByRequest")
    public AuctionRoom(User hostUser, CreateAuctionRequest createAuctionRequest) {
        Assert.notNull(hostUser, () -> "유저는 널일 수 없습니다.");
        Assert.notNull(createAuctionRequest, () -> "생성 요청은 널일 수 없습니다.");

        this.hostUser = hostUser;
        this.password = createAuctionRequest.password();
        this.name = createAuctionRequest.name();
        this.item = createAuctionRequest.item();
        this.category = createAuctionRequest.category();
        this.limitOfEnrollment = createAuctionRequest.limitOfEnrollment();
        this.beginAuctionDateTime = createAuctionRequest.beginAuctionDateTime();
        this.closeAuctionDateTime = createAuctionRequest.closeAuctionDateTime();
    }

    @Override
    public Long sendChat(Long senderId, String message) {
        return null;
    }

    @Override
    public Long readChat(Long readerId) {
        return null;
    }

    @Override
    public void addParticipants(User user) {
        //TODO 발리데이트 추가 필요
        user.getAuctionRooms().add(this);
        this.participants.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuctionRoom that = (AuctionRoom) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
