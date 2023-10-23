package com.animalplatform.platform.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class SessionResponse implements Serializable {

    private static final long serialVersionUID = 8_162_166_523_751_584_964L;

    private Long userNo;

    private String userId;

    private String userName;

    private String nickName;

    private String email;
    //초기 비밀번호 변경 여부
    private String initialPasswordYn;
    //비밀번호 만료 여부
    private String isExpiredPassword;
}
