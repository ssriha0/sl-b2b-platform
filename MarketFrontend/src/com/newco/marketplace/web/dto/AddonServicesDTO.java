package com.newco.marketplace.web.dto;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.interfaces.OrderConstants;

public class AddonServicesDTO extends SerializedBaseDTO
{
	private static final long serialVersionUID = -393425911737977967L;

	private List<AddonServiceRowDTO> addonServicesList = new ArrayList<AddonServiceRowDTO>();
	private Double sumOfEndCustomerChargesMinusMisc = 0.0;
	private List<LabelValueBean> addonDropdownList = new ArrayList<LabelValueBean>();
	private HashMap paymentRadio = null;
	
	private boolean upsellPaymentIndicator = false;
	// Totals at the bottom of each of the columns
	private Integer quantityTotal;
	private Double providerPaidTotal;	
	private Double endCustomerChargeTotal;
	private Double endCustomerSubtotalTotal;
	private Double hiddenEndCustomerSubtotalTotal;
	
	// Addon Services Checkbox
	private String addonCheckbox ="";
	
	// Customer Payment Radio Button
	private String paymentRadioSelection = OrderConstants.UPSELL_PAYMENT_TYPE_CHECK;
	
	public boolean paymentByCheck = false;
	public boolean paymentByCash = false;
	public boolean paymentByCC = false;

	// Customer Payment: check
	private String checkNumber;
	private Double checkAmount;
	
	private Double paymentAmount; 
	private String paymentAmountDisplayStr;
	// Customer Payment: Credit Card
	private Integer expMonth;
	private Integer expYear;
	private String preAuthNumber;
	private Double amtAuthorized;
	private String creditCardNumber;
	private String creditCardNumberActual;
	private String enCreditCardNo;
	private String maskedAccountNo;
	private String token;
	// Static dropdown menus
	private List<DropdownOptionDTO> monthOptions = null;	
	private List<DropdownOptionDTO> yearOptions = null;	
	private List<DropdownOptionDTO> creditCardOptions = null;
	private Integer selectedMonth;
	private Integer selectedYear;
	private Integer selectedCreditCardType;
	private String paymentInformation;
	private String loggedInUser = "";
	private String paymentReceivedDateDisplayFormattedStr;
	private String selectedCreditCardTypeStr;
	private String editOrCancel;
	private String cardAddedOrEdited;
	private boolean isTokenizedMasked =false;
	private Double providerTaxPaidTotal;
	
	//SL-3768
	private String creditCardAuthTokenizeUrl;
	private String creditCardTokenUrl;
	private String creditCardTokenAPICrndl;
	
	private String userName;
	 
	private String maskedCardNumber;
	private String tokenizeCardNumber;
	private String correlationId;
	private String responseCode;
	private String responseMessage;
	private String authToken;
	private String tokenLife;
	private String responseXML;
	private String sowErrFieldId;
	private String sowErrMsg;
	
	public void addAddonService(Integer addonId,String sku, String desc, Integer qty, Double providerPaid, Double endCustomerCharge, Double endCustomerSubtotal, boolean misc, Double margin, String coverage, boolean skipReqAddon, boolean autoGenInd,Integer addonPermitTypeId)
	{
		AddonServiceRowDTO row = setAddonServiceRowValue(addonId, sku, desc, qty, providerPaid, endCustomerCharge, endCustomerSubtotal,
				misc, margin, coverage, skipReqAddon, autoGenInd, addonPermitTypeId);
		addonServicesList.add(row);
		
	}
	
	public void addAddonService(Integer addonId, String sku, String desc, Integer qty, Double providerPaid,
			Double endCustomerCharge, Double endCustomerSubtotal, boolean misc, Double margin, String coverage, boolean skipReqAddon,
			boolean autoGenInd, Integer addonPermitTypeId, Double taxPercentage, Integer skuGroupType) {
		AddonServiceRowDTO row = setAddonServiceRowValue(addonId, sku, desc, qty, providerPaid, endCustomerCharge, endCustomerSubtotal,
				misc, margin, coverage, skipReqAddon, autoGenInd, addonPermitTypeId);
		row.setTaxPercentage(taxPercentage);
		row.setSkuGroupType(skuGroupType);
		addonServicesList.add(row);
		
	}

	private AddonServiceRowDTO setAddonServiceRowValue(Integer addonId, String sku, String desc, Integer qty, Double providerPaid,
			Double endCustomerCharge, Double endCustomerSubtotal, boolean misc, Double margin, String coverage, boolean skipReqAddon,
			boolean autoGenInd, Integer addonPermitTypeId) {
		AddonServiceRowDTO row = new AddonServiceRowDTO();
		
		row.setAddonId(addonId);
		row.setSku(sku);
		row.setDescription(desc);
		row.setQuantity(qty);
		row.setProviderPaid(providerPaid);
		row.setEndCustomerCharge(endCustomerCharge);
		row.setEndCustomerSubtotal(endCustomerSubtotal);
		row.setMisc(misc);
		row.setMargin(margin);
		row.setCoverage(coverage);
		row.setSkipReqAddon(skipReqAddon);
		row.setAutoGenInd(autoGenInd);
		row.setAddonPermitTypeId(addonPermitTypeId);
		row.setPermitType(addonPermitTypeId);
		
		return row;
	}
	
	public void resetAddonService(String sku, String desc, Double providerPaid, Double endCustomerCharge, Double endCustomerSubtotal, boolean misc, Double margin)
	{
		for(AddonServiceRowDTO row : addonServicesList)
		{
			if(sku == row.getSku())
			{
				if(misc == false)
				{
					row.setEndCustomerCharge(endCustomerCharge);
					row.setProviderPaid(providerPaid);
					row.setDescription(desc);
				}
				row.setEndCustomerSubtotal(row.getQuantity() * row.getEndCustomerSubtotal());
				break;
			}
		}
		
		
	}
	
	
	
	public List<AddonServiceRowDTO> getAddonServicesList()
	{
		return addonServicesList;
	}
	public void setAddonServicesList(List<AddonServiceRowDTO> addonServicesList)
	{
		this.addonServicesList = addonServicesList;
	}
	public Integer getQuantityTotal()
	{
		return quantityTotal;
	}
	public void setQuantityTotal(Integer quantityTotal)
	{
		this.quantityTotal = quantityTotal;
	}
	public Double getProviderPaidTotal()
	{
		return providerPaidTotal;
	}
	public void setProviderPaidTotal(Double providerPaidTotal)
	{
		this.providerPaidTotal = providerPaidTotal;
	}
	public Double getEndCustomerChargeTotal()
	{
		return endCustomerChargeTotal;
	}
	public void setEndCustomerChargeTotal(Double endCustomerChargeTotal)
	{
		this.endCustomerChargeTotal = endCustomerChargeTotal;
	}
	
	public Double getHiddenEndCustomerSubtotalTotal() {
		return hiddenEndCustomerSubtotalTotal;
	}
	public void setHiddenEndCustomerSubtotalTotal(Double hiddenEndCustomerSubtotalTotal) {
		this.hiddenEndCustomerSubtotalTotal = hiddenEndCustomerSubtotalTotal;
	}


	public String getPaymentRadioSelection()
	{
		return paymentRadioSelection;
	}
	public void setPaymentRadioSelection(String paymentRadioSelection)
	{
		this.paymentRadioSelection = paymentRadioSelection;
	}
	public String getCheckNumber()
	{
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber)
	{
		this.checkNumber = checkNumber;
	}
	public Double getCheckAmount()
	{
		return checkAmount;
	}
	public void setCheckAmount(Double checkAmount)
	{
		this.checkAmount = checkAmount;
	}
	public Integer getExpMonth()
	{
		return expMonth;
	}
	public void setExpMonth(Integer expMonth)
	{
		this.expMonth = expMonth;
	}
	public Integer getExpYear()
	{
		return expYear;
	}
	public void setExpYear(Integer expYear)
	{
		this.expYear = expYear;
	}
	public String getPreAuthNumber()
	{
		return preAuthNumber;
	}
	public void setPreAuthNumber(String preAuthNumber)
	{
		this.preAuthNumber = preAuthNumber;
	}
	public Double getAmtAuthorized()
	{
		return amtAuthorized;
	}
	public void setAmtAuthorized(Double amtAuthorized)
	{
		this.amtAuthorized = amtAuthorized;
	}


	public Double getEndCustomerSubtotalTotal()
	{
		return endCustomerSubtotalTotal;
	}


	public void setEndCustomerSubtotalTotal(Double endCustomerSubtotalTotal)
	{
		this.endCustomerSubtotalTotal = endCustomerSubtotalTotal;
	}


	public List<LabelValueBean> getAddonDropdownList()
	{
		return addonDropdownList;
	}


	public void setAddonDropdownList(List<LabelValueBean> addonDropdownList)
	{
		this.addonDropdownList = addonDropdownList;
	}


	public HashMap getPaymentRadio()
	{
		return paymentRadio;
	}


	public void setPaymentRadio(HashMap paymentRadio)
	{
		this.paymentRadio = paymentRadio;
	}


	public List<DropdownOptionDTO> getMonthOptions()
	{
		return monthOptions;
	}


	public void setMonthOptions(List<DropdownOptionDTO> monthOptions)
	{
		this.monthOptions = monthOptions;
	}


	public List<DropdownOptionDTO> getYearOptions()
	{
		return yearOptions;
	}


	public void setYearOptions(List<DropdownOptionDTO> yearOptions)
	{
		this.yearOptions = yearOptions;
	}


	public List<DropdownOptionDTO> getCreditCardOptions()
	{
		return creditCardOptions;
	}


	public void setCreditCardOptions(List<DropdownOptionDTO> creditCardOptions)
	{
		this.creditCardOptions = creditCardOptions;
	}


	public Integer getSelectedMonth()
	{
		return selectedMonth;
	}


	public void setSelectedMonth(Integer selectedMonth)
	{
		this.selectedMonth = selectedMonth;
	}


	public Integer getSelectedYear()
	{
		return selectedYear;
	}


	public void setSelectedYear(Integer selectedYear)
	{
		this.selectedYear = selectedYear;
	}


	public Integer getSelectedCreditCardType()
	{
		return selectedCreditCardType;
	}


	public void setSelectedCreditCardType(Integer selectedCreditCardType)
	{
		this.selectedCreditCardType = selectedCreditCardType;
	}


	public Double getPaymentAmount() {
		return paymentAmount;
	}


	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}


	public boolean isUpsellPaymentIndicator() {
		return upsellPaymentIndicator;
	}


	public void setUpsellPaymentIndicator(boolean upsellPaymentIndicator)
	{
		this.upsellPaymentIndicator = upsellPaymentIndicator;
	}


	public String getCreditCardNumber()
	{
		return creditCardNumber;
	}


	public void setCreditCardNumber(String creditCardNumber)
	{
		this.creditCardNumber = creditCardNumber;
	}


	public String getPaymentInformation() {
		return paymentInformation;
	}


	public void setPaymentInformation(String paymentInformation) {
		this.paymentInformation = paymentInformation;
	}


	public Double getSumOfEndCustomerChargesMinusMisc() {
		return sumOfEndCustomerChargesMinusMisc;
	}


	public void setSumOfEndCustomerChargesMinusMisc(
			Double sumOfEndCustomerChargesMinusMisc) {
		this.sumOfEndCustomerChargesMinusMisc = sumOfEndCustomerChargesMinusMisc;
	}


	public String getAddonCheckbox()
	{
		return addonCheckbox;
	}


	public void setAddonCheckbox(String addonCheckbox)
	{
		this.addonCheckbox = addonCheckbox;
	}


	public boolean isPaymentByCheck() {
		return paymentByCheck;
	}


	public void setPaymentByCheck(boolean paymentByCheck) {
		this.paymentByCheck = paymentByCheck;
	}


	public String getLoggedInUser() {
		return loggedInUser;
	}


	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}


	/**
	 * @return the paymentByCash
	 */
	public boolean isPaymentByCash() {
		return paymentByCash;
	}


	/**
	 * @param paymentByCash the paymentByCash to set
	 */
	public void setPaymentByCash(boolean paymentByCash) {
		this.paymentByCash = paymentByCash;
	}


	/**
	 * @return the paymentByCC
	 */
	public boolean isPaymentByCC() {
		return paymentByCC;
	}


	/**
	 * @param paymentByCC the paymentByCC to set
	 */
	public void setPaymentByCC(boolean paymentByCC) {
		this.paymentByCC = paymentByCC;
	}


	/**
	 * @return the paymentReceivedDateDisplayFormattedSr
	 */
	public String getPaymentReceivedDateDisplayFormattedStr() {
		return paymentReceivedDateDisplayFormattedStr;
	}


	/**
	 * @param paymentReceivedDateDisplayFormattedSr the paymentReceivedDateDisplayFormattedSr to set
	 */
	public void setPaymentReceivedDateDisplayFormattedStr(
			String paymentReceivedDateDisplayFormattedSr) {
		this.paymentReceivedDateDisplayFormattedStr = paymentReceivedDateDisplayFormattedSr;
	}


	/**
	 * @return the selectedCreditCardTypeStr
	 */
	public String getSelectedCreditCardTypeStr() {
		return selectedCreditCardTypeStr;
	}


	/**
	 * @param selectedCreditCardTypeStr the selectedCreditCardTypeStr to set
	 */
	public void setSelectedCreditCardTypeStr(String selectedCreditCardTypeStr) {
		this.selectedCreditCardTypeStr = selectedCreditCardTypeStr;
	}


	/**
	 * @return the paymentAmountDisplayStr
	 */
	public String getPaymentAmountDisplayStr() {
		return paymentAmountDisplayStr;
	}


	/**
	 * @param paymentAmountDisplayStr the paymentAmountDisplayStr to set
	 */
	public void setPaymentAmountDisplayStr(String paymentAmountDisplayStr) {
		this.paymentAmountDisplayStr = paymentAmountDisplayStr;
	}


	public String getEditOrCancel() {
		return editOrCancel;
	}


	public void setEditOrCancel(String editOrCancel) {
		this.editOrCancel = editOrCancel;
	}


	public String getCreditCardNumberActual() {
		return creditCardNumberActual;
	}


	public void setCreditCardNumberActual(String creditCardNumberActual) {
		this.creditCardNumberActual = creditCardNumberActual;
	}

    public String getEnCreditCardNo() {
		return enCreditCardNo;
	}


	public void setEnCreditCardNo(String enCreditCardNo) {
		this.enCreditCardNo = enCreditCardNo;
	}


	public String getCardAddedOrEdited() {
		return cardAddedOrEdited;
	}


	public void setCardAddedOrEdited(String cardAddedOrEdited) {
		this.cardAddedOrEdited = cardAddedOrEdited;
	}


	public String getMaskedAccountNo() {
		return maskedAccountNo;
	}


	public void setMaskedAccountNo(String maskedAccountNo) {
		this.maskedAccountNo = maskedAccountNo;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public boolean isTokenizedMasked() {
		return isTokenizedMasked;
	}


	public void setTokenizedMasked(boolean isTokenizedMasked) {
		this.isTokenizedMasked = isTokenizedMasked;
	}

	public Double getProviderTaxPaidTotal() {
		return providerTaxPaidTotal;
	}

	public void setProviderTaxPaidTotal(Double providerTaxPaidTotal) {
		this.providerTaxPaidTotal = providerTaxPaidTotal;
	}
	
	//SL-3768 start
	
	public String getMaskedCardNumber() {
	return maskedCardNumber;
}

public void setMaskedCardNumber(String maskedCardNumber) {
	this.maskedCardNumber = maskedCardNumber;
}

public String getTokenizeCardNumber() {
	return tokenizeCardNumber;
}

public void setTokenizeCardNumber(String tokenizeCardNumber) {
	this.tokenizeCardNumber = tokenizeCardNumber;
}

public String getCorrelationId() {
	return correlationId;
}

public void setCorrelationId(String correlationId) {
	this.correlationId = correlationId;
}

public String getResponseCode() {
	return responseCode;
}

public void setResponseCode(String responseCode) {
	this.responseCode = responseCode;
}

public String getResponseMessage() {
	return responseMessage;
}

public void setResponseMessage(String responseMessage) {
	this.responseMessage = responseMessage;
}

public String getCreditCardAuthTokenizeUrl() {
	return creditCardAuthTokenizeUrl;
}

public void setCreditCardAuthTokenizeUrl(String creditCardAuthTokenizeUrl) {
	this.creditCardAuthTokenizeUrl = creditCardAuthTokenizeUrl;
}

public String getCreditCardTokenUrl() {
	return creditCardTokenUrl;
}

public void setCreditCardTokenUrl(String creditCardTokenUrl) {
	this.creditCardTokenUrl = creditCardTokenUrl;
}

public String getCreditCardTokenAPICrndl() {
	return creditCardTokenAPICrndl;
}

public void setCreditCardTokenAPICrndl(String creditCardTokenAPICrndl) {
	this.creditCardTokenAPICrndl = creditCardTokenAPICrndl;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
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

public String getResponseXML() {
	return responseXML;
}
public void setResponseXML(String responseXML) {
	this.responseXML = responseXML;
}

public String getSowErrFieldId() {
	return sowErrFieldId;
}

public void setSowErrFieldId(String sowErrFieldId) {
	this.sowErrFieldId = sowErrFieldId;
}

public String getSowErrMsg() {
	return sowErrMsg;
}

public void setSowErrMsg(String sowErrMsg) {
	this.sowErrMsg = sowErrMsg;
}


//SL-3768 end
}
