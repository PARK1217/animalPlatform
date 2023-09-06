package com.animalplatform.platform.user.controller;

import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.user.dto.RegUserRequest;
import com.animalplatform.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/reg")
    public RsResponse regUser(@RequestBody @Validated RegUserRequest regUserRequest) {
        return userService.regUser(regUserRequest);
    }

}
