package com.newco.marketplace.web.dto;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;

public class FMManageFundsTabDTO extends SOWBaseTabDTO {
	
	private static final long serialVersionUID = 644571216255504645L;
	
	private long accountId;
	private String accountTypeId;
	NumberFormat formatter = new DecimalFormat("###0.00");
	private String withdrawAmount;
	private String depositAmount;
	private String fmReleaseDate;
	private String accountList;
	private String cvvCode;
	private String refundNote;
	private String autoACHInd;
	private boolean enabledIndicator ; 
	private String einStoredFlag;
	private String buyerTotalDeposit; 
	private String taxPayerId;
	private String confirmTaxPayerId;
	private String einSsnInd;
	private String nonce;
	private Double providerMaxWithdrawalLimit; 
	private Integer providerMaxWithdrawalNo;
	private String einTaxPayerId;
	private String ssnTaxPayerId;
	private String confirmSsnTaxPayerId;
	private String confirmEinTaxPayerId;
	private String ssnDob;
	private String altIdDob;
	private String userName = null;
	private String walletControlAmount;
	private String walletControlType; 
	private boolean onHold;
	
	
	
	private List <SLDocumentDTO> walletControldocuments;
	
	private List <File> walletControlFiles;
	private List <String> walletControlFilesFileName;
	private List <String> walletControlFilesContentType;
	
	private Integer entityWalletControlID;
	
	public String getEinTaxPayerId() {
		return einTaxPayerId;
	}

	public void setEinTaxPayerId(String einTaxPayerId) {
		this.einTaxPayerId = einTaxPayerId;
	}

	public String getSsnTaxPayerId() {
		return ssnTaxPayerId;
	}

	public void setSsnTaxPayerId(String ssnTaxPayerId) {
		this.ssnTaxPayerId = ssnTaxPayerId;
	}

	public String getConfirmSsnTaxPayerId() {
		return confirmSsnTaxPayerId;
	}

	public void setConfirmSsnTaxPayerId(String confirmSsnTaxPayerId) {
		this.confirmSsnTaxPayerId = confirmSsnTaxPayerId;
	}

	public String getConfirmEinTaxPayerId() {
		return confirmEinTaxPayerId;
	}

	public void setConfirmEinTaxPayerId(String confirmEinTaxPayerId) {
		this.confirmEinTaxPayerId = confirmEinTaxPayerId;
	}

	public String getSsnDob() {
		return ssnDob;
	}

	public void setSsnDob(String ssnDob) {
		this.ssnDob = ssnDob;
	}

	public String getAltIdDob() {
		return altIdDob;
	}

	public void setAltIdDob(String altIdDob) {
		this.altIdDob = altIdDob;
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

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
	public String getTabIdentifier() {
		return "";
	}
	
	
	public boolean checkAccount() {
		return (accountId >= 0);
	}
	
	public boolean checkWithdrawAmount()
	{
		return validateAmount(withdrawAmount);	
	}
	
	public boolean checkDepositAmount()
	{
		return validateAmount(depositAmount);	
	}
	
	public boolean checkWalletControlAmount()
	{
		return validateAmount(walletControlAmount);	
	}
	private boolean validateAmount(String amountStr)
	{
		boolean okAmount = false;
		if(amountStr != null && !amountStr.equals("")){
			try{
				double amount = Double.parseDouble(amountStr);
				if(amount > 0) 
					okAmount = true;
			}catch(NumberFormatException nf){
				// return false set as a default
			}	
		}
		return okAmount;
	}
	
	public void validate() {
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
	}

	public void checkForEnabledAccount()
	{
		if (!enabledIndicator )
		{
			addError(getTheResourceBundle().getString("Account_disabled"),
					getTheResourceBundle().getString(
							"Account_disabled_validation"),
					OrderConstants.SOW_TAB_ERROR);
		}
	}	
	
	public String getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(String withdrawAmount) {
		if(withdrawAmount == null || withdrawAmount.equals("")){
			this.withdrawAmount = formatter.format(0.0);
		}else{
			this.withdrawAmount = withdrawAmount;
		}
		
	}

	public String getFmReleaseDate() {
		return fmReleaseDate;
	}

	public void setFmReleaseDate(String fmReleaseDate) {
		this.fmReleaseDate = fmReleaseDate;
	}

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		if(depositAmount == null || depositAmount.equals("")){
			this.depositAmount = formatter.format(0.0);
		}else{
			this.depositAmount = depositAmount;
		}
	}

	public String getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getAccountList() {
		return accountList;
	}

	public void setAccountList(String accountList) {
		this.accountList = accountList;
	}

	public String getCvvCode() {
		return cvvCode;
	}

	public void setCvvCode(String cvvCode) {
		this.cvvCode = cvvCode;
	}

	public String getRefundNote() {
		return refundNote;
	}

	public void setRefundNote(String refundNote) {
		this.refundNote = refundNote;
	}

	public String getAutoACHInd() {
		return autoACHInd;
	}

	public void setAutoACHInd(String autoACHInd) {
		this.autoACHInd = autoACHInd;
	}

	public boolean isEnabledIndicator() {
		return enabledIndicator;
	}

	public void setEnabledIndicator(boolean enabledIndicator) {
		this.enabledIndicator = enabledIndicator;
	}

	public String getEinStoredFlag() {
		return einStoredFlag;
	}

	public void setEinStoredFlag(String einStoredFlag) {
		this.einStoredFlag = einStoredFlag;
	}

	public String getBuyerTotalDeposit() {
		return buyerTotalDeposit;
	}

	public void setBuyerTotalDeposit(String buyerTotalDeposit) {
		this.buyerTotalDeposit = buyerTotalDeposit;
	}

	public String getTaxPayerId() {
		return taxPayerId;
	}

	public void setTaxPayerId(String taxPayerId) {
		this.taxPayerId = taxPayerId;
	}

	public String getConfirmTaxPayerId() {
		return confirmTaxPayerId;
	}

	public void setConfirmTaxPayerId(String confirmTaxPayerId) {
		this.confirmTaxPayerId = confirmTaxPayerId;
	}

	public String getEinSsnInd() {
		return einSsnInd;
	}

	public void setEinSsnInd(String einSsnInd) {
		this.einSsnInd = einSsnInd;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	private String dob;
	private String documentType;
	private String documentIdNo;
	private String country;
	private Boolean ssnSaveInd;
	
	/*private Double buyerThreshold;
	private Boolean buyerBitFlag;
	private Boolean isSSN;
	private String ssnNo;*/
	
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentIdNo() {
		return documentIdNo;
	}

	public void setDocumentIdNo(String documentIdNo) {
		this.documentIdNo = documentIdNo;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getSsnSaveInd() {
		return ssnSaveInd;
	}

	public void setSsnSaveInd(Boolean ssnSaveInd) {
		this.ssnSaveInd = ssnSaveInd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	

	public String getWalletControlAmount() {
		return walletControlAmount;
	}

	public void setWalletControlAmount(String walletControlAmount) {
		this.walletControlAmount = walletControlAmount;
	}

	public List<SLDocumentDTO> getWalletControldocuments() {
		return walletControldocuments;
	}

	public void setWalletControldocuments(List<SLDocumentDTO> walletControldocuments) {
		this.walletControldocuments = walletControldocuments;
	}

	public String getWalletControlType() {
		return walletControlType;
	}

	public void setWalletControlType(String walletControlType) {
		this.walletControlType = walletControlType;
	}

	public Boolean getOnHold() {
		return new Boolean (onHold);
	}

	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
	}

	public List<File> getWalletControlFiles() {
		return walletControlFiles;
	}

	public void setWalletControlFiles(List<File> walletControlFiles) {
		this.walletControlFiles = walletControlFiles;
	}

	public List<String> getWalletControlFilesFileName() {
		return walletControlFilesFileName;
	}

	public void setWalletControlFilesFileName(List<String> walletControlFilesFileName) {
		this.walletControlFilesFileName = walletControlFilesFileName;
	}

	public List<String> getWalletControlFilesContentType() {
		return walletControlFilesContentType;
	}

	public void setWalletControlFilesContentType(List<String> walletControlFilesContentType) {
		this.walletControlFilesContentType = walletControlFilesContentType;
	}


	public Integer getEntityWalletControlID() {
		return entityWalletControlID;
	}

	public void setEntityWalletControlID(Integer entityWalletControlID) {
		this.entityWalletControlID = entityWalletControlID;
	}



	/*public Double getBuyerThreshold() {
		return buyerThreshold;
	}

	public void setBuyerThreshold(Double buyerThreshold) {
		this.buyerThreshold = buyerThreshold;
	}

	public Boolean getBuyerBitFlag() {
		return buyerBitFlag;
	}

	public void setBuyerBitFlag(Boolean buyerBitFlag) {
		this.buyerBitFlag = buyerBitFlag;
	}

	public Boolean getIsSSN() {
		return isSSN;
	}

	public void setIsSSN(Boolean isSSN) {
		this.isSSN = isSSN;
	}

	public String getSsnNo() {
		return ssnNo;
	}

	public void setSsnNo(String ssnNo) {
		this.ssnNo = ssnNo;
	}*/
	
	

}
