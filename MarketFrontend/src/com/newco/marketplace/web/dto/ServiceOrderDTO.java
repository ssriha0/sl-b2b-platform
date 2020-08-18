package com.newco.marketplace.web.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.SOPartLaborPriceReasonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.interfaces.OrderConstants;

public class ServiceOrderDTO extends SOWBaseTabDTO implements Comparable<ServiceOrderDTO>{
	
	private static final long serialVersionUID = 6595263930617494667L;
	// ***************  START ORIGINAL *******************************
	private Integer status;	
	private String title;
	private Date serviceOrderDate;
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private Timestamp serviceDateGMT1;
	private Timestamp serviceDateGMT2;
	private String serviceTimeStartGMT;
	private String serviceTimeEndGMT;
	private String serviceLocationTimeZone;
	private String scheduleStatus;
	private Integer loggedInResourceId;
	private int countOfParts = 0;
	private int countOfInvoice = 0;
	private String originalSoId;
	private boolean recallProvider;
	//Added for displaying total est provider payment
	private double totalEstproviderPayment;
	
	private Integer serviceDateTypeId;
	
	
	

	public Integer getLoggedInResourceId() {
		return loggedInResourceId;
	}

	public void setLoggedInResourceId(Integer loggedInResourceId) {
		this.loggedInResourceId = loggedInResourceId;
	}

	private List<PreCallHistory> scheduleHistory;

	public List<PreCallHistory> getScheduleHistory() {
		return scheduleHistory;
	}

	public void setScheduleHistory(List<PreCallHistory> scheduleHistory) {
		this.scheduleHistory = scheduleHistory;
	}

	private String city;
	private String state;
	private String zip;	
	private String cityStateZip;
	private String phoneNumber;
	private String userID;
	private Integer locationId;
	private Integer subStatus;
	private String subStatusString;
	private String  spendLimitLabor;
	private String  spendLimitParts;
	private String  spendLimitTotal;
	private String spendLimitTotalCurrencyFormat;
	private String note;
	private ArrayList<ServiceOrderNote> noteList;
	private Integer logodocumentId;
	private Double distanceInMiles;
	private String zip5;

	private String providerName;
	private String providerAlternatePhoneNumber;
	private String providerMainPhoneNumber;
	private String buyerName;
	private String resourceDispatchAddress; // for Google Direction
	
	private Integer providersSentTo;
	private Integer providersDeclined;
	private Integer providersConditionalAccept;
	private Long responseCount;
	
	private List<RoutedProvider> routedResources;
	private VendorResource acceptedResource;
	private Integer acceptedResourceId;
	private Integer acceptedVendorId;
	private Timestamp acceptedDate;
	
	private String timeToAppointmentDisplay;
	private String ageOfOrderDisplay;
	private String serviceOrderDescr;
	
	private Integer acceptedCounts;
	private Integer condCounts;
	private Integer rejectedCounts;
	private String routedResourceId;
	private String routedResourceName;
	
	//survey results indicator
	private boolean buyerHasRatedProvider;
	private boolean providerHasRatedBuyer;
	
	// Value of 'notVerifiedInsPresent' is true if Vehicle Liability or Workers compensation insurance is not present for the provider.
	private Boolean notVerifiedInsPresent = Boolean.FALSE;
	private Boolean insPresent = Boolean.TRUE;
	private boolean acceptInsuranceCondition;
	
	// For story 8439
	private String subject;
	private String message;
	private Integer entityID;
	
	// Foreign keys that needed to be 'expanded'
	private String statusString;
	private String serviceOrderDateString;
		
	private String errorMsg;
	
	private String timeToAppointment;
	private String ageOfOrder;
	private String endCustomerName;
	private String soDescription;
	
	//For details in areas such as Posted tab
	private Integer routedProvidersCount;
	private Integer conditionalAcceptedCount;
	private Integer rejectionsCount;
	
	// ***************  END ORIGINAL *******************************
	
	private String parentGroupId;
	private String parentGroupTitle;
	private String groupCreatedDateString;
	private Double groupSpendLimitLabor;
	private Double groupSpendLimitParts;
	private Double groupSpendLimit;
	private String sortSOandGroupID;
	
	private Integer providerResponseId;
	private boolean showSubStatusChange;
	
	// ***************  START new (perhaps duplicate) data members ************************
	
	//Service Order Details Section
	private String id;
	private String primaryStatus;
	private ArrayList<LabelValueBean> statusAndDateList = new ArrayList<LabelValueBean>();	
	private String appointmentDates;
	private String rescheduleDates;
	private String serviceWindow;
	private String serviceWindowComment;
	private String rescheduleServiceWindow;
	private String continuationOrderID=null; //Optional
	private String continuationReason=null; //Optional	
	private String overview;
	private String buyersTerms;	
	private String buyerID;
	private boolean taskLevelPriceInd;
	private Integer buyerRoleId;
	private String companyID;
	private String companyRating;
	private  ArrayList<SOWSelBuyerRefDTO> selByerRefDTO =  new ArrayList<SOWSelBuyerRefDTO>();
	
	// Scope of Work Section
	private SOContactDTO locationContact;	
	private String locationNotes;
	private String mainServiceCategory;
	private Integer mainServiceCategoryId;	
	private String categoriesRequired;
	private String subcategoriesRequired;
	private String skillsRequired;
	private Integer numberOfTasks;
	private String jobInfo;
	private String jobInfoOptional1=null; // optional
	private String jobInfoOptional2=null; // optional4
	private ArrayList<SOTaskDTO> taskList = new ArrayList<SOTaskDTO>();
	private Double prePaidPermitPrice;
	private Double addOnPermitPrice;
	private Double finalPermitPrice;
	private Double soTaskMaxLabor;
	private Double soFinalMaxLabor;
	private Double totalExclPermits;
	private double permitTaskAddonPrice;
	private double nonPermitTaskAddonPrice;
	private ArrayList<SOTaskDTO> permitTaskList = new ArrayList<SOTaskDTO>();
	private ArrayList<SOTaskDTO> nonPermitTaskList = new ArrayList<SOTaskDTO>();
	private String generalComments;
	private String cancelAmount;
	private String buyerPrice;
	private String buyerComments;
	private String  buyerEntryDate;
	private String providerPrice;
	private String providerComments;
	private String  providerEntryDate;
	private String pendingCancelSubstatus;
	
	private PendingCancelPriceVO buyerPendingCancelPrice;
	private PendingCancelPriceVO providerPendingCancelPrice;
 

	
	

	
	
	//Contact Information Panel
	SOContactDTO locationPrimaryContact; 	// 'Service Location Primary Contact'
	SOContactDTO locationAlternateContact; 	// 'Service Location Alternate Contact'
	SOContactDTO buyerContact;				// 'Buyer'
	SOContactDTO buyerSupportContact;		// 'Buyer Contact'
	SOContactDTO providerContact; 			// 'Service Location Contact Information'
	 private boolean shareContactInd;
	 private boolean isCommercialLocation;
	
	
	// Service Order Pricing Section
	 private String pricingSectionComment; //The buyer set the pricing type and spend limits as outlined below.
	 private String pricingType;// 	Hourly
	 private String rate;// 	$4.50/hr
	 private String rateType;// 	Provider Selected
	 private Double laborSpendLimit;
	 private Double partsSpendLimit;
	 private Double partsTaxPercent;
	 private Double laborTaxPercent;
	 private Double totalSpendLimit;	 
	 private String specialInstructions;
	 private String accessFee;
	 private String totalAmountDue;
	 private String initialFunds;
	 private String additionalFundsBank;
	 private String additionalFundsAmount;
	 private String finalFunds;
	 private String partsSuppliedBy;// checking whether supplied by provider or buyer or parts are not needed
	 // Parts Panel
	 ArrayList<SOPartsDTO> partsList = new ArrayList<SOPartsDTO>();
	 private List<ProviderInvoicePartsVO> invoiceParts = new ArrayList<ProviderInvoicePartsVO>();
	 private List<SOPartLaborPriceReasonVO> partLaborPriceReason = new ArrayList<SOPartLaborPriceReasonVO>();
	 private String partsCount; 
	 private String priceModel;
	 private Integer priceModelType;
	 
	 // Documents and Photos Panel
	 ArrayList<SODocumentDTO> documentsList = new ArrayList<SODocumentDTO>();
	 private String documentsInstructions;
	 
	 // Terms and Conditions Panel
	 private String termsAndConditions;
	 private Integer termsAndConditionsId;
	 private String pleaseVerify;

	 
	 // Prices: final parts and labor prices close and pay panel
	 private Boolean showPrices = true;
	 private Boolean allowBidOrders = false;
	 private Boolean allowSealedBidOrders = false;
	 private Double finalPartsPrice = null;
	 private Double finalLaborPrice = null;
	 private Double totalFinalPrice = null;
	 private Double postingFee = null;
	 private Double serviceFee = null;
	 private Double providerPayment = null;
	 
	 //For display purpose only so as not to impact the existing calculation
	 private Double serviceFeeDisplay = null;// SL-16817
	 private Double providerPaymentDisplay = null;// SL-16817
	 
	 private Double cancellationFee = null;
	 private String resolutionComment = null;
	 private String acceptTermsAndConditions = OrderConstants.SOW_REVIEW_DONT_ACCEPT_TERMS_AND_CONDITIONS;
	 // Providers Panel
	 ArrayList<SOWProviderDTO> providersList = new ArrayList<SOWProviderDTO>();
	 ArrayList reviewErrors =null;
	 private List<ProviderResultVO> routedResourcesForFirm; // For providers, specific to firm's providers used for Accept and Reject
	 private List<ProviderResultVO> routedProvExceptCounterOffer; // For providers panel specific to firm's providers
	 private List<ServiceDatetimeSlot> serviceDatetimeSlots;
	 private boolean carSO; // Determine if it SO is routed thru CAR or not
	 
	 // formatted attributes for widgets
	 private String buyerWidget = null;
	 private String providerWidget = null;
	 private String endCustomerWidget = null;
	 private String locationWidget = null;
	 private String titleWidget = null;
	 private Integer partsSupplier=0; 
	 private String endCustomerPrimaryPhoneNumberWidget=null;
	 private String providerPrimaryPhoneNumberWidget=null;
	 private String providerAltPhoneNumberWidget=null;
	 
	 private boolean showIncreaseSpendLimitButton = false; //used in JSP
	 private String claimedByResource=null;
	 private String systemTimezone;
	 
	 private boolean tryingToPost;
	 private List<AssociatedIncidentVO> associatedIncidents;
	 private AddonServicesDTO upsellInfo;
	 private boolean showUpsellInfo;
	 private Double addonPrice;
	 
	 private boolean claimable;
	 private String resourceId;
	 private Timestamp tempSortDate;
	 
	 private String locationForDirections;
	// ***************  END SODetailsSummaryDTO *******************************
	 
	 private String firmName = null;
	 private String firmPhoneNumber = null;
	 
	 private Integer bids;
	 private Float currentBidPrice;
	 private Float lowBid;
	 private Float highBid;
	 private Timestamp bidEarliestStartDate;
	 private Timestamp bidLatestEndDate;
	 private Float yourBid;
	 private Date bidExpirationDate;
	 
	 private Integer noteOrQuestionCount;
	 private Integer newNoteOrQuestionCount;
	 private Integer newBidCount;

	private double sessionId;
	private List<ProviderResultVO> availableProviders;
	private boolean sealedBidInd;
	private String soLocationTimeZone;
	private String priceType;
	private String assignmentType;
	//SL-18226
	private Integer spnId;
	
	//labor and parts price
	private Double finalPartsPriceNew = null;
	 private Double finalLaborPriceNew = null;
	 
	 //non funded buyer indicator
	 private Boolean nonFundedInd = false;
	 
	 //invoice parts pricing model from so workflow control
	 private String invoicePartsPricingModel;
	 
	 private String invoicePartsInd;
	 
	 
	//Priority 5B change
		//invalid model serial no ind from so workflow control
		private String invalidModelSerialInd;
		private boolean modelImage;
		private boolean serialImage;
		
		 private Double laborTaxPercentage = null;
		 private Double materialsTaxPercentage = null;
		 private Double orginalServiceFee;
		 private Double providerPay;
		 private Double orginalProviderPay;
		 private Double tax;
		 private Double orginalFinalPrice;


		
		public boolean isModelImage() {
			return modelImage;
		}

		public void setModelImage(boolean modelImage) {
			this.modelImage = modelImage;
		}

		public boolean isSerialImage() {
			return serialImage;
		}

		public void setSerialImage(boolean serialImage) {
			this.serialImage = serialImage;
		}

		public String getInvalidModelSerialInd() {
			return invalidModelSerialInd;
		}

		public void setInvalidModelSerialInd(String invalidModelSerialInd) {
			this.invalidModelSerialInd = invalidModelSerialInd;
		}
	 
	 public String getInvoicePartsInd() {
			return invoicePartsInd;
		}

	public void setInvoicePartsInd(String invoicePartsInd) {
			this.invoicePartsInd = invoicePartsInd;
		}
	
	

	

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
	//Creating a new variable to store method of routing for posted so
	public String methodOfRouting;
	/**
	 * @return the methodOfRouting
	 */
	public String getMethodOfRouting() {
		return methodOfRouting;
	}

	/**
	 * @param methodOfRouting the methodOfRouting to set
	 */
	public void setMethodOfRouting(String methodOfRouting) {
		this.methodOfRouting = methodOfRouting;
	}
//Sl 18698 For Estimated Time of Arrival
	private String eta;
	/**
	 * @return the eta
	 */
	public String getEta() {
		return eta;
	}

	/**
	 * @param eta the eta to set
	 */
	public void setEta(String eta) {
		this.eta = eta;
	}

	public boolean isSealedBidInd() {
		return sealedBidInd;
	}

	public void setSealedBidInd(boolean sealedBidInd) {
		this.sealedBidInd = sealedBidInd;
	}

	public double getSessionId() {
		return sessionId;
	}

	public void setSessionId(double sessionId) {
		this.sessionId = sessionId;
	}

    public boolean getShowUpsellInfo() {
		return showUpsellInfo;
	}

	public void setShowUpsellInfo(boolean showUpsellInfo) {
		this.showUpsellInfo = showUpsellInfo;
	}

	public List<AssociatedIncidentVO> getAssociatedIncidents() {
		return associatedIncidents;
	}

	public void setAssociatedIncidents(
			List<AssociatedIncidentVO> associatedIncidents) {
		this.associatedIncidents = associatedIncidents;
	}

	public ArrayList getReviewErrors() {
		return reviewErrors;
	}

	public void setReviewErrors(ArrayList reviewErrors) {
		this.reviewErrors = reviewErrors;
	}

	public ArrayList<SOWProviderDTO> getProvidersList() {
		return providersList;
	}

	public void setProvidersList(ArrayList<SOWProviderDTO> providersList) {
		this.providersList = providersList;
	}

	public ServiceOrderDTO()
	{
		this.title = null;
		this.phoneNumber = null;
		this.zip = null;
		this.city = null;
		this.state = null;
		this.userID = null;
		this.serviceOrderDate = null;
		this.locationId = null;
		this.status = null;
		this.subStatus = null;
		this.note = null;
		this.subject=null;
		this.id=null;
		this.message=null;
		this.buyerName = null;
		this.providerName = null;
	} 
	
	public ServiceOrderDTO(ServiceOrder so)	{
		this.title = "";
		this.id=so.getSoId();
		this.status = so.getWfStateId();
		//this.phoneNumber = so.getEndUserContact().getPhoneNo();
		//this.serviceOrderDateString = serviceOrderDate.toLocaleString();
	}

	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public Integer getStatus()
  {
  	return status;
  }
	public void setStatus(Integer status)
  {
  	this.status = status;
  }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getServiceOrderDate() {
		return serviceOrderDate;
	}
	public void setServiceOrderDate(Date serviceOrderDate) {
		this.serviceOrderDate = serviceOrderDate;
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
	public String getPhoneNumber()
  {
  	return phoneNumber;
  }
	public void setPhoneNumber(String phoneNumber)
  {
  	this.phoneNumber = phoneNumber;
  }
	public String getUserID()
  {
  	return userID;
  }
	public void setUserID(String userID)
  {
  	this.userID = userID;
  }
	public Integer getLocationId()
  {
  	return locationId;
  }
	public void setLocationId(Integer locationId)
  {
  	this.locationId = locationId;
  }
	public Integer getSubStatus()
  {
  	return subStatus;
  }
	public void setSubStatus(Integer subStatus)
  {
  	this.subStatus = subStatus;
  }
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getProvidersSentTo()
	{
		return providersSentTo;
	}

	public void setProvidersSentTo(Integer providersSentTo)
	{
		this.providersSentTo = providersSentTo;
	}

	public Integer getProvidersDeclined()
	{
		return providersDeclined;
	}




	public void setProvidersDeclined(Integer providersDeclined)
	{
		this.providersDeclined = providersDeclined;
	}




	public Integer getProvidersConditionalAccept()
	{
		return providersConditionalAccept;
	}




	public void setProvidersConditionalAccept(Integer providersConditionalAccept)
	{
		this.providersConditionalAccept = providersConditionalAccept;
	}

	public String getServiceOrderDateString()
	{
		return serviceOrderDateString;
	}

	public void setServiceOrderDateString(String serviceOrderDateString)
	{
		this.serviceOrderDateString = serviceOrderDateString;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getEntityID() {
		return entityID;
	}

	public void setEntityID(Integer entityID) {
		this.entityID = entityID;
	}

	public String getSubStatusString() {
		return subStatusString;
	}

	public void setSubStatusString(String subStatusString) {
		this.subStatusString = subStatusString;
	}

	public ArrayList<ServiceOrderNote> getNoteList() {
		return noteList;
	}

	public void setNoteList(ArrayList<ServiceOrderNote> noteList) {
		this.noteList = noteList;
	}


	public String getAgeOfOrder() {
		return ageOfOrder;
	}

	public void setAgeOfOrder(String ageOfOrder) {
		this.ageOfOrder = ageOfOrder;
	}

	public String getEndCustomerName() {
		return endCustomerName;
	}

	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
	}

	public String getTimeToAppointment() {
		return timeToAppointment;
	}

	public void setTimeToAppointment(String timeToAppointment) {
		this.timeToAppointment = timeToAppointment;
	}

	public Integer getConditionalAcceptedCount() {
		return conditionalAcceptedCount;
	}

	public void setConditionalAcceptedCount(Integer conditionalAcceptedCount) {
		this.conditionalAcceptedCount = conditionalAcceptedCount;
	}

	public Integer getRejectionsCount() {
		return rejectionsCount;
	}

	public void setRejectionsCount(Integer rejectionsCount) {
		this.rejectionsCount = rejectionsCount;
	}

	public Integer getRoutedProvidersCount() {
		return routedProvidersCount;
	}

	public void setRoutedProvidersCount(Integer routedProvidersCount) {
		this.routedProvidersCount = routedProvidersCount;
	}

	public String getSoDescription() {
		return soDescription;
	}

	public void setSoDescription(String soDescription) {
		this.soDescription = soDescription;
	}

	public String getRoutedResourceId() {
		return routedResourceId;
	}

	public void setRoutedResourceId(String routedResourceId) {
		this.routedResourceId = routedResourceId;
	}


	public String getSpendLimitTotalCurrencyFormat() {
		return spendLimitTotalCurrencyFormat;
	}

	public void setSpendLimitTotalCurrencyFormat(String spendLimitTotalCurrencyFormat) {
		this.spendLimitTotalCurrencyFormat = spendLimitTotalCurrencyFormat;
	}


	public String getTimeToAppointmentDisplay() {
		return timeToAppointmentDisplay;
	}


	public void setTimeToAppointmentDisplay(String timeToAppointmentDisplay) {
		this.timeToAppointmentDisplay = timeToAppointmentDisplay;
	}


	public String getAgeOfOrderDisplay() {
		return ageOfOrderDisplay;
	}


	public void setAgeOfOrderDisplay(String ageOfOrderDisplay) {
		this.ageOfOrderDisplay = ageOfOrderDisplay;
	}


	public String getServiceOrderDescr() {
		return serviceOrderDescr;
	}


	public void setServiceOrderDescr(String serviceOrderDescr) {
		this.serviceOrderDescr = serviceOrderDescr;
	}


	public Integer getAcceptedCounts() {
		return acceptedCounts;
	}


	public void setAcceptedCounts(Integer acceptedCounts) {
		this.acceptedCounts = acceptedCounts;
	}


	public Integer getCondCounts() {
		return condCounts;
	}


	public void setCondCounts(Integer condCounts) {
		this.condCounts = condCounts;
	}


	public Integer getRejectedCounts() {
		return rejectedCounts;
	}


	public void setRejectedCounts(Integer rejectedCounts) {
		this.rejectedCounts = rejectedCounts;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPrimaryStatus() {
		return primaryStatus;
	}


	public void setPrimaryStatus(String primaryStatus) {
		this.primaryStatus = primaryStatus;
	}




	public String getAppointmentDates() {
		return appointmentDates;
	}


	public void setAppointmentDates(String appointmentDates) {
		this.appointmentDates = appointmentDates;
	}


	public String getRescheduleDates() {
		return rescheduleDates;
	}


	public void setRescheduleDates(String rescheduleDates) {
		this.rescheduleDates = rescheduleDates;
	}


	public String getServiceWindow() {
		return serviceWindow;
	}


	public void setServiceWindow(String serviceWindow) {
		this.serviceWindow = serviceWindow;
	}


	public String getContinuationOrderID() {
		return continuationOrderID;
	}


	public void setContinuationOrderID(String continuationOrderID) {
		this.continuationOrderID = continuationOrderID;
	}


	public String getContinuationReason() {
		return continuationReason;
	}


	public void setContinuationReason(String continuationReason) {
		this.continuationReason = continuationReason;
	}


	public String getOverview() {
		return overview;
	}


	public void setOverview(String overview) {
		this.overview = overview;
	}


	public String getBuyersTerms() {
		return buyersTerms;
	}


	public void setBuyersTerms(String buyersTerms) {
		this.buyersTerms = buyersTerms;
	}


	public String getBuyerID() {
		return buyerID;
	}


	public void setBuyerID(String buyerID) {
		this.buyerID = buyerID;
	}


	public String getCompanyID() {
		return companyID;
	}


	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}


	public String getCompanyRating() {
		return companyRating;
	}


	public void setCompanyRating(String companyRating) {
		this.companyRating = companyRating;
	}


	public SOContactDTO getLocationContact() {
		return locationContact;
	}


	public void setLocationContact(SOContactDTO locationContact) {
		this.locationContact = locationContact;
	}


	public String getLocationNotes() {
		return locationNotes;
	}


	public void setLocationNotes(String locationNotes) {
		this.locationNotes = locationNotes;
	}


	public String getMainServiceCategory() {
		return mainServiceCategory;
	}


	public void setMainServiceCategory(String mainServiceCategory) {
		this.mainServiceCategory = mainServiceCategory;
	}


	public String getCategoriesRequired() {
		return categoriesRequired;
	}


	public void setCategoriesRequired(String categoriesRequired) {
		this.categoriesRequired = categoriesRequired;
	}


	public String getSubcategoriesRequired() {
		return subcategoriesRequired;
	}


	public void setSubcategoriesRequired(String subcategoriesRequired) {
		this.subcategoriesRequired = subcategoriesRequired;
	}


	public String getSkillsRequired() {
		return skillsRequired;
	}


	public void setSkillsRequired(String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}


	public Integer getNumberOfTasks() {
		return numberOfTasks;
	}


	public void setNumberOfTasks(Integer numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}


	public String getJobInfo() {
		return jobInfo;
	}


	public void setJobInfo(String jobInfo) {
		this.jobInfo = jobInfo;
	}


	public String getJobInfoOptional1() {
		return jobInfoOptional1;
	}


	public void setJobInfoOptional1(String jobInfoOptional1) {
		this.jobInfoOptional1 = jobInfoOptional1;
	}


	public String getJobInfoOptional2() {
		return jobInfoOptional2;
	}


	public void setJobInfoOptional2(String jobInfoOptional2) {
		this.jobInfoOptional2 = jobInfoOptional2;
	}


	public ArrayList<SOTaskDTO> getPermitTaskList() {
		return permitTaskList;
	}

	public void setPermitTaskList(ArrayList<SOTaskDTO> permitTaskList) {
		this.permitTaskList = permitTaskList;
	}

	public ArrayList<SOTaskDTO> getTaskList() {
		return taskList;
	}


	public void setTaskList(ArrayList<SOTaskDTO> taskList) {
		this.taskList = taskList;
	}


	public String getGeneralComments() {
		return generalComments;
	}


	public void setGeneralComments(String generalComments) {
		this.generalComments = generalComments;
	}


	public SOContactDTO getLocationAlternateContact() {
		return locationAlternateContact;
	}


	public void setLocationAlternateContact(SOContactDTO locationAlternateContact) {
		this.locationAlternateContact = locationAlternateContact;
	}


	public SOContactDTO getBuyerContact() {
		return buyerContact;
	}


	public void setBuyerContact(SOContactDTO buyerContact) {
		this.buyerContact = buyerContact;
	}


	public SOContactDTO getBuyerSupportContact() {
		return buyerSupportContact;
	}


	public void setBuyerSupportContact(SOContactDTO buyerSupportContact) {
		this.buyerSupportContact = buyerSupportContact;
	}


	public SOContactDTO getProviderContact() {
		return providerContact;
	}


	public void setProviderContact(SOContactDTO providerContact) {
		this.providerContact = providerContact;
	}


	public String getPricingSectionComment() {
		return pricingSectionComment;
	}


	public void setPricingSectionComment(String pricingSectionComment) {
		this.pricingSectionComment = pricingSectionComment;
	}


	public String getPricingType() {
		return pricingType;
	}


	public void setPricingType(String pricingType) {
		this.pricingType = pricingType;
	}


	public String getRate() {
		return rate;
	}


	public void setRate(String rate) {
		this.rate = rate;
	}


	public String getRateType() {
		return rateType;
	}


	public void setRateType(String rateType) {
		this.rateType = rateType;
	}




	public String getSpecialInstructions() {
		return specialInstructions;
	}


	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}


	public String getAccessFee() {
		return accessFee;
	}


	public void setAccessFee(String accessFee) {
		this.accessFee = accessFee;
	}




	public ArrayList<SOPartsDTO> getPartsList() {
		return partsList;
	}


	public void setPartsList(ArrayList<SOPartsDTO> partsList) {
		this.partsList = partsList;
	}


	public ArrayList<SODocumentDTO> getDocumentsList() {
		return documentsList;
	}


	public void setDocumentsList(ArrayList<SODocumentDTO> documentsList) {
		this.documentsList = documentsList;
	}


	public String getTermsAndConditions() {
		return termsAndConditions;
	}


	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}


	public boolean isBuyerHasRatedProvider() {
		return buyerHasRatedProvider;
	}


	public void setBuyerHasRatedProvider(boolean buyerHasRatedProvider) {
		this.buyerHasRatedProvider = buyerHasRatedProvider;
	}


	public boolean isProviderHasRatedBuyer() {
		return providerHasRatedBuyer;
	}


	public void setProviderHasRatedBuyer(boolean providerHasRatedBuyer) {
		this.providerHasRatedBuyer = providerHasRatedBuyer;
	}


	public ArrayList<LabelValueBean> getStatusAndDateList() {
		return statusAndDateList;
	}


	public void setStatusAndDateList(ArrayList<LabelValueBean> statusAndDateList) {
		this.statusAndDateList = statusAndDateList;
	}


	/**
	 * @return the rescheduleServiceWindow
	 */
	public String getRescheduleServiceWindow() {
		return rescheduleServiceWindow;
	}


	/**
	 * @param rescheduleServiceWindow the rescheduleServiceWindow to set
	 */
	public void setRescheduleServiceWindow(String rescheduleServiceWindow) {
		this.rescheduleServiceWindow = rescheduleServiceWindow;
	}


	/**
	 * @return the providerResponseId
	 */
	public Integer getProviderResponseId() {
		return providerResponseId;
	}


	/**
	 * @param providerResponseId the providerResponseId to set
	 */
	public void setProviderResponseId(Integer providerResponseId) {
		this.providerResponseId = providerResponseId;
	}

    public Double getFinalPartsPrice() {
    
        return finalPartsPrice;
    }

    public void setFinalPartsPrice(Double finalPartsPrice) {
    
        this.finalPartsPrice = finalPartsPrice;
    }

    public Double getFinalLaborPrice() {
    
        return finalLaborPrice;
    }

    public void setFinalLaborPrice(Double finalLaborPrice) {
    
        this.finalLaborPrice = finalLaborPrice;
    }


	/**
	 * @return the showSubStatusChange
	 */
	public boolean isShowSubStatusChange() {
		return showSubStatusChange;
	}

	/**
	 * @param showSubStatusChange the showSubStatusChange to set
	 */
	public void setShowSubStatusChange(boolean showSubStatusChange) {
		this.showSubStatusChange = showSubStatusChange;
	}

	public String getPleaseVerify() {
		return pleaseVerify;
	}

	public void setPleaseVerify(String pleaseVerify) {
		this.pleaseVerify = pleaseVerify;
	}


	public String getDocumentsInstructions() {
		return documentsInstructions;
	}

	public void setDocumentsInstructions(String documentsInstructions) {
		this.documentsInstructions = documentsInstructions;
	}

	public String getInitialFunds() {
		return initialFunds;
	}

	public void setInitialFunds(String initialFunds) {
		this.initialFunds = initialFunds;
	}

	public String getAdditionalFundsBank() {
		return additionalFundsBank;
	}

	public void setAdditionalFundsBank(String additionalFundsBank) {
		this.additionalFundsBank = additionalFundsBank;
	}

	public String getAdditionalFundsAmount() {
		return additionalFundsAmount;
	}

	public void setAdditionalFundsAmount(String additionalFundsAmount) {
		this.additionalFundsAmount = additionalFundsAmount;
	}

	public String getFinalFunds() {
		return finalFunds;
	}

	public void setFinalFunds(String finalFunds) {
		this.finalFunds = finalFunds;
	}

	public String getTotalAmountDue() {
		return totalAmountDue;
	}

	public void setTotalAmountDue(String totalAmountDue) {
		this.totalAmountDue = totalAmountDue;
	}

	@Override
	public String getTabIdentifier() {
		return OrderConstants.SOW_REVIEW_TAB;
	}

	@Override
	public void validate() {
		clearAllErrors();
		setErrors(new ArrayList<IError>());
		_doWorkFlowValidation();
		if (acceptTermsAndConditions.equals(OrderConstants.SOW_REVIEW_DONT_ACCEPT_TERMS_AND_CONDITIONS)) 
		{
			addError(getTheResourceBundle().getString("Accept_Terms_And_Conditions"), 
					 getTheResourceBundle().getString("Accept_Terms_And_Conditions_Message")
					,OrderConstants.SOW_TAB_WARNING );
		
		}
		if(notVerifiedInsPresent){
			if (!acceptInsuranceCondition) 
			{
				addError(getTheResourceBundle().getString("Accept_Insurance_Conditions"), 
						 getTheResourceBundle().getString("Accept_Insurance_Conditions_Message")
						,OrderConstants.SOW_TAB_WARNING );
			
			}
		}
	}
	public void addError(String fieldId, String errorMsg, String errorType){
		addError(new SOWError(fieldId, errorMsg, errorType));
		
	}

	public String getAcceptTermsAndConditions() {
		return acceptTermsAndConditions;
	}

	public void setAcceptTermsAndConditions(String acceptTermsAndConditions) {
		this.acceptTermsAndConditions = acceptTermsAndConditions;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setMyReviewErrors(ArrayList list){
		super.setErrors(list);
	}

	public String getRoutedResourceName() {
		return routedResourceName;
	}

	public void setRoutedResourceName(String routedResourceName) {
		this.routedResourceName = routedResourceName;
	}

	public ArrayList<SOWSelBuyerRefDTO> getSelByerRefDTO() {
		return selByerRefDTO;
	}

	public void setSelByerRefDTO(ArrayList<SOWSelBuyerRefDTO> selByerRefDTO) {
		this.selByerRefDTO = selByerRefDTO;
	}

	public String getServiceWindowComment() {
		return serviceWindowComment;
	}

	public void setServiceWindowComment(String serviceWindowComment) {
		this.serviceWindowComment = serviceWindowComment;
	}

	public SOContactDTO getLocationPrimaryContact() {
		return locationPrimaryContact;
	}

	public void setLocationPrimaryContact(SOContactDTO locationPrrimaryContact) {
		this.locationPrimaryContact = locationPrrimaryContact;
	}

	public String getBuyerWidget() {
		return buyerWidget;
	}

	public void setBuyerWidget(String buyerWidget) {
		this.buyerWidget = buyerWidget;
	}

	public String getProviderWidget() {
		return providerWidget;
	}

	public void setProviderWidget(String providerWidget) {
		this.providerWidget = providerWidget;
	}

	public String getEndCustomerWidget() {
		return endCustomerWidget;
	}

	public void setEndCustomerWidget(String endCustomerWidget) {
		this.endCustomerWidget = endCustomerWidget;
	}

	public String getLocationWidget() {
		return locationWidget;
	}

	public void setLocationWidget(String locationWidget) {
		this.locationWidget = locationWidget;
	}

	public String getCityStateZip() {
		return cityStateZip;
	}

	public void setCityStateZip(String cityStateZip) {
		this.cityStateZip = cityStateZip;
	}

	public String getTitleWidget() {
		return titleWidget;
	}

	public void setTitleWidget(String titleWidget) {
		this.titleWidget = titleWidget;
	}
	public String getPartsSuppliedBy() {
		return partsSuppliedBy;
	}

	public void setPartsSuppliedBy(String partsSuppliedBy) {
		this.partsSuppliedBy = partsSuppliedBy;
	}

	public String getSpendLimitLabor() {
		return spendLimitLabor;
	}

	public void setSpendLimitLabor(String spendLimitLabor) {
		this.spendLimitLabor = spendLimitLabor;
	}

	public String getSpendLimitParts() {
		return spendLimitParts;
	}

	public void setSpendLimitParts(String spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}

	public String getSpendLimitTotal() {
		return spendLimitTotal;
	}

	public void setSpendLimitTotal(String spendLimitTotal) {
		this.spendLimitTotal = spendLimitTotal;
	}

	public Integer getPartsSupplier() {
		return partsSupplier;
	}

	public void setPartsSupplier(Integer partsSupplier) {
		this.partsSupplier = partsSupplier;
	}

	public Integer getLogodocumentId() {
		return logodocumentId;
	}

	public void setLogodocumentId(Integer logodocumentId) {
		this.logodocumentId = logodocumentId;
	}

	public Integer getTermsAndConditionsId() {
		return termsAndConditionsId;
	}

	public void setTermsAndConditionsId(Integer termsAndConditionsId) {
		this.termsAndConditionsId = termsAndConditionsId;
	}

	public boolean isShowIncreaseSpendLimitButton() {
		return showIncreaseSpendLimitButton;
	}

	public void setShowIncreaseSpendLimitButton(boolean showIncreaseSpendLimitButton) {
		this.showIncreaseSpendLimitButton = showIncreaseSpendLimitButton;
	}

	public String getClaimedByResource() {
		return claimedByResource;
	}

	public void setClaimedByResource(String claimedByResource) {
		this.claimedByResource = claimedByResource;
	}

	public Double getDistanceInMiles() {
		return distanceInMiles;
	}

	public void setDistanceInMiles(Double distanceInMiles) {
		this.distanceInMiles = distanceInMiles;
	}

	public Double getTotalFinalPrice() {
		return totalFinalPrice;
	}

	public void setTotalFinalPrice(Double totalFinalPrice) {
		this.totalFinalPrice = totalFinalPrice;
	}

	public Double getPostingFee() {
		return postingFee;
	}

	public void setPostingFee(Double postingFee) {
		this.postingFee = postingFee;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Double getProviderPayment() {
		return providerPayment;
	}

	public void setProviderPayment(Double providerPayment) {
		this.providerPayment = providerPayment;
	}

	public Double getCancellationFee() {
		return cancellationFee;
	}

	public void setCancellationFee(Double cancellationFee) {
		this.cancellationFee = cancellationFee;
	}

	public String getResolutionComment() {
		return resolutionComment;
	}

	public void setResolutionComment(String resolutionComment) {
		this.resolutionComment = resolutionComment;
	}

	public Double getLaborSpendLimit() {
		return laborSpendLimit;
	}

	public void setLaborSpendLimit(Double laborSpendLimit) {
		this.laborSpendLimit = laborSpendLimit;
	}

	public Double getPartsSpendLimit() {
		return partsSpendLimit;
	}

	public void setPartsSpendLimit(Double partsSpendLimit) {
		this.partsSpendLimit = partsSpendLimit;
	}

	public Double getTotalSpendLimit() {
		return totalSpendLimit;
	}

	public void setTotalSpendLimit(Double totalSpendLimit) {
		this.totalSpendLimit = totalSpendLimit;
	}
	public Integer getMainServiceCategoryId() {
		return mainServiceCategoryId;
	}

	public void setMainServiceCategoryId(Integer mainServiceCategoryId) {
		this.mainServiceCategoryId = mainServiceCategoryId;
	}

	public Long getResponseCount() {
		return responseCount;
	}
	public void setResponseCount(Long responseCount) {
		this.responseCount = responseCount;
	}

	public List<RoutedProvider> getRoutedResources() {
		return routedResources;
	}

	public void setRoutedResources(List<RoutedProvider> routedResources) {
		this.routedResources = routedResources;
	}

	public VendorResource getAcceptedResource() {
		return acceptedResource;
	}

	public void setAcceptedResource(VendorResource acceptedResource) {
		this.acceptedResource = acceptedResource;
	}

	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}

	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}

	public Integer getAcceptedVendorId() {
		return acceptedVendorId;
	}

	public void setAcceptedVendorId(Integer acceptedVendorId) {
		this.acceptedVendorId = acceptedVendorId;
	}

	public String getZip5() {
		return zip5;
	}

	public void setZip5(String zip5) {
		this.zip5 = zip5;
	}

	public String getSystemTimezone() {
		return systemTimezone;
	}

	public void setSystemTimezone(String systemTimezone) {
		this.systemTimezone = systemTimezone;
	}

	public boolean isTryingToPost() {
		return tryingToPost;
	}

	public void setTryingToPost(boolean tryingToPost) {
		this.tryingToPost = tryingToPost;
	}	

	public String getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getParentGroupTitle() {
		return parentGroupTitle;
	}

	public void setParentGroupTitle(String parentGroupTitle) {
		this.parentGroupTitle = parentGroupTitle;
	}

	public String getSortSOandGroupID() {
		return sortSOandGroupID;
	}

	public void setSortSOandGroupID(String sortSOandGroupID) {
		this.sortSOandGroupID = sortSOandGroupID;
	}

	public Double getGroupSpendLimitLabor() {
		return groupSpendLimitLabor;
	}

	public void setGroupSpendLimitLabor(Double groupSpendLimitLabor) {
		this.groupSpendLimitLabor = groupSpendLimitLabor;
	}

	public Double getGroupSpendLimitParts() {
		return groupSpendLimitParts;
	}

	public void setGroupSpendLimitParts(Double groupSpendLimitParts) {
		this.groupSpendLimitParts = groupSpendLimitParts;
	}

	public Double getGroupSpendLimit() {
		return groupSpendLimit;
	}

	public void setGroupSpendLimit(Double groupSpendLimit) {
		this.groupSpendLimit = groupSpendLimit;
	}

	public String getGroupCreatedDateString() {
		return groupCreatedDateString;
	}

	public void setGroupCreatedDateString(String groupCreatedDateString) {
		this.groupCreatedDateString = groupCreatedDateString;
	}

	public AddonServicesDTO getUpsellInfo() {
		return upsellInfo;
	}

	public void setUpsellInfo(AddonServicesDTO upsellInfo) {
		this.upsellInfo = upsellInfo;
	}

	public Double getAddonPrice()
	{
		return addonPrice;
	}

	public void setAddonPrice(Double addonPrice)
	{
		this.addonPrice = addonPrice;
	}	
	public String getEndCustomerPrimaryPhoneNumberWidget() {
		return endCustomerPrimaryPhoneNumberWidget;
	}

	public void setEndCustomerPrimaryPhoneNumberWidget(
			String endCustomerPrimaryPhoneNumberWidget) {
		this.endCustomerPrimaryPhoneNumberWidget = endCustomerPrimaryPhoneNumberWidget;
	}

	public String getProviderPrimaryPhoneNumberWidget() {
		return providerPrimaryPhoneNumberWidget;
	}

	public void setProviderPrimaryPhoneNumberWidget(
			String providerPrimaryPhoneNumberWidget) {
		this.providerPrimaryPhoneNumberWidget = providerPrimaryPhoneNumberWidget;
	}

	public String getProviderAltPhoneNumberWidget() {
		return providerAltPhoneNumberWidget;
	}

	public void setProviderAltPhoneNumberWidget(String providerAltPhoneNumberWidget) {
		this.providerAltPhoneNumberWidget = providerAltPhoneNumberWidget;
	}

	public Timestamp getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(Timestamp acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public Timestamp getServiceDate1() {
		return serviceDate1;
	}

	public void setServiceDate1(Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	public Timestamp getServiceDate2() {
		return serviceDate2;
	}

	public void setServiceDate2(Timestamp serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}

	public String getServiceTimeStart() {
		return serviceTimeStart;
	}

	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}

	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}

	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}

	public boolean isClaimable() {
		return claimable;
	}

	public void setClaimable(boolean claimable) {
		this.claimable = claimable;
	}

	public String getProviderAlternatePhoneNumber() {
		return providerAlternatePhoneNumber;
	}

	public void setProviderAlternatePhoneNumber(String providerAlternatePhoneNumber) {
		this.providerAlternatePhoneNumber = providerAlternatePhoneNumber;
	}

	public String getProviderMainPhoneNumber() {
		return providerMainPhoneNumber;
	}

	public void setProviderMainPhoneNumber(String providerMainPhoneNumber) {
		this.providerMainPhoneNumber = providerMainPhoneNumber;
	}

	public String getPartsCount() {
		return partsCount;
	}

	public void setPartsCount(String partsCount) {
		this.partsCount = partsCount;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}	
	
	//JIRA : 7606. Implemented comparable for date sort.
	public int compareTo(ServiceOrderDTO other) {
		if (this.tempSortDate != null && other != null && other.getTempSortDate() != null)
		  return this.tempSortDate.compareTo(other.getTempSortDate());
		else 
		  return 0;
	}

	public Timestamp getTempSortDate() {
		return tempSortDate;
	}

	public void setTempSortDate(Timestamp tempSortDate) {
		this.tempSortDate = tempSortDate;	
	}

	public String getResourceDispatchAddress() {
		return resourceDispatchAddress;
	}

	public void setResourceDispatchAddress(String resourceDispatchAddress) {
		this.resourceDispatchAddress = resourceDispatchAddress;
	}

	public String getPriceModel()
	{
		return priceModel;
	}

	public void setPriceModel(String priceModel)
	{
		this.priceModel = priceModel;
	}

	public Integer getBids() {
		return bids;
	}

	public void setBids(Integer bids) {
		this.bids = bids;
	}

	public Float getCurrentBidPrice() {
		return currentBidPrice;
	}

	public void setCurrentBidPrice(Float currentBidPrice) {
		this.currentBidPrice = currentBidPrice;
	}

	public Float getYourBid() {
		return yourBid;
	}

	public void setYourBid(Float yourBid) {
		this.yourBid = yourBid;
	}

	public Float getLowBid() {
		return lowBid;
	}

	public void setLowBid(Float lowBid) {
		this.lowBid = lowBid;
	}

	public Float getHighBid() {
		return highBid;
	}

	public void setHighBid(Float highBid) {
		this.highBid = highBid;
	}

	public Date getBidExpirationDate()
	{
		return bidExpirationDate;
	}

	public void setBidExpirationDate(Date bidExpirationDate)
	{
		this.bidExpirationDate = bidExpirationDate;
	}	
	

	/**
	 * @return the serviceLocationTimeZone
	 */
	public String getServiceLocationTimeZone() {
		return serviceLocationTimeZone;
	}

	/**
	 * @param serviceLocationTimeZone the serviceLocationTimeZone to set
	 */
	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}

	public Integer getNoteOrQuestionCount() {
		return noteOrQuestionCount;
	}

	public void setNoteOrQuestionCount(Integer noteOrQuestionCount) {
		this.noteOrQuestionCount = noteOrQuestionCount;
	}

	public Integer getBuyerRoleId()
	{
		return buyerRoleId;
	}

	public void setBuyerRoleId(Integer buyerRoleId)
	{
		this.buyerRoleId = buyerRoleId;
	}
	
	public List<ProviderResultVO> getAvailableProviders() {
		return availableProviders;
	}
	public void setAvailableProviders(List<ProviderResultVO> availableProviders) {
		this.availableProviders = availableProviders;
	}

	public Boolean getShowPrices() {
		return showPrices;
	}

	public void setShowPrices(Boolean showPrices) {
		this.showPrices = showPrices;
	}

	public void setShareContactInd(boolean shareContactInd) {
		this.shareContactInd = shareContactInd;
	}

	public boolean isShareContactInd() {
		return shareContactInd;
	}

	public void setPriceModelType(Integer priceModelType) {
		this.priceModelType = priceModelType;
	}

	public Integer getPriceModelType() {
		return priceModelType;
	}

	public Boolean getNotVerifiedInsPresent() {
		return notVerifiedInsPresent;
	}

	public void setNotVerifiedInsPresent(Boolean notVerifiedInsPresent) {
		this.notVerifiedInsPresent = notVerifiedInsPresent;
	}

	public boolean getAcceptInsuranceCondition() {
		return acceptInsuranceCondition;
	}

	public void setAcceptInsuranceCondition(boolean acceptInsuranceCondition) {
		this.acceptInsuranceCondition = acceptInsuranceCondition;
	}

	public void setCommercialLocation(boolean isCommercialLocation) {
		this.isCommercialLocation = isCommercialLocation;
	}

	public boolean isCommercialLocation() {
		return isCommercialLocation;
	}

	public void setNewNoteOrQuestionCount(Integer newNoteOrQuestionCount) {
		this.newNoteOrQuestionCount = newNoteOrQuestionCount;
	}

	public Integer getNewNoteOrQuestionCount() {
		return newNoteOrQuestionCount;
	}

	public void setNewBidCount(Integer newBidCount) {
		this.newBidCount = newBidCount;
	}

	public Integer getNewBidCount() {
		return newBidCount;
	}

	public void setLocationForDirections(String locationForDirections) {
		this.locationForDirections = locationForDirections;
	}

	public String getLocationForDirections() {
		return locationForDirections;
	}

	public void setAllowBidOrders(Boolean allowBidOrders) {
		this.allowBidOrders = allowBidOrders;
	}

	public Boolean getAllowBidOrders() {
		return allowBidOrders;
	}

	public Timestamp getBidEarliestStartDate()
	{
		return bidEarliestStartDate;
	}

	public void setBidEarliestStartDate(Timestamp bidEarliestStartDate)
	{
		this.bidEarliestStartDate = bidEarliestStartDate;
	}

	public Timestamp getBidLatestEndDate()
	{
		return bidLatestEndDate;
	}

	public void setBidLatestEndDate(Timestamp bidLatestEndDate)
	{
		this.bidLatestEndDate = bidLatestEndDate;
	}
	public Boolean getInsPresent() {
		return insPresent;
	}

	public void setInsPresent(Boolean insPresent) {
		this.insPresent = insPresent;
	}

	public Boolean getAllowSealedBidOrders() {
		return allowSealedBidOrders;
	}

	public void setAllowSealedBidOrders(Boolean allowSealedBidOrders) {
		this.allowSealedBidOrders = allowSealedBidOrders;
	}

	public List<ProviderResultVO> getRoutedResourcesForFirm() {
		return routedResourcesForFirm;
	}

	public void setRoutedResourcesForFirm(
			List<ProviderResultVO> routedResourcesForFirm) {
		this.routedResourcesForFirm = routedResourcesForFirm;
	}

	public boolean isCarSO() {
		return carSO;
	}

	public void setCarSO(boolean carSO) {
		this.carSO = carSO;
	}

	public List<ProviderResultVO> getRoutedProvExceptCounterOffer() {
		return routedProvExceptCounterOffer;
	}

	public void setRoutedProvExceptCounterOffer(
			List<ProviderResultVO> routedProvExceptCounterOffer) {
		this.routedProvExceptCounterOffer = routedProvExceptCounterOffer;
	}

	public ArrayList<SOTaskDTO> getNonPermitTaskList() {
		return nonPermitTaskList;
	}

	public void setNonPermitTaskList(ArrayList<SOTaskDTO> nonPermitTaskList) {
		this.nonPermitTaskList = nonPermitTaskList;
	}

	public Double getPrePaidPermitPrice() {
		return prePaidPermitPrice;
	}

	public void setPrePaidPermitPrice(Double prePaidPermitPrice) {
		this.prePaidPermitPrice = prePaidPermitPrice;
	}

	public Double getAddOnPermitPrice() {
		return addOnPermitPrice;
	}

	public void setAddOnPermitPrice(Double addOnPermitPrice) {
		this.addOnPermitPrice = addOnPermitPrice;
	}

	public Double getFinalPermitPrice() {
		return finalPermitPrice;
	}

	public boolean isTaskLevelPriceInd() {
		if(OrderConstants.TASK_LEVEL_PRICING.equals(this.priceType))
		{
			taskLevelPriceInd = true;
		}
		/*else if (OrderConstants.SO_LEVEL_PRICING.equals(this.priceType) && this.buyerID.equals("3000")) 
		{
			taskLevelPriceInd = true;
		}*/
		else
		{
			taskLevelPriceInd = false;
		}
		return taskLevelPriceInd;
	}

	public void setTaskLevelPriceInd(boolean taskLevelPriceInd) {
		this.taskLevelPriceInd = taskLevelPriceInd;
	}

	public Double getSoTaskMaxLabor() {
		return soTaskMaxLabor;
	}

	public void setSoTaskMaxLabor(Double soTaskMaxLabor) {
		this.soTaskMaxLabor = soTaskMaxLabor;
	}

	public Double getTotalExclPermits() {
		return totalExclPermits;
	}

	public void setTotalExclPermits(Double totalExclPermits) {
		this.totalExclPermits = totalExclPermits;
	}

	public void setFinalPermitPrice(Double finalPermitPrice) {
		this.finalPermitPrice = finalPermitPrice;
	}

	public Double getSoFinalMaxLabor() {
		return soFinalMaxLabor;
	}

	public void setSoFinalMaxLabor(Double soFinalMaxLabor) {
		this.soFinalMaxLabor = soFinalMaxLabor;
	}

	public double getPermitTaskAddonPrice() {
		return permitTaskAddonPrice;
	}

	public void setPermitTaskAddonPrice(double permitTaskAddonPrice) {
		this.permitTaskAddonPrice = permitTaskAddonPrice;
	}

	public double getNonPermitTaskAddonPrice() {
		return nonPermitTaskAddonPrice;
	}

	public void setNonPermitTaskAddonPrice(double nonPermitTaskAddonPrice) {
		this.nonPermitTaskAddonPrice = nonPermitTaskAddonPrice;
	}

	public Double getServiceFeeDisplay() {
		return serviceFeeDisplay;
	}

	public void setServiceFeeDisplay(Double serviceFeeDisplay) {
		this.serviceFeeDisplay = serviceFeeDisplay;
	}

	public Double getProviderPaymentDisplay() {
		return providerPaymentDisplay;
	}

	public void setProviderPaymentDisplay(Double providerPaymentDisplay) {
		this.providerPaymentDisplay = providerPaymentDisplay;
	}
	
	public List<ProviderInvoicePartsVO> getInvoiceParts() {
		return invoiceParts;
	}
	public String getBuyerComments() {
		return buyerComments;
	}
	public void setBuyerComments(String buyerComments) {
		this.buyerComments = buyerComments;
	}
	
	public void setInvoiceParts(List<ProviderInvoicePartsVO> invoiceParts) {
		this.invoiceParts = invoiceParts;
	}
	 public String getCancelAmount() {
		return cancelAmount;
	}

	public String getBuyerPrice() {
		return buyerPrice;
	}

	public String getBuyerEntryDate() {
		return buyerEntryDate;
	}

	public void setBuyerEntryDate(String buyerEntryDate) {
		this.buyerEntryDate = buyerEntryDate;
	}

	public String getProviderPrice() {
		return providerPrice;
	}

	public void setProviderPrice(String providerPrice) {
		this.providerPrice = providerPrice;
	}

	public String getProviderEntryDate() {
		return providerEntryDate;
	}

	public void setProviderEntryDate(String providerEntryDate) {
		this.providerEntryDate = providerEntryDate;
	}

	public void setCancelAmount(String cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public void setBuyerPrice(String buyerPrice) {
		this.buyerPrice = buyerPrice;
	}
	public String getProviderComments() {
		return providerComments;
	}

	public void setProviderComments(String providerComments) {
		this.providerComments = providerComments;
	}

	
	public String getPendingCancelSubstatus() {
		return pendingCancelSubstatus;
	}

	public void setPendingCancelSubstatus(String pendingCancelSubstatus) {
		this.pendingCancelSubstatus = pendingCancelSubstatus;
	}
	
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public PendingCancelPriceVO getBuyerPendingCancelPrice() {
		return buyerPendingCancelPrice;
	}

	public void setBuyerPendingCancelPrice(
			PendingCancelPriceVO buyerPendingCancelPrice) {
		this.buyerPendingCancelPrice = buyerPendingCancelPrice;
	}

	public PendingCancelPriceVO getProviderPendingCancelPrice() {
		return providerPendingCancelPrice;
	}

	public void setProviderPendingCancelPrice(
			PendingCancelPriceVO providerPendingCancelPrice) {
		this.providerPendingCancelPrice = providerPendingCancelPrice;
	}
	
  
	public String getSoLocationTimeZone() {
		return soLocationTimeZone;
	}

	public void setSoLocationTimeZone(String soLocationTimeZone) {
		this.soLocationTimeZone = soLocationTimeZone;
	}

	public List<SOPartLaborPriceReasonVO> getPartLaborPriceReason() {
		return partLaborPriceReason;
	}

	public void setPartLaborPriceReason(
			List<SOPartLaborPriceReasonVO> partLaborPriceReason) {
		this.partLaborPriceReason = partLaborPriceReason;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public String getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getFirmPhoneNumber() {
		return firmPhoneNumber;
	}

	public void setFirmPhoneNumber(String firmPhoneNumber) {
		this.firmPhoneNumber = firmPhoneNumber;
	}

	public Double getFinalPartsPriceNew() {
		return finalPartsPriceNew;
	}

	public void setFinalPartsPriceNew(Double finalPartsPriceNew) {
		this.finalPartsPriceNew = finalPartsPriceNew;
	}

	public Double getFinalLaborPriceNew() {
		return finalLaborPriceNew;
	}

	public void setFinalLaborPriceNew(Double finalLaborPriceNew) {
		this.finalLaborPriceNew = finalLaborPriceNew;
	}

	public Boolean getNonFundedInd() {
		return nonFundedInd;
	}

	public void setNonFundedInd(Boolean nonFundedInd) {
		this.nonFundedInd = nonFundedInd;
	}

	public Timestamp getServiceDateGMT1() {
		return serviceDateGMT1;
	}

	public void setServiceDateGMT1(Timestamp serviceDateGMT1) {
		this.serviceDateGMT1 = serviceDateGMT1;
	}

	public Timestamp getServiceDateGMT2() {
		return serviceDateGMT2;
	}

	public void setServiceDateGMT2(Timestamp serviceDateGMT2) {
		this.serviceDateGMT2 = serviceDateGMT2;
	}

	public String getServiceTimeStartGMT() {
		return serviceTimeStartGMT;
	}

	public void setServiceTimeStartGMT(String serviceTimeStartGMT) {
		this.serviceTimeStartGMT = serviceTimeStartGMT;
	}

	public String getServiceTimeEndGMT() {
		return serviceTimeEndGMT;
	}

	public void setServiceTimeEndGMT(String serviceTimeEndGMT) {
		this.serviceTimeEndGMT = serviceTimeEndGMT;
	}

	public int getCountOfParts() {
		return countOfParts;
	}

	public void setCountOfParts(int countOfParts) {
		this.countOfParts = countOfParts;
	}

	public int getCountOfInvoice() {
		return countOfInvoice;
	}

	public void setCountOfInvoice(int countOfInvoice) {
		this.countOfInvoice = countOfInvoice;
	}

	public double getTotalEstproviderPayment() {
		return totalEstproviderPayment;
	}

	public void setTotalEstproviderPayment(double totalEstproviderPayment) {
		this.totalEstproviderPayment = totalEstproviderPayment;
	}

	public String getInvoicePartsPricingModel() {
		return invoicePartsPricingModel;
	}

	public void setInvoicePartsPricingModel(String invoicePartsPricingModel) {
		this.invoicePartsPricingModel = invoicePartsPricingModel;
	}

	public String getOriginalSoId() {
		return originalSoId;
	}

	public void setOriginalSoId(String originalSoId) {
		this.originalSoId = originalSoId;
	}

	public boolean isRecallProvider() {
		return recallProvider;
	}

	public void setRecallProvider(boolean recallProvider) {
		this.recallProvider = recallProvider;
	}

    

	public Double getLaborTaxPercentage() {
		return laborTaxPercentage;
	}

	public void setLaborTaxPercentage(Double laborTaxPercentage) {
		this.laborTaxPercentage = laborTaxPercentage;
	}
	
	

	public Double getMaterialsTaxPercentage() {
		return materialsTaxPercentage;
	}

	public void setMaterialsTaxPercentage(Double materialsTaxPercentage) {
		this.materialsTaxPercentage = materialsTaxPercentage;
	}

	public Double getOrginalServiceFee() {
		return orginalServiceFee;
	}

	public void setOrginalServiceFee(Double orginalServiceFee) {
		this.orginalServiceFee = orginalServiceFee;
	}

	public Double getProviderPay() {
		return providerPay;
	}

	public void setProviderPay(Double providerPay) {
		this.providerPay = providerPay;
	}

	public Double getOrginalProviderPay() {
		return orginalProviderPay;
	}

	public void setOrginalProviderPay(Double orginalProviderPay) {
		this.orginalProviderPay = orginalProviderPay;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getOrginalFinalPrice() {
		return orginalFinalPrice;
	}

	public void setOrginalFinalPrice(Double orginalFinalPrice) {
		this.orginalFinalPrice = orginalFinalPrice;
	}

	public List<ServiceDatetimeSlot> getServiceDatetimeSlots() {
		return serviceDatetimeSlots;
	}

	public void setServiceDatetimeSlots(
			List<ServiceDatetimeSlot> serviceDatetimeSlots) {
		this.serviceDatetimeSlots = serviceDatetimeSlots;
	}

	public Integer getServiceDateTypeId() {
		return serviceDateTypeId;
	}

	public void setServiceDateTypeId(Integer serviceDateTypeId) {
		this.serviceDateTypeId = serviceDateTypeId;
	}

	public Double getPartsTaxPercent() {
		return partsTaxPercent;
	}

	public void setPartsTaxPercent(Double partsTaxPercent) {
		this.partsTaxPercent = partsTaxPercent;
	}

	public Double getLaborTaxPercent() {
		return laborTaxPercent;
	}

	public void setLaborTaxPercent(Double laborTaxPercent) {
		this.laborTaxPercent = laborTaxPercent;
	}
    

	
   
	

	 
}
	
