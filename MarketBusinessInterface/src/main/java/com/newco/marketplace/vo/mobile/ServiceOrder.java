package com.newco.marketplace.vo.mobile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.SOWorkflowControlsVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ProductDetailVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderContactVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.SODocument;
import com.newco.marketplace.dto.vo.serviceorder.SOPartLaborPriceReasonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAltBuyerContactVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderBrandingInfoVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSurveyResponseVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.serviceorder.SoPriceChangeHistory;
import com.newco.marketplace.dto.vo.serviceorder.SoSchedule;
import com.newco.marketplace.dto.vo.serviceorder.TierRoutedProvider;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.webservices.base.CommonVO;

public class ServiceOrder extends CommonVO {

	private static final long serialVersionUID = 6558490507956928210L;
	private String soId;
	private String assocSoId;
	private Integer partsSupplier;
	private String partsSupplierDesc;
	private Integer assocReasonId;
	private String assocReasonDesc;
	private String creatorUserName;
	private String closerUserName;
	private Timestamp lastStatusChange;
	private Timestamp createdDate;
	private Timestamp initialRoutedDate;
	private Timestamp routedDate;
	private Timestamp expiredDate;
	private Timestamp acceptedDate;
	private Timestamp activatedDate;
	private Timestamp cancelledDate;
	private Timestamp voidedDate;
	private Timestamp completedDate;
	private Timestamp closedDate;
	private Timestamp actArrivalStartDate;
	private Timestamp actArrivalEndDate;
	private Integer pricingTypeId;
	private Double initialPrice;
	private Double upsellAmt = new Double(0.0);
	private Double accessFee;
	private String sowTitle;
	private String sowDs;
	private Integer providerServiceConfirmInd;
	private String providerInstructions;
	private String buyerTermsCond;
	private Integer providerTermsCondResp;
	private Integer providerSOTermsCondInd;
	private String providerTermsCondDate;
	private Timestamp buyerTermsCondDate;
	private Integer buyerSOTermsCondInd;
	private Integer soTermsCondId;
	private String soTermsCondContent;
	private String soProviderTermsCondContent;

	private Double serviceFeePercentage;

	private String lastStatus;
	/**
	 * Resolution Description
	 */
	private String resolutionDs;
	private String modifiedDate;
	private String modifiedBy;
	private Double increaseSpendLimit;
	private Integer wfStateId;
	private String status;
	private Integer primarySkillCatId;
	private Integer wfSubStatusId;
	private String subStatus;
	private Integer acceptedResourceId;
	private Integer acceptedVendorId;
	private String acceptedVendorName;

	private Double spendLimitLabor;
	private Double spendLimitParts;
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private Integer serviceDateTypeId;

	private Timestamp rescheduleServiceDate1;
	private Timestamp rescheduleServiceDate2;
	private String rescheduleServiceTimeStart;
	private String rescheduleServiceTimeEnd;
	private Integer rescheduleServiceDateTypeId;

	private String spendLimitIncrComment;
	private String problemResolutionComment;

	private Long docSizeTotal;

	private String altBuyerContactId;
	private Contact serviceContact;
	private Contact altServiceContact;
	private Contact serviceContactFax;
	private Contact serviceContactAlt;
	private Contact endUserContact;
	private Contact altEndUserContact;
	private Contact buyerSupportContact;
	private Contact altBuyerSupportContact;
	private Contact altEndUserFax;
	private Contact buyerAssociateContact;
	private Contact vendorResourceContact;
	private Contact vendorResourceContactFax;
	private ProviderContactVO serviceproviderContactOnQuickLinks;
	private Buyer buyer;
	private VendorResource acceptedResource;
	private List<ServiceOrderNote> soNotes;
	private List<LookupVO> reasonCodes;
	private String reasonText;
	private String comments;
	private List<LookupVO> customerResponseCodes;
	private List<LookupVO> preCallReasonCodes;
	private ProductDetailVO product;
	
	private boolean spendLimitDecrease;

	public ProductDetailVO getProduct() {
		return product;
	}

	public void setProduct(ProductDetailVO product) {
		this.product = product;
	}

	private SoSchedule schedule;

	public SoSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(SoSchedule schedule) {
		this.schedule = schedule;
	}

	private List<PreCallHistory> scheduleHistory;

	// SL 15642 Creating variable to idendtify newly created SO
	private Boolean newSoIndicator = false;

	/**
	 * @return the newSoIndicator
	 */
	public Boolean getNewSoIndicator() {
		return newSoIndicator;
	}

	/**
	 * @param newSoIndicator
	 *            the newSoIndicator to set
	 */
	public void setNewSoIndicator(Boolean newSoIndicator) {
		this.newSoIndicator = newSoIndicator;
	}

	public List<LookupVO> getPreCallReasonCodes() {
		return preCallReasonCodes;
	}

	public void setPreCallReasonCodes(List<LookupVO> preCallReasonCodes) {
		this.preCallReasonCodes = preCallReasonCodes;
	}

	public List<LookupVO> getCustomerResponseCodes() {
		return customerResponseCodes;
	}

	public void setCustomerResponseCodes(List<LookupVO> customerResponseCodes) {
		this.customerResponseCodes = customerResponseCodes;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	private List<SODocument> soDocuments = Collections.emptyList();
	private List<BuyerReferenceVO> buyerRefs = new ArrayList<BuyerReferenceVO>();
	private List<BuyerReferenceVO> mandatoryBuyerRefs = new ArrayList<BuyerReferenceVO>();
	private List<ServiceOrderTask> tasks = Collections.emptyList();
	// Creating variable to associate sku with service order
	private List<ServiceOrderTask> skus = Collections.emptyList();

	// SL-18007 for so level price history
	private List<SoPriceChangeHistory> soPriceChangeHistoryList = Collections
			.emptyList();

	public List<SoPriceChangeHistory> getSoPriceChangeHistoryList() {
		return soPriceChangeHistoryList;
	}

	public void setSoPriceChangeHistoryList(
			List<SoPriceChangeHistory> soPriceChangeHistoryList) {
		this.soPriceChangeHistoryList = soPriceChangeHistoryList;
	}

	// Creating variable to identify so creation using task or sku
	private boolean skuTaskIndicator;

	public List<ServiceOrderTask> getSkus() {
		return skus;
	}

	public void setSkus(List<ServiceOrderTask> skus) {
		this.skus = skus;
	}

	public List<LookupVO> getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(List<LookupVO> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	boolean taskLevelPricingInd;
	private List<RoutedProvider> routedResources = Collections.emptyList();
	private List<TierRoutedProvider> tierRoutedResources = Collections
			.emptyList();
	private List<Part> parts;
	private SkillNodeVO skill;
	private List<ProviderInvoicePartsVO> invoiceParts;
	private List<SOPartLaborPriceReasonVO> soPartLaborPriceReason;

	// location objects
	private SoLocation serviceLocation;
	private String serviceLocationTimeZone;
	private String actualServiceLocationTimeZone;
	private SoLocation altServiceLocation;
	private SoLocation buyerSupportLocation;
	private SoLocation altBuyerSupportLocation;
	private SoLocation buyerAssociateLocation;
	private SoLocation vendorResourceLocation;
	private Integer lastChngStateId = 0;
	private Double LaborFinalPrice = 0.0;
	private Double PartsFinalPrice = 0.0;
	private Integer loctEditInd = OrderConstants.SO_VIEW_MODE_FLAG;
	private Integer logoDocumentId;
	private String resourceDispatchAddress; // for Google Direction

	private List<ServiceOrderBrandingInfoVO> brandingInfoList = Collections
			.emptyList();
	private List<ServiceOrderCustomRefVO> customRefs = Collections.emptyList();

	private List<VendorResource> providers;
	private List<ServiceOrderAltBuyerContactVO> altBuyerContacts;
	private ServiceOrderAltBuyerContactVO selectedAltBuyerContact;

	private ServiceOrderSurveyResponseVO buyerToProviderResults;
	private ServiceOrderSurveyResponseVO providerToBuyerResults;

	private Integer buyerId;
	private Integer buyerResourceId = 0;
	private Integer buyerContactId = 0;
	private BuyerResource buyerResource;
	private BuyerResource altBuyerResource;
	private Boolean isEditMode;
	private Timestamp problemDate;

	private String priceModel;
	private Integer fundingTypeId;
	private Double postingFee;
	private Double cancellationFee;
	private Double retailPrice;
	private Double retailCancellationFee;

	private Double fundingAmountCC;
	private boolean fundingCCReqd;
	private String groupId;
	private String orignalGroupId;
	private boolean updateSoPriceFlag = true;
	private ServiceOrderPriceVO soPrice;

	private Integer spnId;
	private String externalSoId;
	private Long simpleBuyerAccountId;

	private List<ServiceOrderAddonVO> upsellInfo;

	private String clientStatus;
	private Integer serviceDateTimezoneOffset;

	private Double initialPostedLaborSpendLimit;
	private Double initialPostedPartsSpendLimit;
	private BigDecimal groupLaborSpendLimit;
	private BigDecimal groupPartsSpendLimit;
	private BigDecimal totalGroupPermitPrice;

	private Integer serviceLiveBuckAgrementId; // This is ID from lu_terms_cond
	private Timestamp serviceLiveBuckAgrementAcceptedDt;

	private ProviderDocumentVO acceptedProviderDocument;
	private String accountId;

	private Map<String, String> buyerSpecificFields;

	private Boolean shareContactInd;

	private Double soProjectBalance;
	private Boolean sealedBidInd;

	private Integer carRuleId; // Determine if it SO is routed thru CAR or not
	// new...
	private boolean isUpdate;

	private boolean scopeChange;
	private boolean postFromFE;
	private boolean saveAsDraft;
	private String priceType;

	private PendingCancelPriceVO buyerPendingCancelPrice;
	private PendingCancelPriceVO providerPendingCancelPrice;
	// sl-18007
	private boolean isAutoPost;
	private boolean isRouteFromFE;
	private boolean isSaveAsDraftFE;

	private SOWorkflowControlsVO soWrkFlowControls;

	public SOWorkflowControlsVO getSoWrkFlowControls() {
		return soWrkFlowControls;
	}

	public void setSoWrkFlowControls(SOWorkflowControlsVO soWrkFlowControls) {
		this.soWrkFlowControls = soWrkFlowControls;
	}

	public boolean isSaveAsDraftFE() {
		return isSaveAsDraftFE;
	}

	public void setSaveAsDraftFE(boolean isSaveAsDraftFE) {
		this.isSaveAsDraftFE = isSaveAsDraftFE;
	}

	public boolean isRouteFromFE() {
		return isRouteFromFE;
	}

	public void setRouteFromFE(boolean isRouteFromFE) {
		this.isRouteFromFE = isRouteFromFE;
	}

	public boolean isAutoPost() {
		return isAutoPost;
	}

	public void setAutoPost(boolean isAutoPost) {
		this.isAutoPost = isAutoPost;
	}

	private String assignmentType;

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public boolean isSaveAsDraft() {
		return saveAsDraft;
	}

	public void setSaveAsDraft(boolean saveAsDraft) {
		this.saveAsDraft = saveAsDraft;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public boolean isScopeChange() {
		return scopeChange;
	}

	public void setScopeChange(boolean scopeChange) {
		this.scopeChange = scopeChange;
	}

	public Double getSoProjectBalance() {
		return soProjectBalance;
	}

	public void setSoProjectBalance(Double soProjectBalance) {
		this.soProjectBalance = soProjectBalance;
	}

	public Double getInitialPostedLaborSpendLimit() {
		return initialPostedLaborSpendLimit;
	}

	public void setInitialPostedLaborSpendLimit(
			Double initialPostedLaborSpendLimit) {
		this.initialPostedLaborSpendLimit = initialPostedLaborSpendLimit;
	}

	public Double getInitialPostedPartsSpendLimit() {
		return initialPostedPartsSpendLimit;
	}

	public void setInitialPostedPartsSpendLimit(
			Double initialPostedPartsSpendLimit) {
		this.initialPostedPartsSpendLimit = initialPostedPartsSpendLimit;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Double getLaborFinalPrice() {
		return LaborFinalPrice;
	}

	public void setLaborFinalPrice(Double laborFinalPrice) {
		LaborFinalPrice = laborFinalPrice;
	}

	public Double getPartsFinalPrice() {
		return PartsFinalPrice;
	}

	public void setPartsFinalPrice(Double partsFinalPrice) {
		PartsFinalPrice = partsFinalPrice;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Integer getWfSubStatusId() {
		return wfSubStatusId;
	}

	public void setWfSubStatusId(Integer wfSubStatusId) {
		this.wfSubStatusId = wfSubStatusId;
	}

	public Timestamp getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(Timestamp acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public Double getAccessFee() {
		return accessFee;
	}

	public void setAccessFee(Double accessFee) {
		this.accessFee = accessFee;
	}

	public Timestamp getActArrivalEndDate() {
		return actArrivalEndDate;
	}

	public void setActArrivalEndDate(Timestamp actArrivalEndDate) {
		this.actArrivalEndDate = actArrivalEndDate;
	}

	public Timestamp getActArrivalStartDate() {
		return actArrivalStartDate;
	}

	public void setActArrivalStartDate(Timestamp actArrivalStartDate) {
		this.actArrivalStartDate = actArrivalStartDate;
	}

	public String getBuyerTermsCond() {
		return buyerTermsCond;
	}

	public void setBuyerTermsCond(String buyerTermsCond) {
		this.buyerTermsCond = buyerTermsCond;
	}

	public Timestamp getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Timestamp cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public Timestamp getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
	}

	public String getCloserUserName() {
		return closerUserName;
	}

	public void setCloserUserName(String closerUserName) {
		this.closerUserName = closerUserName;
	}

	public Timestamp getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Timestamp completedDate) {
		this.completedDate = completedDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

	public Double getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public Timestamp getLastStatusChange() {
		return lastStatusChange;
	}

	public void setLastStatusChange(Timestamp lastStatusChange) {
		this.lastStatusChange = lastStatusChange;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getPricingTypeId() {
		return pricingTypeId;
	}

	public void setPricingTypeId(Integer pricingTypeId) {
		this.pricingTypeId = pricingTypeId;
	}

	public Integer getPrimarySkillCatId() {
		return primarySkillCatId;
	}

	public void setPrimarySkillCatId(Integer primarySkillCatId) {
		this.primarySkillCatId = primarySkillCatId;
	}

	public String getProviderInstructions() {
		return providerInstructions;
	}

	public void setProviderInstructions(String providerInstructions) {
		this.providerInstructions = providerInstructions;
	}

	public List<RoutedProvider> getRoutedResources() {
		return routedResources;
	}

	public void setRoutedResources(List<RoutedProvider> routedResources) {

		this.routedResources = routedResources;
	}

	public void addResource(RoutedProvider routedProvider) {

		if (null != routedResources) {
			routedResources.add(routedProvider);
		} else {
			routedResources = new ArrayList<RoutedProvider>();
			routedResources.add(routedProvider);
		}
	}

	public String getProviderTermsCondDate() {
		return providerTermsCondDate;
	}

	public void setProviderTermsCondDate(String providerTermsCondDate) {
		this.providerTermsCondDate = providerTermsCondDate;
	}

	public Integer getProviderTermsCondResp() {
		return providerTermsCondResp;
	}

	public void setProviderTermsCondResp(Integer providerTermsCondResp) {
		this.providerTermsCondResp = providerTermsCondResp;
	}

	public String getResolutionDs() {
		return resolutionDs;
	}

	public void setResolutionDs(String resolutionDs) {
		this.resolutionDs = resolutionDs;
	}

	public Timestamp getRoutedDate() {
		return routedDate;
	}

	public void setRoutedDate(Timestamp routedDate) {
		this.routedDate = routedDate;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getSowDs() {
		return sowDs;
	}

	public void setSowDs(String sowDs) {
		this.sowDs = sowDs;
	}

	public String getSowTitle() {
		return sowTitle;
	}

	public void setSowTitle(String sowTitle) {
		this.sowTitle = sowTitle;
	}

	public List<ServiceOrderTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<ServiceOrderTask> tasks) {
		this.tasks = tasks;
	}

	public void addTask(ServiceOrderTask task) {
		if (null != tasks) {
			tasks.add(task);
		} else {
			tasks = new ArrayList<ServiceOrderTask>();
			tasks.add(task);
		}
	}

	public Integer getWfStateId() {
		return wfStateId;
	}

	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}

	public Timestamp getVoidedDate() {
		return voidedDate;
	}

	public void setVoidedDate(Timestamp voidedDate) {
		this.voidedDate = voidedDate;
	}

	public String getAssocSoId() {
		return assocSoId;
	}

	public void setAssocSoId(String assocSoId) {
		this.assocSoId = assocSoId;
	}

	public Integer getAssocReasonId() {
		return assocReasonId;
	}

	public void setAssocReasonId(Integer assocReasonId) {
		this.assocReasonId = assocReasonId;
	}

	public Integer getProviderServiceConfirmInd() {
		return providerServiceConfirmInd;
	}

	public void setProviderServiceConfirmInd(Integer providerServiceConfirmInd) {
		this.providerServiceConfirmInd = providerServiceConfirmInd;
	}

	/*
	 * public String getIncreaseLimitComment() { return increaseLimitComment; }
	 * public void setIncreaseLimitComment(String increaseLimitComment) {
	 * this.increaseLimitComment = increaseLimitComment; }
	 */

	public SoLocation getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(SoLocation serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public SoLocation getBuyerSupportLocation() {
		return buyerSupportLocation;
	}

	public void setBuyerSupportLocation(SoLocation buyerSupportLocation) {
		this.buyerSupportLocation = buyerSupportLocation;
	}

	public Contact getServiceContact() {
		return serviceContact;
	}

	public void setServiceContact(Contact serviceContact) {
		this.serviceContact = serviceContact;
	}

	public Contact getEndUserContact() {
		return endUserContact;
	}

	public void setEndUserContact(Contact endUserContact) {
		this.endUserContact = endUserContact;
	}

	public Contact getBuyerSupportContact() {
		return buyerSupportContact;
	}

	public void setBuyerSupportContact(Contact buyerSupportContact) {
		this.buyerSupportContact = buyerSupportContact;
	}

	public List<SODocument> getSoDocuments() {
		return soDocuments;
	}

	public void addSoDocument(SODocument soDocument) {
		if (null != soDocuments) {
			soDocuments.add(soDocument);
		} else {
			soDocuments = new ArrayList<SODocument>();
			soDocuments.add(soDocument);
		}
	}

	public void setSoDocuments(List<SODocument> soDocuments) {
		this.soDocuments = soDocuments;
	}

	public VendorResource getAcceptedResource() {
		return acceptedResource;
	}

	public void setAcceptedResource(VendorResource acceptedResource) {
		this.acceptedResource = acceptedResource;
	}

	public Double getSpendLimitLabor() {
		if (spendLimitLabor == null) {
			spendLimitLabor = new Double(0.0);
		}
		return spendLimitLabor;
	}

	public void setSpendLimitLabor(Double spendLimitLabor) {
		this.spendLimitLabor = spendLimitLabor;
	}

	public Double getSpendLimitParts() {
		if (spendLimitParts == null) {
			return 0.0;
		} else {
			return spendLimitParts;
		}
	}

	public void setSpendLimitParts(Double spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
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

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> partList) {
		this.parts = partList;
	}

	public List<ProviderInvoicePartsVO> getInvoiceParts() {
		return invoiceParts;
	}

	public void setInvoiceParts(List<ProviderInvoicePartsVO> invoiceParts) {
		this.invoiceParts = invoiceParts;
	}

	public void addPart(Part part) {
		if (parts != null) {
			parts.add(part);
		} else {
			parts = new ArrayList<Part>();
			parts.add(part);
		}
	}

	public Integer getServiceDateTypeId() {
		return serviceDateTypeId;
	}

	public void setServiceDateTypeId(Integer serviceDateTypeId) {
		this.serviceDateTypeId = serviceDateTypeId;
	}

	public String getSpendLimitIncrComment() {
		return spendLimitIncrComment;
	}

	public void setSpendLimitIncrComment(String spendLimitIncrComment) {
		this.spendLimitIncrComment = spendLimitIncrComment;
	}

	public Long getDocSizeTotal() {
		if (docSizeTotal == null)
			return 0L;
		return docSizeTotal;
	}

	public void setDocSizeTotal(Long docSizeTotal) {
		this.docSizeTotal = docSizeTotal;
	}

	public List<ServiceOrderNote> getSoNotes() {
		return soNotes;
	}

	public void setSoNotes(List<ServiceOrderNote> soNotes) {
		this.soNotes = soNotes;
	}

	public void addNote(ServiceOrderNote soNote) {
		if (soNotes != null) {
			soNotes.add(soNote);
		} else {
			soNotes = new ArrayList<ServiceOrderNote>();
			soNotes.add(soNote);
		}

	}

	public Integer getAcceptedVendorId() {
		return acceptedVendorId;
	}

	public void setAcceptedVendorId(Integer acceptedVendorId) {
		this.acceptedVendorId = acceptedVendorId;
	}

	public String getProblemResolutionComment() {
		return problemResolutionComment;
	}

	public void setProblemResolutionComment(String problemResolutionComment) {
		this.problemResolutionComment = problemResolutionComment;
	}

	public Integer getLastChngStateId() {
		return lastChngStateId;
	}

	public void setLastChngStateId(Integer lastChngStateId) {
		this.lastChngStateId = lastChngStateId;
	}

	public Double getIncreaseSpendLimit() {
		return increaseSpendLimit;
	}

	public void setIncreaseSpendLimit(Double increaseSpendLimit) {
		this.increaseSpendLimit = increaseSpendLimit;
	}

	public Integer getPartsSupplier() {
		return partsSupplier;
	}

	/**
	 * Set these from OrderConstants.SOW_SOW_BUYER_PROVIDES_PART, etc
	 * 
	 * @param partsSupplier
	 */
	public void setPartsSupplier(Integer partsSupplier) {
		this.partsSupplier = partsSupplier;
	}

	public SkillNodeVO getSkill() {
		return skill;
	}

	public void setSkill(SkillNodeVO skill) {
		this.skill = skill == null ? new SkillNodeVO() : skill;
	}

	/**
	 * @return the serviceContactAlt
	 */
	public Contact getServiceContactAlt() {
		return serviceContactAlt;
	}

	/**
	 * @param serviceContactAlt
	 *            the serviceContactAlt to set
	 */
	public void setServiceContactAlt(Contact serviceContactAlt) {
		this.serviceContactAlt = serviceContactAlt;
	}

	/**
	 * @return the soTermsCondId
	 */
	public Integer getSoTermsCondId() {
		return soTermsCondId;
	}

	/**
	 * @param soTermsCondId
	 *            the soTermsCondId to set
	 */
	public void setSoTermsCondId(Integer soTermsCondId) {
		this.soTermsCondId = soTermsCondId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}

	/**
	 * @param subStatus
	 *            the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	/**
	 * @return the soTermsCondContent
	 */
	public String getSoTermsCondContent() {
		return soTermsCondContent;
	}

	/**
	 * @param soTermsCondContent
	 *            the soTermsCondContent to set
	 */
	public void setSoTermsCondContent(String soTermsCondContent) {
		this.soTermsCondContent = soTermsCondContent;
	}

	/**
	 * @return the assocReasonDesc
	 */
	public String getAssocReasonDesc() {
		return assocReasonDesc;
	}

	/**
	 * @param assocReasonDesc
	 *            the assocReasonDesc to set
	 */
	public void setAssocReasonDesc(String assocReasonDesc) {
		this.assocReasonDesc = assocReasonDesc;
	}

	/**
	 * @return the buyerAssociateContact
	 */
	public Contact getBuyerAssociateContact() {
		return buyerAssociateContact;
	}

	/**
	 * @param buyerAssociateContact
	 *            the buyerAssociateContact to set
	 */
	public void setBuyerAssociateContact(Contact buyerAssociateContact) {
		this.buyerAssociateContact = buyerAssociateContact;
	}

	/**
	 * @return the buyerAssociateLocation
	 */
	public SoLocation getBuyerAssociateLocation() {
		return buyerAssociateLocation;
	}

	/**
	 * @param buyerAssociateLocation
	 *            the buyerAssociateLocation to set
	 */
	public void setBuyerAssociateLocation(SoLocation buyerAssociateLocation) {
		this.buyerAssociateLocation = buyerAssociateLocation;
	}

	/**
	 * @return the vendorResourceContact
	 */
	public Contact getVendorResourceContact() {
		return vendorResourceContact;
	}

	/**
	 * @param vendorResourceContact
	 *            the vendorResourceContact to set
	 */
	public void setVendorResourceContact(Contact vendorResourceContact) {
		this.vendorResourceContact = vendorResourceContact;
	}

	/**
	 * @return the vendorResourceLocation
	 */
	public SoLocation getVendorResourceLocation() {
		return vendorResourceLocation;
	}

	/**
	 * @param vendorResourceLocation
	 *            the vendorResourceLocation to set
	 */
	public void setVendorResourceLocation(SoLocation vendorResourceLocation) {
		this.vendorResourceLocation = vendorResourceLocation;
	}

	/**
	 * @return the acceptedResourceId
	 */
	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}

	/**
	 * @param acceptedResourceId
	 *            the acceptedResourceId to set
	 */
	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}

	/**
	 * @return the acceptedVendorName
	 */
	public String getAcceptedVendorName() {
		return acceptedVendorName;
	}

	/**
	 * @param acceptedVendorName
	 *            the acceptedVendorName to set
	 */
	public void setAcceptedVendorName(String acceptedVendorName) {
		this.acceptedVendorName = acceptedVendorName;
	}

	/**
	 * @return the rescheduleServiceDate1
	 */
	public Timestamp getRescheduleServiceDate1() {
		return rescheduleServiceDate1;
	}

	/**
	 * @param rescheduleServiceDate1
	 *            the rescheduleServiceDate1 to set
	 */
	public void setRescheduleServiceDate1(Timestamp rescheduleServiceDate1) {
		this.rescheduleServiceDate1 = rescheduleServiceDate1;
	}

	/**
	 * @return the rescheduleServiceDate2
	 */
	public Timestamp getRescheduleServiceDate2() {
		return rescheduleServiceDate2;
	}

	/**
	 * @param rescheduleServiceDate2
	 *            the rescheduleServiceDate2 to set
	 */
	public void setRescheduleServiceDate2(Timestamp rescheduleServiceDate2) {
		this.rescheduleServiceDate2 = rescheduleServiceDate2;
	}

	/**
	 * @return the rescheduleServiceDateTypeId
	 */
	public Integer getRescheduleServiceDateTypeId() {
		return rescheduleServiceDateTypeId;
	}

	/**
	 * @param rescheduleServiceDateTypeId
	 *            the rescheduleServiceDateTypeId to set
	 */
	public void setRescheduleServiceDateTypeId(
			Integer rescheduleServiceDateTypeId) {
		this.rescheduleServiceDateTypeId = rescheduleServiceDateTypeId;
	}

	/**
	 * @return the rescheduleServiceTimeEnd
	 */
	public String getRescheduleServiceTimeEnd() {
		return rescheduleServiceTimeEnd;
	}

	/**
	 * @param rescheduleServiceTimeEnd
	 *            the rescheduleServiceTimeEnd to set
	 */
	public void setRescheduleServiceTimeEnd(String rescheduleServiceTimeEnd) {
		this.rescheduleServiceTimeEnd = rescheduleServiceTimeEnd;
	}

	/**
	 * @return the rescheduleServiceTimeStart
	 */
	public String getRescheduleServiceTimeStart() {
		return rescheduleServiceTimeStart;
	}

	/**
	 * @param rescheduleServiceTimeStart
	 *            the rescheduleServiceTimeStart to set
	 */
	public void setRescheduleServiceTimeStart(String rescheduleServiceTimeStart) {
		this.rescheduleServiceTimeStart = rescheduleServiceTimeStart;
	}

	public ServiceOrderSurveyResponseVO getBuyerToProviderResults() {
		return buyerToProviderResults;
	}

	public ServiceOrderSurveyResponseVO getProviderToBuyerResults() {
		return providerToBuyerResults;
	}

	public void setProviderToBuyerResults(
			ServiceOrderSurveyResponseVO providerToBuyerResults) {
		this.providerToBuyerResults = providerToBuyerResults;
	}

	public void setBuyerToProviderResults(
			ServiceOrderSurveyResponseVO buyerToProviderResults) {
		this.buyerToProviderResults = buyerToProviderResults;
	}

	public Contact getAltServiceContact() {
		return altServiceContact;
	}

	public void setAltServiceContact(Contact altServiceContact) {
		this.altServiceContact = altServiceContact;
	}

	public Contact getAltBuyerSupportContact() {
		return altBuyerSupportContact;
	}

	public void setAltBuyerSupportContact(Contact altBuyerSupportContact) {
		this.altBuyerSupportContact = altBuyerSupportContact;
	}

	public SoLocation getAltServiceLocation() {
		return altServiceLocation;
	}

	public void setAltServiceLocation(SoLocation altServiceLocation) {
		this.altServiceLocation = altServiceLocation;
	}

	public SoLocation getAltBuyerSupportLocation() {
		return altBuyerSupportLocation;
	}

	public void setAltBuyerSupportLocation(SoLocation altBuyerSupportLocation) {
		this.altBuyerSupportLocation = altBuyerSupportLocation;
	}

	public List<ServiceOrderBrandingInfoVO> getBrandingInfoList() {
		return brandingInfoList;
	}

	public void setBrandingInfoList(
			List<ServiceOrderBrandingInfoVO> brandingInfoList) {
		this.brandingInfoList = brandingInfoList;
	}

	public List<ServiceOrderCustomRefVO> getCustomRefs() {
		return customRefs;
	}

	public void setCustomRefs(List<ServiceOrderCustomRefVO> customRefs) {
		this.customRefs = customRefs;
	}

	public List<VendorResource> getProviders() {
		return providers;
	}

	public void setProviders(List<VendorResource> providers) {
		this.providers = providers;
	}

	public Integer getLoctEditInd() {
		return loctEditInd;
	}

	public void setLoctEditInd(Integer loctEditInd) {
		this.loctEditInd = loctEditInd;
	}

	public List<ServiceOrderAltBuyerContactVO> getAltBuyerContacts() {
		return altBuyerContacts;
	}

	public void setAltBuyerContacts(
			List<ServiceOrderAltBuyerContactVO> altBuyerContacts) {
		this.altBuyerContacts = altBuyerContacts;
	}

	public ServiceOrderAltBuyerContactVO getSelectedAltBuyerContact() {
		return selectedAltBuyerContact;
	}

	public void setSelectedAltBuyerContact(
			ServiceOrderAltBuyerContactVO selectedAltBuyerContact) {
		this.selectedAltBuyerContact = selectedAltBuyerContact;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getLogoDocumentId() {
		return logoDocumentId;
	}

	public void setLogoDocumentId(Integer logoDocumentId) {
		this.logoDocumentId = logoDocumentId;
	}

	public String getPartsSupplierDesc() {
		return partsSupplierDesc;
	}

	public void setPartsSupplierDesc(String partsSupplierDesc) {
		this.partsSupplierDesc = partsSupplierDesc;
	}

	public Contact getAltEndUserContact() {
		return altEndUserContact;
	}

	public void setAltEndUserContact(Contact altEndUserContact) {
		this.altEndUserContact = altEndUserContact;
	}

	public Contact getAltEndUserFax() {
		return altEndUserFax;
	}

	public void setAltEndUserFax(Contact altEndUserFax) {
		this.altEndUserFax = altEndUserFax;
	}

	public String getAltBuyerContactId() {
		return altBuyerContactId;
	}

	public void setAltBuyerContactId(String altBuyerContactId) {
		this.altBuyerContactId = altBuyerContactId;
	}

	/**
	 * @return the expiredDate
	 */
	public Timestamp getExpiredDate() {
		return expiredDate;
	}

	/**
	 * @param expiredDate
	 *            the expiredDate to set
	 */
	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

	/**
	 * @return the activatedDate
	 */
	public Timestamp getActivatedDate() {
		return activatedDate;
	}

	/**
	 * @param activatedDate
	 *            the activatedDate to set
	 */
	public void setActivatedDate(Timestamp activatedDate) {
		this.activatedDate = activatedDate;
	}

	public Contact getServiceContactFax() {
		return serviceContactFax;
	}

	public void setServiceContactFax(Contact serviceContactFax) {
		this.serviceContactFax = serviceContactFax;
	}

	public Boolean getIsEditMode() {
		return isEditMode;
	}

	public void setIsEditMode(Boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	public String getSoProviderTermsCondContent() {
		return soProviderTermsCondContent;
	}

	public void setSoProviderTermsCondContent(String soProviderTermsCondContent) {
		this.soProviderTermsCondContent = soProviderTermsCondContent;
	}

	public Integer getBuyerSOTermsCondInd() {
		return buyerSOTermsCondInd;
	}

	public void setBuyerSOTermsCondInd(Integer buyerSOTermsCondInd) {
		this.buyerSOTermsCondInd = buyerSOTermsCondInd;
	}

	public Integer getProviderSOTermsCondInd() {
		return providerSOTermsCondInd;
	}

	public void setProviderSOTermsCondInd(Integer providerSOTermsCondInd) {
		this.providerSOTermsCondInd = providerSOTermsCondInd;
	}

	public Timestamp getBuyerTermsCondDate() {
		return buyerTermsCondDate;
	}

	public void setBuyerTermsCondDate(Timestamp buyerTermsCondDate) {
		this.buyerTermsCondDate = buyerTermsCondDate;
	}

	public Timestamp getProblemDate() {
		return problemDate;
	}

	public void setProblemDate(Timestamp problemDate) {
		this.problemDate = problemDate;
	}

	public Integer getFundingTypeId() {
		return fundingTypeId;
	}

	public void setFundingTypeId(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	public Double getPostingFee() {
		return postingFee;
	}

	public void setPostingFee(Double postingFee) {
		this.postingFee = postingFee;
	}

	public Double getCancellationFee() {
		return cancellationFee;
	}

	public void setCancellationFee(Double cancellationFee) {
		this.cancellationFee = cancellationFee;
	}

	public Integer getBuyerResourceId() {
		return buyerResourceId;
	}

	public void setBuyerResourceId(Integer buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}

	public BuyerResource getBuyerResource() {
		return buyerResource;
	}

	public void setBuyerResource(BuyerResource buyerResource) {
		this.buyerResource = buyerResource;
	}

	public Integer getBuyerContactId() {
		return buyerContactId;
	}

	public void setBuyerContactId(Integer buyerContactId) {
		this.buyerContactId = buyerContactId;
	}

	public BuyerResource getAltBuyerResource() {
		return altBuyerResource;
	}

	public void setAltBuyerResource(BuyerResource altBuyerResource) {
		this.altBuyerResource = altBuyerResource;
	}

	public Double getRetailCancellationFee() {
		return retailCancellationFee;
	}

	public void setRetailCancellationFee(Double retailCancellationFee) {
		this.retailCancellationFee = retailCancellationFee;
	}

	public String getServiceLocationTimeZone() {
		return serviceLocationTimeZone;
	}

	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}

	/**
	 * Compare a service order and put change data or keys in a map
	 * 
	 * @param Service
	 *            Order update
	 * @return
	 */

	private ServiceOrderTask compareTasks(List<ServiceOrderTask> newTasks,
			List<ServiceOrderTask> oldTasks) {
		int newSkuIndex;
		int oldSkuIndex;
		ServiceOrderTask deliveryTask = null;
		for (Iterator<ServiceOrderTask> oldIt = oldTasks.iterator(); oldIt
				.hasNext();) {
			ServiceOrderTask oldTask = oldIt.next();
			String oldTaskName = oldTask.getTaskName();
			// Remove any task added through frontend from the comparison list
			if (null == oldTask.getAutoInjectedInd()
					|| StringUtils.isBlank(oldTaskName)) {
				oldIt.remove();
				continue;
			}

			for (Iterator<ServiceOrderTask> newIt = newTasks.iterator(); newIt
					.hasNext();) {
				ServiceOrderTask newTask = newIt.next();
				String newTaskName = newTask.getTaskName();
				if (newTaskName.equals(OrderConstants.DELIVERY_TASK_NAME)
						&& oldTaskName
								.equals(OrderConstants.DELIVERY_TASK_NAME)) {
					newIt.remove();
					oldIt.remove();
					break;
				}
				if (newTaskName.equals(OrderConstants.DELIVERY_TASK_NAME)) {
					continue;
				}
				// Compare only the sku and serive type ID(all characters before
				// the first hyphen)
				newSkuIndex = newTaskName.indexOf(SurveyConstants.HYPHEN);
				oldSkuIndex = oldTaskName.indexOf(SurveyConstants.HYPHEN);
				if (newSkuIndex != -1
						&& oldSkuIndex != -1
						&& newTaskName.substring(0, newSkuIndex)
								.equalsIgnoreCase(
										oldTaskName.substring(0, oldSkuIndex))
						&& newTask.getServiceTypeId().equals(
								oldTask.getServiceTypeId())) {
					newIt.remove();
					oldIt.remove();
					break;
				}
			}
		}
		return deliveryTask;
	}

	public Double getFundingAmountCC() {
		return fundingAmountCC;
	}

	public void setFundingAmountCC(Double fundingAmountCC) {
		this.fundingAmountCC = fundingAmountCC;
	}

	public boolean isFundingCCReqd() {
		return fundingCCReqd;
	}

	public void setFundingCCReqd(boolean fundingCCReqd) {
		this.fundingCCReqd = fundingCCReqd;
	}

	public Contact getVendorResourceContactFax() {
		return vendorResourceContactFax;
	}

	public void setVendorResourceContactFax(Contact vendorResourceContactFax) {
		this.vendorResourceContactFax = vendorResourceContactFax;
	}

	public List<BuyerReferenceVO> getBuyerRefs() {
		return buyerRefs;
	}

	public void setBuyerRefs(List<BuyerReferenceVO> buyerRefs) {
		this.buyerRefs = buyerRefs;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;

	}

	public ServiceOrderPriceVO getSoPrice() {
		return soPrice;
	}

	public void setSoPrice(ServiceOrderPriceVO soPrice) {
		this.soPrice = soPrice;
	}

	public String getOrignalGroupId() {
		return orignalGroupId;
	}

	public void setOrignalGroupId(String orignalGroupId) {
		this.orignalGroupId = orignalGroupId;
	}

	public String getExternalSoId() {
		return externalSoId;
	}

	public void setExternalSoId(String externalSoId) {
		this.externalSoId = externalSoId;
	}

	public Double getServiceFeePercentage() {
		return serviceFeePercentage;
	}

	public void setServiceFeePercentage(Double serviceFeePercentage) {
		this.serviceFeePercentage = serviceFeePercentage;
	}

	public List<ServiceOrderAddonVO> getUpsellInfo() {
		return upsellInfo;
	}

	public void setUpsellInfo(List<ServiceOrderAddonVO> upsellInfo) {
		this.upsellInfo = upsellInfo;
	}

	public Double getUpsellAmt() {
		return upsellAmt;
	}

	public void setUpsellAmt(Double upsellAmt) {
		this.upsellAmt = upsellAmt;
	}

	public boolean isUpdateSoPriceFlag() {
		return updateSoPriceFlag;
	}

	public void setUpdateSoPriceFlag(boolean updateSoPriceFlag) {
		this.updateSoPriceFlag = updateSoPriceFlag;
	}

	public ProviderContactVO getServiceproviderContactOnQuickLinks() {
		return serviceproviderContactOnQuickLinks;
	}

	public void setServiceproviderContactOnQuickLinks(
			ProviderContactVO serviceproviderContactOnQuickLinks) {
		this.serviceproviderContactOnQuickLinks = serviceproviderContactOnQuickLinks;
	}

	public String getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}

	public void setContactInfo(List<Contact> contactInfo) {
		if (contactInfo == null)
			return;

		for (Contact contact : contactInfo) {
			int contactTypeId = contact.getContactTypeId() == null ? 0
					: contact.getContactTypeId();
			int contactLocTypeId = contact.getContactLocTypeId() == null ? 0
					: contact.getContactLocTypeId();
			int phoneTypeId = contact.getPhoneTypeId() == null ? 0 : contact
					.getPhoneTypeId();

			if (contactTypeId == 10 && contactLocTypeId == 10
					&& (phoneTypeId == 1 || phoneTypeId == 0))
				setServiceContact(contact);
			else if (contactLocTypeId == 10 && phoneTypeId == 2)
				setServiceContactAlt(contact);
			else if (contactLocTypeId == 10 && phoneTypeId == 3)
				setServiceContactFax(contact);
			else if (contactLocTypeId == 20 && phoneTypeId == 1)
				setEndUserContact(contact);
			else if (contactLocTypeId == 20 && phoneTypeId == 2)
				setAltEndUserContact(contact);
			else if (contactLocTypeId == 20 && phoneTypeId == 3)
				setAltEndUserFax(contact);
			else if (contactTypeId == 10 && contactLocTypeId == 30)
				setBuyerSupportContact(contact);
			else if (contactLocTypeId == 50 && phoneTypeId == 1)
				setVendorResourceContact(contact);
			else if (contactLocTypeId == 50 && phoneTypeId == 3)
				setVendorResourceContactFax(contact);
			else if (contactLocTypeId == 60 && phoneTypeId == 1)
				setBuyerAssociateContact(contact);
		}

		// fix cell phone no
		if (getServiceContactAlt() != null
				&& getServiceContactAlt().getContactTypeId() == 10) {
			if (getServiceContact() != null) {
				getServiceContact().setCellNo(
						getServiceContactAlt().getPhoneNo());
			}
		}

	}

	public void setLocationInfo(List<SoLocation> locationInfo) {
		if (locationInfo == null)
			return;

		for (SoLocation location : locationInfo) {
			int contactLocTypeId = location.getContactLocTypeId();

			if (contactLocTypeId == 10)
				setServiceLocation(location);
			else if (contactLocTypeId == 30)
				setBuyerSupportLocation(location);
			else if (contactLocTypeId == 50)
				setVendorResourceLocation(location);
			else if (contactLocTypeId == 60)
				setBuyerAssociateLocation(location);
		}
	}

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Long getSimpleBuyerAccountId() {
		return simpleBuyerAccountId;
	}

	public void setSimpleBuyerAccountId(Long simpleBuyerAccountId) {
		this.simpleBuyerAccountId = simpleBuyerAccountId;
	}

	public String getResourceDispatchAddress() {
		return resourceDispatchAddress;
	}

	public void setResourceDispatchAddress(String resourceDispatchAddress) {
		this.resourceDispatchAddress = resourceDispatchAddress;

	}

	public Integer getServiceDateTimezoneOffset() {
		return serviceDateTimezoneOffset;
	}

	public void setServiceDateTimezoneOffset(Integer serviceDateTimezoneOffset) {
		this.serviceDateTimezoneOffset = serviceDateTimezoneOffset;
	}

	public Timestamp getInitialRoutedDate() {
		return initialRoutedDate;
	}

	public void setInitialRoutedDate(Timestamp initialRoutedDate) {
		this.initialRoutedDate = initialRoutedDate;
	}

	public String getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}

	/**
	 * @return the serviceLiveBuckAgrementId
	 */
	public Integer getServiceLiveBuckAgrementId() {
		return serviceLiveBuckAgrementId;
	}

	/**
	 * @param serviceLiveBuckAgrementId
	 *            the serviceLiveBuckAgrementId to set
	 */
	public void setServiceLiveBuckAgrementId(Integer serviceLiveBuckAgrementId) {
		this.serviceLiveBuckAgrementId = serviceLiveBuckAgrementId;
	}

	/**
	 * @return the serviceLiveBuckAgrementAcceptedDt
	 */
	public Timestamp getServiceLiveBuckAgrementAcceptedDt() {
		return serviceLiveBuckAgrementAcceptedDt;
	}

	/**
	 * @param serviceLiveBuckAgrementAcceptedDt
	 *            the serviceLiveBuckAgrementAcceptedDt to set
	 */
	public void setServiceLiveBuckAgrementAcceptedDt(
			Timestamp serviceLiveBuckAgrementAcceptedDt) {
		this.serviceLiveBuckAgrementAcceptedDt = serviceLiveBuckAgrementAcceptedDt;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public ProviderDocumentVO getAcceptedProviderDocument() {
		return acceptedProviderDocument;
	}

	public void setAcceptedProviderDocument(
			ProviderDocumentVO acceptedProviderDocument) {
		this.acceptedProviderDocument = acceptedProviderDocument;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Map<String, String> getBuyerSpecificFields() {
		return buyerSpecificFields;
	}

	public void setBuyerSpecificFields(Map<String, String> buyerSpecificFields) {
		this.buyerSpecificFields = buyerSpecificFields;
	}

	public Boolean isShareContactInd() {
		return shareContactInd;
	}

	public void setShareContactInd(Boolean shareContactInd) {
		this.shareContactInd = shareContactInd;
	}

	public void setGroupLaborSpendLimit(BigDecimal groupLaborSpendLimit) {
		this.groupLaborSpendLimit = groupLaborSpendLimit.setScale(2,
				RoundingMode.HALF_EVEN);
	}

	public BigDecimal getGroupLaborSpendLimit() {
		return groupLaborSpendLimit;
	}

	public void setGroupPartsSpendLimit(BigDecimal groupPartsSpendLimit) {
		this.groupPartsSpendLimit = groupPartsSpendLimit.setScale(2,
				RoundingMode.HALF_EVEN);
	}

	public BigDecimal getGroupPartsSpendLimit() {
		return groupPartsSpendLimit;
	}

	public Boolean getSealedBidInd() {
		return sealedBidInd;
	}

	public void setSealedBidInd(Boolean sealedBidInd) {
		this.sealedBidInd = sealedBidInd;
	}

	public Integer getCarRuleId() {
		return carRuleId;
	}

	public void setCarRuleId(Integer carRuleId) {
		this.carRuleId = carRuleId;
	}

	public String getActualServiceLocationTimeZone() {
		return actualServiceLocationTimeZone;
	}

	public BigDecimal getTotalGroupPermitPrice() {
		return totalGroupPermitPrice;
	}

	public void setActualServiceLocationTimeZone(
			String actualServiceLocationTimeZone) {
		this.actualServiceLocationTimeZone = actualServiceLocationTimeZone;
	}

	public List<BuyerReferenceVO> getMandatoryBuyerRefs() {
		return mandatoryBuyerRefs;
	}

	public void setMandatoryBuyerRefs(List<BuyerReferenceVO> mandatoryBuyerRefs) {
		this.mandatoryBuyerRefs = mandatoryBuyerRefs;
	}

	public void setTotalGroupPermitPrice(BigDecimal totalGroupPermitPrice) {
		this.totalGroupPermitPrice = totalGroupPermitPrice;
	}

	public boolean isTaskLevelPricingInd() {
		return taskLevelPricingInd;
	}

	public void setTaskLevelPricingInd(boolean taskLevelPricingInd) {
		this.taskLevelPricingInd = taskLevelPricingInd;
	}

	public boolean isPostFromFE() {
		return postFromFE;
	}

	public void setPostFromFE(boolean postFromFE) {
		this.postFromFE = postFromFE;
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

	public void setSkuTaskIndicator(boolean skuTaskIndicator) {
		this.skuTaskIndicator = skuTaskIndicator;
	}

	public boolean isSkuTaskIndicator() {
		return skuTaskIndicator;
	}

	public List<SOPartLaborPriceReasonVO> getSoPartLaborPriceReason() {
		return soPartLaborPriceReason;
	}

	public void setSoPartLaborPriceReason(
			List<SOPartLaborPriceReasonVO> soPartLaborPriceReason) {
		this.soPartLaborPriceReason = soPartLaborPriceReason;
	}

	public List<ServiceOrderTask> getActiveTasks(List<ServiceOrderTask> soTask) {
		List<ServiceOrderTask> taskList = new ArrayList<ServiceOrderTask>();
		for (ServiceOrderTask task : soTask) {
			if (task.getTaskStatus().equals("ACTIVE")) {
				taskList.add(task);
			}
		}
		return taskList;
	}

	public List<PreCallHistory> getScheduleHistory() {
		return scheduleHistory;
	}

	public void setScheduleHistory(List<PreCallHistory> scheduleHistory) {
		this.scheduleHistory = scheduleHistory;
	}

	public List<TierRoutedProvider> getTierRoutedResources() {
		return tierRoutedResources;
	}

	public void setTierRoutedResources(
			List<TierRoutedProvider> tierRoutedResources) {
		this.tierRoutedResources = tierRoutedResources;
	}

	public boolean isSpendLimitDecrease() {
		return spendLimitDecrease;
	}

	public void setSpendLimitDecrease(boolean spendLimitDecrease) {
		this.spendLimitDecrease = spendLimitDecrease;
	}
	
	

}
