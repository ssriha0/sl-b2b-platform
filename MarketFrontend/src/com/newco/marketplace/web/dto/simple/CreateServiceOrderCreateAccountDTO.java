package com.newco.marketplace.web.dto.simple;

import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.IWarning;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.buyer.BuyerRegistrationDTO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.OrderConstants;

public class CreateServiceOrderCreateAccountDTO extends SOWBaseTabDTO {

	private static final long serialVersionUID = 0L;
	
	private String username;
	private String usernameConfirm;
	private String email;
	private String emailConfirm;
	private String firstName;
	private String lastName;
	private String primaryPhone; 	// Break this into 3 parts? CG
	private String secondaryPhone; // Break this into 3 parts? CG
	private String referralCode;

	private String street1;
	private String aptNo;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String homeAddressInd;
	private String accountAddressInd;
	private String locName;
    private String buyerTermsAndConditionAgreeInd;
    private String slBucksAgreeInd;
    private String promotionalMailInd;
    
	private List<LookupVO> statesList;
    private String buyerTermsAndConditionText;
    private String slBucksText;
    private Integer slBucksAgreeId;

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsernameConfirm() {
		return usernameConfirm;
	}
	
	public void setUsernameConfirm(String usernameConfirm) {
		this.usernameConfirm = usernameConfirm;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		if(email != null){
			this.email = email.trim();
		}else{
			this.email = email;
		}		
	}
	
	public String getEmailConfirm() {
		return emailConfirm;
	}
	
	public void setEmailConfirm(String emailConfirm) {
		if(emailConfirm != null){
			this.emailConfirm = emailConfirm.trim();
		}else{
			this.emailConfirm = emailConfirm;
		}		
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

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getAptNo() {
		return aptNo;
	}

	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getHomeAddressInd() {
		return homeAddressInd;
	}

	public void setHomeAddressInd(String homeAddressInd) {
		this.homeAddressInd = homeAddressInd;
	}

	public String getAccountAddressInd() {
		return accountAddressInd;
	}

	public void setAccountAddressInd(String accountAddressInd) {
		this.accountAddressInd = accountAddressInd;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}
	
	public String getBuyerTermsAndConditionAgreeInd() {
		return buyerTermsAndConditionAgreeInd;
	}

	public void setBuyerTermsAndConditionAgreeInd(String buyerTermsAndConditionAgreeInd) {
		this.buyerTermsAndConditionAgreeInd = buyerTermsAndConditionAgreeInd;
	}

	public String getSlBucksAgreeInd() {
		return slBucksAgreeInd;
	}

	public void setSlBucksAgreeInd(String slBucksAgreeInd) {
		this.slBucksAgreeInd = slBucksAgreeInd;
	}
	
	public List<LookupVO> getStatesList() {
		return statesList;
	}
	
	public void setStatesList(List<LookupVO> statesList) {
		this.statesList = statesList;
	}

	public String getBuyerTermsAndConditionText() {
		return buyerTermsAndConditionText;
	}

	public void setBuyerTermsAndConditionText(String buyerTermsAndConditionText) {
		this.buyerTermsAndConditionText = buyerTermsAndConditionText;
	}

	public String getSlBucksText() {
		return slBucksText;
	}

	public void setSlBucksText(String slBucksText) {
		this.slBucksText = slBucksText;
	}
	
	@Override
	public String getTabIdentifier() {
		// No implementation required as simple buyer workflow is sequential and not tab based
		return OrderConstants.SSO_CREATE_ACCOUNT_DTO;
	}
	
	@Override
	public void validate() {
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		
		validateBasics();
		validateUsernameConfirm();
		validateEmailConfirm();
		validateLocationName();
		validatePhone(primaryPhone, "primaryPhone");
		validatePhone(secondaryPhone, "secondaryPhone");
		validateTermsnConditions();
	}

	private void validateBasics() {
		
		// User name basic validations
		if (StringUtils.isBlank(username)) {
			addError("accountDTO.username", getTheResourceBundle().getString("accountDTO.username.error.required"), OrderConstants.SOW_TAB_ERROR);
		} else if (null != username && username.length() < 8) {
			addError("accountDTO.username", getTheResourceBundle().getString("accountDTO.username.error.required.minlength"), OrderConstants.SOW_TAB_ERROR);
		} else if (null != username && username.length() > 30) {
			addError("accountDTO.username", getTheResourceBundle().getString("accountDTO.username.error.required.maxlength"), OrderConstants.SOW_TAB_ERROR);
		}
		//Confirm User Name
		if (StringUtils.isBlank(usernameConfirm)) {
			addError("accountDTO.usernameConfirm", getTheResourceBundle().getString("accountDTO.usernameConfirm.error.required"), OrderConstants.SOW_TAB_ERROR);
		}		
		// Email basic validations
		if (StringUtils.isBlank(email)) {
			addError("accountDTO.email", getTheResourceBundle().getString("accountDTO.email.error.required"), OrderConstants.SOW_TAB_ERROR);
		} else if (!isValidEmailAddress(email)) {
			addError("accountDTO.email", getTheResourceBundle().getString("accountDTO.email.error"), OrderConstants.SOW_TAB_ERROR);
		}
		
		//Confirm Email Basic Validations
		if (StringUtils.isBlank(emailConfirm)) {
			addError("accountDTO.emailConfirm", getTheResourceBundle().getString("accountDTO.emailConfirm.error.required"), OrderConstants.SOW_TAB_ERROR);
		}

		// First name, last name basic validations
		if (StringUtils.isBlank(firstName)) {
			addError("accountDTO.firstName", getTheResourceBundle().getString("accountDTO.firstName.error.required"), OrderConstants.SOW_TAB_ERROR);
		}
		if (StringUtils.isBlank(lastName)) {
			addError("accountDTO.lastName", getTheResourceBundle().getString("accountDTO.lastName.error.required"), OrderConstants.SOW_TAB_ERROR);
		}
		
		// Street1, City, State basic validations
		if (StringUtils.isBlank(street1)) {
			addError("accountDTO.street1", getTheResourceBundle().getString("accountDTO.street1.error.required"), OrderConstants.SOW_TAB_ERROR);
		}
		if (StringUtils.isBlank(city)) {
			addError("accountDTO.city", getTheResourceBundle().getString("accountDTO.city.error.required"), OrderConstants.SOW_TAB_ERROR);
		}
		if (StringUtils.isBlank(state)) {
			addError("accountDTO.state", getTheResourceBundle().getString("accountDTO.state.error.required"), OrderConstants.SOW_TAB_ERROR);
		}
		
		// Zip code basic validations
		if (StringUtils.isBlank(zip)) {
			addError("accountDTO.zip", getTheResourceBundle().getString("accountDTO.zip.error.required"), OrderConstants.SOW_TAB_ERROR);
		} else if (null != zip && zip.length() < 5) {
			addError("accountDTO.zip", getTheResourceBundle().getString("accountDTO.zip.error.required.minlength"), OrderConstants.SOW_TAB_ERROR);
		} else if (!StringUtils.isNumeric(zip)) {
			addError("accountDTO.zip", getTheResourceBundle().getString("accountDTO.zip.error.required.number"), OrderConstants.SOW_TAB_ERROR);
		}
	}

	public boolean isValidEmailAddress(String aEmailAddress){
		if (aEmailAddress == null) return false;
		boolean result = true;
		try {
			new InternetAddress(aEmailAddress);
			String[] tokens = aEmailAddress.split("@");
			if ( ! (tokens.length == 2 && StringUtils.isNotBlank(tokens[0]) && StringUtils.isNotBlank(tokens[1])
						&& tokens[1].indexOf(".") > 0 && tokens[1].indexOf(".") < tokens[1].length() - 1) ) {
				result = false;
			}
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	private void validateUsernameConfirm() {
		String username1 = getUsername()!= null ? getUsername().trim() : "";
		String username2 = getUsernameConfirm() != null ? getUsernameConfirm().trim() : "";
		if(!username1.equalsIgnoreCase(username2)) {
			addError("accountDTO.username", getTheResourceBundle().getString("accountDTO.username.error.mismatch"), OrderConstants.SOW_TAB_ERROR);
		}
	}

	private void validateEmailConfirm() {
		String email1 = getEmail()!= null ? getEmail().trim() : "";
		String email2 = getEmailConfirm() != null ? getEmailConfirm().trim() : "";
		if(!email1.equalsIgnoreCase(email2)) {
			addError("accountDTO.email", getTheResourceBundle().getString("accountDTO.email.error.mismatch"), OrderConstants.SOW_TAB_ERROR);
		}
	}
	
	private void validateLocationName() {
		if ("true".equals(homeAddressInd)) {
			locName = BuyerConstants.LOCATION_NAME_HOME;
		} else {
			if (StringUtils.isBlank(locName)) {
				addError("accountDTO.locName", getTheResourceBundle().getString("accountDTO.locName.error.mismatch"), OrderConstants.SOW_TAB_ERROR);
			}
		}
	}
	
	private void validateTermsnConditions() {
		if("false".equals(getBuyerTermsAndConditionAgreeInd())) {
			addError("accountDTO.buyerTermsAndConditionAgreeInd", getTheResourceBundle().getString("accountDTO.buyerTermsAndCondition.error.mustAgree")
						, OrderConstants.SOW_TAB_ERROR);
		}
		if("false".equals(getSlBucksAgreeInd())) {
			addError("accountDTO.slBucksAgreeInd", getTheResourceBundle().getString("accountDTO.slBucks.error.mustAgree"), OrderConstants.SOW_TAB_ERROR);
		}
	}
	
	private void validatePhone(String phone, String phoneFieldName) {
		if (StringUtils.isNotBlank(phone)) {
			try {
				// Assuming phone format: (999) 999-9999
				String areaCode = phone.substring(1, 4);
				String phonePart1 = phone.substring(6, 9);
				String phonePart2 = phone.substring(10);
				phone = areaCode + phonePart1 + phonePart2;
				Long.parseLong(phone);
			} catch (NumberFormatException nfEx) {
				addError("accountDTO." + phoneFieldName, getTheResourceBundle().getString("accountDTO.phone.error.invalid"), OrderConstants.SOW_TAB_ERROR);
				return;
			}
			
			if (phone.length() != 10) {
				addError("accountDTO.TermsAndCondition", getTheResourceBundle().getString("accountDTO.phone.error.invalid"), OrderConstants.SOW_TAB_ERROR);
			}
		} else {
			if ("primaryPhone".equalsIgnoreCase(phoneFieldName)) {
				addError("accountDTO.primaryPhone", getTheResourceBundle().getString("accountDTO.primaryPhone.error.required"), OrderConstants.SOW_TAB_ERROR);
			}
		}
	}
	
	public BuyerRegistrationDTO convertToBuyerRegistrationDTO() {
		
		BuyerRegistrationDTO buyerRegDTO = new BuyerRegistrationDTO();
		
		// User Profile Info
		buyerRegDTO.setUserName(username);
		buyerRegDTO.setEmail(email);
		buyerRegDTO.setFirstName(firstName);
		buyerRegDTO.setLastName(lastName);
		buyerRegDTO.setSimpleBuyerInd(true);
		
		// Location Info
		buyerRegDTO.setHomeAddressInd(homeAddressInd);
		buyerRegDTO.setBusinessStreet1(street1);
		buyerRegDTO.setBusinessAprt(aptNo);
		buyerRegDTO.setBusinessStreet2(street2);
		buyerRegDTO.setBusinessCity(city);
		buyerRegDTO.setBusinessState(state);
		buyerRegDTO.setBusinessZip(zip);
		buyerRegDTO.setLocName(locName);
		buyerRegDTO.setPromotionalMailInd(promotionalMailInd);
		buyerRegDTO.setPromotionCode(referralCode);
		
		// Buyer Source Id
		buyerRegDTO.setHowDidYouHear(String.valueOf(OrderConstants.OTHER_WEBSITE_OR_FORUM));
		
		// Contact Info
		buyerRegDTO.setRoleWithinCom(String.valueOf(OrderConstants.SIMPLE_BUYER_COMPANY_ROLE_ID));
		if (StringUtils.isNotBlank(primaryPhone)) {
			parsePhoneNumber(buyerRegDTO, primaryPhone, true);
		}
		
		if (StringUtils.isNotBlank(secondaryPhone)) {
			parsePhoneNumber(buyerRegDTO, secondaryPhone, false);
		}
		
		return buyerRegDTO;
	}
	
	private void parsePhoneNumber(BuyerRegistrationDTO dto, String phone, boolean primary) {
		if(primary) {
			dto.setBusPhoneNo1(phone.substring(1, 4));
			dto.setBusPhoneNo2(phone.substring(6, 9));
			dto.setBusPhoneNo3(phone.substring(10, 14));
		}
		else {
			dto.setMobPhoneNo1(phone.substring(1, 4));
			dto.setMobPhoneNo2(phone.substring(6, 9));
			dto.setMobPhoneNo3(phone.substring(10, 14));
		}
		
	}
	
	public void populateFromBuyerRegistrationDTO(BuyerRegistrationDTO buyerRegDTO) {
		
		// User Profile Info
		username = buyerRegDTO.getUserName();
		email = buyerRegDTO.getEmail();
		firstName = buyerRegDTO.getFirstName();
		lastName = buyerRegDTO.getLastName();
		
		// Location Info
		homeAddressInd = buyerRegDTO.getHomeAddressInd();
		street1 = buyerRegDTO.getBusinessStreet1();
		aptNo = buyerRegDTO.getBusinessAprt();
		street2 = buyerRegDTO.getBusinessStreet2();
		city = buyerRegDTO.getBusinessCity();
		state = buyerRegDTO.getBusinessState();
		zip = buyerRegDTO.getBusinessZip();
		locName = buyerRegDTO.getLocName();
		
		// Contact Info
		if (StringUtils.isNotBlank(buyerRegDTO.getBusPhoneNo1())) {
			String phoneArea = buyerRegDTO.getBusPhoneNo1();
			String phonePart1 = buyerRegDTO.getBusPhoneNo2();
			String phonePart2 = buyerRegDTO.getBusPhoneNo3();
			primaryPhone = phoneArea + phonePart1 + phonePart2;
		}
		if (StringUtils.isNotBlank(buyerRegDTO.getMobPhoneNo1())) {
			String phoneArea = buyerRegDTO.getMobPhoneNo1();
			String phonePart1 = buyerRegDTO.getMobPhoneNo2();
			String phonePart2 = buyerRegDTO.getMobPhoneNo3();
			secondaryPhone = phoneArea + phonePart1 + phonePart2;
		}
	}

	public String getPromotionalMailInd() {
		return promotionalMailInd;
	}

	public void setPromotionalMailInd(String promotionalMailInd) {
		this.promotionalMailInd = promotionalMailInd;
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

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	
}
