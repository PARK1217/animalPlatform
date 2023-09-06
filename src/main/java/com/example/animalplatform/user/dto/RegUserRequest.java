package com.example.animalplatform.user.dto;

import com.example.animalplatform.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RegUserRequest {


    private String userId;
    private String userPw;
    private String userName;
    private String email;
    private String phone;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .email(email)
                .phone(phone)
                .delYn("N")
                .build();
    }
}
