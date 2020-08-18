package com.newco.marketplace.web.action.admin;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class CustomNumberFieldValidator extends FieldValidatorSupport{
	public void validate(Object object) throws ValidationException {
	       String fieldName = getFieldName();
		Object value = this.getFieldValue(fieldName, object);
		if (value == null)
			return;
		
		
		String str = "";
		if(value instanceof Integer) {
			str = ((Integer) value).toString();
			
		}else if(value instanceof String){
			str = ((String) value).trim();
		}
		
		if (str.trim().length() <= 0) {
			return;
		}
		
		if (value != null && str.trim().equals("-1"))
			return;
		 
		
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			addFieldError(fieldName, object);
			return;
		}
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			addFieldError(fieldName, object);
			return;
		}

	 }
}
