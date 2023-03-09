package com.carrot.auction.domain.user.domain.entity;

import com.carrot.auction.domain.auction.domain.entity.AuctionParticipation;
import com.carrot.auction.global.domain.BaseEntity;
import com.mysema.commons.lang.Assert;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String nickname;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = Set.of(UserRole.USER);

    @OneToMany(mappedBy = "user")
    private List<AuctionParticipation> participatedRoom = new ArrayList<>();

    /**
     * 생성 메서드
     */
    @Builder(builderClassName = "createUser", builderMethodName = "createUser")
    public User(String email, String nickname, String password) {
        Assert.hasText(email, "이메일은 빈값일 수 없습니다.");
        Assert.hasText(nickname, "닉네임은 빈값일 수 없습니다.");
        Assert.hasText(password, "패스워드는 빈값일 수 없습니다.");
        
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void addAuctionParticipation(AuctionParticipation auctionParticipation) {
        this.participatedRoom.add(auctionParticipation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
