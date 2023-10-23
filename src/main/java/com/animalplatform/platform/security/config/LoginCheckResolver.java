package com.animalplatform.platform.security.config;

import com.animalplatform.platform.log.JLog;
import com.animalplatform.platform.security.config.annotation.SessionUser;
import com.animalplatform.platform.security.dto.AuthenticationUserDetails;
import com.animalplatform.platform.security.dto.SessionResponse;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginCheckResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(SessionUser.class);

        boolean isSessionResponse = parameter.getParameterType().isAssignableFrom(SessionResponse.class);

        return hasLoginAnnotation && isSessionResponse;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !(authentication.getPrincipal() instanceof AuthenticationUserDetails)) {
            JLog.logd("로그인 상태가 아닙니다.");
            return null;
        }

        return ((AuthenticationUserDetails) authentication.getPrincipal()).getSession();
    }
}
