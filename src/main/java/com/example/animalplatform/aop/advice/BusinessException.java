package com.example.animalplatform.aop.advice;

import com.example.animalplatform.define.ReturnStatus;
import com.example.animalplatform.define.RsResponse;

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
