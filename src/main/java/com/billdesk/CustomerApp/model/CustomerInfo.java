package com.billdesk.CustomerApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



//CONTAINS CUSTOMER INFORMATION
public class CustomerInfo {
	
	@JsonProperty(value="customer_id")
	private String custId;
	
	@JsonProperty(value="customer_name")
	private String custName;
	
	@JsonProperty(value="customer_age")
	private String custAge;
	
	@JsonIgnore
	private Integer validationFlag;
	
	@JsonProperty(value="customer_address")
	private String custAddress;
	
	@JsonProperty(value="customer_contact")
	private String custContact;
	
	@JsonProperty(value="customer_email")
	private String custEmail;
	
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustAge() {
		return custAge;
	}
	public void setCustAge(String custAge) {
		this.custAge = custAge;
	}
	public Integer getValidationFlag() {
		return validationFlag;
	}
	public void setValidationFlag(Integer validationFlag) {
		this.validationFlag = validationFlag;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getCustContact() {
		return custContact;
	}
	public void setCustContact(String custContact) {
		this.custContact = custContact;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id :"+custId+"\nname :"+custName+"\nage :"+custAge+"\nadd :"+custAddress;
	}
	
	

}
