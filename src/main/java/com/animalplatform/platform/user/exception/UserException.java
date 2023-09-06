package com.animalplatform.platform.user.exception;

import com.animalplatform.platform.aop.advice.BusinessException;
import com.animalplatform.platform.define.ReturnStatus;

public class UserException extends BusinessException {

    public UserException(ReturnStatus returnStatus) {
        super(returnStatus);
        super.printDebugMessage();
    }

    public UserException(ReturnStatus returnStatus, Object obj) {
        super(returnStatus);
        super.printDebugMessage(obj);
    }
}
