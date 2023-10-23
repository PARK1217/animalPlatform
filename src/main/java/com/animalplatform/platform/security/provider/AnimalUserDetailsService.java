package com.animalplatform.platform.security.provider;

import com.animalplatform.platform.log.JLog;
import com.animalplatform.platform.user.entity.User;
import com.animalplatform.platform.user.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalUserDetailsService implements UserDetailsService {

    private final UserLoginService userLoginService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        JLog.logd("Login Request UserId : " + userId);

        User entity = userLoginService.findByUserId(userId);

        return entity.toAuthenticationUserDetails();
    }
}
