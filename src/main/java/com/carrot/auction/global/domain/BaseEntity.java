package com.carrot.auction.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public  class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private ZonedDateTime createDate;

    @LastModifiedDate
    private ZonedDateTime updateDate;
}
