package com.animalplatform.platform.adopt.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AdoptType {

    SELL_A_PET("S"),
    GET_A_PET("G");

    private final String type;

    public static AdoptType filterAdoptType(String type) {
        return Arrays.stream(values())
                .filter(adoptType -> adoptType.type.equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }

}