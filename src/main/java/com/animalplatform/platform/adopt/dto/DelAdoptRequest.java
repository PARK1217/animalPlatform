package com.animalplatform.platform.adopt.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class DelAdoptRequest {

    private Long adoptNo;
}
