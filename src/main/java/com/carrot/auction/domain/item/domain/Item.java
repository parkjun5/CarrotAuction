package com.carrot.auction.domain.item.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Item {

    @NotBlank private String title;
    @Min(value=1_000, message = "천원보다는 비싼 금액을 입력하세요." ) private int price;
    @NotBlank private String content;

    public static Item of(String title, Integer price, String content) {
        Item item = new Item();
        item.title = title;
        item.price = price;
        item.content = content;
        return item;
    }

    public void changeInfo(String title, int price, String content) {
        this.title = title;
        this.price = price;
        this.content = content;
    }

}
