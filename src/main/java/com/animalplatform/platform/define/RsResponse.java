package com.animalplatform.platform.define;

import java.util.Calendar;
import java.util.Date;



/**
 * Created by philosophia on 22/05/2017.
 */
public class RsResponse<T> {

    private int responseCode;
    private final Date execDt;
    private final String message;

    private T response;

    /**
     * A Creates a new instance of Response
     *
     * @param eu
     */
    public RsResponse(final ReturnStatus eu) {
        int rCode = eu.getCode();
        this.execDt = Calendar.getInstance().getTime();
        this.message = eu.getMsg();
        this.responseCode = rCode;
        this.response = null;
    }

    /**
     * A Creates a new instance of Response
     *
     * @param eu
     * @param msg
     */
    public RsResponse(final ReturnStatus eu, final String msg) {
        int rCode = eu.getCode();
        this.execDt = Calendar.getInstance().getTime();
        this.message  = msg == null ? eu.getMsg() : msg;
        this.responseCode = rCode;
        this.response = null;
    }

    public RsResponse(final int code, final String msg) {
        this.execDt = Calendar.getInstance().getTime();
        this.message  = msg;
        this.responseCode = code;
        this.response = null;
    }

    /**
     * A Creates a new instance of Response
     *
     * @param eu
     * @param msg
     * @param obj
     */
    public RsResponse(final ReturnStatus eu, final String msg, final T obj) {
        int rCode = eu.getCode();
        this.execDt = Calendar.getInstance().getTime();
        this.message  = msg == null ? eu.getMsg() : msg;
        this.responseCode = rCode;
        this.response = obj;
    }

    /**
     * @return the execDt
     */
    public Date getExecDt() {

        return this.execDt;
    }

    /**
     * @return the message
     */
    public String getMessage() {

        return this.message;
    }

    /**
     * @return the response
     */
    public T getResponse() {

        return this.response;
    }

    /**
     * @return the responseCode
     */
    public int getResponseCode() {

        return this.responseCode;
    }

    /**
     * sets the response object
     *
     * @param obj
     * @return
     */
    public RsResponse<T> setResponse(final T obj) {

        this.response = obj;
        return this;
    }
}