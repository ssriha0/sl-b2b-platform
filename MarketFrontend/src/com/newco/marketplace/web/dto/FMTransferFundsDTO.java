package com.newco.marketplace.web.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;

public class FMTransferFundsDTO extends SLBaseDTO implements OrderConstants{

	private static final long serialVersionUID = 0L;
	
	private Integer reasonCode;
	private Integer toAccount;
	private Integer fromAccount;
	private Double amount;
	private String note;
	private String roleType;
	private String user;
	private String adminUser;
	private String firstName;
	private String lastName;
	private Integer walletControlId;
	private Integer entityId;
	private String slBucksMail;
	private String authToken;
	private String tokenLife;
	private String creditCardTokenUrl;
	private String CreditCardTokenAPICrndl;
	
	public Integer getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}
	public Integer getToAccount() {
		return toAccount;
	}
	public void setToAccount(Integer toAccount) {
		this.toAccount = toAccount;
	}
	public Integer getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Integer fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public boolean validateWarnings() {
		return false;
	}
	public boolean validateErrors() {
		boolean flag=false; // only errors in this DTO validation...
		
		if (getAllMessages().size() > 0) flag = true;
			return flag;
	}
	
	
	public List<IMessage> validate() {
		this.getAllMessages().clear();

		if (reasonCode != null && reasonCode.intValue() == -1)
			addError("reason", "adminSLBucksTransfer.reason.required");
		if (amount == null || (amount != null && amount.doubleValue() <= 0.0))
			addError("amount", "adminSLBucksTransfer.amount.required");
		if (note != null)
			note = note.trim();
		if (StringUtils.isEmpty(note))
			addError("note", "adminSLBucksTransfer.note.required");
		if (reasonCode.intValue() == 90) {
			if (walletControlId != OrderConstants.Wallet_Control_Id_IRS_Levy) {
				addError("reasonCode", "adminSLBucksTransfer.reason.invalid");
			}
		}
		if (reasonCode.intValue() == 100) {
			if (walletControlId != OrderConstants.Wallet_Control_Id_Legal_hold) {
				addError("reasonCode", "adminSLBucksTransfer.reason.invalid");
			}
		}

		return getAllMessages();

	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getAdminUser()
	{
		return adminUser;
	}
	public void setAdminUser(String adminUser)
	{
		this.adminUser = adminUser;
	}
	public Integer getWalletControlId() {
		return walletControlId;
	}
	public void setWalletControlId(Integer walletControlId) {
		this.walletControlId = walletControlId;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public String getSlBucksMail() {
		return slBucksMail;
	}
	public void setSlBucksMail(String slBucksMail) {
		this.slBucksMail = slBucksMail;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getTokenLife() {
		return tokenLife;
	}
	public void setTokenLife(String tokenLife) {
		this.tokenLife = tokenLife;
	}
	public String getCreditCardTokenUrl() {
		return creditCardTokenUrl;
	}
	public void setCreditCardTokenUrl(String creditCardTokenUrl) {
		this.creditCardTokenUrl = creditCardTokenUrl;
	}
	public String getCreditCardTokenAPICrndl() {
		return CreditCardTokenAPICrndl;
	}
	public void setCreditCardTokenAPICrndl(String creditCardTokenAPICrndl) {
		CreditCardTokenAPICrndl = creditCardTokenAPICrndl;
	}
	
}
