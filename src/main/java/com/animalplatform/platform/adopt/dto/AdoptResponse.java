package com.animalplatform.platform.adopt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AdoptResponse {
    private Long adoptNo;
    private String adoptTitle;
    private String adoptWriter;
    private String adoptContent;
    private String adoptType;
    private String adoptKind;
    private String adoptRegion;
    private String adoptFile;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "Asia/Seoul")
    private LocalDateTime regDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "Asia/Seoul")
    private LocalDateTime modDate;

}
