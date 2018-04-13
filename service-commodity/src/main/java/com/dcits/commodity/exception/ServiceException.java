package com.dcits.commodity.exception;


/**
 * service服务层异常
 */
public class ServiceException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ServiceException(Throwable e) {
        super(e);
    }

    public ServiceException(String errorMessage) {
        super(errorMessage);
    }

    public ServiceException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public ServiceException(String errorCode, String errorMessage, String type) {
        super(errorCode, errorMessage, type);
    }

    public ServiceException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public ServiceException(String errorCode, String errorMessage, Throwable e) {
        super(errorCode, errorMessage, e);
    }

    public ServiceException(String errorCode, String errorMessage, String type,
            Throwable e) {
        super(errorCode, errorMessage, type, e);
    }

    public ServiceException(String errorCode, String errorMessage, String type,
            String platSerialNo, Throwable e) {
        super(errorCode, errorMessage, type, platSerialNo, e);
    }
}
