package com.animalplatform.platform.aop.advice;

import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.log.JLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public RsResponse<List<String>> handleRequestArgumentBindingException(final BindingResult bindingResult) {

        List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new RsResponse<>(ReturnStatus.FAIL, String.join(", ", errors));
    }

    @ExceptionHandler(value = {Exception.class})
    public RsResponse<Object> handleException(final Exception e, HttpServletRequest httpServletRequest) {

        JLog.loggingException(e, httpServletRequest);

        return new RsResponse<>(ReturnStatus.FAIL, e.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    public RsResponse<Object> businessExceptionHandler(final BusinessException e, HttpServletRequest httpServletRequest) {

        JLog.loggingException(e, httpServletRequest);

        return e.getBusinessExceptionResponse();
    }

//    @ExceptionHandler(value = IsAccountLockException.class)
//    public RsResponse<Object> accountLockException(final IsAccountLockException e) {
//        e.printStackTrace();
//        return e.bindResponse();
//    }
}
