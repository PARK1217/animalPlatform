package com.animalplatform.platform.user.dto;

import com.animalplatform.platform.user.entity.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RegUserRequest {

    @NotNull(message = "아이디를 입력해주세요.")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+])(?=\\S+$).{8,20}$", message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자리로 입력해주세요.")
    private String userPw;

    private String userName;

    @NotNull(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotNull(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
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
