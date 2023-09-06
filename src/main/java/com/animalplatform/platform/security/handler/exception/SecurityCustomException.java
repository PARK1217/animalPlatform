package com.animalplatform.platform.security.handler.exception;

import com.animalplatform.platform.aop.advice.BusinessException;
import com.animalplatform.platform.define.ReturnStatus;

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