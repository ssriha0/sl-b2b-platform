package com.newco.marketplace.web.dto; 

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.CreditCardValidatonUtil;
import com.newco.marketplace.web.action.wizard.ISOWTabModel;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.IOrderGroupDelegate;
import com.sears.os.utils.DateValidationUtils;

public class SOWScopeOfWorkTabDTO extends SOWBaseTabDTO implements ISOWTabModel{
	
	private static final long serialVersionUID = 2967749815833113778L;
	
	private Integer continuationReasonId;

	private String continuationReasonDesc;

	private Integer mainServiceCategoryId = -1;

	private String mainServiceCategoryName;

	private SOWContactLocationDTO serviceLocationContact;

	private ArrayList<SOTaskDTO> tasks;
	private String serviceOrderTypeIndicator;
	// creating variable to store the selected sku category for SL-17504
	private String selectedSkuCategoryName;
	
	private Boolean  skuIndicatorForDocument;
	
	private Integer buyerContactId;
	
	//SL-21070
	private boolean lockEditInd = false;
		
	public Boolean getSkuIndicatorForDocument() {
		return skuIndicatorForDocument;
	}

	public void setSkuIndicatorForDocument(Boolean skuIndicatorForDocument) {
		this.skuIndicatorForDocument = skuIndicatorForDocument;
	}

	public String getSelectedSkuCategoryName() {
		return selectedSkuCategoryName;
	}

	public void setSelectedSkuCategoryName(String selectedSkuCategoryName) {
		this.selectedSkuCategoryName = selectedSkuCategoryName;
	}

	//Variable to identify so created using sku or not
	private Boolean serviceOrderSkuIndicator; 
	
	public Boolean getServiceOrderSkuIndicator() {
		return serviceOrderSkuIndicator;
	}

	public void setServiceOrderSkuIndicator(Boolean serviceOrderSkuIndicator) {
		this.serviceOrderSkuIndicator = serviceOrderSkuIndicator;
	}

	public String getServiceOrderTypeIndicator() {
		return serviceOrderTypeIndicator;
	}

	public void setServiceOrderTypeIndicator(String serviceOrderTypeIndicator) {
		this.serviceOrderTypeIndicator = serviceOrderTypeIndicator;
	}
	
	// Creating variable for sku SL-17504
	private ArrayList<SOTaskDTO> skus;

	
	
	public ArrayList<SOTaskDTO> getSkus() {
		return skus;
	}

	public void setSkus(ArrayList<SOTaskDTO> skus) {
		this.skus = skus;
	}
	
	private String sowNotes;

	private String title;

	private String overview;

	private String buyerTandC;

	private String specialInstructions;

	private String serviceDate1;
	private String serviceDate2;

	private String startTime;

	private String endTime;

	private String timeZone;
	
	private Integer serviceDateType;

	private Integer confirmServiceTime;

	private boolean containsTasks;

	private SODocumentDTO document;

	private ArrayList<SODocumentDTO> documents;
	
	//Using selectedCategoryId as id for the selected sku  for Sl-17504
	private Integer selectedCategoryId = -1;
	

	private Boolean isEditMode = false;
	
	private List<Integer> documentsInCurrentVisit; 
	
	private boolean taskLevelPricingInd = false;
	private Double permitPrepaidPrice;
	private List<ServiceOrderAddonVO> addonInfo;
	
	private String skuCategory;
	private String skuCategoryHidden;
	private String skuName;
	private String selectedSkuName;
	private String onlySkuDeleteInd;
	
	public String getOnlySkuDeleteInd() {
		return onlySkuDeleteInd;
	}

	public void setOnlySkuDeleteInd(String onlySkuDeleteInd) {
		this.onlySkuDeleteInd = onlySkuDeleteInd;
	}

	public String getSelectedSkuName() {
		return selectedSkuName;
	}

	public void setSelectedSkuName(String selectedSkuName) {
		this.selectedSkuName = selectedSkuName;
	}

	public String getSkuCategory() {
		return skuCategory;
	}

	public void setSkuCategory(String skuCategory) {
		this.skuCategory = skuCategory;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Double getPermitPrepaidPrice() {
		return permitPrepaidPrice;
	}

	public void setPermitPrepaidPrice(Double permitPrepaidPrice) {
		this.permitPrepaidPrice = permitPrepaidPrice;
	}

	public boolean isTaskLevelPricingInd() {
		return taskLevelPricingInd;
	}

	public void setTaskLevelPricingInd(boolean taskLevelPricingInd) {
		this.taskLevelPricingInd = taskLevelPricingInd;
	}

	public List<Integer> getDocumentsInCurrentVisit() { 
		return documentsInCurrentVisit; 
	} 
	    
	public void setDocumentsInCurrentVisit(List<Integer> documentsInCurrentVisit) { 
		this.documentsInCurrentVisit = documentsInCurrentVisit; 
	} 

	public Integer getContinuationReasonId() {
		return continuationReasonId;
	}

	public void setContinuationReasonId(Integer continuationReasonId) {
		this.continuationReasonId = continuationReasonId;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getContinuationReasonDesc() {
		return continuationReasonDesc;
	}

	public void setContinuationReasonDesc(String continuationReasonDesc) {
		this.continuationReasonDesc = continuationReasonDesc;
	}

	public Integer getMainServiceCategoryId() {
		return mainServiceCategoryId == null ? -1 : mainServiceCategoryId;
	}

	public void setMainServiceCategoryId(Integer mainServiceCategoryId) {
		this.mainServiceCategoryId = mainServiceCategoryId;
	}

	public SOWContactLocationDTO getServiceLocationContact() {
		return serviceLocationContact;
	}

	public void setServiceLocationContact(
			SOWContactLocationDTO serviceLocationContact) {
		this.serviceLocationContact = serviceLocationContact;
	}

	public ArrayList<SOTaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<SOTaskDTO> tasks) {
		containsTasks = true;
		this.tasks = tasks;
	}

	public String getSowNotes() {
		return sowNotes;
	}

	public void setSowNotes(String sowNotes) {
		this.sowNotes = sowNotes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getBuyerTandC() {
		return buyerTandC;
	}

	public void setBuyerTandC(String buyerTandC) {
		this.buyerTandC = buyerTandC;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getServiceDate1() {
		return serviceDate1;
	}

	public void setServiceDate1(String serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	public String getServiceDate2() {
		return serviceDate2;
	}

	public void setServiceDate2(String serviceDate2) {
		this.serviceDate2 = serviceDate2;
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

	public Integer getServiceDateType() {
		return serviceDateType;
	}

	public void setServiceDateType(Integer serviceDateType) {
		this.serviceDateType = serviceDateType;
	}

	public Integer getConfirmServiceTime() {
		return confirmServiceTime;
	}

	public void setConfirmServiceTime(Integer confirmServiceTime) {
		this.confirmServiceTime = confirmServiceTime;
	}

	public SODocumentDTO getDocument() {
		return document;
	}

	public void setDocument(SODocumentDTO document) {
		this.document = document;
	}
	
	public Boolean getIsEditMode() {
		return isEditMode;
	}

	public void setIsEditMode(Boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	private void validateServiceLocation() {
		if (getServiceLocationContact() != null) {
			if (getServiceLocationContact().getBusinessName() != null
					&& getServiceLocationContact().getBusinessName().length() > 100) {
				addError(getTheResourceBundle().getString("Business_Name"),
						getTheResourceBundle().getString(
								"Business_Name_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}

			if (getServiceLocationContact().getFirstName() == null
					|| getServiceLocationContact().getFirstName().length() < 1) {
				addWarning(getTheResourceBundle().getString("First_Name"),
						getTheResourceBundle().getString(
								"First_Name_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (getServiceLocationContact().getFirstName() != null
					&& getServiceLocationContact().getFirstName().trim()
							.length() > 50) {
				addError(getTheResourceBundle().getString("First_Name"),
						getTheResourceBundle().getString(
								"First_Name_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}

			if (getServiceLocationContact().getLastName() == null
					|| getServiceLocationContact().getLastName().length() < 1) {
				addWarning(getTheResourceBundle().getString("Last_Name"),
						getTheResourceBundle()
								.getString("Last_Name_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (getServiceLocationContact().getLastName() != null
					&& getServiceLocationContact().getLastName().trim()
							.length() > 50) {
				addError(getTheResourceBundle().getString("Last_Name"),
						getTheResourceBundle().getString(
								"Last_Name_Lenght_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}

			if (getServiceLocationContact().getStreetName1() == null
					|| getServiceLocationContact().getStreetName1().length() < 1) {
				addWarning(getTheResourceBundle().getString("Street_Name1"),
						getTheResourceBundle().getString(
								"Street_Name_Validation"),
						OrderConstants.SOW_TAB_WARNING);

			}
			if (getServiceLocationContact().getStreetName1() != null
					&& getServiceLocationContact().getStreetName1().length() > SOConstants.FieldLength.STREET1) {
				addError(getTheResourceBundle().getString("Street_Name1"),
						getTheResourceBundle().getString(
								"Street_Length_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			}

			if (getServiceLocationContact().getStreetName2() != null
					&& getServiceLocationContact().getStreetName2().length() > SOConstants.FieldLength.STREET2) {
				addWarning(getTheResourceBundle().getString("Street_Name2"),
						getTheResourceBundle().getString(
								"Street2_Length_Validation_Msg"),
						OrderConstants.SOW_TAB_WARNING);

			}

			if (getServiceLocationContact().getCity() == null
					|| getServiceLocationContact().getCity().length() < 1) {
				addWarning(getTheResourceBundle().getString("City"),
						getTheResourceBundle().getString("City_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (getServiceLocationContact().getCity() != null
					&& getServiceLocationContact().getCity().length() > SOConstants.FieldLength.CITY) {
				addError(getTheResourceBundle().getString("City"),
						getTheResourceBundle().getString(
								"City_Length_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
			if (getServiceLocationContact().getState() != null
					&& getServiceLocationContact().getState().equals("-1")) {
				addWarning(getTheResourceBundle().getString("State"),
						getTheResourceBundle()
								.getString("State_Validation_Msg"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (getServiceLocationContact().getZip() != null 
					&& getServiceLocationContact().getZip().trim().length() > 0) {
				boolean valResult = false;
				String numPattern = "(\\d{5})";
				valResult = getServiceLocationContact().getZip().matches(
						numPattern);
				if (valResult == false) {
					addError(getTheResourceBundle().getString("Zip"),
							getTheResourceBundle().getString("Zip_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}
			if(getServiceLocationContact().getZip4() != null && getServiceLocationContact().getZip4().trim().length()>0){
				boolean valResult = false;
				String numPattern = "(\\d{4})";
				valResult = getServiceLocationContact().getZip4().matches(
						numPattern);
				if (valResult == false) {
					addError(getTheResourceBundle().getString("Zip4"),
							getTheResourceBundle().getString("Zip4_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}
			if (getServiceLocationContact().getZip() == null
					|| getServiceLocationContact().getZip().trim().length() == 0) {
				addError(getTheResourceBundle().getString("Zip"),
						getTheResourceBundle().getString(
								"Zip_Validation_Missing"),
						OrderConstants.SOW_TAB_ERROR);

			}

			if (getServiceLocationContact().getEmail() != null
					&& getServiceLocationContact().getEmail().trim().length() > 255) {
				addError(getTheResourceBundle().getString("Email"),
						getTheResourceBundle()
								.getString("Email_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			} else if (getServiceLocationContact().getEmail() != null
					&& getServiceLocationContact().getEmail().length() > 1) {
				Pattern p = Pattern.compile("^[\\w!#$%&*+/=?`{|}~^-]+(?:\\.[\\w!#$%&*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
				//Pattern p = Pattern.compile(".+@.+\\.[a-zA-Z]+");
				Matcher m = p.matcher(getServiceLocationContact().getEmail());
				boolean valResult = m.matches();
				if (valResult == false) {
					addError(getTheResourceBundle().getString("Email"),
							getTheResourceBundle().getString(
									"Email_Pattern_Validation_Msg"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}

			/*
			 * Validating the Phones in Service Location Contact DTO
			 */
			if (getServiceLocationContact().getPhones() != null) {
				List<SOWPhoneDTO> phones = getServiceLocationContact()
						.getPhones();

				for (int i = 0; i < phones.size(); i++) {
					SOWPhoneDTO iPhone = phones.get(i);
					if (iPhone.getPhone() != null
							&& iPhone.getPhone().trim().length() > 0  && !iPhone.getPhone().trim().equalsIgnoreCase("null")) {
						boolean valResult = false;
						String numPattern ="(\\d{10})";					
						valResult = iPhone.getPhone().matches(numPattern);

						//Check to see if the phone is empty. I.E. the user never even entered phone
						if(!(iPhone.getAreaCode() == null && iPhone.getPhonePart1() == null && iPhone.getPhonePart2() == null)){
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
									addError(
											"Alternate_phone",
											getTheResourceBundle()
													.getString(
															"Alternate_Phone_Missing_Number_Validation_Msg"),
											OrderConstants.SOW_TAB_ERROR);
								}
								if(iPhone.getPhoneClassId().equals(new Integer(-1))){
									addError(getTheResourceBundle().getString("Alternate_Phone_Type"),
											getTheResourceBundle()
													.getString(
															"Alternate_Phone_Type_Validation"),
											OrderConstants.SOW_TAB_ERROR);
								}

							}
							if (valResult == false && i == 2) {
								addError(
										"Fax",
										getTheResourceBundle()
												.getString(
														"Fax_Missing_Number_Validation_Msg"),
										OrderConstants.SOW_TAB_ERROR);
							}
						}

					}
				}
				
				if (phones != null && phones.size() > 0) {
					if (phones.get(0) != null) {
						if (StringUtils.isEmpty(phones.get(0).getPhonePart1())
								|| StringUtils.isEmpty(phones.get(0)
										.getPhonePart2())
								|| StringUtils.isEmpty(phones.get(0)
										.getAreaCode())) {
							addWarning("Phones",
									"Please enter a contact phone no",
									OrderConstants.SOW_TAB_WARNING);
						}
					}
				}
			}
		}
	}
	

	private void validateTasks() {
		if (tasks != null && getTasks() != null) {
			//Check if there are no tasks provided by User and show warning if so.
			if(getTasks().size() == 0) { //No task provided. Show the warning.
				addWarning("Task", getTheResourceBundle().getString("Task_Not_Provided"),
						OrderConstants.SOW_TAB_WARNING);
			} else { //Process the provided tasks
				for (int i = 0; i < getTasks().size(); i++) {
					SOTaskDTO sotd = getTasks().get(i);
					// if(/*!sotd.getIsFreshTask()*/true){ //is this a fresh task?
					// then don't validate it.
					if (sotd != null && sotd.getComments() != null
							&& sotd.getComments().trim().length() > OrderConstants.SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH_RTF) {
						addError("Task "
								+ (i + 1)
								+ " "
								+ getTheResourceBundle().getString(
										"New_Task_Comment"), getTheResourceBundle()
								.getString("Comment_Validation_RTF"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (sotd != null && sotd.getTaskName() != null
							&& sotd.getTaskName().trim().length() == 0) {
						addWarning("Task " + (i + 1) + " "
								+ getTheResourceBundle().getString("Task_Name"),
								getTheResourceBundle().getString(
										"Task_Name_Validation_Msg"),
								OrderConstants.SOW_TAB_WARNING);
					}
					if (	sotd != null &&
							sotd.getTaskName() != null &&
							sotd.getTaskName().length() > 70) {
						addError("Task " + (i + 1) + " "
								+ getTheResourceBundle().getString("Task_Name"),
								getTheResourceBundle().getString(
										"Task_Name_Validation_Length_Msg"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (sotd != null && sotd.getCategoryId() == null
							|| sotd.getCategoryId().intValue() == -1) {
						addWarning("Task " + (i + 1) + " "
								+ getTheResourceBundle().getString("Category"),
								getTheResourceBundle().getString(
										"Category_Validation_Msg"),
								OrderConstants.SOW_TAB_WARNING);
					}
					if (sotd != null && sotd.getCategoryId() != null
							&& sotd.getCategoryId() != -1) {
						if (sotd.getSubCategoryList().size() > 0 && (sotd.getSubCategoryId() == null)) {
							addError("Task "
									+ (i + 1)
									+ " "
									+ getTheResourceBundle().getString(
											"Sub_Category"), getTheResourceBundle()
									.getString("Sub_Category_Validation_Msg"),
									OrderConstants.SOW_TAB_ERROR);
						}
					}
	
					if (sotd != null && sotd.getSkillId() == null
							|| sotd.getSkillId().intValue() == -1) {
						addWarning("Task " + (i + 1) + " "
								+ getTheResourceBundle().getString("Skill"),
								getTheResourceBundle().getString(
										"Skill_Validation_Msg"),
								OrderConstants.SOW_TAB_WARNING);
					}					
				}
			}
		}

	}

	private void validateMiscellaneous() {
		String skuIndicator=getServiceOrderTypeIndicator();
		setSkuCategory(getSkuCategoryHidden());
		//Re set hidden field value so that while re submitting on error, following check is included.
		//setSkuCategoryHidden("-1");
		if(skuIndicator!=null){
			boolean catSelectedFlag = false;
			boolean skuSelectedFlag = false;
			if(!skuIndicator.equalsIgnoreCase("SoUsingSku")){
				if (getMainServiceCategoryId() == null
						|| getMainServiceCategoryId().intValue() == -1) {
					addError(getTheResourceBundle().getString("MainServiceCategory"),
							getTheResourceBundle().getString(
									"MainService_Category_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
			}else{
				if(getSkus() == null || getSkus().size()== 0){
					if(getSkuCategory() == null || ("-1").equals(getSkuCategory())){
						addError(getTheResourceBundle().getString("SKUCategory"),
								getTheResourceBundle().getString(
										"SKUCategory_Validation"),
								OrderConstants.SOW_TAB_ERROR);
						setOnlySkuDeleteInd("0");
						catSelectedFlag = true;
					}	
					
					if(StringUtils.isEmpty(getSelectedSkuName()) || ("-1").equals(getSelectedSkuName())){
						addError(getTheResourceBundle().getString("SKUNAME"),
								getTheResourceBundle().getString(
										"SKUNAME_Validation"),
								OrderConstants.SOW_TAB_ERROR);
						setOnlySkuDeleteInd("0");
						skuSelectedFlag = true;
					}	
					if(!skuSelectedFlag && !catSelectedFlag){
						addError(getTheResourceBundle().getString("SKUDetails"),
								getTheResourceBundle().getString(
										"SKUDetails_Validation"),
								OrderConstants.SOW_TAB_ERROR);		
					}
				}
	
			}
		}else{
			if (getMainServiceCategoryId() == null
				|| getMainServiceCategoryId().intValue() == -1) {
			addError(getTheResourceBundle().getString("MainServiceCategory"),
					getTheResourceBundle().getString(
							"MainService_Category_Validation"),
					OrderConstants.SOW_TAB_ERROR);
			}
		}
		
		if ( getServiceLocationContact()!= null && getServiceLocationContact().getPhones() == null) {
			addWarning(getTheResourceBundle().getString("Phone"),
					getTheResourceBundle()
							.getString("Phone_Validation_Msg_Req"),
					OrderConstants.SOW_TAB_WARNING);

		}
		/*
		 * Validating General Info
		 */
		if (getBuyerTandC() == null || getBuyerTandC().trim().length() == 0) {

			addWarning(getTheResourceBundle().getString("Buyer_Terms_Condition"),
					getTheResourceBundle().getString("Buyer_Terms_Condition_Req_validation_Msg"),
					OrderConstants.SOW_TAB_WARNING);
		}else if (getBuyerTandC() != null && getBuyerTandC().trim().length() > OrderConstants.SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH_RTF) {
			addError(getTheResourceBundle().getString("Buyer_Terms_Condition"),
					getTheResourceBundle().getString(
							"Buyer_Terms_Condition_validation_Msg_RTF"),
					OrderConstants.SOW_TAB_ERROR);
		}
		
		if (getOverview() == null || getOverview().trim().length() == 0) {

			addWarning(getTheResourceBundle().getString("Overview"),
					getTheResourceBundle().getString("Overview_Req_Validation_Msg"),
					OrderConstants.SOW_TAB_WARNING);
		}else if (getOverview() != null && getOverview().trim().length() > OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH_RTF) {
			addError(
					getTheResourceBundle().getString("Overview"),
					getTheResourceBundle().getString("Overview_Validation_Msg_RTF"),
					OrderConstants.SOW_TAB_ERROR);
		}
		
		if (getTitle() == null || getTitle().trim().length() == 0) {

			addError(getTheResourceBundle().getString("Title"),
					getTheResourceBundle().getString("Title_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}else if (getTitle() != null && getTitle().trim().length() > 255) {
			addError(getTheResourceBundle().getString("Title"), 
					getTheResourceBundle().getString("Title_Length_Validation"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		
        if(getSpecialInstructions() != null && getSpecialInstructions().trim().length() > OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH_RTF ) {
			addError(getTheResourceBundle().getString("Special_Instruction"),
					getTheResourceBundle().getString(
					"Special_Instruction_Validation_Msg_RTF"),
			OrderConstants.SOW_TAB_ERROR);
		} 
		
        // SLT-1165
		if (CreditCardValidatonUtil.validateCCNumbers(getSpecialInstructions())) {
			addError(getTheResourceBundle().getString("Buyer_Special_Instruction"),
					getTheResourceBundle().getString("Buyer_Special_Instruction_Validation_Msg"),
					OrderConstants.SOW_TAB_ERROR);
		}
	}

	private void validateSchedule() {
		if (getServiceDateType() != null && getServiceDateType() != 1) {
			if (getServiceDate1() == null
					|| getServiceDate1().trim().length() < 1) {
				addWarning(getTheResourceBundle().getString("Service_Date1"),
						getTheResourceBundle().getString("Date1_validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (getServiceDate2() == null
					|| getServiceDate2().trim().length() < 1) {
				addWarning(getTheResourceBundle().getString("Service_Date2"),
						getTheResourceBundle().getString("Date2_validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (getServiceDate1() != null
					&& getServiceDate1().trim().length() > 1
					&& getServiceDate2() != null && getServiceDate2().trim().length() > 1) {
				if (DateValidationUtils.isValidDate(getServiceDate1()).trim()
						.equals(OrderConstants.VALID_DATE)
						&& DateValidationUtils.isValidDate(getServiceDate2())
								.trim().equals(OrderConstants.VALID_DATE)) {
					if(getStartTime() != null && !getStartTime().trim().equalsIgnoreCase("[HH:MM]")){
						boolean isCurrentDate = DateValidationUtils.fromDateGreaterCurrentDate(getServiceDate1());
						boolean isPastDateTime = false;
						if(getTimeZone() != null){
							isPastDateTime = DateValidationUtils
													.fromDateTimeGreaterCurrentDateTime(getServiceDate1()
															,getStartTime(),getTimeZone());
						}else{
							isPastDateTime = DateValidationUtils
												.fromDateTimeGreaterCurrentDateTime(getServiceDate1(),getStartTime());
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
								.fromDateGreaterCurrentDate(getServiceDate1())) {
							addError(getTheResourceBundle().getString(
									"Service_Date1"), getTheResourceBundle()
									.getString("CurrentDate_Date1_Validation"),
									OrderConstants.SOW_TAB_ERROR);
						}
					}
					if (!DateValidationUtils.fromDateLesserToDate(
							getServiceDate1(), getServiceDate2())) {
						addError(getTheResourceBundle().getString(
								"Service_Date2"), getTheResourceBundle()
								.getString("Date1_Date2_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				} else {
					if (getServiceDate1() != null
							&& DateValidationUtils.isValidDate(
									getServiceDate1()).trim().equals(
									OrderConstants.INVALID_DATE)) {
						addError(getTheResourceBundle().getString(
								"Service_Date1"), getTheResourceBundle()
								.getString("InvalidDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (getServiceDate2() != null
							&& DateValidationUtils.isValidDate(
									getServiceDate2()).trim().equals(
									OrderConstants.INVALID_DATE)) {
						addError(getTheResourceBundle().getString(
								"Service_Date2"), getTheResourceBundle()
								.getString("InvalidDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (getServiceDate1() != null
							&& DateValidationUtils.isValidDate(
									getServiceDate1()).trim().equals(
									OrderConstants.INVALID_FORMAT)) {
						addError(getTheResourceBundle().getString(
								"Service_Date1"), getTheResourceBundle()
								.getString("InvalidFormatDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (getServiceDate2() != null
							&& DateValidationUtils.isValidDate(
									getServiceDate2()).trim().equals(
									OrderConstants.INVALID_FORMAT)) {
						addError(getTheResourceBundle().getString(
								"Service_Date2"), getTheResourceBundle()
								.getString("InvalidFormatDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
			if (getStartTime() == null
					|| getStartTime().trim().equals("[HH:MM]")) {
				addWarning(getTheResourceBundle()
						.getString("Service_StartTime"), getTheResourceBundle()
						.getString("StartTime_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (getEndTime() == null || getEndTime().trim().equals("[HH:MM]")) {
				addWarning(getTheResourceBundle().getString("Service_EndTime"),
						getTheResourceBundle().getString("EndTime_Validation"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (getServiceDate1() != null
					&& getServiceDate1().trim().length() > 1
					&& getServiceDate2() != null && getServiceDate2().trim().length() > 1) {
				if (DateValidationUtils.validDate(getServiceDate1().trim())
						.compareTo(
								DateValidationUtils.validDate(getServiceDate2()
										.trim())) == 0) {
					if (getStartTime() != null && getEndTime() != null && !getStartTime().trim().equals("[HH:MM]")
							&& !getEndTime().trim().equals("[HH:MM]")) {
						boolean validTime = DateValidationUtils.timeValidation(
								getStartTime().trim(), getEndTime().trim());
						if (!validTime) {
							addError(getTheResourceBundle().getString(
									"Service_EndTime"), getTheResourceBundle()
									.getString("StartEndTime_Validation"),
									OrderConstants.SOW_TAB_ERROR);
						}
					}
				}
			}
		} else {
			if (getServiceDate1() == null
					|| getServiceDate1().trim().length() < 1) {
				addWarning(getTheResourceBundle().getString("Service_Date1_Fixed"),
						getTheResourceBundle().getString("Date1_validation_Fixed_Msg"),
						OrderConstants.SOW_TAB_WARNING);
			}
			if (getServiceDate1() != null
					&& getServiceDate1().trim().length() > 1) {
				if (DateValidationUtils.isValidDate(getServiceDate1()).trim()
						.equals(OrderConstants.VALID_DATE)) {
					if(getStartTime() != null && !getStartTime().trim().equalsIgnoreCase("[HH:MM]")){
						boolean isCurrentDate = DateValidationUtils.fromDateGreaterCurrentDate(getServiceDate1());
						boolean isPastDateTime = false;
						if(getTimeZone() != null){
							isPastDateTime = DateValidationUtils
													.fromDateTimeGreaterCurrentDateTime(getServiceDate1()
															,getStartTime(),getTimeZone());
						}else{
							isPastDateTime = DateValidationUtils
												.fromDateTimeGreaterCurrentDateTime(getServiceDate1(),getStartTime());
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
								.fromDateGreaterCurrentDate(getServiceDate1())) {
							addError(getTheResourceBundle().getString(
									"Service_Date1"), getTheResourceBundle()
									.getString("CurrentDate_Date1_Validation"),
									OrderConstants.SOW_TAB_ERROR);
						}
					}
				} else {
					if (getServiceDate1() != null
							&& DateValidationUtils.isValidDate(
									getServiceDate1()).trim().equals(
									OrderConstants.INVALID_DATE)) {
						addError(getTheResourceBundle().getString(
								"Service_Date1"), getTheResourceBundle()
								.getString("InvalidDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
					if (getServiceDate1() != null
							&& DateValidationUtils.isValidDate(
									getServiceDate1()).trim().equals(
									OrderConstants.INVALID_FORMAT)) {
						addError(getTheResourceBundle().getString(
								"Service_Date1"), getTheResourceBundle()
								.getString("InvalidFormatDate_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
			if (getStartTime() == null
					|| getStartTime().trim().equals("[HH:MM]")) {
				addWarning(getTheResourceBundle()
						.getString("Service_Time"), getTheResourceBundle()
						.getString("StartTime_Validation_Fixed"),
						OrderConstants.SOW_TAB_WARNING);
			}
		}

	}

	@Override
	public void validate() {
		
		List<IError> errorList = getErrorsOnly();
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		_doWorkFlowValidation();
		addZipCodeErrors(errorList);
		validateSchedule();
		validateServiceLocation();
		validateMiscellaneous();
		String skuIndicator=getServiceOrderTypeIndicator();
		
		if(skuIndicator!=null)
		{
			if(!skuIndicator.equalsIgnoreCase("SoUsingSku"))
			{
				validateTasks() ;
			}
			else 
			{
				//validateTasks(); //To avoid unnecessary warn msg while handling SO using SKU			
			} 
		}
		else
		{
			validateTasks();
		}
	
	}

	public String getTabIdentifier() {
		return OrderConstants.SOW_SOW_TAB;
	}

	
	public void addTask(SOTaskDTO task) {
		if (tasks == null) {
			tasks = new ArrayList<SOTaskDTO>();
		}		
		tasks.add(task);		
		containsTasks = true;
	}
	
	public void addSku(SOTaskDTO sku) {
		
		if (skus == null) {
			skus = new ArrayList<SOTaskDTO>();
		}		
		skus.add(sku);		
		containsTasks = true;
	}
	
	public void handleOrderGroupChanges(String groupId, List<String> soIdList, IOrderGroupDelegate orderGroupDelegate, SecurityContext securityContext)
	{
		if(StringUtils.isBlank(groupId))
			return;
		
		if(soIdList == null)
			return;
		
		if(orderGroupDelegate == null)
			return;
		
		if(securityContext == null)
			return;

		// Copy contact changes to all sibling orders
		SOWContactLocationDTO contact = getServiceLocationContact();
		orderGroupDelegate.updateServiceOrderLocationForList(soIdList, contact, securityContext);

		// Copy Schedule changes to all sibling orders
		String startDate = getServiceDate1();
		String endDate = getServiceDate2();
		orderGroupDelegate.updateGroupServiceDate(groupId, startDate, endDate,  getStartTime(), getEndTime(),  securityContext);
	}
	

	public boolean isContainsTasks() {
		return containsTasks;
	}

	public void setContainsTasks(boolean containsTasks) {
		this.containsTasks = containsTasks;
	}

	public String getMainServiceCategoryName() {
		return mainServiceCategoryName == null ? "Select One"
				: mainServiceCategoryName;
	}

	public void setMainServiceCategoryName(String mainServiceCategoryName) {
		this.mainServiceCategoryName = mainServiceCategoryName;
	}

	public Integer getSelectedCategoryId() {
		return selectedCategoryId;
	}

	public void setSelectedCategoryId(Integer selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
	}

	public ArrayList<SODocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<SODocumentDTO> documents) {
		this.documents = documents;
	}

	public void clearErrorsAndWarnings() {
		this.clearAllErrors();
		this.clearAllWarnings();
	}

	public SOWBaseTabDTO getTabById(String soId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setTab(SOWBaseTabDTO tab)
	{
		// TODO Auto-generated method stub
		
	}

	public List<ServiceOrderAddonVO> getAddonInfo() {
		return addonInfo;
	}

	public void setAddonInfo(List<ServiceOrderAddonVO> addonInfo) {
		this.addonInfo = addonInfo;
	}

	public String getSkuCategoryHidden() {
		return skuCategoryHidden == null ? "-1" : skuCategoryHidden;
	}

	public void setSkuCategoryHidden(String skuCategoryHidden) {
		this.skuCategoryHidden = skuCategoryHidden;
	}

	public Integer getBuyerContactId() {
		return buyerContactId;
	}

	public void setBuyerContactId(Integer buyerContactId) {
		this.buyerContactId = buyerContactId;
	}
	public boolean getLockEditInd() {
		return lockEditInd;
	}

	public void setLockEditInd(boolean lockEditInd) {
		this.lockEditInd = lockEditInd;
	}

}
