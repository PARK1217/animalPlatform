package com.animalplatform.platform.user.entity;

import com.animalplatform.platform.user.dto.RegUserResponse;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jdk.jfr.Description;
import lombok.*;
import org.hibernate.annotations.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Format;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Description("회원정보")
@Table(name = "user_t")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false, unique = true)
    @Comment("회원번호")
    private Long userNo;

    @Column(name = "user_id", length = 50)
    @Comment("회원아이디")
    private String userId;

    @Column(name = "user_pw", length = 100)
    @Comment("회원비밀번호")
    private String userPw;

    @Column(name = "user_name", length = 50)
    @Comment("회원이름")
    private String userName;

    @Column(name = "email", length = 100)
    @Comment("이메일")
    @Format(formats = "email")
    private String email;

    @Column(name = "phone", length = 20)
    @Comment("연락처")
    private String phone;

    @Column(name = "last_login")
    @Comment("최종접속일시")
    private LocalDateTime lastLogin;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    @Comment("등록일시")
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(length = 20)
    @Comment("수정일시")
    private LocalDateTime modDate;

    @ColumnDefault("'N'")
    @Comment("삭제여부")
    private String delYn;


    public RegUserResponse toRegUserResponse() {
        return RegUserResponse.builder()
                .userNo(userNo)
                .userId(userId)
                .userName(userName)
                .email(email)
                .phone(phone)
                .regDate(regDate)
                .modDate(modDate)
                .build();
    }
}
