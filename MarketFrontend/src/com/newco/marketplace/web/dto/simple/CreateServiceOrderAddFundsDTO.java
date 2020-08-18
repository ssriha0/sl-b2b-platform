package com.newco.marketplace.web.dto.simple;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;

public class CreateServiceOrderAddFundsDTO extends SOWBaseTabDTO{

	private static final long serialVersionUID = 0L;
	
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zipcode;
	private String locationName;
	private String apartmentNo;
	private Integer sameAddressAsBilling = 1;
	private CreditCardDTO newCreditCard;	
	
	private Double transactionAmount;
	private Double availableBalance;
	private Double currentBalance;
	private Double totalSpendLimit;

	private String taxPayerId;
	private String confirmTaxPayerId;
	private Boolean showTaxPayerWidget = Boolean.FALSE;
	
	private Boolean checkboxSaveThisCard = Boolean.FALSE;
	private Boolean hasExistingCreditCard = Boolean.FALSE;
	private Boolean useExistingCard = Boolean.FALSE;
	private String existingCardSecurityCode;
	private CreditCardDTO existingCreditCard;

	private Map<String, Integer> monthList;
	private Map<String, Integer> yearList;
	private ArrayList<LookupVO> creditCardTypeList;
	
	private String serviceDate1Text;
	private String serviceDate2Text;
	private String fixedServiceDate;
	
    private String buyerTermsAndConditionAgreeInd;
    
	private String einSsnInd;
	
	private Boolean slBucksAgreeInd;
	private Integer slBucksAgreeId;
	
	public Boolean getCheckboxSaveThisCard() {
		return checkboxSaveThisCard;
	}

	public void setCheckboxSaveThisCard(Boolean checkboxSaveThisCard) {
		this.checkboxSaveThisCard = checkboxSaveThisCard;
	}

	public Integer getSameAddressAsBilling() {
		return sameAddressAsBilling;
	}

	public void setSameAddressAsBilling(Integer sameAddressAsBilling) {
		this.sameAddressAsBilling = sameAddressAsBilling;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Map<String, Integer> getMonthList() {
		return monthList;
	}

	public void setMonthList(Map<String, Integer> monthList) {
		this.monthList = monthList;
	}

	public Map<String, Integer> getYearList() {
		return yearList;
	}

	public void setYearList(Map<String, Integer> yearList) {
		this.yearList = yearList;
	}


	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return OrderConstants.SSO_ADD_FUNDS_DTO;
	}

	@Override
	public void validate() {
		checkValidZipAndState();
		if(getSameAddressAsBilling().intValue() == 0){
			isBillingAddressValidate();
		}
		
		if(transactionAmount > 0.0) {	
			if(getHasExistingCreditCard()) {
				checkIfUsingExistingCard();
			}
			if(!getUseExistingCard() && getNewCreditCard() != null) {
				isCreditCardInfoValidate();
			}
			validateTaxPayerId();
			validateTermsnConditions();
			validateSlBuckTermsnConditions();
		}
	}
	
	private void validateTermsnConditions() {
		if("false".equals(getBuyerTermsAndConditionAgreeInd())) {
			addError(getTheResourceBundle().getString("Buyer_Terms_And_Conditions"), getTheResourceBundle().getString("accountDTO.buyerTermsAndCondition.error.mustAgree")
						, OrderConstants.SOW_TAB_ERROR);
		}
	}

	private void validateSlBuckTermsnConditions() {
			if((getSlBucksAgreeInd() != null) && (getSlBucksAgreeInd().booleanValue()== false)) {
				addError(getTheResourceBundle().getString("Buyer_SLBUCKS_Terms_And_Conditions"), getTheResourceBundle().getString("accountDTO.slBucks.error.mustAgree")
							, OrderConstants.SOW_TAB_ERROR);
			}
	}
	
	
	
	
	private void checkValidZipAndState()
	{		
		if(getNewCreditCard() != null){
			if(StringUtils.isNotBlank(getNewCreditCard().getBillingZipCode())
					&& StringUtils.isNotBlank(getNewCreditCard().getBillingState())){
		
				int zipCheck = LocationUtils.checkIfZipAndStateValid(getNewCreditCard().getBillingZipCode()
						,getNewCreditCard().getBillingState());
				switch (zipCheck) {
					case Constants.LocationConstants.ZIP_NOT_VALID:
						addError(getTheResourceBundle().getString("Zip"), 
								getTheResourceBundle().getString("Zip_Not_Valid"), OrderConstants.SOW_TAB_ERROR);
						break;
					case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
						addError(getTheResourceBundle().getString("Zip"), 
								getTheResourceBundle().getString("Zip_State_No_Match"), OrderConstants.SOW_TAB_ERROR);
						break;
				}		
			}
		}
	}
	
	private void isBillingAddressValidate(){
		if(getNewCreditCard() != null){
			if(StringUtils.isBlank(getNewCreditCard().getBillingCity())){
				addError(getTheResourceBundle().getString("Billing_City"),
							getTheResourceBundle().getString(
									"Billing_City_Validation"),
							OrderConstants.SOW_TAB_ERROR);			
			}
			if(StringUtils.isBlank(getNewCreditCard().getBillingLocationName())){
				addError(getTheResourceBundle().getString("Billing_Location_Name"),
						getTheResourceBundle().getString(
								"Billing_Location_Name_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}		
			if(StringUtils.isBlank(getNewCreditCard().getBillingState())){
				addError(getTheResourceBundle().getString("Billing_State"),
						getTheResourceBundle().getString(
								"Billing_State_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}else if("-1".equals(getNewCreditCard().getBillingState())){
				addError(getTheResourceBundle().getString("Billing_State"),
						getTheResourceBundle().getString(
								"Billing_State_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}
			if(StringUtils.isBlank(getNewCreditCard().getBillingAddress1())){
				addError(getTheResourceBundle().getString("Billing_Street1"),
						getTheResourceBundle().getString(
								"Billing_Street1_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}
			if(StringUtils.isBlank(getNewCreditCard().getBillingZipCode())){
				addError(getTheResourceBundle().getString("Billing_Zip"),
						getTheResourceBundle().getString(
								"Billing_Zip_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}	
		}
	}
		
	private void isCreditCardInfoValidate(){
		String cardNumber = getNewCreditCard().getCreditCardNumber();
		int cardType = Integer.parseInt(getNewCreditCard().getCreditCardType());
		boolean formatError = false;
		
		
		if(getNewCreditCard() != null){
			if (cardNumber != null && cardNumber.length() > 0  ) {
				
				try{
					 Long.parseLong(cardNumber);
				}catch(NumberFormatException nf){
					formatError = true;
					addError(getTheResourceBundle().getString("CardNumber"),
							getTheResourceBundle().getString(
									"CardNumber_Integer_Digits_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}
			if(StringUtils.isBlank(getNewCreditCard().getCreditCardType())|| cardType == -1){
				addError(getTheResourceBundle().getString("CardTypeId"),
						getTheResourceBundle().getString(
								"CardTypeId_Description_Req_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}
			else if(StringUtils.isBlank(cardNumber)){
				addError(getTheResourceBundle().getString("Credit_Card_Number"),
						getTheResourceBundle().getString(
								"CardNumber_Description_Req_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}
			/*//Sears Commercial Card (not allowed at present) 
			else if ((cardNumber.length() == 16) && (cardNumber.substring(0, 6).equals(CreditCardConstants.SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS)))
				{
					addError(getTheResourceBundle().getString("CardNumber"),
							getTheResourceBundle().getString(
							"Sears_Commercial_Card_Validation_Msg"),
					OrderConstants.SOW_TAB_ERROR);
					
				}*/

			//
			else if((!formatError) && !UIUtils.isCreditCardValid(cardNumber, cardType)){
				addError(getTheResourceBundle().getString("Credit_Card_Number"),
						getTheResourceBundle().getString(
								"Credit_Card_Number_Not_Valid"),
						OrderConstants.SOW_TAB_ERROR);
			}

			if(StringUtils.isBlank(getNewCreditCard().getCreditCardHolderName())){
				addError(getTheResourceBundle().getString("Credit_Card_Holders_Name"),
						getTheResourceBundle().getString(
								"Credit_Card_Holders_Name_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}	

			boolean ifSearsWhiteCard = UIUtils.checkForSearsWhiteCard(cardNumber,cardType);
			if (ifSearsWhiteCard)
			{
				//For Sears White card  expiry date is not required so set it to a default value

					getNewCreditCard().setExpirationMonth("12");
					getNewCreditCard().setExpirationYear("49");
					// SearsWhiteCard indicator :For frontend display
					getNewCreditCard().setIsSearsWhiteCard(true);
					
			}
			else
			{
				if(StringUtils.isBlank(getNewCreditCard().getExpirationMonth())){
					addError(getTheResourceBundle().getString("Expiration_Month"),
							getTheResourceBundle().getString(
									"Expiration_Month_Validation"),
							OrderConstants.SOW_TAB_ERROR);			
				}else if("-1".equals(getNewCreditCard().getExpirationMonth())){
					addError(getTheResourceBundle().getString("Expiration_Month"),
							getTheResourceBundle().getString(
									"Expiration_Month_Validation"),
							OrderConstants.SOW_TAB_ERROR);	
				}
				
				if(StringUtils.isBlank(getNewCreditCard().getExpirationYear())){
					addError(getTheResourceBundle().getString("Expiration_Year"),
							getTheResourceBundle().getString(
									"Expiration_Year_Validation"),
							OrderConstants.SOW_TAB_ERROR);			
				}else if("-1".equals(getNewCreditCard().getExpirationYear())){
					addError(getTheResourceBundle().getString("Expiration_Year"),
							getTheResourceBundle().getString(
									"Expiration_Year_Validation"),
							OrderConstants.SOW_TAB_ERROR);	
				}
				// Check if expiration date entered is in the past. Made by Carlos
				else
				{				
					if(getNewCreditCard().getExpirationYear() != null &&
						!getNewCreditCard().getExpirationYear().equals("") &&
						getNewCreditCard().getExpirationMonth() != null &&
						!getNewCreditCard().getExpirationMonth().equals(""))
					{
						
						Calendar calendar = Calendar.getInstance();
						int current_year = calendar.get(Calendar.YEAR);
						int current_month = calendar.get(Calendar.MONTH);
						
						int dropdown_year = Integer.parseInt(getNewCreditCard().getExpirationYear());
						if((dropdown_year + 2000) == current_year)
						{
							int dropdown_month = Integer.parseInt(getNewCreditCard().getExpirationMonth());
							if(dropdown_month < current_month)
							{
								addError(getTheResourceBundle().getString("Expiration_Month"),
										getTheResourceBundle().getString(
										"Expiration_Date_Validation"),
										OrderConstants.SOW_TAB_ERROR);								
							}
							
						}
					}
					
				}
			}
			if(StringUtils.isBlank(getNewCreditCard().getSecurityCode())){
				addError(getTheResourceBundle().getString("Security_Code"),
						getTheResourceBundle().getString(
								"Security_Code_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}else if(!(getNewCreditCard().getSecurityCode().length() == 3
						|| getNewCreditCard().getSecurityCode().length() == 4)){
				addError(getTheResourceBundle().getString("Security_Code"),
						getTheResourceBundle().getString(
								"Security_Code_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
		}
	}

	private void checkIfUsingExistingCard(){
		if(getHasExistingCreditCard()){
			if(getTransactionAmount() != null
					&& getTransactionAmount().doubleValue() > 0.0){
				if(getUseExistingCard()){
					if(StringUtils.isEmpty(getExistingCardSecurityCode())){
						addError(getTheResourceBundle().getString("Security_Code"),
								getTheResourceBundle().getString(
										"Security_Code_Validation"),
								OrderConstants.SOW_TAB_ERROR);	
					}
					else if(!(getExistingCardSecurityCode().length() == 3
							|| getExistingCardSecurityCode().length() == 4)){
						addError(getTheResourceBundle().getString("Security_Code"),
							getTheResourceBundle().getString(
									"Security_Code_Length_Validation"),
							OrderConstants.SOW_TAB_ERROR);
					}
				}else if (null != newCreditCard){
					if(StringUtils.isEmpty(newCreditCard.getSecurityCode())){
						addError(getTheResourceBundle().getString("Security_Code"),
								getTheResourceBundle().getString(
										"Security_Code_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				} else {
					addError("",
							getTheResourceBundle().getString(
									"Use_Existing_Card_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}
		}
	}

	private void validateTaxPayerId(){
		if(getShowTaxPayerWidget()){
			if (getTaxPayerId() == null
					|| getTaxPayerId().length() < 9) {
				if (getTaxPayerId() == null
						|| getTaxPayerId().length() < 1) {
					addError(getTheResourceBundle().getString("TaxPayer_id"),
							getTheResourceBundle()
									.getString("TaxPayer_id_req_validation"),
							OrderConstants.SOW_TAB_ERROR);
	
				}else{
				addError(getTheResourceBundle().getString("TaxPayer_id"),
						getTheResourceBundle()
								.getString("TaxPayer_id_digit_validation"),
						OrderConstants.SOW_TAB_ERROR);
				}
	
			}
			if (getConfirmTaxPayerId() == null
					|| getConfirmTaxPayerId().length() < 9) {
				if (getConfirmTaxPayerId() == null
						|| getConfirmTaxPayerId().length() < 1) {
					addError(getTheResourceBundle().getString("Conf_TaxPayer_id"),
							getTheResourceBundle()
									.getString("Conf_TaxPayer_id_req_validation"),
							OrderConstants.SOW_TAB_ERROR);
	
				}else{
					addError(getTheResourceBundle().getString("Conf_TaxPayer_id"),
							getTheResourceBundle()
									.getString("Conf_TaxPayer_id_digit_validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
				
			}
			if(getTaxPayerId() != null && getConfirmTaxPayerId() != null && getTaxPayerId().length() > 1 && 
					getTaxPayerId().length() == 9 && getConfirmTaxPayerId().length() > 1 && getConfirmTaxPayerId().length() == 9){
				int ssn = 0;
				int confirmSsn = 0;
				if(!getTaxPayerId().startsWith("XXXXX")){
					try{
						ssn = Integer.parseInt(getTaxPayerId());
					}catch(NumberFormatException nf){
						addError(getTheResourceBundle().getString("TaxPayer_id"),
								getTheResourceBundle().getString(
										"TaxPayer_id_validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					try{
						confirmSsn 	= Integer.parseInt(getConfirmTaxPayerId());
					}catch(NumberFormatException nf){
						addError(getTheResourceBundle().getString("Conf_TaxPayer_id"),
								getTheResourceBundle().getString(
										"Conf_TaxPayer_id_validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if(!(ssn == confirmSsn)){
						addError(getTheResourceBundle().getString("TaxPayer_ConfTaxPayer"),
								getTheResourceBundle().getString(
										"TaxPayer_ConfTaxPayer_validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					
				}
				
			}
		}
	}
	
	public void addPostError(String fieldId, String errorMsg,String errorType){
		addError(fieldId,errorMsg,errorType);	
	}
	
	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public ArrayList<LookupVO> getCreditCardTypeList() {
		return creditCardTypeList;
	}

	public void setCreditCardTypeList(ArrayList<LookupVO> creditCardTypeList) {
		this.creditCardTypeList = creditCardTypeList;
	}

	public CreditCardDTO getExistingCreditCard() {
		return existingCreditCard;
	}

	public void setExistingCreditCard(CreditCardDTO existingCreditCard) {
		this.existingCreditCard = existingCreditCard;
	}

	public Boolean getHasExistingCreditCard() {
		return hasExistingCreditCard;
	}

	public void setHasExistingCreditCard(Boolean hasExistingCreditCard) {
		this.hasExistingCreditCard = hasExistingCreditCard;
	}

	public Boolean getUseExistingCard() {
		return useExistingCard;
	}

	public void setUseExistingCard(Boolean useExistingCard) {
		this.useExistingCard = useExistingCard;
	}

	public String getExistingCardSecurityCode() {
		return existingCardSecurityCode;
	}

	public void setExistingCardSecurityCode(String existingCardSecurityCode) {
		this.existingCardSecurityCode = existingCardSecurityCode;
	}

	public CreditCardDTO getNewCreditCard() {
		return newCreditCard;
	}

	public void setNewCreditCard(CreditCardDTO newCreditCard) {
		this.newCreditCard = newCreditCard;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Double getTotalSpendLimit() {
		return totalSpendLimit;
	}

	public void setTotalSpendLimit(Double totalSpendLimit) {
		this.totalSpendLimit = totalSpendLimit;
	}

	public String getConfirmTaxPayerId() {
		return confirmTaxPayerId;
	}

	public void setConfirmTaxPayerId(String confirmTaxPayerId) {
		this.confirmTaxPayerId = confirmTaxPayerId;
	}

	public Boolean getShowTaxPayerWidget() {
		return showTaxPayerWidget;
	}

	public void setShowTaxPayerWidget(Boolean showTaxPayerWidget) {
		this.showTaxPayerWidget = showTaxPayerWidget;
	}

	public String getTaxPayerId() {
		return taxPayerId;
	}

	public void setTaxPayerId(String taxPayerId) {
		this.taxPayerId = taxPayerId;
	}

	public String getFixedServiceDate() {
		return fixedServiceDate;
	}

	public void setFixedServiceDate(String fixedServiceDate) {
		this.fixedServiceDate = fixedServiceDate;
	}

	public String getServiceDate1Text() {
		return serviceDate1Text;
	}

	public void setServiceDate1Text(String serviceDate1Text) {
		this.serviceDate1Text = serviceDate1Text;
	}

	public String getServiceDate2Text() {
		return serviceDate2Text;
	}

	public void setServiceDate2Text(String serviceDate2Text) {
		this.serviceDate2Text = serviceDate2Text;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getApartmentNo() {
		return apartmentNo;
	}

	public void setApartmentNo(String apartmentNo) {
		this.apartmentNo = apartmentNo;
	}

	public String getBuyerTermsAndConditionAgreeInd() {
		return buyerTermsAndConditionAgreeInd;
	}

	public void setBuyerTermsAndConditionAgreeInd(
			String buyerTermsAndConditionAgreeInd) {
		this.buyerTermsAndConditionAgreeInd = buyerTermsAndConditionAgreeInd;
	}

	public String getEinSsnInd() {
		return einSsnInd;
	}

	public void setEinSsnInd(String einSsnInd) {
		this.einSsnInd = einSsnInd;
	}

	/**
	 * @return the slBucksAgreeInd
	 */
	public Boolean getSlBucksAgreeInd() {
		return slBucksAgreeInd;
	}

	/**
	 * @param slBucksAgreeInd the slBucksAgreeInd to set
	 */
	public void setSlBucksAgreeInd(Boolean slBucksAgreeInd) {
		this.slBucksAgreeInd = slBucksAgreeInd;
	}

	/**
	 * @return the slBucksAgreeId
	 */
	public Integer getSlBucksAgreeId() {
		return slBucksAgreeId;
	}

	/**
	 * @param slBucksAgreeId the slBucksAgreeId to set
	 */
	public void setSlBucksAgreeId(Integer slBucksAgreeId) {
		this.slBucksAgreeId = slBucksAgreeId;
	}

}
