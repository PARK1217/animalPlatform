package com.animalplatform.platform.security.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDto {

    private String userId;

    private String userPw;

    //인증코드
//    private String authNo;

    //인증코드 확인 여부
//    public Authentication convertAuthenticationToken() {
//        return new UsernamePasswordAuthenticationToken(
//                this.email,
//                this.password
//        );
//    }
}