package com.animalplatform.platform.adopt.entity.converter;

import com.animalplatform.platform.adopt.entity.enums.AdoptType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AdoptTypeConverter implements AttributeConverter<AdoptType, String> {

    //Database에 저장할 때 Enum 값을 어떤 형태로 저장할지 결정
    @Override
    public String convertToDatabaseColumn(AdoptType attribute) {
        if (attribute == null) {
            return null;
        }


        return attribute.getType();
    }

    // 조회해서 가져왔을 때 AdoptType으로 변환
    @Override
    public AdoptType convertToEntityAttribute(String dbData) {
        return AdoptType.filterAdoptType(dbData);
    }

}
