package com.yangcs.base.exception;

/*
* 定义我们自己的异常类型
* */
public class XueChengPlusException extends RuntimeException{
    private String errException;

    public XueChengPlusException() {
    }

    public XueChengPlusException(String message) {
        super(message);
        this.errException = message;
    }

    public String getErrException() {
        return errException;
    }

    public void setErrException(String errException) {
        this.errException = errException;
    }

    public static void cast(CommonError error){
        throw new XueChengPlusException(error.getErrMessage());
    }
    public static void cast(String errMessage) {
        throw new XueChengPlusException(errMessage);
    }
}
