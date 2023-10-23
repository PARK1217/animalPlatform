package com.animalplatform.platform.security.handler;

import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.log.JLog;
import com.animalplatform.platform.utils.ObjectMappingUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class AnimalAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMappingUtil objectMappingUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        JLog.logd("로그인 에러 : " + exception.getMessage());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        RsResponse<Object> rsResponse = new RsResponse<>(ReturnStatus.FAIL_ATTEMPT_LOGIN, exception.getMessage());

//        if(exception instanceof IsAccountLockException) {
//            IsAccountLockException isAccountLockException = (IsAccountLockException) exception;
//            rsResponse.setResponse(isAccountLockException.bindMessage());
//        }

        response.getWriter().write(
                objectMappingUtil.toJson(rsResponse)
        );
    }
}
