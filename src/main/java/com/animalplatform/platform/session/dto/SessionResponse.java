package com.animalplatform.platform.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
    TODO : 변경 또는 삭제될 수 있음,  예시로 사용되는 세션 저장용
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SessionResponse implements Serializable {

    /**
     *  직렬화 관련 내용 => https://madplay.github.io/post/java-serialization-advanced
     *  멤버 변수를 추가, 삭제, 이름변경, 타입변경, 접근 제어자 변경시 살펴볼 것
     */

    private static final long serialVersionUID = 5_166_343_196_751_584_964L;

//    private Long userId;
//
//    private String username;
//
//    private String customerCode;
//
//    private String customerCodeName;
//
//    private String locationInfoConsentStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
    private LocalDateTime locationInfoConsentTime;

    // 비즈케어 고객사 코드
    private String compCode;

//    @Override
//    public String toString() {
//        return String.format("접속중인 사용자 id: %d, name: %s, 약관동의 여부 : %s, compCode: %s",
//                this.userId, this.username, this.locationInfoConsentStatus, this.compCode);
//    }
}