package com.animalplatform.platform.adopt.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ModAdoptRequest {

    private Long adoptNo;
    private String adoptTitle;
    private String adoptContent;
    private String adoptType;
    private String adoptKind;
    private String adoptRegion;
    private String adoptFile;
}
