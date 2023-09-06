package com.example.animalplatform.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegUserResponse {

    private Long userNo;
    private String userId;
    private String userPw;
    private String userName;
    private String email;
    private String phone;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
