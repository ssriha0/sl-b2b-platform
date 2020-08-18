package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.utils.CreditCardValidatonUtil;

public class FMManageAccountsTabDTO extends SOWBaseTabDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8532309702706160493L;
	
	// Bank Account Panel
	private String accountDescription;
	private String accountHolder;
	private String accountType;
	private String financialInstitution;
	private String routingNumber;
	private String confirmRoutingNumber;
	private String accountNumber;
	private String confirmAccountNumber;
	private String accountId;
	private Integer entityTypeId;
	private String oldAccountId;
	private String activeInd;
	private String autoACHInd;
	private int saveAutoFundInd;
	
	// Credit Card Account Panel
	private Long cardId;
	private String oldCardId;
	private String cardAccountType;
	private String nameOnCard;
	private Integer locationTypeId;
	private String transactionId;
	private Double transactionAmount;
	private String cardHolderName;
	private Long cardTypeId;
	private String encCardNo;
	private String cardNumber;
	private String expirationMonth;
	private String expirationYear;
	private String billingAddress1;
	private String billingAddress2;
	private String billingCity;
	private String billingState;
	private String billingZip;
	private boolean ifSsearsWhiteCard = false;
	
	private String creditCardAuthTokenizeUrl;
	private String creditCardAuthTokenizeXapikey;
	private String creditCardTokenUrl;
	private String creditCardTokenAPICrndl;
	
	private String userName;
	
	private boolean enabledIndicator ; 
	private String maskedCardNumber;
	private String tokenizeCardNumber;
	private String correlationId;
	private String responseCode;
	private String responseMessage;
	
	private String creditCardErrorMessage;
	
	// Escheat Account Panel
	
	private String escheatAccountDescription;
	private String escheatAccountHolder;
	private String escheatAccountType;
	private String escheatFinancialInstitution;
	private String escheatRoutingNumber;
	private String escheatConfirmRoutingNumber;
	private String escheatAccountNumber;
	private String escheatConfirmAccountNumber;
	private String escheatAccountId;
	private Integer escheatEntityTypeId;
	private String escheatOldAccountId;
	private String escheatActiveInd;
	private String escheatAutoACHInd;
	private int escheatSaveAutoFundInd;	
	private boolean escheatEnabledIndicator ;
	
	
	
	
	
	
	@Override
	public void validate() {
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
			if (this.accountDescription != null
					&& this.accountDescription.length() > 50) {
				addError(getTheResourceBundle().getString("Account_Description"),
						getTheResourceBundle().getString(
								"Account_Description_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (this.accountDescription == null
					|| this.accountDescription.length() < 1) {
				addWarning(getTheResourceBundle().getString("Account_Description"),
						getTheResourceBundle().getString(
								"Account_Description_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
		//SLT-1172	
		if (CreditCardValidatonUtil.validateCCNumbers(accountDescription)) {
			addError(getTheResourceBundle().getString("Account_Description"),
					getTheResourceBundle().getString("Account_Description_CreditCard_Validation_Msg"),
					OrderConstants.SOW_TAB_ERROR);
		}
			
		
//			if (this.accountHolder!= null
//					&& this.accountHolder.length() > 50) {
//				addError(getTheResourceBundle().getString("Account_Holder"),
//						getTheResourceBundle().getString(
//								"Account_Holder_Length_Validation"),
//						OrderConstants.SOW_TAB_ERROR);
//			}
			if (this.accountHolder == null
					|| this.accountHolder.length() < 1) {
				addWarning(getTheResourceBundle().getString("Account_Holder"),
						getTheResourceBundle().getString(
								"Account_Holder_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			
			if (this.accountType == null
					|| this.accountType.trim().equals("-1")) {
				addWarning(getTheResourceBundle().getString("Account_Type"),
						getTheResourceBundle().getString(
								"Account_Type_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			
			if (this.financialInstitution == null
					|| this.financialInstitution.length() < 1) {
				addWarning(getTheResourceBundle().getString("Financial_Institution"),
						getTheResourceBundle().getString(
								"Financial_Institution_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (this.financialInstitution != null
					&& this.financialInstitution.length() > 50) {
				addError(getTheResourceBundle().getString("Financial_Institution"),
						getTheResourceBundle().getString(
								"Financial_Institution_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			//
			
			if (this.routingNumber == null
					|| this.routingNumber.length() < 1) {
				addWarning(getTheResourceBundle().getString("Routing_Number"),
						getTheResourceBundle()
								.getString("Routing_Number_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (this.routingNumber != null
					&& (this.routingNumber.length()>0 && this.routingNumber.length() < 9)) {
				addError(getTheResourceBundle().getString("Routing_Number"),
						getTheResourceBundle()
								.getString("Routing_Number_Digit_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (this.confirmRoutingNumber == null
					|| this.confirmRoutingNumber.length() < 1) {
				addWarning(getTheResourceBundle().getString("Conf_Routing_Number"),
						getTheResourceBundle()
								.getString("Conf_Routing_Number_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (this.confirmRoutingNumber != null
					&& (this.confirmRoutingNumber.length()>0 && this.confirmRoutingNumber.length() < 9)) {
					addError(getTheResourceBundle().getString("Conf_Routing_Number"),
							getTheResourceBundle()
									.getString("Conf_Routing_Number_Digit_Validation"),
							OrderConstants.SOW_TAB_WARNING);
				
			}
			
			if (this.routingNumber != null && this.routingNumber.length() == 9) {
			int routingNo = 0;

			if (!this.routingNumber.startsWith("X")) {
				try {
					routingNo = Integer.parseInt(this.routingNumber);
					boolean validRoutingNumber = this
							.validateRoutingNumber(this.routingNumber);
					if (!validRoutingNumber) {
						addError(getTheResourceBundle().getString(
								"Routing_Number"), getTheResourceBundle()
								.getString("Routing_Number_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				} catch (NumberFormatException nf) {
					addError(
							getTheResourceBundle().getString("Routing_Number"),
							getTheResourceBundle().getString(
									"Routing_Number_Numeric_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}

			}

			if (this.confirmRoutingNumber != null
					&& this.confirmRoutingNumber.length() == 9) {

				int confirmRoutingNo = 0;
				if (!this.routingNumber.startsWith("X")) {
					try {
						confirmRoutingNo = Integer
								.parseInt(this.confirmRoutingNumber);

					} catch (NumberFormatException nf) {
						addError(getTheResourceBundle().getString(
								"Conf_Routing_Number"), getTheResourceBundle()
								.getString("Conf_Routing_Number_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (!(routingNo == confirmRoutingNo)) {
						addError(getTheResourceBundle().getString(
								"Routing_ConfRouting"), getTheResourceBundle()
								.getString("Routing_ConfRouting_validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
			//
		}
			//
			if (this.accountNumber == null
					|| this.accountNumber.length() < 1) {
				addWarning(getTheResourceBundle().getString("Account_Number"),
						getTheResourceBundle()
								.getString("Account_Number_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (this.accountNumber != null
					&& this.accountNumber.length() > 0 && (this.accountNumber.length() > 17 || this.accountNumber.length()< 3)) {
				addError(getTheResourceBundle().getString("Account_Number"),
						getTheResourceBundle()
								.getString("Account_Number_Digit_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (this.confirmAccountNumber == null
					|| this.confirmAccountNumber.length() < 1) {
				addWarning(getTheResourceBundle().getString("Conf_Account_Number"),
						getTheResourceBundle()
								.getString("Conf_Account_Number_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (this.confirmAccountNumber != null
					&& this.confirmAccountNumber.length() > 0 && (this.confirmAccountNumber.length() > 17 || this.confirmAccountNumber.length()< 3)) {
				addError(getTheResourceBundle().getString("Conf_Account_Number"),
						getTheResourceBundle()
								.getString("Conf_Account_Number_Digit_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if(this.accountNumber != null && this.confirmAccountNumber != null && this.accountNumber.length() >= 3 && 
					this.accountNumber.length() <= 17 && this.confirmAccountNumber.length() >= 3 && this.confirmAccountNumber.length() <= 17){
				if(this.accountNumber.length()>0 && this.confirmAccountNumber.length()>0){
					long accountNo = 0;
					long confirmAccountNo = 0;
					if(!this.accountNumber.startsWith("X") && !this.confirmAccountNumber.startsWith("X")){
						try{
							accountNo = Long.parseLong(this.accountNumber);
						}catch(NumberFormatException nf){
							addError(getTheResourceBundle().getString("Account_Number"),
									getTheResourceBundle().getString(
											"Account_Number_Validation"),
									OrderConstants.SOW_TAB_ERROR);
						}
					try{
						confirmAccountNo 	= Long.parseLong(this.confirmAccountNumber);
					}catch(NumberFormatException nf){
						addError(getTheResourceBundle().getString("Conf_Account_Number"),
								getTheResourceBundle().getString(
										"Conf_Account_Number_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if(!(accountNo == confirmAccountNo)){
						addError(getTheResourceBundle().getString("Account_ConfAccount"),
								getTheResourceBundle().getString(
										"Account_ConfAccount_validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
		}
			
			//
		
	}
	
	//Validation for escheat account
	
	
	public void validateEscheatAccount() {
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
			if (this.escheatAccountDescription != null
					&& this.escheatAccountDescription.length() > 50) {
				addError(getTheResourceBundle().getString("Account_Description"),
						getTheResourceBundle().getString(
								"Account_Description_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (this.escheatAccountDescription == null
					|| this.escheatAccountDescription.length() < 1) {
				addWarning(getTheResourceBundle().getString("Account_Description"),
						getTheResourceBundle().getString(
								"Account_Description_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}

			if (this.escheatAccountHolder == null
					|| this.escheatAccountHolder.length() < 1) {
				addWarning(getTheResourceBundle().getString("Account_Holder"),
						getTheResourceBundle().getString(
								"Account_Holder_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			
			/*if (this.escheatAccountType == null
					|| this.escheatAccountType.trim().equals("-1")) {
				addWarning(getTheResourceBundle().getString("Account_Type"),
						getTheResourceBundle().getString(
								"Account_Type_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}*/
			
			if (this.escheatFinancialInstitution == null
					|| this.escheatFinancialInstitution.length() < 1) {
				addWarning(getTheResourceBundle().getString("Financial_Institution"),
						getTheResourceBundle().getString(
								"Financial_Institution_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (this.escheatFinancialInstitution != null
					&& this.escheatFinancialInstitution.length() > 50) {
				addError(getTheResourceBundle().getString("Financial_Institution"),
						getTheResourceBundle().getString(
								"Financial_Institution_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			//
			
			if (this.escheatRoutingNumber == null
					|| this.escheatRoutingNumber.length() < 1) {
				addWarning(getTheResourceBundle().getString("Routing_Number"),
						getTheResourceBundle()
								.getString("Routing_Number_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (this.escheatRoutingNumber != null
					&& (this.escheatRoutingNumber.length()>0 && this.escheatRoutingNumber.length() < 9)) {
				addError(getTheResourceBundle().getString("Routing_Number"),
						getTheResourceBundle()
								.getString("Routing_Number_Digit_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (this.escheatConfirmRoutingNumber == null
					|| this.escheatConfirmRoutingNumber.length() < 1) {
				addWarning(getTheResourceBundle().getString("Conf_Routing_Number"),
						getTheResourceBundle()
								.getString("Conf_Routing_Number_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (this.escheatConfirmRoutingNumber != null
					&& (this.escheatConfirmRoutingNumber.length()>0 && this.escheatConfirmRoutingNumber.length() < 9)) {
					addError(getTheResourceBundle().getString("Conf_Routing_Number"),
							getTheResourceBundle()
									.getString("Conf_Routing_Number_Digit_Validation"),
							OrderConstants.SOW_TAB_WARNING);
				
			}
			
			if (this.escheatRoutingNumber != null && this.escheatRoutingNumber.length() == 9) {
			int routingNo = 0;

			if (!this.escheatRoutingNumber.startsWith("X")) {
				try {
					routingNo = Integer.parseInt(this.escheatRoutingNumber);
					boolean validRoutingNumber = this
							.validateRoutingNumber(this.escheatRoutingNumber);
					if (!validRoutingNumber) {
						addError(getTheResourceBundle().getString(
								"Routing_Number"), getTheResourceBundle()
								.getString("Routing_Number_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				} catch (NumberFormatException nf) {
					addError(
							getTheResourceBundle().getString("Routing_Number"),
							getTheResourceBundle().getString(
									"Routing_Number_Numeric_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}

			}

			if (this.escheatConfirmRoutingNumber != null
					&& this.escheatConfirmRoutingNumber.length() == 9) {

				int confirmRoutingNo = 0;
				if (!this.escheatRoutingNumber.startsWith("X")) {
					try {
						confirmRoutingNo = Integer
								.parseInt(this.escheatConfirmRoutingNumber);

					} catch (NumberFormatException nf) {
						addError(getTheResourceBundle().getString(
								"Conf_Routing_Number"), getTheResourceBundle()
								.getString("Conf_Routing_Number_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (!(routingNo == confirmRoutingNo)) {
						addError(getTheResourceBundle().getString(
								"Routing_ConfRouting"), getTheResourceBundle()
								.getString("Routing_ConfRouting_validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
			//
		}
			//
			if (this.escheatAccountNumber == null
					|| this.escheatAccountNumber.length() < 1) {
				addWarning(getTheResourceBundle().getString("Account_Number"),
						getTheResourceBundle()
								.getString("Account_Number_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (this.escheatAccountNumber != null
					&& this.escheatAccountNumber.length() > 0 && (this.escheatAccountNumber.length() > 17 || this.escheatAccountNumber.length()< 3)) {
				addError(getTheResourceBundle().getString("Account_Number"),
						getTheResourceBundle()
								.getString("Account_Number_Digit_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (this.escheatConfirmAccountNumber == null
					|| this.escheatConfirmAccountNumber.length() < 1) {
				addWarning(getTheResourceBundle().getString("Conf_Account_Number"),
						getTheResourceBundle()
								.getString("Conf_Account_Number_Req_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (this.escheatConfirmAccountNumber != null
					&& this.escheatConfirmAccountNumber.length() > 0 && (this.escheatConfirmAccountNumber.length() > 17 || this.escheatConfirmAccountNumber.length()< 3)) {
				addError(getTheResourceBundle().getString("Conf_Account_Number"),
						getTheResourceBundle()
								.getString("Conf_Account_Number_Digit_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if(this.escheatAccountNumber != null && this.escheatConfirmAccountNumber != null && this.escheatAccountNumber.length() >= 3 && 
					this.escheatAccountNumber.length() <= 17 && this.escheatConfirmAccountNumber.length() >= 3 && this.escheatConfirmAccountNumber.length() <= 17){
				if(this.escheatAccountNumber.length()>0 && this.escheatConfirmAccountNumber.length()>0){
					long accountNo = 0;
					long confirmAccountNo = 0;
					if(!this.escheatAccountNumber.startsWith("X") && !this.escheatConfirmAccountNumber.startsWith("X")){
						try{
							accountNo = Long.parseLong(this.escheatAccountNumber);
						}catch(NumberFormatException nf){
							addError(getTheResourceBundle().getString("Account_Number"),
									getTheResourceBundle().getString(
											"Account_Number_Validation"),
									OrderConstants.SOW_TAB_ERROR);
						}
					try{
						confirmAccountNo 	= Long.parseLong(this.escheatConfirmAccountNumber);
					}catch(NumberFormatException nf){
						addError(getTheResourceBundle().getString("Conf_Account_Number"),
								getTheResourceBundle().getString(
										"Conf_Account_Number_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if(!(accountNo == confirmAccountNo)){
						addError(getTheResourceBundle().getString("Account_ConfAccount"),
								getTheResourceBundle().getString(
										"Account_ConfAccount_validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
		}
			
			//
		
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
	
	public void creditCardValidate(){
		/*credit card validations -- start*/
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		
		boolean formatError = false;		
		if(creditCardErrorMessage!=null  && StringUtils.isNotBlank(creditCardErrorMessage) && creditCardErrorMessage.length()>1){
			addError(getTheResourceBundle().getString("CardNumber"),
					creditCardErrorMessage,
					OrderConstants.SOW_TAB_ERROR);
			formatError=true;
		}
		if (this.cardHolderName != null
				&& this.cardHolderName.length() > 75) {
			addError(getTheResourceBundle().getString("CardHolderName"),
					getTheResourceBundle().getString(
							"CardHolderName_Description_Length_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}
		if (StringUtils.isBlank(cardHolderName)) {
			addError(getTheResourceBundle().getString("CardHolderName"),
					getTheResourceBundle().getString(
							"CardHolderName_Description_Req_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}
		
		if (this.cardTypeId == null || this.cardTypeId.intValue() == -1) {
			addError(getTheResourceBundle().getString("CardTypeId"),
					getTheResourceBundle().getString(
							"CardTypeId_Description_Req_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}
		
//		if(StringUtils.isBlank(cardNumber)){
//			addError(getTheResourceBundle().getString("CardNumber"),
//					getTheResourceBundle().getString(
//							"CardNumber_Description_Req_Validation"),
//					OrderConstants.SOW_TAB_ERROR);
//		}
//		
//		if (this.cardNumber != null
//				&& this.cardNumber.length() > 0 && (this.cardNumber.length() > 16)) {
//			addError(getTheResourceBundle().getString("CardNumber"),
//					getTheResourceBundle()
//							.getString("CardNumber_Length_Validation_Msg"),
//					OrderConstants.SOW_TAB_ERROR);
//		}
//		
//			
//		if (this.cardNumber != null
//				&& this.cardNumber.length() > 0 && (this.cardNumber.length() <= 16)  && this.responseMessage.equalsIgnoreCase("DATA VALIDATION") && this.responseCode.equalsIgnoreCase("08")) {
//			
//				formatError = true;
//				addError(getTheResourceBundle().getString("CardNumber"),
//						getTheResourceBundle().getString(
//								"CardNumber_Integer_Digits_Validation"),
//						OrderConstants.SOW_TAB_ERROR);
//			
//		}
		if(!formatError && this.tokenizeCardNumber == null || StringUtils.isBlank(tokenizeCardNumber)){
			
			addError(getTheResourceBundle().getString("CardNumber"),
					"Credit card is not tokenized",
					OrderConstants.SOW_TAB_ERROR);
		
	}
	
		
		if (!formatError && this.cardTypeId != null && StringUtils.isNotBlank(cardNumber)) // if no format errors...in the number entered...
	{
//			boolean cardNumberError = false;
//			
//			// Sears Commercial Card (not allowed at present)
//			/*if ((this.cardNumber.length() == 16)
//					&& (cardNumber.substring(0, 6)
//							.equals(CreditCardConstants.SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS))) {
//				
//				addError(getTheResourceBundle().getString("CardNumber"),
//						getTheResourceBundle().getString(
//								"Sears_Commercial_Card_Validation_Msg"),
//						OrderConstants.SOW_TAB_ERROR);
//
//			}*/
//
//			// VISA
//			if (this.cardTypeId == 6) {
//				if ((this.cardNumber.length() != 16) || Integer.parseInt(this.cardNumber.substring(0, 1)) != 4) {
//					cardNumberError = true;
//				}
//			}
//
//			// MASTER CARD
//			else if (this.cardTypeId == 7) {
//				if (this.cardNumber.length() != 16
//						|| Integer.parseInt(this.cardNumber.substring(0, 2)) < 51
//						|| Integer.parseInt(this.cardNumber.substring(0, 2)) > 55
//						|| checkForSearsMasterCard()) {
//					cardNumberError = true;
//				}
//			}
//			// SEARS MasterCard
//			else if (this.cardTypeId == 4 && (!checkForSearsMasterCard())) {
//				cardNumberError = true;
//			}
//
//			// AMEX
//			// SLT-2591 and SLT-2592: Disable Amex
//			/*else if (this.cardTypeId == 8) {
//				if (this.cardNumber.length() != 15
//						|| (Integer.parseInt(this.cardNumber.substring(0, 2)) != 34 && Integer
//								.parseInt(this.cardNumber.substring(0, 2)) != 37)) {
//					cardNumberError = true;
//				}
//			}*/
//
//			else if ((this.cardTypeId == 0)) {
//
//				if (this.cardNumber.length() != 16
//						&& this.cardNumber.length() != 13) {
//					cardNumberError = true;
//				}
//				// Sears 16 digit Card
//				else if ((this.cardNumber.length() == 16)
//						&& (!checkForSearsCardOf16digits())) {
//					cardNumberError = true;
//				}
//				// Sears White Card
//				else if ((this.cardNumber.length() == 13)
//						&& (!checkForSearsWhiteCard())) {
//
//					cardNumberError = true;
//
//				}
//
//			}
//			
//			// SEARS MasterCard
//			else if (this.cardTypeId == 4 && (!checkForSearsMasterCard())) {
//				cardNumberError = true;
//			}
//			
//			// SEARS Commercial Card
//			else if (this.cardTypeId == 3 && (this.cardNumber.length() != 16 || !this.cardNumber.substring(0, 6).equals(CreditCardConstants.SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS))) {
//				cardNumberError = true;
//			}
//
//			if (cardNumberError) {
//				addError(getTheResourceBundle().getString("CardNumber"),
//						getTheResourceBundle().getString(
//								"CardNumber_Validation_Msg"),
//						OrderConstants.SOW_TAB_ERROR);
//			}
		
	if (!(ifSsearsWhiteCard))
	{
			if (this.expirationMonth == null
						|| this.expirationMonth.length() < 1 || this.expirationYear == null
						|| this.expirationYear.length() < 1) {
					addError(getTheResourceBundle().getString("ExpiryDate"),
							getTheResourceBundle().getString(
									"ExpiryDate_Description_Req_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
			
			if((Integer.parseInt(expirationYear)) < getCurrentYear()) { // year entered is not valid 
				addError(getTheResourceBundle().getString("ExpiryDate"),
						getTheResourceBundle().getString(
								"ExpiryDate_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			else if ((Integer.parseInt(expirationYear)) == getCurrentYear()) { //for the same year check if month is valid enough...
				
				if((Integer.parseInt(expirationMonth)) < getCurrentMonth()) { //any month lower than a current month is not valid..
					addError(getTheResourceBundle().getString("ExpiryDate"),
							getTheResourceBundle().getString(
									"ExpiryDate_Validation"),
							OrderConstants.SOW_TAB_ERROR);		
					
				}
			
			}
	} else 
	{
		// credit card expiration is good
	}
	
	
		
		
		if (this.billingAddress1 == null
				|| this.billingAddress1.length() < 1) {
			addError(getTheResourceBundle().getString("BillingAddress1"),
					getTheResourceBundle().getString(
							"BillingAddress1_Validation"),
					OrderConstants.SOW_TAB_WARNING);

		}
		if (this.billingAddress1 != null
				&& this.billingAddress1.length() > 30) {
			addError(getTheResourceBundle().getString("BillingAddress1"),
					getTheResourceBundle().getString(
							"BillingAddress1_Length_Validation_Msg"),
					OrderConstants.SOW_TAB_ERROR);
		}
		//SLT-278
		    String address1=this.billingAddress1.replaceAll("[^0-9]", "");
			if (this.billingAddress1 != null && address1.length()!=0 && this.checkCreditCardFormat(address1)) {
				addError(getTheResourceBundle().getString("BillingAddress1"),
						getTheResourceBundle().getString("BillingAddress1_CreditCard_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			}
				
		if (this.billingAddress2 != null
				&& this.billingAddress2.length() > 30) {
			addError(getTheResourceBundle().getString("BillingAddress2"),
					getTheResourceBundle().getString(
							"BillingAddress2_Length_Validation_Msg"),
					OrderConstants.SOW_TAB_WARNING);

		}
		//SLT-278
		String address2=this.billingAddress2.replaceAll("[^0-9]", "");
		if (this.billingAddress2 != null
				&& address2.length()!=0 && this.checkCreditCardFormat(address2)) {
				addError(getTheResourceBundle().getString("BillingAddress2"),
						getTheResourceBundle().getString("BillingAddress2_CreditCard_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			}
		
		if (this.billingCity == null
				|| this.billingCity.length() < 1) {
			addError(getTheResourceBundle().getString("City"),
					getTheResourceBundle().getString("City_Validation"),
					OrderConstants.SOW_TAB_WARNING);
		}
		if (this.billingCity != null
				&& this.billingCity.length() > 30) {
			addError(getTheResourceBundle().getString("City"),
					getTheResourceBundle().getString(
							"City_Length_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}
		//SLT-278
		String city=this.billingCity.replaceAll("[^0-9]", "");
		if (this.billingCity != null && city.length()!=0 && this.checkCreditCardFormat(city)) {
				addError(getTheResourceBundle().getString("City"),
						getTheResourceBundle().getString("City_CreditCard_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			}
		if (this.billingState != null
				&& this.billingState.equals("-1")) {
			addError(getTheResourceBundle().getString("State"),
					getTheResourceBundle()
							.getString("State_Validation_Msg"),
					OrderConstants.SOW_TAB_WARNING);
		}
		if (this.billingZip != null 
				&& this.billingZip.trim().length() > 0) {
			boolean valResult = false;
			String numPattern = "(\\d{5})";
			valResult = this.billingZip.matches(
					numPattern);
			if (valResult == false) {
				addError(getTheResourceBundle().getString("Zip"),
						getTheResourceBundle().getString("Zip_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
		}
		
		if (this.billingZip == null
				|| this.billingZip.trim().length() == 0) {
			addError(getTheResourceBundle().getString("Zip"),
					getTheResourceBundle().getString(
							"Zip_Validation_Missing"),
					OrderConstants.SOW_TAB_ERROR);
		}
		if (this.billingZip != null && this.billingZip.trim().length() > 0 && this.billingState != null){
			int zipCheck = LocationUtils.checkIfZipAndStateValid(this.billingZip,this.billingState);
			switch (zipCheck) {
				case Constants.LocationConstants.ZIP_NOT_VALID:
					addError(getTheResourceBundle().getString("Zip"),
							getTheResourceBundle().getString(
									"Zip_Not_Valid"),
							OrderConstants.SOW_TAB_ERROR);
					break;
				case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
					addError(getTheResourceBundle().getString("Zip"),
							getTheResourceBundle().getString(
									"ZipState_Validation_Missing"),
							OrderConstants.SOW_TAB_ERROR);
					break;
			}
		}
	
	}
	
		/*credit card validations -- End*/
	}	
	// SLT-278
	public boolean checkCreditCardFormat(String ccNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}	

	public boolean isBankAccountInfoEmpty(){
		boolean empty = false;
		
		if ((this.accountDescription == null)
			&& (this.accountHolder == null)				
			&& (this.accountType == null)
			&& (this.financialInstitution == null)
			&& (this.routingNumber == null	)
			&& (this.accountNumber == null	)
			&& (this.confirmAccountNumber == null )) {
			empty = true;
		}
		
		return empty;
	}
	
	public boolean isCreditCardInfoEmpty(){
		boolean empty = false;
		if((this.cardHolderName == null)
			&& (this.cardTypeId == null)
			&& (this.cardNumber == null )
			&& (this.expirationMonth == null)
			&& (this.expirationYear == null)
			&& (this.billingAddress1 == null)
			&& (this.billingCity == null)
			&& (this.billingZip == null)){
			empty = true;
		}
		return empty;
	}	
	
	protected  int getCurrentMonth() {
		   Calendar calendar = new GregorianCalendar();
		   int month = calendar.get(Calendar.MONTH)+1;
		return month;
		   
	  }
	protected  int getCurrentYear() {
		   Calendar calendar = new GregorianCalendar();
		   int year = calendar.get(Calendar.YEAR)-2000; //needs refactoring later...for the next 100 years...
		return year;
		   
	  }	

	
	public boolean validateRoutingNumber(String routingNo){
		char[] routingChar = routingNo.toCharArray();
		char[] weightChar = {'3','7','1','3','7','1','3','7'};
		double mulval = 0;
		double divval = 0;
		int j = 0;
		for(int i=0; i<8; i++){
			mulval = mulval + new Integer(Character.toString(routingChar[j]))* new Integer(Character.toString(weightChar[j]));
			j++;
		}
		divval = Math.ceil((mulval/10));
		divval = divval*10;
		double checkDigit = divval - mulval;
		double lastroutingDigit = new Double(Character.toString(routingNo.charAt(8)));
		if(checkDigit == lastroutingDigit){
			return true;
		}else{
			return false;
		}
	}
	public String getTabIdentifier() {
		return "";
	}
	public String getAccountDescription() {
		return accountDescription;
	}
	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getFinancialInstitution() {
		return financialInstitution;
	}
	public void setFinancialInstitution(String financialInstitution) {
		this.financialInstitution = financialInstitution;
	}
	public String getRoutingNumber() {
		return routingNumber;
	}
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
	public String getConfirmRoutingNumber() {
		return confirmRoutingNumber;
	}
	public void setConfirmRoutingNumber(String confirmRoutingNumber) {
		this.confirmRoutingNumber = confirmRoutingNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getConfirmAccountNumber() {
		return confirmAccountNumber;
	}
	public void setConfirmAccountNumber(String confirmAccountNumber) {
		this.confirmAccountNumber = confirmAccountNumber;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public String getOldAccountId() {
		return oldAccountId;
	}
	public void setOldAccountId(String oldAccountId) {
		this.oldAccountId = oldAccountId;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = StringUtils.trim(nameOnCard);
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = StringUtils.trim(cardNumber);
	}
	public String getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public String getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}
	public String getBillingAddress1() {
		return billingAddress1;
	}
	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = StringUtils.trim(billingAddress1);
	}
	public String getBillingAddress2() {
		return billingAddress2;
	}
	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = StringUtils.trim(billingAddress2);
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = StringUtils.trim(billingCity);
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public String getBillingZip() {
		return billingZip;
	}
	public void setBillingZip(String billingZip) {
		this.billingZip = StringUtils.trim(billingZip);
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = StringUtils.trim(cardHolderName);
	}
	public Long getCardTypeId() {
		return cardTypeId;
	}
	public void setCardTypeId(Long cardTypeId) {
		this.cardTypeId = cardTypeId;
	}
	public String getEncCardNo() {
		return encCardNo;
	}
	public void setEncCardNo(String encCardNo) {
		this.encCardNo = encCardNo;
	}
	public Integer getLocationTypeId() {
		return locationTypeId;
	}
	public void setLocationTypeId(Integer locationTypeId) {
		this.locationTypeId = locationTypeId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOldCardId() {
		return oldCardId;
	}

	public void setOldCardId(String oldCardId) {
		this.oldCardId = oldCardId;
	}

	public String getCardAccountType() {
		return cardAccountType;
	}

	public void setCardAccountType(String cardAccountType) {
		this.cardAccountType = cardAccountType;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public boolean checkForSearsWhiteCard()
	{
		if (cardNumber.length()== 13 && cardTypeId == 0)//Sears card has card type id of 0
		{
			String searsWhiteCardInitial2digits[] = CreditCardConstants.SEARS_WHITE_CARD_BIN_RANGE;
			String cardInitialTwoDigits = cardNumber.substring(0, 2);
			for (int i = 0; i < searsWhiteCardInitial2digits.length;i++)
			{
				if (cardInitialTwoDigits.equalsIgnoreCase(searsWhiteCardInitial2digits[i]))
				{
					ifSsearsWhiteCard = true;
				}
			}
			if (ifSsearsWhiteCard == true)
			{
				this.setExpirationMonth("12");
				this.setExpirationYear("49");
				return true;
			}
		}
		return false;
	}
	
	public boolean checkForSearsCardOf16digits()
	{
		if (cardNumber.length()== 16 && cardTypeId == 0)//Sears card has card type id of 0
		{
			String searsCardInitial6digits[] = CreditCardConstants.SEARS_CARD_16_BIN_RANGE;
			String cardInitialSixDigits = cardNumber.substring(0, 6);
			for (int i = 0; i < searsCardInitial6digits.length;i++)
			{
				if (cardInitialSixDigits.equalsIgnoreCase(searsCardInitial6digits[i]))
				{
					return true;
				}
			}

		}
		return false;
	}
	
	
	public boolean checkForSearsMasterCard()
	{
		if (cardNumber.length()== 16 )//Sears Master card has card type id of 4
		{
			String searsMasterCardInitial6digits[] = CreditCardConstants.SEARS_MASTER_CARD_BIN_RANGE;
			String cardInitialSixDigits = cardNumber.substring(0, 6);
			for (int i = 0; i < searsMasterCardInitial6digits.length;i++)
			{
				if (StringUtils.equals(cardInitialSixDigits,searsMasterCardInitial6digits[i]))
				{
					
					return true;
					
				}
			}
		}
		return false;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public String getAutoACHInd() {
		return autoACHInd;
	}

	public void setAutoACHInd(String autoACHInd) {
		this.autoACHInd = autoACHInd;
	}

	public int getSaveAutoFundInd() {
		return saveAutoFundInd;
	}

	public void setSaveAutoFundInd(int saveAutoFundInd) {
		this.saveAutoFundInd = saveAutoFundInd;
	}

	public boolean isEnabledIndicator() {
		return enabledIndicator;
	}

	public void setEnabledIndicator(boolean enabledIndicator) {
		this.enabledIndicator = enabledIndicator;
	}

	public String getEscheatAccountDescription() {
		return escheatAccountDescription;
	}

	public void setEscheatAccountDescription(String escheatAccountDescription) {
		this.escheatAccountDescription = escheatAccountDescription;
	}

	public String getEscheatAccountHolder() {
		return escheatAccountHolder;
}

	public void setEscheatAccountHolder(String escheatAccountHolder) {
		this.escheatAccountHolder = escheatAccountHolder;
	}

	public String getEscheatAccountType() {
		return escheatAccountType;
	}

	public void setEscheatAccountType(String escheatAccountType) {
		this.escheatAccountType = escheatAccountType;
	}

	public String getEscheatFinancialInstitution() {
		return escheatFinancialInstitution;
	}

	public void setEscheatFinancialInstitution(String escheatFinancialInstitution) {
		this.escheatFinancialInstitution = escheatFinancialInstitution;
	}

	public String getEscheatRoutingNumber() {
		return escheatRoutingNumber;
	}

	public void setEscheatRoutingNumber(String escheatRoutingNumber) {
		this.escheatRoutingNumber = escheatRoutingNumber;
	}

	public String getEscheatConfirmRoutingNumber() {
		return escheatConfirmRoutingNumber;
	}

	public void setEscheatConfirmRoutingNumber(String escheatConfirmRoutingNumber) {
		this.escheatConfirmRoutingNumber = escheatConfirmRoutingNumber;
	}

	public String getEscheatAccountNumber() {
		return escheatAccountNumber;
	}

	public void setEscheatAccountNumber(String escheatAccountNumber) {
		this.escheatAccountNumber = escheatAccountNumber;
	}

	public String getEscheatConfirmAccountNumber() {
		return escheatConfirmAccountNumber;
	}

	public void setEscheatConfirmAccountNumber(String escheatConfirmAccountNumber) {
		this.escheatConfirmAccountNumber = escheatConfirmAccountNumber;
	}

	public String getEscheatAccountId() {
		return escheatAccountId;
	}

	public void setEscheatAccountId(String escheatAccountId) {
		this.escheatAccountId = escheatAccountId;
	}

	public Integer getEscheatEntityTypeId() {
		return escheatEntityTypeId;
	}

	public void setEscheatEntityTypeId(Integer escheatEntityTypeId) {
		this.escheatEntityTypeId = escheatEntityTypeId;
	}

	public String getEscheatOldAccountId() {
		return escheatOldAccountId;
	}

	public void setEscheatOldAccountId(String escheatOldAccountId) {
		this.escheatOldAccountId = escheatOldAccountId;
	}

	public String getEscheatActiveInd() {
		return escheatActiveInd;
	}

	public void setEscheatActiveInd(String escheatActiveInd) {
		this.escheatActiveInd = escheatActiveInd;
	}

	public String getEscheatAutoACHInd() {
		return escheatAutoACHInd;
	}

	public void setEscheatAutoACHInd(String escheatAutoACHInd) {
		this.escheatAutoACHInd = escheatAutoACHInd;
	}

	public int getEscheatSaveAutoFundInd() {
		return escheatSaveAutoFundInd;
	}

	public void setEscheatSaveAutoFundInd(int escheatSaveAutoFundInd) {
		this.escheatSaveAutoFundInd = escheatSaveAutoFundInd;
	}

	public boolean isEscheatEnabledIndicator() {
		return escheatEnabledIndicator;
	}

	public void setEscheatEnabledIndicator(boolean escheatEnabledIndicator) {
		this.escheatEnabledIndicator = escheatEnabledIndicator;
	}

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

	public String getCreditCardAuthTokenizeXapikey() {
		return creditCardAuthTokenizeXapikey;
	}

	public void setCreditCardAuthTokenizeXapikey(
			String creditCardAuthTokenizeXapikey) {
		this.creditCardAuthTokenizeXapikey = creditCardAuthTokenizeXapikey;
	}

	public String getCreditCardErrorMessage() {
		return creditCardErrorMessage;
	}

	public void setCreditCardErrorMessage(String creditCardErrorMessage) {
		this.creditCardErrorMessage = creditCardErrorMessage;
	}

   
	
	

}
