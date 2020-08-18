package com.newco.marketplace.web.dto.simple;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.businessImpl.alert.Address;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;

/**
* @author Nick Sanzeri
*
* $Revision: 1.3 $ $Author: mkhair $ $Date: 2008/05/23 00:57:22 $
*/
public class CreateServiceOrderEditAccountDTO extends SOWBaseTabDTO{
	
	private static final long serialVersionUID = 3260943019097370453L;
	private Integer locId;
	private Integer buyerResId;
	private Integer contactId;
	private String firstName;
	private String lastName;
	private Address address;
	private String email;
	private String primaryPhone;
	private String secondaryPhone;
	private String message;

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return OrderConstants.SSO_EDIT_ACCOUNT_DTO;
	}

	@Override
	public void validate() {
		if (getEmail() == null || getEmail().equals("")) {
			addError(getTheResourceBundle().getString("Email"),
					getTheResourceBundle()
							.getString("Email_Empty_Validation_Msg"),
					OrderConstants.SOW_TAB_ERROR);
		}

		List<IError> errorList = getErrorsOnly();
		setErrors(errorList);
	}

	public void parsePhoneNumber(String phoneNumber, String phoneType)
	{

		if (StringUtils.isNotBlank(phoneNumber) && phoneNumber.length() == 14) 
		{

			if (StringUtils.equals(phoneType, "PRIMARY"))
			{
			setPrimaryPhone(primaryPhone.substring(1, 4)+primaryPhone.substring(6, 9) + primaryPhone.substring(10, 14));
			}
			else if (StringUtils.equals(phoneType, "SECONDARY"))
			{
				setSecondaryPhone(secondaryPhone.substring(1, 4)+secondaryPhone.substring(6, 9) + secondaryPhone.substring(10, 14));
			}
		}

	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getLocId() {
		return locId;
	}

	public void setLocId(Integer locId) {
		this.locId = locId;
	}

	public Integer getBuyerResId() {
		return buyerResId;
	}

	public void setBuyerResId(Integer buyerResId) {
		this.buyerResId = buyerResId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
