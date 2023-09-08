package com.animalplatform.platform.adopt.entity;

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

    @Column(name = "adopt_title", length = 100)
    @Comment("입양/분양제목")
    private String adoptTitle;

    @Column(name = "adopt_writer", length = 100)
    @Comment("입양/분양작성자")
    private String adoptWriter;

    @Column(name = "adopt_content", length = 100)
    @Comment("입양/분양내용")
    private String adoptContent;

    @Column(name = "adopt_hit", length = 100)
    @Comment("입양/분양조회수")
    private String adoptHit;

    @Column(name = "adopt_type", length = 100)
    @Comment("입양/분양종류")
    private String adoptType;

    @Column(name = "adopt_kind", length = 100)
    @Comment("입양/분양동물종류")
    private String adoptKind;

    @Column(name = "adopt_region", length = 100)
    @Comment("입양/분양지역")
    private String adoptRegion;

    @Column(name = "adopt_file", length = 100)
    @Comment("입양/분양파일")
    private String adoptFile;

    @Column(name = "del_yn", length = 100)
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
}
