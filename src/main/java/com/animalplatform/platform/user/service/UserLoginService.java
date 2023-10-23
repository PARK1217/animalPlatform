package com.animalplatform.platform.user.service;

import com.animalplatform.platform.user.entity.User;
import com.animalplatform.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private static final String IS_NOT_MATCH_PASSWORD_MESSAGE = "패스워드를 잘못 입력하였습니다.";

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUserId(String userId) {
        return userRepository.findByEmail(userId)
                .orElse(null);
    }
}
