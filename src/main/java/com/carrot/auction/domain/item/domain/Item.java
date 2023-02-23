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

    public static Item of(String title, Integer price, String content) {
        Item item = new Item();
        item.title = title;
        item.price = price;
        item.content = content;
        return item;
    }

    private Item changeInfo(Item item) {
        title = item.title;
        price = item.price;
        content = item.content;
        return this;
    }
}
