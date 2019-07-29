package com.billdesk.CustomerApp.exception;
import com.billdesk.CustomerApp.model.CustomerInfo;

public class ResponseMessage extends CustomerInfo{

	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "id :"+getCustId()+"\nname :"+getCustName()+"\nage :"+getCustAge()+"\nadd :"+getCustAddress();
	}
	
	
	
}
