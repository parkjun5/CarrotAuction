package com.carrot.core.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public  class BaseEntity {
    @Column(updatable = false)
    private ZonedDateTime createDate;

    private ZonedDateTime updateDate;

    @PrePersist
    private void prePersist() {
        createDate = ZonedDateTime.now();
        updateDate = ZonedDateTime.now();
    }

    @PreUpdate
    private void  preUpdate() {
        updateDate = ZonedDateTime.now();
    }
}
