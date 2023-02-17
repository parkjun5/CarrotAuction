package com.carrot.auction.domain.account.domain.entity;

import com.carrot.auction.domain.account.serailizer.AccountSerializer;
import com.carrot.auction.global.domain.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

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

    @OneToMany
    @JoinColumn(name = "account_id")
    @JsonSerialize(using = AccountSerializer.class)
    private Account hostUser;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

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
