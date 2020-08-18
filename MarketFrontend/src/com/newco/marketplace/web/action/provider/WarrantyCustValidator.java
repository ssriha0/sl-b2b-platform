package com.newco.marketplace.web.action.provider;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
/*
 * Custom validator for Warranty and Policies page.
 * Ignores validation for a disabled field.
 * This code based on Reference: http://today.java.net/pub/a/today/2006/01/19/webwork-validation.html?page=3#custom-validators
 *  and http://www.opensymphony.com/webwork/wikidocs/Validation.html
 *  Required string field code taken from:http://svn.opensymphony.com/svn/xwork/trunk/src/java/com/opensymphony/xwork2/validator/validators/RequiredStringValidator.java
 */
public class WarrantyCustValidator extends FieldValidatorSupport {

	String dependentFieldName;
	private boolean doTrim = true;
	String warranty;  // reverses validation logic for warranty on labor and parts
	
	public void validate(Object object) throws ValidationException {		
		
		String fieldName = getFieldName();
		Object value = this.getFieldValue(fieldName, object);
		Object dependentValue = this.getFieldValue(dependentFieldName, object);
		
		Object warrantyValue = this.getFieldValue(warranty, object);
				
		System.out.println("Field Name " + getFieldName() +    " Warranty Value " +  warrantyValue + "Dependent Value: " + dependentValue);	
				
		if("on".equalsIgnoreCase((String) warrantyValue) || "".equalsIgnoreCase((String) warrantyValue)|| warrantyValue == null)
		{
			if("0".equalsIgnoreCase((String)dependentValue)){//if Radio button "no" option selected (value = "0") then short-circuit required field validation
				return;
			}else{//If Radio button "yes" option selected (value = "1")  do required field validation			
		        //required string validation code
				if (!(value instanceof String)) {				
		            addFieldError(fieldName, object);
		        } else {
		            String s = (String) value;

		            if (doTrim) {
		                s = s.trim();
		            }

		            if (s.length() == 0) {
		                addFieldError(fieldName, object);
		            }
		        }
	        }//
			
			
		}
		else
		{
//		if(((String)dependentValue).equalsIgnoreCase("0")){//if Radio button "yes" option selected (value = "1") then short-circuit required field validation
		if("1".equalsIgnoreCase((String)dependentValue)){//if Radio button "yes" option selected (value = "1") then short-circuit required field validation
			return;
		}else{//If Radio button "No" option selected (value = "0")  do required field validation			
	        //required string validation code
			if (!(value instanceof String)) {				
	            addFieldError(fieldName, object);
	        } else {
	            String s = (String) value;

	            if (doTrim) {
	                s = s.trim();
	            }

	            if (s.length() == 0) {
	                addFieldError(fieldName, object);
	            }
	        }
        }//
		}
	}

	public String getDependentFieldName() {
		return dependentFieldName;
	}

	public void setDependentFieldName(String dependentFieldName) {
		this.dependentFieldName = dependentFieldName;
	}

	/**
	 * @return the warranty
	 */
	public String getWarranty() {
		return warranty;
	}

	/**
	 * @param warranty the warranty to set
	 */
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	
}//
