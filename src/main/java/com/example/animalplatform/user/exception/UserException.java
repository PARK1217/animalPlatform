package com.example.animalplatform.user.exception;

import com.example.animalplatform.aop.advice.BusinessException;
import com.example.animalplatform.define.ReturnStatus;

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
