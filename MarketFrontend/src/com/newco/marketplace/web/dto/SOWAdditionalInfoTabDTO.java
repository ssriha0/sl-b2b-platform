package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.interfaces.OrderConstants;

public class SOWAdditionalInfoTabDTO extends SOWBaseTabDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -316374475112161025L;
	private Integer companyId;
	private double companyRating;
	private Integer logodocumentId;

	// Start Primary & Alternate Location Contact Info
	private SOWContactLocationDTO serviceLocationContact = new SOWContactLocationDTO();;
	private SOWContactLocationDTO alternateLocationContact = new SOWContactLocationDTO();
	private boolean altServiceLocationContactFlg;
	// End Primary & Alternate Location Contact Info

	// Start Branding
	private List<SOWBrandingInfoDTO> brandingInfoList = new ArrayList<SOWBrandingInfoDTO>();
	// End Branding

	// Start Custom Ref
	private List<SOWCustomRefDTO> customRefs = new ArrayList<SOWCustomRefDTO>();
	private List<LuBuyerRefVO> buyerRefs = new ArrayList<LuBuyerRefVO>();
	private String refTypeValue;
	private Integer refTypeId;
	private boolean isCreated = false;
	// End Custom Ref

	// Start ? Buyer and Buyer Support Contact Info ?
	private boolean altBuyerSupportLocationContactFlg;
	private List<SOWAltBuyerContactDTO> altBuyerContacts;
	private SOWAltBuyerContactDTO selectedAltBuyerContact = new SOWAltBuyerContactDTO();
	private SOWContactLocationDTO altBuyerSupportLocationContact = new SOWContactLocationDTO();

	// End ? Buyer and Buyer Support Contact Info ?
	
	// Start Misc Info - Needed for Review Tab,
	private String statusString;
	private String subStatusString;	
	private Integer subStatusId;
	private Integer wfStateId;
	private boolean requiredRefTypeFieldsValidation = false;
	// End Misc Info

	public SOWAdditionalInfoTabDTO() {
	}	

	public String getRefTypeValue() {
		return refTypeValue;
	}

	public void setRefTypeValue(String refTypeValue) {
		this.refTypeValue = refTypeValue;
	}
	
	public Integer getRefTypeId() {
		return refTypeId;
	}

	public void setRefTypeId(Integer refTypeId) {
		this.refTypeId = refTypeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public double getCompanyRating() {
		return companyRating;
	}

	public void setCompanyRating(double companyRating) {
		this.companyRating = companyRating;
	}

	public boolean isAltServiceLocationContactFlg() {
		return altServiceLocationContactFlg;
	}

	public void setAltServiceLocationContactFlg(
			boolean altServiceLocationContactFlg) {
		this.altServiceLocationContactFlg = altServiceLocationContactFlg;
	}

	public boolean isAltBuyerSupportLocationContactFlg() {
		return altBuyerSupportLocationContactFlg;
	}

	public void setAltBuyerSupportLocationContactFlg(
			boolean altBuyerSupportLocationContactFlg) {
		this.altBuyerSupportLocationContactFlg = altBuyerSupportLocationContactFlg;
	}

	public SOWContactLocationDTO getAlternateLocationContact() {
		return alternateLocationContact;
	}

	public void setAlternateLocationContact(
			SOWContactLocationDTO alternateLocationContact) {
		this.alternateLocationContact = alternateLocationContact;
	}

	public SOWContactLocationDTO getAltBuyerSupportLocationContact() {
		return altBuyerSupportLocationContact;
	}

	public void setAltBuyerSupportLocationContact(
			SOWContactLocationDTO altBuyerSupportLocationContact) {
		this.altBuyerSupportLocationContact = altBuyerSupportLocationContact;
	}

	public List<SOWCustomRefDTO> getCustomRefs() {
		return customRefs;
	}

	public void setCustomRefs(List<SOWCustomRefDTO> customRefs) {
		this.customRefs = customRefs;
	}

	public List<SOWBrandingInfoDTO> getBrandingInfoList() {
		return brandingInfoList;
	}

	public void setBrandingInfoList(List<SOWBrandingInfoDTO> brandingInfoList) {
		this.brandingInfoList = brandingInfoList;
	}

	public List<SOWAltBuyerContactDTO> getAltBuyerContacts() {
		return altBuyerContacts;
	}

	public void setAltBuyerContacts(List<SOWAltBuyerContactDTO> altBuyerContacts) {
		this.altBuyerContacts = altBuyerContacts;
	}

	public SOWAltBuyerContactDTO getSelectedAltBuyerContact() {
		return selectedAltBuyerContact;
	}

	public void setSelectedAltBuyerContact(
			SOWAltBuyerContactDTO selectedAltBuyerContact) {
		this.selectedAltBuyerContact = selectedAltBuyerContact;
	}

	@Override
	public void validate() {
		
		// making the error list empty
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		_doWorkFlowValidation();
		//validateRequiredRefTypes();
		customRefValidation();
		validateAlternateContact();
		validateRequiredRefTypes();
	
		// return getErrors();
	}

	// validating for additional info custom links : Making sure that user
	// selects a required ref type from drop down and enters a value for it

	public void validateRequiredRefTypes() {

		List<LuBuyerRefVO> buyerRefTypeList = this.getBuyerRefs();
		for (LuBuyerRefVO luBuyerRefVO : buyerRefTypeList) {
			if (StringUtils.isBlank(luBuyerRefVO.getReferenceValue()) && luBuyerRefVO.getRequired() == 1){
				addError(getTheResourceBundle().getString("Required_Reference_Type_Field"), 
						 getTheResourceBundle().getString("Required_Reference_Type_Field")
						,OrderConstants.SOW_TAB_ERROR );
				break;
			}
		}
	}
		

	// validating for additional info custom links : Making sure that user
	// selects a ref type from drop down and enters a value for it
	private void customRefValidation() {
		try {
			List cleanErrorList = new ArrayList<SOWCustomRefDTO>();
			
			List<SOWCustomRefDTO> customList = getCustomRefs();
			if (customList != null) {
				for (int i = 0; i < customList.size(); i++) {
					if (customList.get(i) != null)
						customList.get(i).setTheResourceBundle(
								this.getTheResourceBundle());
					customList.get(i).validate();
					cleanErrorList.addAll(customList.get(i).getErrors());
					if (customList.get(i) != null && customList.get(i).getRefTypeId() != null
							&& customList.get(i).getRefValue().equals("")) {
						customList.remove(i);
					}

				}

				if (cleanErrorList.size() > 0) {
					getErrors().addAll(cleanErrorList);
				}
				int count = 0;
				for (int j = 0; j < customList.size(); j++) {
					if (customList.get(j) != null && customList.get(j).getRefTypeId() != null && 
							customList.get(j).getRefTypeId().equals(refTypeId)) {
						count++;
						if (count == 2) {
							System.out.print("removed a ref  from list   ===>"
									+ customList.get(j).getRefTypeId()
									+ "\n This is the ID =====>" + refTypeId);
							addError(
									getTheResourceBundle().getString(
											"Custom_Reference"),
									getTheResourceBundle()
											.getString(
													"Duplicate_Custome_Reference_Validation_Msg"),
									OrderConstants.SOW_TAB_ERROR);
							customList.remove(j);

						}
					}

					if (customList.get(j) != null && customList.get(j).getRefValue() != null && 
							customList.get(j).getRefValue().length() > 5000) {
						addError(
								getTheResourceBundle().getString(
										"Custom_Reference"),
								getTheResourceBundle()
										.getString(
												"Custome_Reference_Length_Validation_Msg"),
								OrderConstants.SOW_TAB_ERROR);
						customList.remove(j);
					}
					if (customList.get(j) != null && customList.get(j).getRefTypeId() != null
							&& customList.get(j).getRefTypeId() == -1) {
						addError(
								getTheResourceBundle().getString(
										"Custom_Reference_Type"),
								getTheResourceBundle()
										.getString(
												"Custome_Reference_Select_Validation_Msg"),
								OrderConstants.SOW_TAB_ERROR);
						customList.remove(j);
					}
				}
			}
		} catch (Exception e) {

		}

	}

	private void validateAlternateContact() {
		if( isAltBuyerSupportLocationContactFlg()){
			if(null == getSelectedAltBuyerContact()  || null == getSelectedAltBuyerContact().getContactId()){
				addError(getTheResourceBundle().getString("Alternate_Buyer_Contact"),
						getTheResourceBundle().getString("Alternate_Buyer_Contact_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			}			
		}
		if (getAlternateLocationContact() == null)
			return;

		if (altServiceLocationContactFlg != true)
			return;

		// None of the fields are required.
		
		// Validate Email
		if(altServiceLocationContactFlg)
		{
			if(alternateLocationContact != null)
			{
				if (alternateLocationContact.getFirstName() == null
						|| alternateLocationContact.getFirstName().length() < 1) {
					addWarning(getTheResourceBundle().getString("First_Name"),
							getTheResourceBundle().getString(
									"First_Name_Validation"),
							OrderConstants.SOW_TAB_WARNING);
				}
				if (alternateLocationContact.getFirstName() != null
						&& alternateLocationContact.getFirstName().trim()
								.length() > 50) {
					addError(getTheResourceBundle().getString("First_Name"),
							getTheResourceBundle().getString(
									"First_Name_Length_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}

				if (alternateLocationContact.getLastName() == null
						|| alternateLocationContact.getLastName().length() < 1) {
					addWarning(getTheResourceBundle().getString("Last_Name"),
							getTheResourceBundle()
									.getString("Last_Name_Validation"),
							OrderConstants.SOW_TAB_WARNING);

				}
				if (alternateLocationContact.getLastName() != null
						&& alternateLocationContact.getLastName().trim()
								.length() > 50) {
					addError(getTheResourceBundle().getString("Last_Name"),
							getTheResourceBundle().getString(
									"Last_Name_Lenght_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
				if (alternateLocationContact.getPhones() != null) {
					List<SOWPhoneDTO> phones = alternateLocationContact.getPhones();
					for (int i = 0; i < phones.size(); i++) {
						SOWPhoneDTO iPhone = phones.get(i);
						if (iPhone.getPhone() != null
								&& iPhone.getPhone().trim().length() > 0) {
							boolean valResult = false;
							String numPattern = "(\\d{3})?\\d{3}\\d{4}";
							valResult = iPhone.getPhone().matches(numPattern);
							if (i == 0) {
								if(valResult == false){
									addError("phone", getTheResourceBundle().getString(
									"Phone_Missing_Number_Validation_Msg"),
									OrderConstants.SOW_TAB_ERROR);
								}
								if(iPhone.getPhoneClassId().equals(new Integer(-1))){
									addError(getTheResourceBundle().getString("Phone_Type"),
									getTheResourceBundle().getString("Phone_Type_Validation"),
									OrderConstants.SOW_TAB_ERROR);
								}
								
							}
							if (i == 1) {
								if(valResult == false){
									addError("Alternate_phone",	getTheResourceBundle().getString(
									"Alternate_Phone_Missing_Number_Validation_Msg"),
									OrderConstants.SOW_TAB_ERROR);
								}
								if(iPhone.getPhoneClassId().equals(new Integer(-1))){
									addError(getTheResourceBundle().getString("Alternate_Phone_Type"),
									getTheResourceBundle().getString("Alternate_Phone_Type_Validation"),
									OrderConstants.SOW_TAB_ERROR);
								}
								
							}
							if (valResult == false && i == 2) {
								addError("Fax",getTheResourceBundle().getString(
								"Fax_Missing_Number_Validation_Msg"),
								OrderConstants.SOW_TAB_ERROR);
							}
						}

					}
				}
				if (alternateLocationContact.getEmail() != null
						&& alternateLocationContact.getEmail().trim().length() > 255) {
					addError(getTheResourceBundle().getString("Email"),
							getTheResourceBundle()
									.getString("Email_Validation_Msg"),
							OrderConstants.SOW_TAB_ERROR);
				} else if (alternateLocationContact.getEmail() != null
						&& alternateLocationContact.getEmail().length() > 1) {
					Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)");
					//Pattern p = Pattern.compile(".+@.+\\.[a-zA-Z]+");
					;
					Matcher m = p.matcher(alternateLocationContact.getEmail());
					boolean valResult = m.matches();
					if (valResult == false) {
						addError(getTheResourceBundle().getString("Email"),
								getTheResourceBundle().getString(
										"Email_Pattern_Validation_Msg"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
		}

		// But we would still like to verify valid format for the
		// phones and fax.
		/*
		ArrayList<SOWPhoneDTO> phones = (ArrayList<SOWPhoneDTO>) getAlternateLocationContact()
				.getPhones();
		if (phones != null) {
			for (SOWPhoneDTO phone : phones) {
				if (phone != null) {
					if (phone.isAnySectionEntered()) {
						phone.validate();
						List<IError> list = phone.getErrors();
						for (IError error : list) {
							getErrors().add(error);
						}
					}
				}
			}
		}
		*/
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return OrderConstants.SOW_ADDITIONAL_INFO_TAB;
	}

	public boolean isCreated() {
		return isCreated;
	}

	public void setCreated(boolean isCreated) {
		this.isCreated = isCreated;
	}

	public SOWContactLocationDTO getServiceLocationContact() {
		return serviceLocationContact;
	}

	public void setServiceLocationContact(
			SOWContactLocationDTO serviceLocationContact) {
		this.serviceLocationContact = serviceLocationContact;
	}

	public Integer getLogodocumentId() {
		return logodocumentId;
	}

	public void setLogodocumentId(Integer logodocumentId) {
		this.logodocumentId = logodocumentId;
	}

	public String getStatusString()
	{
		return statusString;
	}

	public void setStatusString(String statusString)
	{
		this.statusString = statusString;
	}

	public String getSubStatusString()
	{
		return subStatusString;
	}

	public void setSubStatusString(String subStatusString)
	{
		this.subStatusString = subStatusString;
	}

	public Integer getSubStatusId() {
		return subStatusId;
	}

	public void setSubStatusId(Integer subStatusId) {
		this.subStatusId = subStatusId;
	}

	public Integer getWfStateId() {
		return wfStateId;
	}

	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}

	public List<LuBuyerRefVO> getBuyerRefs() {
		return buyerRefs;
	}

	public void setBuyerRefs(List<LuBuyerRefVO> buyerRefs) {
		this.buyerRefs = buyerRefs;
	}

	public boolean isRequiredRefTypeFieldsValidation() {
		return requiredRefTypeFieldsValidation;
	}

	public void setRequiredRefTypeFieldsValidation(
			boolean requiredRefTypeFieldsValidation) {
		this.requiredRefTypeFieldsValidation = requiredRefTypeFieldsValidation;
	}

	

}
