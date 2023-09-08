package com.animalplatform.platform.adopt.dto;

import com.animalplatform.platform.adopt.entity.Adopt;
import com.animalplatform.platform.adopt.entity.enums.AdoptType;
import com.animalplatform.platform.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class AddAdoptRequest {

    private Long userNo;
    private String adoptTitle;
    private String adoptContent;
    private String adoptType;
    private String adoptKind;
    private String adoptRegion;
    private String adoptFile;

    public Adopt toEntity(User user) {
        return Adopt.builder()
                .user(user)
                .adoptTitle(this.adoptTitle)
                .adoptType(AdoptType.filterAdoptType(adoptType))
                .adoptContent(adoptContent)
                .adoptKind(this.adoptKind)
                .adoptRegion(adoptRegion)
                .adoptFile(adoptFile)
                .build();
    }
}
