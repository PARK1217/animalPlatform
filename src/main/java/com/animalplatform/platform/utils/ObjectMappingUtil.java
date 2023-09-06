package com.animalplatform.platform.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.animalplatform.platform.log.JLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectMappingUtil {

    private final ObjectMapper objectMapper;

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.");
        }
    }

    /**
     * 문자열을 특정 타입으로 변환한다.
     * @param jsonData
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T readJsonValue(String jsonData, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonData, clazz);
        } catch (JsonProcessingException e) {
            JLog.loge(String.format("데이터 타입 변환 실패, data :  %s, Class :  %s", jsonData, clazz));
            throw new RuntimeException("잘못된 접근입니다. 관리자에게 문의해주세요.");
        }
    }

    public JsonNode readTree(String str) {
        try {
            return objectMapper.readTree(str);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.");
        }
    }
}
