package com.newco.marketplace.web.dto.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.IWarning;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.sears.os.utils.DateValidationUtils;


public class CreateServiceOrderDescribeAndScheduleDTO extends SOWBaseTabDTO {
	
	private static final long serialVersionUID = -2575889402485814981L;
	
	private String locationName;
	private String street1;
	private String street2;
	private String aptNo;
	private String city;
	private String zip;
	private String stateCd;
	private String stateDesc;
	
	private String projectName;
	private String projectDesc;
	private String addnlInstructions;
	
	private String fixedServiceDate;
	private String serviceDate1Text;
	private String serviceDate2Text;
	
	private String startTime;
	private String endTime;
	
	private String laborLimit;
	private String materialsLimit="0.00";
	private String totalLimit;
	private String postingFee;
	private String taskName;
	private Boolean firstServiceOrderLoggedIn = new Boolean(false);
	private Integer wfStateId;
	private String mainCatId;
	
	private static final String CURRENCY_REGULAR_EXPRESSION = "^(((\\d{1,3},)+\\d{3}|\\d+)\\.?\\d{0,2})$|^(\\.(\\d{1,2}))$";
	
	
	
	private boolean provideAllMaterials = true;
	
	private Integer serviceDateType;
	private String timeZone;
	
	private boolean loggedIn;
	
	private boolean fromSelectLocationPage = false;


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}



	public boolean isProvideAllMaterials() {
		return provideAllMaterials;
	}

	public void setProvideAllMaterials(boolean provideAllMaterials) {
		this.provideAllMaterials = provideAllMaterials;
	}
	

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	@Override
	public String getTabIdentifier() {
		return OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO;
	}

	@Override
	public void validate() {
		if(getCity() == null){
		   addError(getTheResourceBundle().getString("Business_Name"),
				    getTheResourceBundle().getString("Business_Name_Validation"),
				    OrderConstants.SOW_TAB_ERROR);
		}
		
		List<IError> errorList = getErrorsOnly();
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		_doWorkFlowValidation();
		
		addZipCodeErrors(errorList);
		// validate location only if not logged in
		if(!fromSelectLocationPage && (firstServiceOrderLoggedIn == null || !firstServiceOrderLoggedIn.booleanValue())) {
			validateServiceLocation();
		}

		validateMiscellaneous();
		validateSchedule();

		validatePricing();

		
	}

	private void validatePricing() {

		
		Pattern p = null;
		Matcher m = null;
		
		//Clear out all previous errors
		//super.clearAllErrors();
		//setErrors(new ArrayList<IError>());
		//setWarnings(new ArrayList<IWarning>());
		
		//Create Regular expression matcher
		p = Pattern.compile(CURRENCY_REGULAR_EXPRESSION);
		Double templaborLimit =  SLStringUtils.getMonetaryNumber(getLaborLimit());
		
		
		//Validate Maximum Price for Labor
		m = p.matcher(this.getLaborLimit() + "");
		
		if (!m.matches()||(templaborLimit == null))
		{
			addError(getTheResourceBundle().getString("Labor_Spend_Limit"), 
					 getTheResourceBundle().getString("Labor_Spend_Limit_Numeric_Value_Message"),
					 OrderConstants.SOW_TAB_ERROR );
			
		}
		else if (templaborLimit < 0.0){
				
			addError(getTheResourceBundle().getString("Labor_Spend_Limit"), 
					getTheResourceBundle().getString("Labor_Spend_Limit_Missing_Message"),
					OrderConstants.SOW_TAB_ERROR);
	
		}else{	//If the entered Maximum Price for Labor value is valid, then check if it is less than $25.00 
				//if(StringUtils.IsParsableNumber(this.getLaborLimit())){
					//double laborSpendLimit = Double.parseDouble(getLaborLimit());			
					
					
					if(templaborLimit < OrderConstants.MIN_LABOR_SPEND_LIMIT) {
						addError(getTheResourceBundle().getString("Labor_Spend_Limit"), 
							       getTheResourceBundle().getString("Labor_Spend_Limit_Below_Minimum_Value"),
							       OrderConstants.SOW_TAB_ERROR);
					}
					
					laborLimit = String.valueOf(templaborLimit);

			}
			
			//Validate Maximum Price for Materials only if provideAllMaterials is not checked
		
			if(!provideAllMaterials){
				
				Double tempMaterialsLimit =  SLStringUtils.getMonetaryNumber(getMaterialsLimit());
				m = p.matcher(this.getMaterialsLimit() + "");
				
				if (!m.matches()||(tempMaterialsLimit == null)){
					addError(getTheResourceBundle().getString("Materials_Spend_Limit"), 
							 getTheResourceBundle().getString("Materials_Spend_Limit_Numeric_Value_Message"),
							 OrderConstants.SOW_TAB_ERROR );
					
				}else if (tempMaterialsLimit <= 0.0){
				//if(this.getMaterialsLimit() < 0.0){
					addError(getTheResourceBundle().getString("Materials_Spend_Limit"), 
							   getTheResourceBundle().getString("Materials_Spend_Limit_Missing_Message"),
							   OrderConstants.SOW_TAB_ERROR );
					
				}
				else
				{
					materialsLimit = String.valueOf(tempMaterialsLimit);
				}
		
			}else if(provideAllMaterials){
				this.materialsLimit = "0.00";
			}

		}



	private void validateMiscellaneous() {
		/*
		 * Validating General Info
		 */

		if (getProjectName()  == null || getProjectName() .trim().length() == 0) {
			addError(getTheResourceBundle().getString("Project_Name"),
					getTheResourceBundle().getString("Project_Name_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}else if (getProjectName()  != null && getProjectName().trim().length() > 255) {
			addError(getTheResourceBundle().getString("Project_Name"), 
					getTheResourceBundle().getString("Title_Length_Validation"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		
		if(getAddnlInstructions() != null && getAddnlInstructions().trim().replaceAll("\\n","").replaceAll("\\t","").length() > OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH ) {
			addError(getTheResourceBundle().getString("Special_Instruction"),
					getTheResourceBundle().getString(
					"Special_Instruction_Validation_Msg"),
			OrderConstants.SOW_TAB_ERROR);
		}
		
		if(getProjectDesc() != null && getProjectDesc().trim().replaceAll("\\n","").replaceAll("\\t","").length() > OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH ) {
			addError(getTheResourceBundle().getString("Special_Instruction"),
					getTheResourceBundle().getString(
					"Special_Instruction_Validation_Msg"),
			OrderConstants.SOW_TAB_ERROR);
		}
		
		
		
	}

	private void validateServiceLocation() {

		if (getLocationName() == null
					|| getLocationName().length() < 1) {
			addError(getTheResourceBundle().getString("Location_Name"),
						getTheResourceBundle().getString("Location_Name_Missing"),
						OrderConstants.SOW_TAB_ERROR);

		}
		
		if ( getLocationName()!= null
					&&  getLocationName().length() > 100) {
				addError(getTheResourceBundle().getString("Location_Name"),
						getTheResourceBundle().getString(
								"Location_Name_Validation"),
						OrderConstants.SOW_TAB_ERROR);
		}
  


		if (getStreet1() == null
					|| getStreet1().length() < 1) {
			addError(getTheResourceBundle().getString("Street_Address1"),
						getTheResourceBundle().getString("Street_Address1_Validation"),
						OrderConstants.SOW_TAB_ERROR);

		}
		if (getStreet1() != null
					&& getStreet1().length() > 30) {
				addError(getTheResourceBundle().getString("Street_Address1"),
						 getTheResourceBundle().getString(
								"Street1_Length_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
		}
			
		if (getStreet2() != null
				&& getStreet2().length() > 30) {
			addError(getTheResourceBundle().getString("Street_Address2"),
					 getTheResourceBundle().getString(
							"Street2_Length_Validation_Msg"),
					OrderConstants.SOW_TAB_ERROR);
		}

			if (getCity() == null
					|| getCity().length() < 1) {
				addError(getTheResourceBundle().getString("City"),
						getTheResourceBundle().getString("City_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (getCity() != null
					&& getCity().length() > 30) {
				addError(getTheResourceBundle().getString("City"),
						getTheResourceBundle().getString(
								"City_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (getStateCd() != null
					&& getStateCd().equals("-1")) {
				addError(getTheResourceBundle().getString("State"),
						getTheResourceBundle()
								.getString("State_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (getZip() != null 
					&& getZip().trim().length() > 0) {
				boolean valResult = false;
				String numPattern = "(\\d{5})";
				valResult = getZip().matches(
						numPattern);
				if (valResult == false) {
					addError(getTheResourceBundle().getString("Zip"),
							getTheResourceBundle().getString("Zip_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}

			if (getZip() == null
					|| getZip().trim().length() == 0) {
				addError(getTheResourceBundle().getString("Zip"),
						getTheResourceBundle().getString(
								"Zip_Validation_Missing"),
						OrderConstants.SOW_TAB_ERROR);

			}
		}


	private void validateSchedule() {
		
		if (getServiceDateType() != null && getServiceDateType().intValue() != 1) {
			
			// nullify fixed dates
			setFixedServiceDate(null);
			setStartTime(null);
			setEndTime(null);
			
			// range dates for service
			if (StringUtils.isBlank(getServiceDate1Text())) {
				addError(getTheResourceBundle().getString("Service_Date1"),
						getTheResourceBundle().getString("B2C_Date1_validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (StringUtils.isBlank(getServiceDate2Text())) {
				addError(getTheResourceBundle().getString("Service_Date2"),
						getTheResourceBundle().getString("B2C_Date2_validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (StringUtils.isNotBlank(getServiceDate1Text()) && StringUtils.isNotBlank(getServiceDate2Text()) 
				&& DateValidationUtils.isValidDate(getServiceDate1Text()).trim().equals(OrderConstants.VALID_DATE)
				&& DateValidationUtils.isValidDate(getServiceDate2Text()).trim().equals(OrderConstants.VALID_DATE))  
			{
						
							if (!DateValidationUtils.fromDateLesserToDate(
									getServiceDate1Text(), getServiceDate2Text())) {
								addError(getTheResourceBundle().getString(
										"Service_Date2"), getTheResourceBundle()
										.getString("Date1_Date2_Validation"),
										OrderConstants.SOW_TAB_ERROR);
							}
							if (!DateValidationUtils
										.fromDateGreaterCurrentDate(getServiceDate1Text())) {
									addError(getTheResourceBundle().getString(
											"Service_Date1"), getTheResourceBundle()
											.getString("CurrentDate_Date1_Validation"),
											OrderConstants.SOW_TAB_ERROR);
							}
					
			} else {
					if (StringUtils.isNotBlank(getServiceDate1Text())
							&& DateValidationUtils.isValidDate(
									getServiceDate1Text()).trim().equals(
									OrderConstants.INVALID_DATE)) {
						addError(getTheResourceBundle().getString(
								"Service_Date1"), getTheResourceBundle()
								.getString("InvalidDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					else if (StringUtils.isNotBlank(getServiceDate1Text())
							&& DateValidationUtils.isValidDate(
									getServiceDate1Text()).trim().equals(
									OrderConstants.INVALID_FORMAT)) {
						addError(getTheResourceBundle().getString(
								"Service_Date1"), getTheResourceBundle()
								.getString("InvalidFormatDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (StringUtils.isNotBlank(getServiceDate2Text())
							&& DateValidationUtils.isValidDate(
									getServiceDate2Text()).trim().equals(
									OrderConstants.INVALID_DATE)) {
						addError(getTheResourceBundle().getString(
								"Service_Date2"), getTheResourceBundle()
								.getString("InvalidDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					} else if (StringUtils.isNotBlank(getServiceDate2Text())
							&& DateValidationUtils.isValidDate(
									getServiceDate2Text()).trim().equals(
									OrderConstants.INVALID_FORMAT)) {
						addError(getTheResourceBundle().getString(
								"Service_Date2"), getTheResourceBundle()
								.getString("InvalidFormatDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
		 else {     // fixed Date validation
			 
			 validateFixedDateTime();
		}

	}

	protected void validateFixedDateTime() {
		 setServiceDate1Text(null);
		 setServiceDate2Text(null);
		 
		 
		if (getFixedServiceDate() == null
				|| getFixedServiceDate().trim().length() < 1) {
			addError(getTheResourceBundle().getString("Service_Date1_Fixed"),
					getTheResourceBundle().getString("B2C_Date1_validation_Fixed_Msg"),
					OrderConstants.SOW_TAB_ERROR);
		}
		if (getFixedServiceDate() != null
				&& getFixedServiceDate().trim().length() > 1) {
			if (DateValidationUtils.isValidDate(getFixedServiceDate()).trim()
					.equals(OrderConstants.VALID_DATE)) {
				if(getStartTime() != null && !getStartTime().trim().equalsIgnoreCase("[HH:MM]")){
					boolean isCurrentDate = DateValidationUtils.fromDateGreaterCurrentDate(getFixedServiceDate());
					boolean isPastDateTime = false;
					if(getTimeZone() != null){
						isPastDateTime = DateValidationUtils
												.fromDateTimeGreaterCurrentDateTime(getFixedServiceDate()
														,getStartTime(),getTimeZone());
					}else{
						isPastDateTime = DateValidationUtils
											.fromDateTimeGreaterCurrentDateTime(getFixedServiceDate(),getStartTime());
					}
					if(isCurrentDate && isPastDateTime){
						addError(getTheResourceBundle().getString(
								"Service_StartTime"), getTheResourceBundle()
								.getString("CurrentDate_Date1_Time_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}else if(!isCurrentDate){
						addError(getTheResourceBundle().getString(
						"Service_Date1"), getTheResourceBundle()
						.getString("CurrentDate_Date1_Validation"),
						OrderConstants.SOW_TAB_ERROR);
					}
				}else{
					if (!DateValidationUtils
							.fromDateGreaterCurrentDate(getFixedServiceDate())) {
						addError(getTheResourceBundle().getString(
								"Service_Date1"), getTheResourceBundle()
								.getString("CurrentDate_Date1_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			} else {
				if (getFixedServiceDate() != null
						&& DateValidationUtils.isValidDate(
								getFixedServiceDate()).trim().equals(
								OrderConstants.INVALID_DATE)) {
					addError(getTheResourceBundle().getString(
							"Service_Time"), getTheResourceBundle()
							.getString("InvalidDate_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
				if (getFixedServiceDate() != null
						&& DateValidationUtils.isValidDate(
								getFixedServiceDate()).trim().equals(
								OrderConstants.INVALID_FORMAT)) {
					addError(getTheResourceBundle().getString(
							"Service_Time"), getTheResourceBundle()
							.getString("InvalidFormatDate_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}
		}
		if (getStartTime() == null
				|| getStartTime().trim().equalsIgnoreCase("[HH:MM]")) {
			addError(getTheResourceBundle()
					.getString("Service_Time"), getTheResourceBundle()
					.getString("StartTime_Validation_Fixed"),
					OrderConstants.SOW_TAB_ERROR);
			return;
		}
		if (getEndTime() == null
				|| getEndTime().trim().equalsIgnoreCase("[HH:MM]")) {
			addError(getTheResourceBundle()
					.getString("Service_Time"), getTheResourceBundle()
					.getString("EndTime_Validation"),
					OrderConstants.SOW_TAB_ERROR);
			return;
		}
		boolean validTime = DateValidationUtils.timeValidation(
				getStartTime().trim(), getEndTime().trim());
		if (!validTime) {
			addError(getTheResourceBundle().getString(
					"Service_EndTime"), getTheResourceBundle()
					.getString("StartEndTime_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}		
	}

	public String getAddnlInstructions() {
		return addnlInstructions;
	}

	public void setAddnlInstructions(String addnlInstructions) {
		this.addnlInstructions = addnlInstructions;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getFixedServiceDate() {
		return fixedServiceDate;
	}

	public void setFixedServiceDate(String fixedServiceDate) {
		this.fixedServiceDate = fixedServiceDate;
	}


	public Integer getServiceDateType() {
		return serviceDateType;
	}

	public void setServiceDateType(Integer serviceDateType) {
		this.serviceDateType = serviceDateType;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(String totalLimit) {
		this.totalLimit = totalLimit;
	}

	public String getPostingFee() {
		return postingFee;
	}

	public void setPostingFee(String postingFee) {
		this.postingFee = postingFee;
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

	public String getAptNo() {
		return aptNo;
	}

	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}

	public String getLaborLimit() {
		return laborLimit;
	}

	public void setLaborLimit(String laborLimit) {
		this.laborLimit = org.apache.commons.lang.StringUtils.trim(laborLimit);
	}

	public String getMaterialsLimit() {
		return materialsLimit;
	}

	public void setMaterialsLimit(String materialsLimit) {
		this.materialsLimit =org.apache.commons.lang.StringUtils.trim(materialsLimit);
	}

	/**
	 * @return the stateCd
	 */
	public String getStateCd() {
		return stateCd;
	}

	/**
	 * @param stateCd the stateCd to set
	 */
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	/**
	 * @return the stateDesc
	 */
	public String getStateDesc() {
		return stateDesc;
	}

	/**
	 * @param stateDesc the stateDesc to set
	 */
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public boolean isFromSelectLocationPage() {
		return fromSelectLocationPage;
	}

	public void setFromSelectLocationPage(boolean fromSelectLocationPage) {
		this.fromSelectLocationPage = fromSelectLocationPage;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Boolean getFirstServiceOrderLoggedIn() {
		return firstServiceOrderLoggedIn;
	}

	public void setFirstServiceOrderLoggedIn(Boolean firstServiceOrderLoggedIn) {
		this.firstServiceOrderLoggedIn = firstServiceOrderLoggedIn;
	}

	/**
	 * @return the wfStateId
	 */
	public Integer getWfStateId() {
		return wfStateId;
	}

	/**
	 * @param wfStateId the wfStateId to set
	 */
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}

	public void setMainCatId(String mainCatId) {
		this.mainCatId = mainCatId;
	}

	public String getMainCatId() {
		return mainCatId;
	}


}
