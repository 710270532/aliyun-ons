package com.aliyun.openservices.ons.api.exception;

public class OnsClientException extends Exception{

    private static final long serialVersionUID = 1L;

    public OnsClientException() {}


    public OnsClientException(String message) {
        super(message);
    }


    public OnsClientException(Throwable cause) {
        super(cause);
    }


    public OnsClientException(String message, Throwable cause) {
        super(message, cause);
    }
	

}
