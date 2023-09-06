package com.example.animalplatform.security.handler.exception;

import com.example.animalplatform.aop.advice.BusinessException;
import com.example.animalplatform.define.ReturnStatus;

public class SecurityCustomException extends BusinessException {

    public SecurityCustomException(ReturnStatus returnStatus) {
        super(returnStatus);
        super.printDebugMessage();
    }

    public SecurityCustomException(ReturnStatus returnStatus, Object obj) {
        super(returnStatus);
        super.printDebugMessage(obj);
    }
}