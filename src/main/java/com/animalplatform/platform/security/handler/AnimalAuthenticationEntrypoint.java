package com.animalplatform.platform.security.handler;

import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.log.JLog;
import com.animalplatform.platform.utils.ObjectMappingUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@RequiredArgsConstructor
public class AnimalAuthenticationEntrypoint implements AuthenticationEntryPoint {

    private static final String API_REQUEST_PATH = "/api/v1/";
    private static final String REDIRECT_PATH_TO_FAIL_AUTHENTICATE = "/sessionExpire";
    private static final int LOGIN_ERROR_STATUS = 444;
    private final ObjectMappingUtil objectMappingUtil;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String requestURL = request.getRequestURL().toString();

        JLog.logd("Request URL : " + requestURL);

        if(requestURL.contains(API_REQUEST_PATH)) {
            response.setStatus(LOGIN_ERROR_STATUS);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");

            RsResponse<Object> rsResponse = new RsResponse<>(ReturnStatus.FAIL_REQUIRED_LOGIN_USER);

            response.getWriter().write(
                    objectMappingUtil.toJson(rsResponse)
            );

            return;
        }

        JLog.logd("미인증 사용자의 요청 : " + authException.getClass());
        JLog.logd("미인증 사용자의 요청 : " + authException.getMessage());

        // 로그인이 안됐을 경우 403페이지 이동
        response.sendRedirect(REDIRECT_PATH_TO_FAIL_AUTHENTICATE);

    }
}