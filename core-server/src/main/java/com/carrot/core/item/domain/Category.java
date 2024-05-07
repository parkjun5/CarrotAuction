package com.carrot.core.item.domain;



import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Category {
    DIGITAL("디지털기기"),
    HOUSEHOLD_APPLIANCES("생활가전"),
    FURNITURE_INTERIOR("가구/인테리어"),
    LIVING_KITCHEN("생활/주방"),
    FOR_INFANTS("유아용"),
    WOMEN_CLOTHING("여성의류"),
    GOODS_FOR_WOMEN("여성잡화"),
    MEN_FASHION("남성패션"),
    BEAUTY("뷰티/미용"),
    SPORTS("스포츠/레저"),
    HOBBY_GAME_MUSIC("취미/게임/음반"),
    WTB("삽니다")
    ;

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public static Category findByName(String name) {
        return Arrays.stream(values())
                .filter(category -> category.name.equals(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name + "카테고리는 존재하지 않습니다."));
    }
}
