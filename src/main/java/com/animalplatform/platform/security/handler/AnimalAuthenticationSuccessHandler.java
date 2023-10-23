package com.animalplatform.platform.security.handler;

import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.log.JLog;
import com.animalplatform.platform.user.service.UserLoginService;
import com.animalplatform.platform.utils.ObjectMappingUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AnimalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMappingUtil objectMappingUtil;
    private final UserLoginService userLoginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        JLog.logd("로그인 성공 : " + authentication);

        LocalDateTime loginDate = LocalDateTime.now();
        String email = authentication.getName();

////        // 해당 유저의 정보를 찾고 시간을 추가한다.
//        userLoginService.recordLastLoginDate(email, loginDate);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        RsResponse<Object> rsResponse = new RsResponse<>(ReturnStatus.SUCCESS, null);

        // TODO : 로그인 메인페이지로 이동 or 클라이언트에서 자체적으로 이동
        response.getWriter().write(
                objectMappingUtil.toJson(rsResponse)
        );
    }
}
