package com.takaka.takakaProducer.exception;

@SuppressWarnings("serial")
public class ResposeBusinessException extends Exception {

private String message;
	
	public ResposeBusinessException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
