package com.billdesk.CustomerApp.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	
	public static String emailRegex;	
	public static String phoneRegex;	
	public static Pattern emailPattern;	
	public static Pattern phonePattern;
	
	//PRE COMPILATION
	static {
		emailRegex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		emailPattern = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		
		phoneRegex = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		phonePattern = Pattern.compile(phoneRegex);
	}
	
	//EMAIL VALIDATION
	public static boolean isEmailValid(String email){
		boolean isValid = false;
		if(email==null){
			return true;
		}
		CharSequence inputStr = email;
		
		//Make the comparison case-insensitive.
		Matcher matcher = emailPattern.matcher(inputStr);
		if(matcher.matches()){
		isValid = true;
		}
		return isValid;
		}
	
	//CONTACT VALIDATION 
    public static boolean isContactValid(String phoneNumber){
    	boolean isValid = false;
    	if(phoneNumber==null){
			return true;
		}
    	
    	//Initialize regex for phone number.     	
    	CharSequence inputStr = phoneNumber;
    	Matcher matcher = phonePattern.matcher(inputStr);
    	if(matcher.matches()){
    	isValid = true;
    	}
    	return isValid;
    	}
    	
	
}
