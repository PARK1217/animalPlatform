package com.animalplatform.platform.security.filter;

import com.animalplatform.platform.security.dto.LoginRequestDto;
import com.animalplatform.platform.user.entity.User;
import com.animalplatform.platform.user.service.UserLoginService;
import com.animalplatform.platform.utils.ObjectMappingUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginApiFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMappingUtil objectMappingUtil;
    private final UserLoginService userLoginService;

    public LoginApiFilter(String defaultFilterProcessesUrl,
                          AuthenticationManager authenticationManager,
                          AuthenticationSuccessHandler authenticationSuccessHandler,
                          AuthenticationFailureHandler authenticationFailureHandler,
                          ObjectMappingUtil objectMappingUtil,
                          UserLoginService userLoginService) {
        super(defaultFilterProcessesUrl, authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
        this.objectMappingUtil = objectMappingUtil;
        this.userLoginService = userLoginService;
    }

    // json으로 로그인 데이터를 받아온다
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException {

        try (ServletInputStream inputStream = request.getInputStream()) {

            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }

            /** Encoding 된 로그인 정보가 들어온다. */

            LoginRequestDto loginRequestDto = objectMappingUtil.readJsonValue(inputStream, LoginRequestDto.class);
//
            String userId = loginRequestDto.getUserId();

            User user = userLoginService.findByUserId(userId);

//            Authentication authenticationToken = loginRequestDto.convertAuthenticationToken();

            return null;
        }
}
