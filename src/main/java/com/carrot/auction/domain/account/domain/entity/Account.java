package com.carrot.auction.domain.account.domain.entity;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class Account extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Integer id;
    private String email;
    private String nickname;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    @OneToMany(mappedBy = "hostUser")
    @Builder.Default
    private List<AuctionRoom> auctions = new ArrayList<>();

    /**
     * 생성 메서드
     */
    public static Account of(String email, String nickname, String password) {
        return Account.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .roles(Set.of(AccountRole.USER))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
