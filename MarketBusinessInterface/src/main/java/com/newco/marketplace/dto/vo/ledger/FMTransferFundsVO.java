package com.newco.marketplace.dto.vo.ledger;

import com.sears.os.vo.SerializableBaseVO;


public class FMTransferFundsVO extends SerializableBaseVO {
	
	private static final long serialVersionUID = -7227314165913188201L;
	
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
	private Integer entityId;
	private Integer walletControlId;
	private String slBucksMail;
	
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
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public Integer getWalletControlId() {
		return walletControlId;
	}
	public void setWalletControlId(Integer walletControlId) {
		this.walletControlId = walletControlId;
	}
	public String getSlBucksMail() {
		return slBucksMail;
	}
	public void setSlBucksMail(String slBucksMail) {
		this.slBucksMail = slBucksMail;
	}
	

/*	public FMTransferFundsVO() {
		// TODO Auto-generated constructor stub
	}*/
	
	

}
