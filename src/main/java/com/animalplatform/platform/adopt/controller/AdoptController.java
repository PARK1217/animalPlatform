package com.animalplatform.platform.adopt.controller;

import com.animalplatform.platform.adopt.dto.AddAdoptRequest;
import com.animalplatform.platform.adopt.service.AdoptService;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.session.annotation.SessionUser;
import com.animalplatform.platform.session.dto.SessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/adopt")
public class AdoptController {

    private final AdoptService adoptService;

    @PostMapping(value = "/add")
    public RsResponse<Object> addAdopt(
//                                @SessionUser SessionResponse session,
                               @RequestBody @Validated AddAdoptRequest addAdoptRequest) {
        return adoptService.addAdopt(addAdoptRequest);

    }

    @PostMapping(value = "/update")
    public RsResponse<Object> updateAdopt(
//                                @SessionUser SessionResponse session,
                               @RequestBody @Validated AddAdoptRequest addAdoptRequest) {
        return adoptService.addAdopt(addAdoptRequest);

    }

}
