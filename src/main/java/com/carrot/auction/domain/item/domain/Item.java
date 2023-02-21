package com.carrot.auction.domain.item.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    private String title;
    private Integer price;
    private String content;

    public Item of(String title, Integer price, String content) {
        this.title = title;
        this.price = price;
        this.content = content;
        return this;
    }

    private Item changeInfo(Item item) {
        title = item.title;
        price = item.price;
        content = item.content;
        return this;
    }
}
