package com.animalplatform.platform.adopt.entity;

import com.animalplatform.platform.adopt.dto.AdoptResponse;
import com.animalplatform.platform.adopt.entity.converter.AdoptKindConverter;
import com.animalplatform.platform.adopt.entity.converter.AdoptTypeConverter;
import com.animalplatform.platform.adopt.entity.enums.AdoptKind;
import com.animalplatform.platform.adopt.entity.enums.AdoptType;
import com.animalplatform.platform.user.entity.User;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Description("입양/분양게시판")
@Table(name = "adopt_t")
@DynamicUpdate
public class Adopt {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "adopt_no", nullable = false, unique = true)
    @Comment("입양/분양번호")
    private Long adoptNo;

    //회원번호로 참조 (작성자이름, 작성자이메일, 작성자연락처)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    @Comment("회원번호")
    private User user;

    @Column(name = "adopt_title", length = 100)
    @Comment("입양/분양제목")
    private String adoptTitle;

    @Column(name = "adopt_content", length = 500)
    @Comment("입양/분양내용")
    private String adoptContent;

    @Column(name = "adopt_hit", columnDefinition = "int default 0", nullable = false)
    @Comment("입양/분양조회수")
    private int adoptHit;

    @Convert(converter = AdoptTypeConverter.class)
    @Column(name = "adopt_type", length = 1, nullable = false)
    @Comment("입양/분양종류")
    private AdoptType adoptType;

    @Convert(converter = AdoptKindConverter.class)
    @Column(name = "adopt_kind", length = 3, nullable = false)
    @Comment("입양/분양동물종류")
    private AdoptKind adoptKind;

    @Column(name = "adopt_region", length = 100)
    @Comment("입양/분양지역")
    private String adoptRegion;

    @Column(name = "adopt_file", length = 100)
    @Comment("입양/분양파일")
    private String adoptFile;

    @Column(name = "del_yn", columnDefinition = "char(10) default 'N'", nullable = false)
    @Comment("입양/분양삭제여부")
    private String DelYn;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    @Comment("등록일시")
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(length = 20)
    @Comment("수정일시")
    private LocalDateTime modDate;

    public AdoptResponse toAddAdoptResponse() {
        return AdoptResponse.builder()
                .adoptNo(adoptNo)
                .adoptTitle(adoptTitle)
                .adoptWriter(user.getUserName())
                .adoptContent(adoptContent)
                .adoptType(this.adoptType.name())
                .adoptKind(this.adoptKind.name())
                .adoptRegion(adoptRegion)
                .adoptFile(adoptFile)
                .regDate(regDate)
                .modDate(modDate)
                .build();
    }
}
