package com.carrot.api.user.domain;

import com.carrot.api.auctionroom.domain.AuctionParticipation;
import com.carrot.api.chat.domain.ChatRoomParticipation;
import com.carrot.api.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder @AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String nickname;
    private String password;
    @Builder.Default
    private boolean isDeleted = false;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<UserRole> roles = Set.of(UserRole.USER);
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<AuctionParticipation> participatedAuctionRoom = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<ChatRoomParticipation> participatedChatRoom = new ArrayList<>();

    public void changeInfo(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void deleteUser() {
        isDeleted = true;
    }
}
