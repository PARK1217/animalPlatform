package com.animalplatform.platform.user.service;

import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.user.dto.RegUserRequest;
import com.animalplatform.platform.user.dto.RegUserResponse;
import com.animalplatform.platform.user.entity.User;
import com.animalplatform.platform.user.exception.UserException;
import com.animalplatform.platform.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

public RsResponse<Object> regUser(RegUserRequest regUserRequest) {
    //중복되는 아이디 또는 이메일이 있는지 확인
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
