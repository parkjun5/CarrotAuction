package com.carrot.auction.domain.item.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Item {

    @NotBlank private String title;
    @NotNull private Integer price;
    @NotBlank private String content;

    public static Item of(String title, Integer price, String content) {
        Item item = new Item();
        item.title = title;
        item.price = price;
        item.content = content;
        return item;
    }

    public void changeInfo(Item item) {
        if (StringUtils.hasText(item.getTitle()) && !title.equals(item.getTitle())) {
            title = item.getTitle();
        }
        if (price != null && !price.equals(item.getPrice())) {
            price = item.getPrice();
        }
        if (StringUtils.hasText(item.getContent()) && !content.equals(item.getContent())) {
            content = item.getContent();
        }
    }

}
