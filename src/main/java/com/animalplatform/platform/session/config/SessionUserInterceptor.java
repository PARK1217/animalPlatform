package com.animalplatform.platform.session.config;

import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.log.JLog;
import com.animalplatform.platform.session.constants.SessionConstants;
import com.animalplatform.platform.session.dto.SessionResponse;
import com.animalplatform.platform.utils.ObjectMappingUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;



@RequiredArgsConstructor
public class SessionUserInterceptor implements HandlerInterceptor {

    private static final int LOGIN_ERROR_STATUS = 444;
    private final ObjectMappingUtil objectMappingUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        //  httpStatus 444을 전달해서 예외호출
        if(session == null || session.getAttribute(SessionConstants.SESSION_KEY) == null) {
            JLog.logd("로그인이 필요한 API 호출.. ");
            response.setStatus(LOGIN_ERROR_STATUS);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");

            RsResponse<Object> rsResponse = new RsResponse<>(ReturnStatus.FAIL_REQUIRED_LOGIN_USER);

            response.getWriter().write(objectMappingUtil.toJson(rsResponse));
            return false;
        }

        SessionResponse sessionResponse = (SessionResponse) session.getAttribute(SessionConstants.SESSION_KEY);
        JLog.logd(String.format("접속중인 사용자 : %s", sessionResponse));

        return true;
    }

}

