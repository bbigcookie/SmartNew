package com.dcits.user.exception;


/**
 * 控制层异常
 */
public class ActionException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ActionException(Throwable e) {
        super(e);
    }

    public ActionException(String errorMessage) {
        super(errorMessage);
    }

    public ActionException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public ActionException(String errorCode, String errorMessage, String type) {
        super(errorCode, errorMessage, type);
    }

    public ActionException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public ActionException(String errorCode, String errorMessage, Throwable e) {
        super(errorCode, errorMessage, e);
    }

    public ActionException(String errorCode, String errorMessage, String type,
            Throwable e) {
        super(errorCode, errorMessage, type, e);
    }

    public ActionException(String errorCode, String errorMessage, String type,
            String platSerialNo, Throwable e) {
        super(errorCode, errorMessage, type, platSerialNo, e);
    }
}
