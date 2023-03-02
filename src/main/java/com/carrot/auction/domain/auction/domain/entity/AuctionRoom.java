package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.serailizer.UserSerializer;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
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
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.util.Assert.*;

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

    @Builder(builderClassName = "createByRequestBuilder", builderMethodName = "createByRequestBuilder")
    public AuctionRoom(User hostUser, AuctionRequest auctionRequest) {
        notNull(hostUser,  "유저는 널일 수 없습니다.");
        notNull(auctionRequest, "생성 요청은 널일 수 없습니다.");

        this.hostUser = hostUser;
        this.password = auctionRequest.password();
        this.name = auctionRequest.name();
        this.item = auctionRequest.item();
        this.category = auctionRequest.category();
        this.limitOfEnrollment = auctionRequest.limitOfEnrollment();
        this.beginAuctionDateTime = auctionRequest.beginAuctionDateTime();
        this.closeAuctionDateTime = auctionRequest.closeAuctionDateTime();
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
        notNull(user,  "유저는 널일 수 없습니다.");
        user.getAuctionRooms().add(this);
        participants.add(user);
    }

    public void changeInfoByRequest(AuctionRequest request) {
        if (StringUtils.hasText(request.name()) && !name.equals(request.name())) {
            name = request.name();
        }
        if (StringUtils.hasText(request.password()) && !request.password().equals(password)) {
            password = request.password();
        }
        if (request.limitOfEnrollment() > 0 && limitOfEnrollment != request.limitOfEnrollment()) {
            limitOfEnrollment = request.limitOfEnrollment();
        }
        if (request.beginAuctionDateTime() != null && !request.beginAuctionDateTime().equals(beginAuctionDateTime)) {
            name = request.name();
        }
        if (request.closeAuctionDateTime() != null && !request.closeAuctionDateTime().equals(closeAuctionDateTime)) {
            closeAuctionDateTime = request.closeAuctionDateTime();
        }
        if (request.item() != null && !request.item().equals(item)) {
            item.changeInfo(request.item());
        }
        if (request.category() != null && !request.category().equals(category)) {
            category = request.category();
        }
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
