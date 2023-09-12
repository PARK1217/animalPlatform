package com.animalplatform.platform.adopt.entity.converter;

import com.animalplatform.platform.adopt.entity.enums.AdoptKind;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AdoptKindConverter implements AttributeConverter<AdoptKind, String> {

    @Override
    public String convertToDatabaseColumn(AdoptKind attribute) {
        if (attribute == null) {
            return null;
        }


        return attribute.name();
    }

    @Override
    public AdoptKind convertToEntityAttribute(String dbData) {
        return AdoptKind.filterAdoptKind(dbData);
    }
}
