package com.dcits.user.exception;

/**
 * dao类异常
 */
public class DaoException extends BaseException {

	private static final long serialVersionUID = 1L;

	public DaoException(Throwable e) {
    	super(e);
    }
	
	public DaoException(String errorMessage){
		super(errorMessage);
	}
	
	public DaoException(String errorCode, String errorMessage) {
		super(errorCode,errorMessage);
	}
	
	public DaoException(String errorCode, String errorMessage, String type) {
		super(errorCode, errorMessage, type);
	}
	
	DaoException(String errorMessage, Throwable e){
    	super(errorMessage, e);
    }
    
	DaoException(String errorCode, String errorMessage, Throwable e){
    	super(errorCode, errorMessage, e);
    }
    
	DaoException(String errorCode, String errorMessage, String type, Throwable e){
    	super(errorCode, errorMessage, type, e);
    }
    
	DaoException(String errorCode, String errorMessage, String type, String platSerialNo, Throwable e) {
        super(errorCode, errorMessage, type, platSerialNo, e);
    }
}

