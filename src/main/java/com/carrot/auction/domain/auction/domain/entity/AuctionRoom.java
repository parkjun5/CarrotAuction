package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.account.domain.entity.Account;
import com.carrot.auction.domain.account.serailizer.AccountSerializer;
import com.carrot.auction.global.domain.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
public class AuctionRoom extends BaseEntity implements ChatRoom {

    @Id @GeneratedValue
    @Column(name="auction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonSerialize(using = AccountSerializer.class)
    private Account hostUser;

    @OneToMany
    @JsonSerialize(using = AccountSerializer.class)
    private List<Account> participants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuctionRoom auction = (AuctionRoom) o;
        return id != null && Objects.equals(id, auction.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
