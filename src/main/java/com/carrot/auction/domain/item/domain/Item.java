package com.carrot.auction.domain.item.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @NotBlank private String title;
    @NotBlank private Integer price;
    @NotBlank private String content;

    public static Item of(String title, Integer price, String content) {
        Item item = new Item();
        item.title = title;
        item.price = price;
        item.content = content;
        return item;
    }

}
