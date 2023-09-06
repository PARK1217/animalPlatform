package com.animalplatform.platform.aop.advice;

import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;

public class BusinessException extends RuntimeException{

    private final ReturnStatus returnStatus;

    public BusinessException(ReturnStatus returnStatus) {
        super(returnStatus.getMsg());
        this.returnStatus = returnStatus;
    }

    public RsResponse<Object> getBusinessExceptionResponse() {
        return new RsResponse<>(this.returnStatus);
    }

    protected void printDebugMessage() {
        this.returnStatus.printDebugMessage();
    }

    /**
     * 추가정보를 표현하고자 할때 사용한다.
     * @param obj
     */
    protected void printDebugMessage(Object obj) {
        this.returnStatus.printDebugMessage(obj);
    }
}
