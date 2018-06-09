package com.ashstr.spider.util;

import java.io.Serializable;

/**
 * @author keven
 * @date 2017-12-15 下午2:36
 * @Description
 */
public class Response<T> implements Serializable {

    private T result;
    private Boolean success;
    private String error;

    public Boolean isSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.success = true;
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setErrors(String errors) {
        this.success = false;
        this.error = errors;
    }

    public static <T> Response<T> ok(T data){
        Response<T> response = new Response<>();
        response.setResult(data);
        return response;
    }

    public static <T> Response<T> fail(String error) {
        Response<T> response = new Response<>();
        response.setErrors(error);
        return response;
    }
}
