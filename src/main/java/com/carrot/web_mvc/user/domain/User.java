package com.carrot.web_mvc.user.domain;

import com.carrot.web_mvc.auctionroom.domain.AuctionParticipation;
import com.carrot.web_mvc.common.domain.BaseEntity;
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
    private List<AuctionParticipation> participatedRoom = new ArrayList<>();

    public void changeInfo(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void deleteUser() {
        isDeleted = true;
    }
}
