package com.ashstr.common.exception;

/**
 * @author keven
 * @date 2018-06-02 下午11:01
 * @Description
 */
public class ServiceException extends RuntimeException{


    private static final long serialVersionUID = -4955616094721542246L;

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
