package com.animalplatform.platform.adopt.entity.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AdoptKind {

    DOG,    // 개
    CAT,    // 고양이
    ETC;    // 기타


    public static AdoptKind filterAdoptKind(String kind) {
        return Arrays.stream(values())
                .filter(adoptKind -> adoptKind.name().equalsIgnoreCase(kind))
                .findFirst()
                .orElse(null);
    }
}
