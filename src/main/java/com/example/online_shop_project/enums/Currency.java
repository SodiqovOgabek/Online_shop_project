package com.example.online_shop_project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@AllArgsConstructor
@Getter
public enum Currency {
    UZS("UZS"),
    USD("$"),
    EURO("EURO");


    private final String key;

    public static Currency findByName(String genre) {
        return Arrays.stream(Currency.values())
                .filter(genre1 -> genre1.name().equalsIgnoreCase(genre))
                .findFirst()
                .orElse(Currency.USD);
    }

    public String getKey() {
        return key;
    }
}
