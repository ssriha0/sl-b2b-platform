package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.OrderConstants;
import com.sears.os.utils.DateValidationUtils;

public class FMFinancialProfileTabDTO extends SOWBaseTabDTO
{

	/**
	 *
	 */
	private static final long serialVersionUID = -793416726353257445L;
	private String businessForeignOwned;
	private String percentOwnedByForeignCompany;
	private String taxpayerId;
	private String confirmTaxpayerId;
	private String fmManageAccountsList;
	private String amount;
	private String refundNote;
	private Double providerMaxWithdrawalLimit; 
	private Integer providerMaxWithdrawalNo;
	
	// Accounts Receivable Point of Contact
	private SOWContactLocationDTO account = new SOWContactLocationDTO();


	public SOWContactLocationDTO getAccount()
	{
		return account;
	}

	public String getFmManageAccountsList() {
		return fmManageAccountsList;
	}

	public void setFmManageAccountsList(String fmManageAccountsList) {
		this.fmManageAccountsList = fmManageAccountsList;
	}

	public void setAccount(SOWContactLocationDTO account)
	{
		this.account = account;
	}
	@Override
	public void validate() {
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		SecurityContext securityContext = (SecurityContext) ServletActionContext.getRequest().getSession().getAttribute("SecurityContext");
		String role = securityContext.getRole();

		if (account != null) {
			if (account.getFirstName() != null
					&& account.getFirstName().length() > 50) {
				addError(getTheResourceBundle().getString("First_Name"),
						getTheResourceBundle().getString(
								"First_Name_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (account.getFirstName() == null
					|| account.getFirstName().length() < 1) {
				addWarning(getTheResourceBundle().getString("First_Name"),
						getTheResourceBundle().getString(
								"First_Name_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (account.getLastName() == null
					|| account.getLastName().length() < 1) {
				addWarning(getTheResourceBundle().getString("Last_Name"),
						getTheResourceBundle()
								.getString("Last_Name_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (account.getLastName() != null
					&& account.getLastName().trim()
							.length() > 50) {
				addError(getTheResourceBundle().getString("Last_Name"),
						getTheResourceBundle().getString(
								"Last_Name_Lenght_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (account.getEmail() != null
					&& account.getEmail().trim().length() > 255) {
				addError(getTheResourceBundle().getString("Email"),
						getTheResourceBundle()
								.getString("Email_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			} else if (account.getEmail() != null
					&& account.getEmail().length() > 1) {
				boolean valResult = DateValidationUtils.emailValidation(account.getEmail());
				if (valResult == false) {
					addError(getTheResourceBundle().getString("Email"),
							getTheResourceBundle().getString(
									"Email_Pattern_Validation_Msg"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}


		if (account.getPhones() != null && account.getPhones().size()>0) {
				List<SOWPhoneDTO> phones = account.getPhones();

					SOWPhoneDTO iPhone = phones.get(0);
					if (iPhone.getPhone() != null
							&& iPhone.getPhone().trim().length() > 0 ) {
						boolean valResult = false;
						String numPattern ="(\\d{10})";
					//	String numPattern = "(\\d{3})?\\d{3}\\d{4}";
						valResult = iPhone.getPhone().matches(numPattern);

						//Check to see if the phone is empty. I.E. the user never even entered phone
						if(!(iPhone.getAreaCode() == null && iPhone.getPhonePart1() == null && iPhone.getPhonePart2() == null)){
							if(valResult == false){
								addError("phone", getTheResourceBundle().getString(
								"Phone_Missing_Number_Validation_Msg"),
								OrderConstants.SOW_TAB_ERROR);
							}

						}
					}
			} 
		}

	}
	public String getTabIdentifier() {
		return "";
	}


	public String getBusinessForeignOwned() {
		return businessForeignOwned;
	}

	public void setBusinessForeignOwned(String businessForeignOwned) {
		this.businessForeignOwned = businessForeignOwned;
	}

	public String getPercentOwnedByForeignCompany() {
		return percentOwnedByForeignCompany;
	}

	public void setPercentOwnedByForeignCompany(String percentOwnedByForeignCompany) {
		this.percentOwnedByForeignCompany = percentOwnedByForeignCompany;
	}

	public String getTaxpayerId() {
		return taxpayerId;
	}

	public void setTaxpayerId(String taxpayerId) {
		this.taxpayerId = taxpayerId;
	}

	public String getConfirmTaxpayerId() {
		return confirmTaxpayerId;
	}

	public void setConfirmTaxpayerId(String confirmTaxpayerId) {
		this.confirmTaxpayerId = confirmTaxpayerId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRefundNote() {
		return refundNote;
	}

	public void setRefundNote(String refundNote) {
		this.refundNote = refundNote;
	}
	public Double getProviderMaxWithdrawalLimit() {
		return providerMaxWithdrawalLimit;
	}

	public void setProviderMaxWithdrawalLimit(Double providerMaxWithdrawalLimit) {
		this.providerMaxWithdrawalLimit = providerMaxWithdrawalLimit;
	}

	public Integer getProviderMaxWithdrawalNo() {
		return providerMaxWithdrawalNo;
	}

	public void setProviderMaxWithdrawalNo(Integer providerMaxWithdrawalNo) {
		this.providerMaxWithdrawalNo = providerMaxWithdrawalNo;
	}

}
