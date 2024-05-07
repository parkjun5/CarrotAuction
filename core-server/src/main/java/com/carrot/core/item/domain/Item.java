package com.carrot.core.item.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @NotBlank
    @Schema(description = "상품 명", example = "맥북")
    private String title;
    @Min(value=1_000, message = "천원보다는 비싼 금액을 입력하세요." )
    @Schema(description = "상품 가격", example = "10000")
    private int price;
    @NotBlank
    @Schema(description = "상품 설명", example = "코딩 속도가 100% 상승한다는 소문이 있는 맥북")
    private String content;

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
