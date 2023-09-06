package com.example.animalplatform.user.controller;

import com.example.animalplatform.define.RsResponse;
import com.example.animalplatform.user.dto.RegUserRequest;
import com.example.animalplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public RsResponse regUser(@RequestBody RegUserRequest regUserRequest) {
        return userService.regUser(regUserRequest);
    }

}
