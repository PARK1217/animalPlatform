package com.animalplatform.platform.session.config;

import com.animalplatform.platform.session.constants.SessionConstants;
import com.animalplatform.platform.session.annotation.SessionUser;
import com.animalplatform.platform.session.dto.SessionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class SessionUserCheckResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasSessionUserAnnotation = parameter.hasParameterAnnotation(SessionUser.class);

        boolean isSessionResponseType = parameter.getParameterType().isAssignableFrom(SessionResponse.class);

        return hasSessionUserAnnotation && isSessionResponseType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);

        if(session == null) {
            return null;
        }

        SessionResponse sessionResponse = (SessionResponse) session.getAttribute(SessionConstants.SESSION_KEY);

        return sessionResponse;
    }
}
