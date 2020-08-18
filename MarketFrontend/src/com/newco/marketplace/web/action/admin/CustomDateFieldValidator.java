package com.newco.marketplace.web.action.admin;
import java.util.Date;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.ValidatorSupport;

public class CustomDateFieldValidator extends ValidatorSupport{

	private String fromDate;
	private String toDate;

	public void validate(Object object) throws ValidationException {

		Object fromValue = this.getFieldValue(fromDate, object);
		Object toValue =  this.getFieldValue(toDate, object);

		if(fromValue == null && toValue == null) return ;

		if(fromValue != null && toValue == null) {
			addFieldError(fromDate, "Both Start Date and End Date are required.");
			return;
		}

		if(fromValue == null && toValue != null) {
			addFieldError(fromDate, "Both Start Date and End Date are required.");
			return;
		}

		if(fromValue instanceof Date && toValue instanceof Date) {
			try{
			Date fDate = (Date)fromValue;
			Date tdate = (Date) toValue;
			if(fDate.after(tdate)){
				addFieldError(fromDate, "The date you entered is invalid. End date should be after Start date.");
				return;
			}

			}catch(Exception e) {
				addFieldError(fromDate, "Invalid Date format.");
				return;
			}

		}


	 }

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
