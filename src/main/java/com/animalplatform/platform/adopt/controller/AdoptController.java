package com.animalplatform.platform.adopt.controller;

import com.animalplatform.platform.adopt.dto.AddAdoptRequest;
import com.animalplatform.platform.adopt.dto.DelAdoptRequest;
import com.animalplatform.platform.adopt.dto.ModAdoptRequest;
import com.animalplatform.platform.adopt.service.AdoptService;
import com.animalplatform.platform.define.RsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
                               @RequestBody @Validated ModAdoptRequest request) {
        return adoptService.updateAdopt(request);

    }

    @GetMapping(value = "/get")
    public RsResponse<Object> getAdopt(
//                                @SessionUser SessionResponse session,
                               @RequestParam Long adoptNo) {
        return adoptService.getAdopt(adoptNo);

    }

    @GetMapping(value = "/get-list")
    public RsResponse<Object> getAdoptList() {
        return adoptService.getAdoptList();

    }

    @PostMapping(value = "/delete")
    public RsResponse<Object> deleteAdopt(
//                                @SessionUser SessionResponse session,
            @RequestBody @Validated DelAdoptRequest request) {
        return adoptService.deleteAdopt(request);

    }



}
