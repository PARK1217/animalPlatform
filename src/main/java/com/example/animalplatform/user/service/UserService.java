package com.example.animalplatform.user.service;

import com.example.animalplatform.define.ReturnStatus;
import com.example.animalplatform.define.RsResponse;
import com.example.animalplatform.user.dto.RegUserRequest;
import com.example.animalplatform.user.dto.RegUserResponse;
import com.example.animalplatform.user.entity.User;
import com.example.animalplatform.user.exception.UserException;
import com.example.animalplatform.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

public RsResponse regUser(RegUserRequest regUserRequest) {
    //중복되는 아이디가 있는지 확인
    isExistUser(regUserRequest.getUserId(), regUserRequest.getEmail());
    User user = userRepository.findByUserId(regUserRequest.getUserId()).orElse(userRepository.save(regUserRequest.toEntity()));

    RegUserResponse response = user.toRegUserResponse();

    return new RsResponse<>(ReturnStatus.SUCCESS, null, response);



    }

    private void isExistUser(String userId, String email) {
        User user = userRepository.findByUserId(userId).orElse(null);

        if (user != null) {
            throw new UserException(ReturnStatus.FAIL_EXIISTED_USER, String.format("userId : %s", userId));
        }

        user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            throw new UserException(ReturnStatus.FAIL_EXIISTED_EMAIL, String.format("email : %s", email));
        }
    }

}
