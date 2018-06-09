package com.ashstr.spider.exception;

/**
 * @author keven
 * @date 2017-12-18 上午9:30
 * @Description
 */
public class JsonResponseException extends RuntimeException{

    private static final long serialVersionUID = -1087679414380151555L;

    private int status = 500;

    private String message = "unknown exception";

    public JsonResponseException() {
    }

    public JsonResponseException(String message) {
        this.message = message;
    }

    public JsonResponseException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public JsonResponseException(int status, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.status = status;
    }

    public JsonResponseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public JsonResponseException(int status, Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
        this.status = status;
    }

    public JsonResponseException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
