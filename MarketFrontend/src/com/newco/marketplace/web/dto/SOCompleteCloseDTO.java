package com.newco.marketplace.web.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.dto.vo.serviceorder.SOPartLaborPriceReasonVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.CreditCardValidatonUtil;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.utils.SLStringUtils;

public class SOCompleteCloseDTO  extends SOWBaseTabDTO implements OrderConstants{

	private static final long serialVersionUID = -3308264616215570639L;
	private static final Logger logger = Logger.getLogger(SOCompleteCloseDTO.class.getName());
	private static final String CURRENCY_REGULAR_EXPRESSION = "^(((\\d{1,3},)+\\d{3}|\\d+)\\.?\\d{0,2})$|^(\\.(\\d{1,2}))$";
	private static final Double MAX_ADDON_CUSTOMER_CHARGE = 2500.00;
	public static final String INSTALLED = "Installed";
	public static final String RETURNED = "Returned";
	public static final String NOT_INSTALLED = "Not Installed";
	public static final String INDICATOR_PARTS_ADDED = "PARTS_ADDED";

	//SL-21811
	private static final Double RETAIL_MAX_PRICE = Double.parseDouble(PropertiesUtils.getPropertyValue("RETAIL_MAX_PRICE"));
	
	private String resComments;
	private double totalSpLimitOrig;
	private double partSpLimit;
	private double laborSpLimit;
	private double totalSpLimit;
	private double partsTaxPercentage;
	private double laborTaxPercentage;
	private double partsSpLimitTax;
	private double laborSpLimitTax;
	private String finalPartPrice;
	private String finalPartsTaxPrice;
	private String finalLaborPrice;
	private String finalLaborTaxPrice;
	private double finalPrice;
	private String soMaxLabor;
	private double soFinalMaxLabor;
	private double permitTaskAddonPrice;
	private double addonProviderPrice;
	private double providerTaxPaidTotal;
	private double addonCustomerPrice;
	private double endCustomerDueFinalTotal;
	private String soInitialMaxLabor;
	private ArrayList<SOPartsDTO> partList;
	private List<ProviderInvoicePartsVO> invoiceParts = new ArrayList<ProviderInvoicePartsVO>();
	private List<SOPartLaborPriceReasonVO> soPartLaborPriceReason = new ArrayList<SOPartLaborPriceReasonVO>();
	private ArrayList<SOTaskDTO> permitTaskList = new ArrayList<SOTaskDTO>();
	private List<BuyerReferenceVO> buyerRefs = new ArrayList<BuyerReferenceVO>();
	private BuyerReferenceVO buyerRef;
	private int incompleteService;
	private boolean taskLevelPricing;
	private String incompletesoMaxLabor;
	private String incompletefinalPartPrice;
	private String incompleteresComments;
	private String incompletesoInitialMaxLabor;
	private String finalWithoutAddons;
	private String custChargeWithoutAddons;
	private String finalTotal;
	private String custChargeFinal;
	private double prepaidPermitPrice;
	private SOCompleteHSRPartsPanelDTO soCompleteHSRPartsPanelDTO = null;
	//HSR parts panel add parts  
	private SOCompleteHSRPartsPanelServicesDTO soCompleteHSRPartsPanelServicesDTO = null;
	// Add-on Services Panel
	private AddonServicesDTO addonServicesDTO = null;
	private AddonServicesDTO upsellInfo;
	private String buyerID;
	private Integer requiredDocInd;
	private Integer permitInd;
	private boolean AutocloseOn;
	private String permitWarningStatusInd; 
	private boolean validateCC;
	private boolean validateTimeOnsite;


	// HSR SL-17511 changes start
	private String selectReasonForLabor;
	private String otherReasonText; 
	private String nonMaxPriceForLaborCheck;	

	private String selectReasonForParts;
	private String otherReasonTextParts;
	private String nonMaxPriceForPartCheck;
	private List <ProviderResultVO> providerList;
	private Integer acceptedProviderId;
	private boolean isAssignedToFirm;

	private String finalLaborPriceNew; 
	private String finalPartsPriceNew;

	private String coverageType;
	private boolean nonFunded=false;
	//R12_1: Adjudication Phase II
	private String invoicePartsPricingModel;
	private Integer roleId;
	
	//R15_3 PCI II
	private String userName;
	
	//Priority 5B changes
	private boolean modelImage;
	private boolean serialImage;
	private boolean modelError;
	private boolean serialError;
	
	private String createdBy;
	private String modifiedBy;
	private String roleIds;
	private List<DepositionCodeDTO> depositionCodes;
	private String selectedDepositionCode;
	
	//SL-20926
	private AdditionalPaymentVO additionalPaymentVO;

	public boolean isModelError() {
		return modelError;
	}

	public void setModelError(boolean modelError) {
		this.modelError = modelError;
	}

	public boolean isSerialError() {
		return serialError;
	}

	public void setSerialError(boolean serialError) {
		this.serialError = serialError;
	}

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


	public AdditionalPaymentVO getAdditionalPaymentVO() {
		return additionalPaymentVO;
	}

	public void setAdditionalPaymentVO(AdditionalPaymentVO additionalPaymentVO) {
		this.additionalPaymentVO = additionalPaymentVO;
	}


	public boolean isNonFunded() {
		return nonFunded;
	}

	public void setNonFunded(boolean nonFunded) {
		this.nonFunded = nonFunded;
	}

	public String getFinalLaborPriceNew() {
		return finalLaborPriceNew;
	}

	public void setFinalLaborPriceNew(String finalLaborPriceNew) {
		this.finalLaborPriceNew = finalLaborPriceNew;
	}

	public boolean isAssignedToFirm() {
		return isAssignedToFirm;
	}

	public void setAssignedToFirm(boolean isAssignedToFirm) {
		this.isAssignedToFirm = isAssignedToFirm;
	}

	public Integer getAcceptedProviderId() {
		return acceptedProviderId;
	}

	public void setAcceptedProviderId(Integer acceptedProviderId) {
		this.acceptedProviderId = acceptedProviderId;
	}

	public List<ProviderResultVO> getProviderList() {
		return providerList;
	}

	public void setProviderList(List<ProviderResultVO> providerList) {
		this.providerList = providerList;
	}

	public String getSelectReasonForLabor() {
		return selectReasonForLabor;
	}

	public void setSelectReasonForLabor(String selectReasonForLabor) {
		this.selectReasonForLabor = selectReasonForLabor;
	}

	public String getOtherReasonText() {
		return otherReasonText;
	}

	public void setOtherReasonText(String otherReasonText) {
		this.otherReasonText = otherReasonText;
	}


	public String getNonMaxPriceForLaborCheck() {
		return nonMaxPriceForLaborCheck;
	}

	public void setNonMaxPriceForLaborCheck(String nonMaxPriceForLaborCheck) {
		this.nonMaxPriceForLaborCheck = nonMaxPriceForLaborCheck;
	}

	public String getSelectReasonForParts() {
		return selectReasonForParts;
	}

	public void setSelectReasonForParts(String selectReasonForParts) {
		this.selectReasonForParts = selectReasonForParts;
	}

	public String getOtherReasonTextParts() {
		return otherReasonTextParts;
	}

	public void setOtherReasonTextParts(String otherReasonTextParts) {
		this.otherReasonTextParts = otherReasonTextParts;
	}



	// HSR SL-17511 changes end

	public boolean isValidateTimeOnsite() {
		return validateTimeOnsite;
	}

	public void setValidateTimeOnsite(boolean validateTimeOnsite) {
		this.validateTimeOnsite = validateTimeOnsite;
	}

	public double getPrepaidPermitPrice() {
		return prepaidPermitPrice;
	}

	public void setPrepaidPermitPrice(double prepaidPermitPrice) {
		this.prepaidPermitPrice = prepaidPermitPrice;
	}

	public String getResComments() {
		return resComments;
	}

	public void setResComments(String resComments) {
		this.resComments = resComments;
	}

	public String getSoId() {
		return super.getSoId();
	}

	public void setSoId(String soId) {
		super.setSoId(soId);
	}

	public double getTotalSpLimitOrig() {
		return totalSpLimitOrig;
	}

	public void setTotalSpLimitOrig(double totalSpLimitOrig) {
		this.totalSpLimitOrig = totalSpLimitOrig;
	}

	public double getPartSpLimit() {
		return partSpLimit;
	}

	public void setPartSpLimit(double partSpLimit) {
		this.partSpLimit = partSpLimit;
	}

	public double getLaborSpLimit() {
		return laborSpLimit;
	}

	public void setLaborSpLimit(double laborSpLimit) {
		this.laborSpLimit = laborSpLimit;
	}

	public double getTotalSpLimit() {
		return totalSpLimit;
	}

	public void setTotalSpLimit(double totalSpLimit) {
		this.totalSpLimit = totalSpLimit;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}


	public String getIncompletesoInitialMaxLabor() {
		return incompletesoInitialMaxLabor;
	}

	public void setIncompletesoInitialMaxLabor(String incompletesoInitialMaxLabor) {
		this.incompletesoInitialMaxLabor = incompletesoInitialMaxLabor;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("resComments", getResComments())
		.append("soId", getSoId())
		.append("totalSpLimitOrig", getTotalSpLimitOrig())
		.append("partSpLimit", getPartSpLimit())	
		.append("laborSpLimit", getLaborSpLimit())
		.append("totalSpLimit", getTotalSpLimit())
		.append("finalPartPrice", getFinalPartPrice())
		.append("finalLaborPrice", getFinalLaborPrice())
		.append("finalPrice", getFinalPrice())
		.append("entityId", getEntityId())
		.toString();
	}

	public Integer getEntityId() {
		return super.getEntityId();
	}

	public void setEntityId(Integer entityId) {
		super.setEntityId(entityId);
	}

	public ArrayList<SOPartsDTO> getPartList() {
		return partList;
	}

	public void setPartList(ArrayList<SOPartsDTO> partList) {
		this.partList = partList;
	}


	public String getIncompletesoMaxLabor() {
		return incompletesoMaxLabor;
	}

	public void setIncompletesoMaxLabor(String incompletesoMaxLabor) {
		this.incompletesoMaxLabor = incompletesoMaxLabor;
	}

	public String getIncompletefinalPartPrice() {
		return incompletefinalPartPrice;
	}

	public void setIncompletefinalPartPrice(String incompletefinalPartPrice) {
		this.incompletefinalPartPrice = incompletefinalPartPrice;
	}

	public String getIncompleteresComments() {
		return incompleteresComments;
	}

	public void setIncompleteresComments(String incompleteresComments) {
		this.incompleteresComments = incompleteresComments;
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		validateHSRPartsPanel();
		validateAddOnPanel();
		validateGeneralCompletionInfoPanel();
		//validatePartsPanel();
		validateBuyerReferencePanel();
		validateInvoiceParts();

	}

	/**
	 * Description:Method to validate time onsite entries.
	 * @param onsiteVisitVO
	 */
	public void validateTimeOnsiteArrivalDeparture(SOOnsiteVisitVO onsiteVisitVO){
		if(null!=onsiteVisitVO ){
			if(onsiteVisitVO.getArrivalDateInd().intValue() == 0 && onsiteVisitVO.getDepartureDateInd().intValue()== 0){
				addError("TimeOnsite", TIMEONSITE_ARRIVAL_AND_DEPARTURE_DATE_REQUIRED, OrderConstants.SOW_TAB_ERROR);
			}else if(onsiteVisitVO.getArrivalDateInd().intValue()== 0){
				addError("TimeOnsite", TIMEONSITE_ARRIVAL_DATE_REQUIRED, OrderConstants.SOW_TAB_ERROR);
			}else if(onsiteVisitVO.getDepartureDateInd().intValue()== 0){
				addError("TimeOnsite", TIMEONSITE_DEPARTURE_DATE_REQUIRED, OrderConstants.SOW_TAB_ERROR);
			}
		}else{
			addError("TimeOnsite", TIMEONSITE_ARRIVAL_AND_DEPARTURE_DATE_REQUIRED, OrderConstants.SOW_TAB_ERROR);
		}
	}

	public void validateProviderAssignment(){
		if(this.isAssignedToFirm() && (null == this.getAcceptedProviderId() || this.getAcceptedProviderId().intValue()< 0)){
			addError("AssignProvider", ASSIGN_PROVIDER, OrderConstants.SOW_TAB_ERROR);
		}
	}

	public void validateServiceIncomplete()
	{
		validateGeneralCompletionInfoPanel();
	}

	private void validateGeneralCompletionInfoPanel() {

		// Resolution Description is required
		if(null !=buyerID && buyerID.equals("1953") && resComments.length()<=25){
			addError("ResolutionComments", RESOLUTION_DESCR_REQUIRED_OfficeMaxBuyer, OrderConstants.SOW_TAB_ERROR);			
		}else if(resComments.equalsIgnoreCase("")){
			addError("ResolutionComments", RESOLUTION_DESCR_REQUIRED, OrderConstants.SOW_TAB_ERROR);
		}
		//SLT-1237	  		 
		if(resComments!=null && resComments.length()!=0 && CreditCardValidatonUtil.validateCCNumbers(resComments)) {
			addError("ResolutionComments", RESOLUTION_COMMENTS_CREDITCARD_VALIDATION_MSG, OrderConstants.SOW_TAB_ERROR);
		}	

		//HSR changes SL-17511 start

		if(nonMaxPriceForLaborCheck != null && !("").equalsIgnoreCase(nonMaxPriceForLaborCheck))
		{	
			if(nonMaxPriceForLaborCheck.equalsIgnoreCase("true")){
				if(null == selectReasonForLabor || selectReasonForLabor.equals("") || selectReasonForLabor.equals("Select Reason")){
					addError("selectReasonForLabor", SELECT_REASON_REQUIRED_LABOR, OrderConstants.SOW_TAB_ERROR);
				}
				if(selectReasonForLabor.equals("Other") &&  ( null == otherReasonText || ("").equals(otherReasonText))){
					addError("otherReasonLabor", SELECT_REASON_REQUIRED_LABOR_TEXT, OrderConstants.SOW_TAB_ERROR);
				}        	
			}
		}

		if(nonMaxPriceForPartCheck != null && !"".equalsIgnoreCase(nonMaxPriceForPartCheck))
		{	        
			if(nonMaxPriceForPartCheck.equalsIgnoreCase("true")){
				if(null == selectReasonForParts ||  selectReasonForParts.equals("")  || selectReasonForParts.equals("Select Reason")){
					addError("selectReasonForParts", SELECT_REASON_REQUIRED_PARTS, OrderConstants.SOW_TAB_ERROR);
				}
				if(selectReasonForParts.equals("Other") &&  ( null == otherReasonTextParts || "".equals(otherReasonTextParts))){
					addError("otherReasonParts", SELECT_REASON_REQUIRED_PARTS_TEXT, OrderConstants.SOW_TAB_ERROR);
				}        	
			}    
		}    

		//HSR changes SL-17511 end
		// Changed for Validation of final Part Price and final Labor Price for HSR
		if(!"3000".equalsIgnoreCase(buyerID)){ 
			// Final parts price should be a valid amount and should not be greater than order's current final parts price
			if(SLStringUtils.IsParsableNumber(finalPartPrice) == false) {
				addError("FinalPartsPrice", PARTSFINALPRICE_ERROR_IN_FORMAT , OrderConstants.SOW_TAB_ERROR);
			} else {
				Double userEnteredPartsPrice = Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(finalPartPrice)));
				if(userEnteredPartsPrice.compareTo(Double.parseDouble(new DecimalFormat("##.##").format(partSpLimit))) > 0 ) {
					addError("FinalPartsPrice", PARTSFINALPRICE_CANNOT_BE_MORE_THAN_PARTSSPENDLIMIT , OrderConstants.SOW_TAB_ERROR);
				}
				if(userEnteredPartsPrice < 0 ) {
					addError("FinalPartsPrice", PARTSFINALPRICE_CANNOT_BE_LESS_THAN_ZERO , OrderConstants.SOW_TAB_ERROR);
				}

			}
			// Final labor price should be a valid amount and should not be greater than order's current final labor price
			if(SLStringUtils.IsParsableNumber(soMaxLabor) == false) {
				addError("FinalLaborPrice", LABORFINALPRICE_ERROR_IN_FORMAT , OrderConstants.SOW_TAB_ERROR);
			} else {
				Double userEnteredLaborPrice = Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(soMaxLabor)));
				if(userEnteredLaborPrice.compareTo(Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(soInitialMaxLabor)))) > 0) {
					addError("FinalLaborPrice", LABORFINALPRICE_CANNOT_BE_MORE_THAN_LABORSPENDLIMIT , OrderConstants.SOW_TAB_ERROR);
				}
				if(userEnteredLaborPrice < 0 ) {
					addError("FinalLaborPrice", LABORFINALPRICE_CANNOT_BE_LESS_THAN_ZERO , OrderConstants.SOW_TAB_ERROR);
				}

			}
		}else{

			if("true".equalsIgnoreCase(nonMaxPriceForLaborCheck))
			{
				if(SLStringUtils.IsParsableNumber(finalLaborPrice) == false) {
					addError("FinalLaborPrice", LABORFINALPRICE_ERROR_IN_FORMAT , OrderConstants.SOW_TAB_ERROR);
				} else {
					Double userEnteredLaborPrice = Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(finalLaborPrice)));
					if(userEnteredLaborPrice.compareTo(Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(soInitialMaxLabor)))) > 0 ) {
						addError("FinalLaborPrice", LABORFINALPRICE_CANNOT_BE_MORE_THAN_LABORSPENDLIMIT , OrderConstants.SOW_TAB_ERROR);
					}
					if(userEnteredLaborPrice < 0 ) {
						addError("FinalLaborPrice", LABORFINALPRICE_CANNOT_BE_LESS_THAN_ZERO , OrderConstants.SOW_TAB_ERROR);
					}
				}
			}else{
				// Setting the final Labor price to Initial value
				this.finalLaborPrice=this.soInitialMaxLabor;
			}
			if("true".equalsIgnoreCase(nonMaxPriceForPartCheck)){

				if(SLStringUtils.IsParsableNumber(finalPartPrice) == false) {
					addError("FinalPartsPrice", PARTSFINALPRICE_ERROR_IN_FORMAT , OrderConstants.SOW_TAB_ERROR);
				} else {
					Double userEnteredPartsPrice = Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(finalPartPrice)));
					if(userEnteredPartsPrice.compareTo(Double.parseDouble(new DecimalFormat("##.##").format(partSpLimit))) > 0 ) {
						addError("FinalPartsPrice", PARTSFINALPRICE_CANNOT_BE_MORE_THAN_PARTSSPENDLIMIT , OrderConstants.SOW_TAB_ERROR);
					}
					if(userEnteredPartsPrice < 0 ) {
						addError("FinalPartsPrice", PARTSFINALPRICE_CANNOT_BE_LESS_THAN_ZERO , OrderConstants.SOW_TAB_ERROR);
					}

				}
			}else{
				if(this.partSpLimit>0){
					this.finalPartPrice=String.valueOf(this.partSpLimit);
				}else{
					this.finalPartPrice="00.00";
				}


			}
		}
		for(SOTaskDTO task : getPermitTaskList()){
			if(task.getFinalPrice()==null){
				addError("PermitTaskType", "Please enter the final permit price" , OrderConstants.SOW_TAB_ERROR);
				break;
			}
			if(task.getPermitType()==null ||task.getPermitType()<1){
				addError("PermitTaskType", "Please enter permit type" , OrderConstants.SOW_TAB_ERROR);
				break;
			}
		}
	}
	public void validateRequiredDocs() {

		if(requiredDocInd!=null)
		{
			if (requiredDocInd.equals((new Integer(Constants.DocumentTypes.CATEGORY.SIGNED_CUSTOMER_COPY)))) {
				addError("DocumentType", SIGNED_CUSTOMER_COPY_REQUIRED, OrderConstants.SOW_TAB_ERROR);
			}
			if (requiredDocInd.equals((new Integer(Constants.DocumentTypes.CATEGORY.PROOF_OF_PERMIT)))) {
				addError("DocumentType", PROOF_OF_PERMIT_REQUIRED, OrderConstants.SOW_TAB_ERROR);
			}
			if (requiredDocInd.equals((new Integer(Constants.DocumentTypes.CATEGORY.BOTH_PROOF_OF_PERMIT_AND_SIGNED_CUSTOMER_COPY)))) {
				addError("DocumentType", BOTH_PROOF_OF_PERMIT_AND_SIGNED_CUSTOMER_COPY_REQUIRED_REQUIRED, OrderConstants.SOW_TAB_ERROR);
			}
		}  
	}

	public void validateMandatoryDocs(List<String> docsToUpload) {
		StringBuffer docs = new StringBuffer("");
		for(String docTitle: docsToUpload){
			docs.append(docTitle).append(',');
		}

		if (null != docsToUpload && docsToUpload.size() > 0) {
			docs.delete(docs.length()-1, docs.length());
			addError("DocumentType", DOCUMENTS_MANDATORY + docs.toString(),
					OrderConstants.SOW_TAB_ERROR);
		}

	}

	private void validateBuyerReferencePanel() {
		
		//Priority 5B changes
		String modelNum = null;
		String serialNum = null;
		
		
		for (BuyerReferenceVO buyRef : buyerRefs) {
			//Priority 5B changes
			if (InHomeNPSConstants.MODEL.equalsIgnoreCase(buyRef.getReferenceType()) && StringUtils.isNotBlank(buyRef.getReferenceValue())){
				modelNum = buyRef.getReferenceValue().trim();
			}
			else if(InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(buyRef.getReferenceType()) && StringUtils.isNotBlank(buyRef.getReferenceValue())){
				serialNum = buyRef.getReferenceValue().trim();
			}
			
			if(buyRef.getRequired() == null) {
				continue;
			}

			if (buyRef.getRequired() == 1 && StringUtils.isBlank(buyRef.getReferenceValue())) {
				String msg = buyRef.getReferenceType() + " is a required Custom Reference";
				addError("CustomReferenceMissingError", msg, OrderConstants.SOW_TAB_ERROR);
			}
			if(buyRef.getReferenceType()!= null){
				if ("Serial Number".equalsIgnoreCase(buyRef.getReferenceType().trim()) ||"Model Number".equalsIgnoreCase(buyRef.getReferenceType().trim()) ){
					if(StringUtils.isNotBlank(buyRef.getReferenceValue()) && !StringUtils.isAlphanumericSpace(buyRef.getReferenceValue())){
						String msg = "Please enter a valid " + buyRef.getReferenceType();
						addError("CustomReferenceMissingError", msg, OrderConstants.SOW_TAB_ERROR);
					}
				}
			}
		}
		//Priority 5B changes
			if(OrderConstants.INHOME_BUYER.equalsIgnoreCase(buyerID) && StringUtils.isNotBlank(modelNum) 
					&& StringUtils.isNotBlank(serialNum) && modelNum.equals(serialNum)){
				addError("CustomReferenceError", Constants.MODEL_SERIAL_EQUAL_ERROR, OrderConstants.SOW_TAB_ERROR);
			}
	}

	private void validatePartsPanel() {
		if (partList != null) {
			String strMsg = "";
			int partCnt = 0;
			for (int i = 0; i < partList.size(); i++) {
				SOPartsDTO part = partList.get(i);
				partCnt = i + 1;
				if (!("".equalsIgnoreCase(part.getCoreReturnTrackingNumber()))
						&& (part.getCoreReturnCarrierId().intValue() == -1)) {
					strMsg = RETURN_CARRIER_ID_MISSING + partCnt;
					addError("ReturnCarrier", strMsg, OrderConstants.SOW_TAB_ERROR);
				}

				if ((part.getCoreReturnCarrierId().intValue() != -1)
						&& ("".equalsIgnoreCase(part.getCoreReturnTrackingNumber()))) {
					strMsg = RETURN_TRACKING_NO_MISSING + partCnt;
					addError("ReturnCarrierTrackingNumber", strMsg, OrderConstants.SOW_TAB_ERROR);
				}
			}
		}
	}

	private void validateHSRPartsPanel() {
		/*SOCompleteHSRPartsPanelServicesDTO soCompleteHSRPartsPanelServicesDTO = getSoCompleteHSRPartsPanelServicesDTO();
		if(soCompleteHSRPartsPanelServicesDTO == null) {
			return;
		}
		for(SOCompleteHSRPartsPanelDTO hsrPartsRow : soCompleteHSRPartsPanelServicesDTO.getHsrpartsList()) {			
		}*/		

	}

	/*
	 * R12_0 Sprint 5: Validation of invoiceParts Details before completion
	 */
	private void validateInvoiceParts() {
		if(null != getInvoiceParts() && getInvoiceParts().size()>0 )
		{
			Double totalRetailPrice = 0.0;			
			Double retailPricePart = 0.0; 

			StringBuilder combinedErrorMsg = new StringBuilder();
			for (ProviderInvoicePartsVO invoicePart : getInvoiceParts()) {
				if ((!INSTALLED.equalsIgnoreCase(invoicePart.getPartStatus()) && !NOT_INSTALLED
						.equalsIgnoreCase(invoicePart.getPartStatus()))){
					addError(
							"Invoice Parts",
							invoicePart.getPartNo()+" has part status " +invoicePart.getPartStatus()+".You must update the part status to INSTALLED/NOT INSTALLED.<br/>",
							OrderConstants.SOW_TAB_ERROR);
				}				
				else{
					StringBuilder errorMsg = new StringBuilder();
					String defaultError="";

					if(INSTALLED.equalsIgnoreCase(invoicePart.getPartStatus())){

						if(StringUtils.isBlank(invoicePart.getPartCoverage())){		
							defaultError="Part "+invoicePart.getPartNo()+" has no Part Coverage. You must update the Part Coverage.<br/>- ";
							errorMsg.append(defaultError);

						}
						if(StringUtils.isBlank(invoicePart.getSource())){
							defaultError = "Part "+invoicePart.getPartNo()+" has no Part Source.You must update the Part Source.<br/>- ";
							errorMsg.append(defaultError);										
						}
						if(StringUtils.isBlank(invoicePart.getPartNo())){
							defaultError = "Part "+invoicePart.getDescription()+" has no Part Number.You must update the Part Number.<br/>- ";
							errorMsg.append(defaultError);							
						}
						if(StringUtils.isBlank(invoicePart.getDescription())){
							defaultError = "Part "+invoicePart.getPartNo()+" has no Part Name.You must update the Part Name.<br/>- ";
							errorMsg.append(defaultError);							
						}
						if(StringUtils.isBlank(invoicePart.getInvoiceNo())){
							defaultError = "Part "+invoicePart.getPartNo()+" has no Invoice Number.You must update the Invoice Number.<br/>- ";
							errorMsg.append(defaultError);								
						}
						if(null != invoicePart.getUnitCost() && 0.00 >= invoicePart
								.getUnitCost().doubleValue()){

							defaultError = "Part "+invoicePart.getPartNo()+" has Unit Cost $0.00. You must update the Unit Cost.<br/>- ";
							errorMsg.append(defaultError);							
						}
						if(null != invoicePart.getRetailPrice() && 0.00 >= invoicePart
								.getRetailPrice().doubleValue()){

							defaultError = "Part "+invoicePart.getPartNo()+" has Retail Price $0.00.You must update the Retail Price.<br/>- ";
							errorMsg.append(defaultError);
						}
						if(0 == invoicePart.getQty()){
							defaultError = "Part "+invoicePart.getPartNo()+" has Quantity 0.You must update the Quantity.<br/>- ";
							errorMsg.append(defaultError);							
						}
						if(StringUtils.isBlank(invoicePart.getDivisionNumber())){
							defaultError = "Part "+invoicePart.getPartNo()+" has no Division Number.You must update the Division Number.<br/>- ";
							errorMsg.append(defaultError);							
						}
						if(StringUtils.isBlank(invoicePart.getSourceNumber())){							
								defaultError = "Part "+invoicePart.getPartNo()+" has no Source Number.You must update the Source Number.<br/>- ";
								errorMsg.append(defaultError);									
							
						}
						combinedErrorMsg.append(errorMsg.toString());
					}
				}
			}

			String combinedErrorMsgFinal = combinedErrorMsg.toString();
			if(StringUtils.isNotBlank(combinedErrorMsgFinal)){														
				int ind = combinedErrorMsgFinal.lastIndexOf("-");
				combinedErrorMsgFinal = new StringBuilder(combinedErrorMsgFinal).replace(ind, ind+1,"").toString();
				addError(
						"Invoice Parts<br/>",
						combinedErrorMsgFinal.toString(),
						OrderConstants.SOW_TAB_ERROR);
			}

			for(ProviderInvoicePartsVO invoicePart : getInvoiceParts()){
				//To multiply with quantity for part status INSTALLED
				if(invoicePart!=null && invoicePart.getRetailPrice()!=null && invoicePart.getQty()!=0 
						&& StringUtils.isNotBlank(invoicePart.getPartStatus())
						&& INSTALLED.equalsIgnoreCase(invoicePart.getPartStatus())){
					retailPricePart = invoicePart.getRetailPrice().doubleValue();
					retailPricePart = retailPricePart * invoicePart.getQty();
					totalRetailPrice = totalRetailPrice + retailPricePart;															
				}	
			}
			//SL-21811
			if(totalRetailPrice > RETAIL_MAX_PRICE) {
				addError(
						"Invoice Parts",
						"Total Retail Price of Invoice Parts should not exceed $"+String.valueOf(RETAIL_MAX_PRICE),
						OrderConstants.SOW_TAB_ERROR);

			}


		}

	}

	private void validateAddOnPanel() {
		AddonServicesDTO addonDTO = getAddonServicesDTO();
		if(addonDTO == null) {
			return;
		}

		// None of the quantities can be greater than 5
		Double addonSum = 0.0;
		boolean errorPresent = false;
		for(AddonServiceRowDTO row : addonDTO.getAddonServicesList()) {			
			if(row.getQuantity()==null){
				if(row.getMisc() && !row.getSku().equals("99888") && row.getEndCustomerCharge()!= null && row.getEndCustomerCharge() > 0.0){
					addError("Addon Service", "The quantity must not NULL if SKU price is non ZERO.", OrderConstants.SOW_TAB_ERROR);
				}
				row.setQuantity(0);
			}
			if(row.getQuantity().intValue() > 5) {
				addError("Addon Service", "The quantity of additional services per SKU can not be more than 5. Please enter a valid quantity to proceed further.", OrderConstants.SOW_TAB_ERROR);
			}

			Double endCustomerCharge = row.getEndCustomerCharge();
			if(row.getQuantity().intValue()>0 
					&& !row.getSku().equals("99888") 
					&& !row.getMisc() 
					&& (null==endCustomerCharge ||  endCustomerCharge == 0.0)){
				if(!errorPresent){
					addError("Addon Service", "Please enter a valid price for the selected add-on service(s).", OrderConstants.SOW_TAB_ERROR);
					errorPresent = true;
				}
			}

			// If quantity is 1 or greater for a Misc SKU, there must be a description entered
			if(row.getMisc() &&  row.getQuantity() > 0 && !row.getSku().equals("99888")) {
				if(StringUtils.isBlank(row.getDescription())) {
					addError("Addon Service", "The miscellaneous SKU must have a description if the quantity is one or more.", OrderConstants.SOW_TAB_ERROR);
				}
				if(null==endCustomerCharge ||  endCustomerCharge == 0.0) {
					addError("Addon Service", "The miscellaneous SKU must have a price if the quantity is one or more.", OrderConstants.SOW_TAB_ERROR);
				}
			}else if(row.getSku().equals("99888") &&  row.getQuantity() > 0) {
				permitInd=1;
				if(StringUtils.isBlank(row.getDescription())) {
					addError("Addon Service", "The permit SKU must have a permit type if the quantity is one.", OrderConstants.SOW_TAB_ERROR);
				}
				if(null==endCustomerCharge ||  endCustomerCharge == 0.0) {
					addError("Addon Service", "The permit SKU must have a price if the quantity is one.", OrderConstants.SOW_TAB_ERROR);
				}
			}
			if(row.getMisc() && !row.getSku().equals("99888") && row.getQuantity() <= 0 && row.getEndCustomerCharge() != null && row.getEndCustomerCharge() > 0.0){
				addError("Addon Service", "The quantity must not NULL if SKU price is non ZERO.", OrderConstants.SOW_TAB_ERROR);
			}

			if(row.getQuantity() > 0 && row.getEndCustomerCharge() != null && row.getEndCustomerCharge() > 0.0) {
				addonSum += (row.getQuantity() * row.getEndCustomerCharge());
			}
		}

		if(addonSum > MAX_ADDON_CUSTOMER_CHARGE) {
			addError("Addon Service", "The total of additional services can not be more than $"+MAX_ADDON_CUSTOMER_CHARGE+". Please enter valid value to proceed further.", OrderConstants.SOW_TAB_ERROR);
		}

		// If checkbox not checked, return.
		/*if (getEndCustomerDueFinalTotal() <= 0.0) {

		 return;
		 }*/

		//checks to determine whether Customer Collect is needed
		if(!(StringUtils.isBlank(addonDTO.getAddonCheckbox())|| addonDTO.getAddonCheckbox().equals("0") || addonDTO.getAddonCheckbox().equals("false"))
				&& (addonSum>0.0)){
			validateCC=true;           
		}
		double totalPermitPriceDifference=0.00;
		if(!validateCC){
			if(getEndCustomerDueFinalTotal() <= 0.0){//code to handle edit completion issue
				for(SOTaskDTO permitTask : getPermitTaskList()){
					if(null != permitTask.getFinalPrice() && null != permitTask.getSellingPrice()){
						if(permitTask.getFinalPrice()>permitTask.getSellingPrice()){
							validateCC=true;
							totalPermitPriceDifference+=permitTask.getFinalPrice()-permitTask.getSellingPrice();
						}
					}
				}
			}else{
				validateCC=true;
			}	           
		}

		if(validateCC){
			//In case of Edit Completion when EndCustomerDueFinalTotal() becomes 0.00 restoring from Permit difference and Addon Prices
			if(getEndCustomerDueFinalTotal() <= 0.0){
				double custDue=0.00;
				custDue+=totalPermitPriceDifference+addonSum;
				setEndCustomerDueFinalTotal(custDue);
			}
			// No need to go further if quantities are all zero
			boolean quantityChanged = false;
			for(AddonServiceRowDTO dto : addonDTO.getAddonServicesList()) {
				if(dto.getQuantity() > 0) {
					quantityChanged = true;
					break;
				}
			}

			// Validate Check information
			if(null!=getAddonServicesDTO().getPaymentRadioSelection() && getAddonServicesDTO().getPaymentRadioSelection().equals(UPSELL_PAYMENT_TYPE_CHECK)) {
				if(StringUtils.isEmpty(addonDTO.getCheckNumber()) || (addonDTO.getCheckNumber() != null && StringUtils.isBlank(addonDTO.getCheckNumber().trim())) ) {
					addError("CheckNumber", "You must enter a check number in the additional payments section", OrderConstants.SOW_TAB_ERROR);
				} 
				else if(!StringUtils.isNumeric(addonDTO.getCheckNumber()) ||  Long.valueOf(addonDTO.getCheckNumber()) <= 0) {
					addError("CheckNumber", "You must enter a positive nueric value for check number in the additional payments section", OrderConstants.SOW_TAB_ERROR);
				}

				if(addonDTO.getCheckAmount() == null) {
					addError("CheckAmount", "Check amount is invalid in the additional payments section", OrderConstants.SOW_TAB_ERROR);
				}else if(null!=addonDTO.getCheckAmount() && getEndCustomerDueFinalTotal() != addonDTO.getCheckAmount()){
					addError("CheckAmount", "Check amount is invalid in the additional payments section", OrderConstants.SOW_TAB_ERROR);
				}
			}//
			/**This block  is written to validate pre auth amount against the total add on amount where javascript validation fails to validate from FE for credit card
			 * java script failing to validate in the below scenario :
			 *   1) Add two addon& its payment from mobile
			 *   2) Remove one add on from Mobile
			 *   3) Complete So from FE with out editing any add on/ payment details
			 * 
			 */
			else if(null!=getAddonServicesDTO().getPaymentRadioSelection() && 
					getAddonServicesDTO().getPaymentRadioSelection().equals(UPSELL_PAYMENT_TYPE_CREDIT)){
				if(null == addonDTO.getAmtAuthorized()){
					addError("Pre Auth Amount","Pre Authorization amount is invalid in the additional payments section.",OrderConstants.SOW_TAB_ERROR);
				}
				if(null!=addonDTO.getAmtAuthorized() && getEndCustomerDueFinalTotal() != addonDTO.getAmtAuthorized()){
				   addError("Pre Auth Amount","Pre Authorization amount does not match with the Total Amount.",OrderConstants.SOW_TAB_ERROR);
			   }
			}

			// Validate Credit Card information
			if(null!= addonDTO.getPaymentRadioSelection()
				   && addonDTO.getPaymentRadioSelection().equals(UPSELL_PAYMENT_TYPE_CREDIT)) {
				   // validate credit card (mod10 validation)
				   isCreditCardInfoValidate(addonDTO);
			}
		}		
	}

	private void isCreditCardInfoValidate(AddonServicesDTO addonDTO) {
		String cardNumber = addonDTO.getCreditCardNumber();
		logger.info("inside isCreditCardInfoValidate ccnum***" + cardNumber);
		//SL-3768 Commenting and moving to front end
		/*if(addonDTO.getSelectedCreditCardType() == null) {
			addError(getTheResourceBundle().getString("CardTypeId"),
					getTheResourceBundle().getString("CardTypeId_Description_Req_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}*/

		int cardType = addonDTO.getSelectedCreditCardType().intValue();
		logger.info("inside isCreditCardInfoValidate cctype***" + cardType);
		boolean formatError = false;
		//SL-3768 Commenting and moving to front end
		logger.info("inside isCreditCardInfoValidate sow err field ****" + addonDTO.getSowErrFieldId());
		logger.info("inside isCreditCardInfoValidate sow err msg ****" + addonDTO.getSowErrMsg());
		if(StringUtils.isNotBlank(addonDTO.getSowErrMsg()) && StringUtils.isNotBlank(addonDTO.getSowErrFieldId()))
		{
			addError(addonDTO.getSowErrFieldId(),addonDTO.getSowErrMsg(),OrderConstants.SOW_TAB_ERROR);
		}
		
		if(cardNumber != null) {
			//No need to validate credit card number if it is saved and not edited from FE
			logger.info("inside if of payment complete action ****" + addonDTO.getEditOrCancel());
			//SL-3768 Commenting not required now as validation moved to front end
			/*if(StringUtils.isNotBlank(addonDTO.getEditOrCancel()) && 
					(StringUtils.equals("edited",addonDTO.getEditOrCancel())||StringUtils.equals("added",addonDTO.getEditOrCancel()))){
				if (cardNumber != null && cardNumber.length() > 0 ) {
	
					try {
						logger.info("inside if of payment complete action2 ****" + addonDTO.getEditOrCancel());
						Long.parseLong(cardNumber);
					} catch(NumberFormatException nf) {
						formatError = true;
						addError(getTheResourceBundle().getString("CardNumber"),
								getTheResourceBundle().getString("CardNumber_Integer_Digits_Validation"),
								OrderConstants.SOW_TAB_ERROR);
					}
				}
			}*/
			//Added to by pass validation in UIUtils.isCreditCardValid
			/*else if(StringUtils.isNotBlank(addonDTO.getEditOrCancel()) 
					&& StringUtils.equals("edit",addonDTO.getEditOrCancel())){
				formatError = true;
			}*/
			
			//SL-3768 Commenting and moving to front end
			/*
			if(cardType == -1) {
				addError(getTheResourceBundle().getString("CardTypeId"),
						getTheResourceBundle().getString("CardTypeId_Description_Req_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			} else if(StringUtils.isBlank(cardNumber)) {
				addError(getTheResourceBundle().getString("Credit_Card_Number"),
						getTheResourceBundle().getString("CardNumber_Description_Req_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}*/
			//Sears Commercial Card (not allowed at present) 
			/*else if ((cardNumber.length() == 16) && (cardNumber.substring(0, 6).equals(CreditCardConstants.SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS)))
				{
					addError(getTheResourceBundle().getString("CardNumber"),
							getTheResourceBundle().getString(
							"Sears_Commercial_Card_Validation_Msg"),
					OrderConstants.SOW_TAB_ERROR);

				}*/
			//SL-3768 Commenting and moving to front end
			/*
			else 	if((!formatError) && !isProviderCompletePaymentAction && !UIUtils.isCreditCardValid(cardNumber, cardType)) {
				addError(getTheResourceBundle().getString("Credit_Card_Number"),
						getTheResourceBundle().getString("Credit_Card_Number_Not_Valid"),
						OrderConstants.SOW_TAB_ERROR);
			}
			//SL-3768
			else 	if((!formatError) && isProviderCompletePaymentAction && !UIUtils.isMaskedCreditCardValid(cardNumber, cardType)) {
				logger.info("inside if of payment complete action ****" + isProviderCompletePaymentAction);
				addError(getTheResourceBundle().getString("Credit_Card_Number"),
						getTheResourceBundle().getString("Credit_Card_Number_Not_Valid"),
						OrderConstants.SOW_TAB_ERROR);
			}
			*/

			/*			if(StringUtils.isBlank(getNewCreditCard().getCreditCardHolderName())){
				addError(getTheResourceBundle().getString("Credit_Card_Holders_Name"),
						getTheResourceBundle().getString(
								"Credit_Card_Holders_Name_Validation"),
						OrderConstants.SOW_TAB_ERROR);			
			}*/	

			boolean ifSearsWhiteCard = UIUtils.checkForSearsWhiteCard(cardNumber,cardType);
			if (ifSearsWhiteCard) {
				//For Sears White card  expiry date is not required so set it to a default value
				addonDTO.setSelectedMonth(new Integer(12));
				addonDTO.setSelectedYear(new Integer(49));
				//getNewCreditCard().setExpirationYear("49");
				// SearsWhiteCard indicator :For frontend display
				//getNewCreditCard().setIsSearsWhiteCard(true);
			} else {
				if(addonDTO.getSelectedMonth()== null) {
					addError(getTheResourceBundle().getString("Expiration_Month"),
							getTheResourceBundle().getString("Expiration_Month_Validation"),
							OrderConstants.SOW_TAB_ERROR);			
				} else if("-1".equals(addonDTO.getSelectedMonth().toString())) {
					addError(getTheResourceBundle().getString("Expiration_Month"),
							getTheResourceBundle().getString("Expiration_Month_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}

				if(addonDTO.getSelectedYear() == null) {
					addError(getTheResourceBundle().getString("Expiration_Year"),
							getTheResourceBundle().getString("Expiration_Year_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}else if("-1".equals(addonDTO.getSelectedYear().toString())) {
					addError(getTheResourceBundle().getString("Expiration_Year"),
							getTheResourceBundle().getString("Expiration_Year_Validation"),
							OrderConstants.SOW_TAB_ERROR);
				}
				// Check if expiration date entered is in the past. Made by Carlos
				else {				
					if(addonDTO.getSelectedYear() != null &&
							!addonDTO.getSelectedYear().equals("") &&
							addonDTO.getSelectedMonth()!= null &&
							!addonDTO.getSelectedMonth().equals("")) {

						Calendar calendar = Calendar.getInstance();
						int current_year = calendar.get(Calendar.YEAR);
						int current_month = calendar.get(Calendar.MONTH);

						int dropdown_year = addonDTO.getSelectedYear().intValue();
						if(dropdown_year == current_year) {
							int dropdown_month = addonDTO.getSelectedMonth().intValue();
							if(dropdown_month < current_month) {
								addError(getTheResourceBundle().getString("Expiration_Month"),
										getTheResourceBundle().getString("Expiration_Date_Validation"),
										OrderConstants.SOW_TAB_ERROR);
							}

						} else if(dropdown_year < current_year) {
							addError(getTheResourceBundle().getString("Expiration_Year"),
									getTheResourceBundle().getString("Expiration_Date_Validation"),
									OrderConstants.SOW_TAB_ERROR);
						}
					}
				}
			}

			if(StringUtils.isBlank(addonDTO.getPreAuthNumber())) {
				addError("Pre Auth Number",
						"Pre Auth Number is required for Credit Card Payment",
						OrderConstants.SOW_TAB_ERROR);
			}

			if(addonDTO.getAmtAuthorized() == null || addonDTO.getAmtAuthorized().doubleValue() <= 0){
				addError("Pre Auth Amount",
						"Pre Auth Amount is required for Credit Card Payment",
						OrderConstants.SOW_TAB_ERROR);
			}
		}
	}
	/**This method is written to validate pre auth amount against the total add on amount where javascript validation fails to validate from FE
	 * java script failing to validate in the below scenario :
	 *   1) Add two addon& its payment from mobile
	 *   2) Remove one add on from Mobile
	 *   3) Complete So from FE with out editing any add on/ payment details
	 * 
	 */
	private void validateAdditionalPaymentAmt() {
		AddonServicesDTO addonDTO = getAddonServicesDTO();
		if(null!= addonDTO && null!= addonDTO.getAmtAuthorized() && null!= addonDTO.getAddonServicesList() && !addonDTO.getAddonServicesList().isEmpty()){
			BigDecimal amountAuthorized =BigDecimal.ZERO;
			BigDecimal addonAmt = BigDecimal.ZERO;
			List<AddonServiceRowDTO> addonServicesList = addonDTO.getAddonServicesList();
			for(AddonServiceRowDTO addon : addonServicesList ){
				 BigDecimal qty = new BigDecimal(addon.getQuantity());
				 Double  currentAddonAmount = addon.getEndCustomerCharge();
				 BigDecimal currentAddAmount = new BigDecimal(currentAddonAmount);
				 BigDecimal currentAddonAmt = currentAddAmount.multiply(qty);
				 addonAmt = addonAmt.add(currentAddonAmt);
			}
			
			//Rounding AmountAuthorized and Total Add on amount
			amountAuthorized = MoneyUtil.getRoundedMoneyBigDecimal(addonDTO.getAmtAuthorized());
			addonAmt = MoneyUtil.getRoundedMoneyBigDecimal(addonAmt.doubleValue());
			if(!amountAuthorized.equals(addonAmt)){
				addError("Pre Auth Amount","Pre Authorization amount does not match with the Total Amount.",OrderConstants.SOW_TAB_ERROR);
			}
		  
		}
		
	}
	public void setTokenizationErrorMessage() {
		addError("Credit Card Tokenization","Service Order could not be completed",OrderConstants.SOW_TAB_ERROR);
	}
	
	
	//SL-20926
	public void setAddonError(){
		addError("", ADDON_COMPLETE_ERROR, OrderConstants.SOW_TAB_ERROR);
	}

	public String getFinalLaborPrice() {
		return finalLaborPrice;
	}

	public void setFinalLaborPrice(String finalLaborPrice) {
		this.finalLaborPrice = finalLaborPrice;
	}

	public String getFinalPartPrice() {
		return finalPartPrice;
	}

	public void setFinalPartPrice(String finalPartPrice) {
		this.finalPartPrice = finalPartPrice;
	}

	public List<BuyerReferenceVO> getBuyerRefs() {
		return buyerRefs;
	}

	public void setBuyerRefs(List<BuyerReferenceVO> buyerRefs) {
		this.buyerRefs = buyerRefs;
	}

	public BuyerReferenceVO getBuyerRef() {
		return buyerRef;
	}

	public void setBuyerRef(BuyerReferenceVO buyerRef) {
		this.buyerRef = buyerRef;
	}

	public AddonServicesDTO getAddonServicesDTO() {
		return addonServicesDTO;
	}

	public void setAddonServicesDTO(AddonServicesDTO addonServicesDTO) {
		this.addonServicesDTO = addonServicesDTO;
	}

	public SOCompleteHSRPartsPanelServicesDTO getSoCompleteHSRPartsPanelServicesDTO() {
		return soCompleteHSRPartsPanelServicesDTO;
	}

	public void setSoCompleteHSRPartsPanelServicesDTO(
			SOCompleteHSRPartsPanelServicesDTO soCompleteHSRPartsPanelServicesDTO) {
		this.soCompleteHSRPartsPanelServicesDTO = soCompleteHSRPartsPanelServicesDTO;
	}

	public AddonServicesDTO getUpsellInfo() {
		return upsellInfo;
	}

	public void setUpsellInfo(AddonServicesDTO upsellInfo) {
		this.upsellInfo = upsellInfo;
	}

	public int getIncompleteService() {
		return incompleteService;
	}

	public void setIncompleteService(int incompleteService) {
		this.incompleteService = incompleteService;
	}

	public ArrayList<SOTaskDTO> getPermitTaskList() {
		return permitTaskList;
	}

	public void setPermitTaskList(ArrayList<SOTaskDTO> permitTaskList) {
		this.permitTaskList = permitTaskList;
	}

	public boolean isTaskLevelPricing() {
		return taskLevelPricing;
	}

	public void setTaskLevelPricing(boolean taskLevelPricing) {
		this.taskLevelPricing = taskLevelPricing;
	}
	public String getSoMaxLabor() {
		return soMaxLabor;
	}

	public void setSoMaxLabor(String soMaxLabor) {
		this.soMaxLabor = soMaxLabor;
	}

	public String getSoInitialMaxLabor() {
		return soInitialMaxLabor;
	}

	public void setSoInitialMaxLabor(String soInitialMaxLabor) {
		this.soInitialMaxLabor = soInitialMaxLabor;
	}

	public double getSoFinalMaxLabor() {
		return soFinalMaxLabor;
	}

	public void setSoFinalMaxLabor(double soFinalMaxLabor) {
		this.soFinalMaxLabor = soFinalMaxLabor;
	}

	public double getPermitTaskAddonPrice() {
		return permitTaskAddonPrice;
	}

	public void setPermitTaskAddonPrice(double permitTaskAddonPrice) {
		this.permitTaskAddonPrice = permitTaskAddonPrice;
	}

	public String getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(String buyerID) {
		this.buyerID = buyerID;
	}

	public Integer getRequiredDocInd() {
		return requiredDocInd;
	}

	public void setRequiredDocInd(Integer requiredDocInd) {
		this.requiredDocInd = requiredDocInd;
	}

	public Integer getPermitInd() {
		return permitInd;
	}

	public void setPermitInd(Integer permitInd) {
		this.permitInd = permitInd;
	}

	public boolean isAutocloseOn() {
		return AutocloseOn;
	}

	public void setAutocloseOn(boolean autocloseOn) {
		AutocloseOn = autocloseOn;
	}

	public String getFinalWithoutAddons() {
		return finalWithoutAddons;
	}

	public void setFinalWithoutAddons(String finalWithoutAddons) {
		this.finalWithoutAddons = finalWithoutAddons;
	}

	public String getCustChargeWithoutAddons() {
		return custChargeWithoutAddons;
	}

	public void setCustChargeWithoutAddons(String custChargeWithoutAddons) {
		this.custChargeWithoutAddons = custChargeWithoutAddons;
	}

	public String getFinalTotal() {
		return finalTotal;
	}

	public void setFinalTotal(String finalTotal) {
		this.finalTotal = finalTotal;
	}

	public String getCustChargeFinal() {
		return custChargeFinal;
	}

	public void setCustChargeFinal(String custChargeFinal) {
		this.custChargeFinal = custChargeFinal;
	}

	public double getEndCustomerDueFinalTotal() {
		return endCustomerDueFinalTotal;
	}

	public void setEndCustomerDueFinalTotal(double endCustomerDueFinalTotal) {
		this.endCustomerDueFinalTotal = endCustomerDueFinalTotal;
	}

	public double getAddonProviderPrice() {
		return addonProviderPrice;
	}

	public void setAddonProviderPrice(double addonProviderPrice) {
		this.addonProviderPrice = addonProviderPrice;
	}

	public double getAddonCustomerPrice() {
		return addonCustomerPrice;
	}

	public void setAddonCustomerPrice(double addonCustomerPrice) {
		this.addonCustomerPrice = addonCustomerPrice;
	}

	public String getPermitWarningStatusInd() {
		return permitWarningStatusInd;
	}

	public void setPermitWarningStatusInd(String permitWarningStatusInd) {
		this.permitWarningStatusInd = permitWarningStatusInd;
	}

	public boolean isValidateCC() {
		return validateCC;
	}

	public void setValidateCC(boolean validateCC) {
		this.validateCC = validateCC;
	}
	public SOCompleteHSRPartsPanelDTO getSoCompleteHSRPartsPanelDTO() {
		return soCompleteHSRPartsPanelDTO;
	}
	public List<ProviderInvoicePartsVO> getInvoiceParts() {
		return invoiceParts;
	}

	public void setInvoiceParts(List<ProviderInvoicePartsVO> invoiceParts) {
		this.invoiceParts = invoiceParts;
	}
	public void setSoCompleteHSRPartsPanelDTO(
			SOCompleteHSRPartsPanelDTO soCompleteHSRPartsPanelDTO) {
		this.soCompleteHSRPartsPanelDTO = soCompleteHSRPartsPanelDTO;
	}

	public List<SOPartLaborPriceReasonVO> getSoPartLaborPriceReason() {
		return soPartLaborPriceReason;
	}

	public void setSoPartLaborPriceReason(
			List<SOPartLaborPriceReasonVO> soPartLaborPriceReason) {
		this.soPartLaborPriceReason = soPartLaborPriceReason;
	}

	public String getNonMaxPriceForPartCheck() {
		return nonMaxPriceForPartCheck;
	}

	public void setNonMaxPriceForPartCheck(String nonMaxPriceForPartCheck) {
		this.nonMaxPriceForPartCheck = nonMaxPriceForPartCheck;
	}

	public String getFinalPartsPriceNew() {
		return finalPartsPriceNew;
	}

	public void setFinalPartsPriceNew(String finalPartsPriceNew) {
		this.finalPartsPriceNew = finalPartsPriceNew;
	}

	public String getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	public String getInvoicePartsPricingModel() {
		return invoicePartsPricingModel;
	}

	public void setInvoicePartsPricingModel(String invoicePartsPricingModel) {
		this.invoicePartsPricingModel = invoicePartsPricingModel;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public double getPartsTaxPercentage() {
		return partsTaxPercentage;
	}

	public void setPartsTaxPercentage(double partsTaxPercentage) {
		this.partsTaxPercentage = partsTaxPercentage;
	}

	public double getLaborTaxPercentage() {
		return laborTaxPercentage;
	}

	public void setLaborTaxPercentage(double laborTaxPercentage) {
		this.laborTaxPercentage = laborTaxPercentage;
	}

	public double getPartsSpLimitTax() {
		return partsSpLimitTax;
	}

	public void setPartsSpLimitTax(double partsSpLimitTax) {
		this.partsSpLimitTax = partsSpLimitTax;
	}

	public double getLaborSpLimitTax() {
		return laborSpLimitTax;
	}

	public void setLaborSpLimitTax(double laborSpLimitTax) {
		this.laborSpLimitTax = laborSpLimitTax;
	}

	public String getFinalPartsTaxPrice() {
		return finalPartsTaxPrice;
	}

	public void setFinalPartsTaxPrice(String finalPartsTaxPrice) {
		this.finalPartsTaxPrice = finalPartsTaxPrice;
	}

	public String getFinalLaborTaxPrice() {
		return finalLaborTaxPrice;
	}

	public void setFinalLaborTaxPrice(String finalLaborTaxPrice) {
		this.finalLaborTaxPrice = finalLaborTaxPrice;
	}

	public List<DepositionCodeDTO> getDepositionCodes() {
		return depositionCodes;
	}

	public void setDepositionCodes(List<DepositionCodeDTO> depositionCodes) {
		this.depositionCodes = depositionCodes;
	}

	public String getSelectedDepositionCode() {
		return selectedDepositionCode;
	}

	public void setSelectedDepositionCode(String selectedDepositionCode) {
		this.selectedDepositionCode = selectedDepositionCode;
	}

	public double getProviderTaxPaidTotal() {
		return providerTaxPaidTotal;
	}

	public void setProviderTaxPaidTotal(double providerTaxPaidTotal) {
		this.providerTaxPaidTotal = providerTaxPaidTotal;
	}
}
