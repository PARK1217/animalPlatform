package com.animalplatform.platform.security.handler;

import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.log.JLog;
import com.animalplatform.platform.security.dto.AuthenticationUserDetails;
import com.animalplatform.platform.utils.ObjectMappingUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class AnimalLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMappingUtil objectMappingUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        RsResponse<Object> rsResponse = null;

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        if(authentication == null) {
            JLog.logd("로그인 상태가 아닙니다.");
            rsResponse = new RsResponse<>(ReturnStatus.FAIL_NOT_LOGIN_STATUS, null);

            response.getWriter().write(
                    objectMappingUtil.toJson(rsResponse)
            );
            return;
        }

        JLog.logd("로그아웃 성공..");

        AuthenticationUserDetails principal = (AuthenticationUserDetails) authentication.getPrincipal();

        JLog.logd("로그아웃 될 사용자 이메일 : " + principal.getUsername());

        rsResponse = new RsResponse<>(ReturnStatus.SUCCESS, null);

        response.getWriter().write(
                objectMappingUtil.toJson(rsResponse)
        );

    }
}
