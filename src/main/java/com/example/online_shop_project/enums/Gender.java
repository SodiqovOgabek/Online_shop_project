package com.example.online_shop_project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Gender {
    WOMAN("Woman"), MAN("Man"), KIDS("Kids");

    private final String key;

    public static Gender findByName(String genre) {
        return Arrays.stream(Gender.values())
                .filter(genre1 -> genre1.name().equalsIgnoreCase(genre))
                .findFirst()
                .orElse(Gender.MAN);
    }

    public String getKey() {
        return key;
    }
}

