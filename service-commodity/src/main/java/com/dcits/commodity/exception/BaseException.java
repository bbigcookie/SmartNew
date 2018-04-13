package com.dcits.commodity.exception;

public class BaseException extends Exception {

    private static final long serialVersionUID = -987772383005945208L;

    private String platSerialNo;
    private String type; //异常类型
    private String errorCode; //错误码
    private String errorMessage; //错误信息
    
    public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPlatSerialNo() {
        return platSerialNo;
    }

    void setPlatSerialNo(String platSerialNo) {
        this.platSerialNo = platSerialNo;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }


    public String getMessage() {
        return errorMessage;
    }

    void setMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    BaseException() {
        super();
    }
    
    public BaseException(Throwable e) {
    	super(e);
    }
    
    public BaseException(String errorMessage) {
    	super(errorMessage);
    	this.errorMessage = errorMessage;
    }
    
    public BaseException(String errorCode, String errorMessage){
    	super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
	}
    
    public BaseException(String errorCode, String errorMessage, String type){
    	super(errorMessage);
        this.type = type;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
	}
    
    BaseException(String errorCode, String errorMessage, String type, String platSerialNo) {
        super(errorMessage);
        this.platSerialNo = platSerialNo;
        this.type = type;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
    
    BaseException(String errorMessage, Throwable e){
    	super(errorMessage, e);
        this.errorMessage = errorMessage;
    }
    
    BaseException(String errorCode, String errorMessage, Throwable e){
    	super(errorMessage, e);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
    
    BaseException(String errorCode, String errorMessage, String type, Throwable e){
    	super(errorMessage, e);
        this.type = type;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
    
    BaseException(String errorCode, String errorMessage, String type, String platSerialNo, Throwable e) {
        super(errorMessage, e);
        this.platSerialNo = platSerialNo;
        this.type = type;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

}

