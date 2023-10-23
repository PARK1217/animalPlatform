package com.animalplatform.platform.security.provider;

import com.animalplatform.platform.log.JLog;
import com.animalplatform.platform.user.service.UserLoginService;
import com.animalplatform.platform.utils.AesUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class AnimalAuthenticationProvider extends DaoAuthenticationProvider {

    private final AesUtil aesUtil;
    private final UserLoginService userLoginService;

    public AnimalAuthenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            AesUtil aesUtil,
            UserLoginService userLoginService
    ) {
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(passwordEncoder);
        this.aesUtil = aesUtil;
        this.userLoginService = userLoginService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String encodedLoginPassword = (String) authentication.getCredentials();
        String email = authentication.getName();

        LocalDateTime accessTime = LocalDateTime.now().withNano(0);

//        isMatchPassword(email, encodedLoginPassword, userDetails.getPassword(), accessTime);
    }

    private String decodingLoginPassword(String encodedPassword) {
        JLog.logd("encodedPassword : " + encodedPassword);

        return aesUtil.decrypt(encodedPassword);
    }

//    private void isMatchPassword(String email, String loginPassword, String adminPassword, LocalDateTime accessTime) {
//        // 계정이 잠금상태인지 확인한다.
//        userLoginService.isLockingAccount(email, accessTime);
//
//        JLog.logd("adminPassword : " + adminPassword);
//
//        String decodingLoginPassword = decodingLoginPassword(loginPassword);
//
////         Security Filter 과정을 통해서 처리되고 MediNAAuthenticationFailureHandler 의 결과를 반환
//        if (!super.getPasswordEncoder().matches(decodingLoginPassword, adminPassword)) {
//            // 틀리면 카운팅을 추가한다.
//            userLoginService.countLoginFail(email, accessTime);
//            throw new NotMatchPasswordException();
//        }
//    }

}