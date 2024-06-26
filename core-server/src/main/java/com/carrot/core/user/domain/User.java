package com.carrot.core.user.domain;

import com.carrot.core.auctionroom.domain.AuctionParticipation;
import com.carrot.core.chat.domain.ChatRoomParticipation;
import com.carrot.core.common.domain.BaseEntity;
import com.carrot.core.user.application.dto.UserRequest;
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<AuctionParticipation> participatedAuctionRoom = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatRoomParticipation> participatedChatRoom = new ArrayList<>();

    public static User of(UserRequest userRequest) {
        User user = new User();
        user.email = userRequest.email();
        user.nickname = userRequest.nickname();
        user.password = userRequest.password();
        return user;
    }

    public void changeInfo(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void deleteUser() {
        isDeleted = true;
    }
}
