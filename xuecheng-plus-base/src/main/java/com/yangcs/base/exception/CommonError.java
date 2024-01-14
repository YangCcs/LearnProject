package com.yangcs.base.exception;

/*
* 有些统一的错误，可以抽取出来共用
* */
public enum CommonError {
    UNKNOWN_ERROR("执行过程异常，请重试。"),
    PARAMS_ERROR("非法参数"),
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询为空"),
    REQUEST_NULL("请求参数为空");

    private String errMessage;
    public String getErrMessage() {
        return errMessage;
    }
    private CommonError(String errMessage) {
        this.errMessage = errMessage;
    }
}
