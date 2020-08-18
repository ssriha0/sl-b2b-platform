package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Parameter;
import com.servicelive.orderfulfillment.domain.type.AssignmentType;
import com.servicelive.orderfulfillment.domain.type.AcceptanceMethod;
import com.servicelive.orderfulfillment.domain.type.RoutingMethod;

@Entity
@Table(name = "so_workflow_controls")
@XmlRootElement()
public class SOWorkflowControls  extends SOElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1996869528449310772L;

	/*
	 * Unfortunately JPA does not allow a PrimaryKey Object to participate in 
	 * Entity Associations. As noted by Christian Bauer & King (pg. 281)
	 */
	@Id @GeneratedValue(generator = "hdrExtraForeignGenerator")
    @org.hibernate.annotations.GenericGenerator(name = "hdrExtraForeignGenerator",
	        strategy = "foreign",
	        parameters = @Parameter(name = "property", value = "serviceOrder")
	    )
	 @Column(name="so_id")
	 @SuppressWarnings("unused")
	 private String soId;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="soWorkflowControls",optional=false)
	private ServiceOrder serviceOrder;
	
	@Column(name = "sealed_bid_ind")
	private Boolean sealedBidIndicator;
	
	@Column(name = "auto_accept_resched_req_ind")
	private Boolean autoAcceptRescheduleRequestIndicator;
	
	@Column(name = "auto_accept_resched_req_cnt")
	private Integer autoAcceptRescheduleRequestCount;
	
	@Column(name = "auto_accept_resched_req_days")
	private Integer autoAcceptRescheduleRequestDays;
	
	@Column(name = "pos_cancellation_indicator")
	private Integer posCancellationIndicator; 
	
	@Column(name ="method_of_acceptance")
	private String methodOfAcceptance;
	
	@Column(name ="problem_type")
	private String problemType;
	
	@Column(name ="method_of_routing")
	private String methodOfRouting;
	
	@Column(name = "tier_route_ind")
	private Boolean tierRouteInd;

	@Column(name = "performance_score_filter_value")
	private Double performanceScore;
	
	@Column(name = "mp_overflow")
	private Boolean mpOverFlow;
	
	@Column(name = "current_tier")
	private Integer currentTier;
	
	@Column(name = "next_tier")
	private Integer nextTier;
	
	@Column(name = "current_tier_routed_date")
	private Date currentTierRoutedDate;
	
	@Column(name = "final_price_labor")
	private BigDecimal finalPriceLabor;
	
	@Column(name = "final_price_parts")
	private BigDecimal finalPriceParts;
	
	
	@Column(name = "nps_inactive_ind")
	private Boolean npsInactiveInd=false;
	
	@Column(name = "non_funded_ind")
	private Boolean nonFundedInd=false;
	
	@Column(name ="invoice_parts_ind")
	private String invoicePartsInd=null;
	
	
	@Column(name ="invoice_parts_pricing_model")
	private String invoicePartsPricingModel=null;
	
	@Column(name ="method_of_closure")
	private String methodOfClosure=null;
	
	@Column(name ="original_so_id")
	private String originalSoId=null;

	//SL-21126
	@Column(name ="primary_so")
	private String primarySO=null;
	
	@Column(name ="warranty_provider_firm")
	private Long warrantyProviderFirm=null;
	
	@Column(name ="labor_price_red_factor")
	private String laborPriceRedFactor =null;
	
	 //Priority 5B changes
    @Column(name ="invalid_model_serial_ind")
    private String invalidModelSerialInd =null;
    
    
    @Column(name = "labor_tax_percentage")
	private BigDecimal laborTaxPercentage;

    
    @Column(name = "materials_tax_percentage")
	private BigDecimal materialsTaxPercentage;
    
    @Column(name = "labor_tax_amount")
	private BigDecimal laborTaxAmount;
    
    @Column(name = "materials_tax_amount")
	private BigDecimal materialsTaxAmount;
    
    public String getInvalidModelSerialInd() {
        return invalidModelSerialInd;
    }
    public void setInvalidModelSerialInd(String invalidModelSerialInd) {
        this.invalidModelSerialInd = invalidModelSerialInd;
    }
	
	public String getInvoicePartsPricingModel() {
		return invoicePartsPricingModel;
	}
	public void setInvoicePartsPricingModel(String invoicePartsPricingModel) {
		this.invoicePartsPricingModel = invoicePartsPricingModel;
	}
	//SL-19291 - Adding variable 'buyerSchedule' to store the reschedule data when buyer reschedules
	@Embedded
	@AttributeOverrides({
	        @AttributeOverride(name="serviceDateTypeId", column=@Column(name = "buyer_preferred_date_type_id")),
	        @AttributeOverride(name="serviceDateGMT1", column=@Column(name = "buyer_preferred_start_date")),
	        @AttributeOverride(name="serviceDateGMT2", column=@Column(name = "buyer_preferred_end_date")),
            @AttributeOverride(name="serviceTimeStartGMT", column=@Column(name = "buyer_preferred_start_time")),
            @AttributeOverride(name="serviceTimeEndGMT", column=@Column(name = "buyer_preferred_end_time"))
	})
	private SOSchedule buyerSchedule;
	
	public Date getCurrentTierRoutedDate() {
		return currentTierRoutedDate;
	}
	public void setCurrentTierRoutedDate(Date currentTierRoutedDate) {
		this.currentTierRoutedDate = currentTierRoutedDate;
	}
	public String getMethodOfAcceptance() {
		return methodOfAcceptance;
	}
	public void setMethodOfAcceptance(String methodOfAcceptance) {
		this.methodOfAcceptance = methodOfAcceptance;
	}
	public String getMethodOfRouting() {
		return methodOfRouting;
	}
	public void setMethodOfRouting(String methodOfRouting) {
		this.methodOfRouting = methodOfRouting;
	}

	public Boolean getSealedBidIndicator() {
		return sealedBidIndicator;
	}
	public Boolean getAutoAcceptRescheduleRequestIndicator() {
		return autoAcceptRescheduleRequestIndicator;
	}
	public Integer getAutoAcceptRescheduleRequestCount() {
		return autoAcceptRescheduleRequestCount;
	}
	public Integer getAutoAcceptRescheduleRequestDays() {
		return autoAcceptRescheduleRequestDays;
	}
	
	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public Boolean getMpOverFlow() {
		return mpOverFlow;
	}
	public void setMpOverFlow(Boolean mpOverFlow) {
		this.mpOverFlow = mpOverFlow;
	}
	public Integer getCurrentTier() {
		return currentTier;
	}
	public void setCurrentTier(Integer currentTier) {
		this.currentTier = currentTier;
	}
	public Integer getNextTier() {
		return nextTier;
	}
	public void setNextTier(Integer nextTier) {
		this.nextTier = nextTier;
	}
	@XmlElement()
	public void setSealedBidIndicator(Boolean sealedBidIndicator) {
		this.sealedBidIndicator = sealedBidIndicator;
	}

	
	@XmlElement()
	public void setAutoAcceptRescheduleRequestIndicator(
			Boolean autoAcceptRescheduleRequestIndicator) {
		this.autoAcceptRescheduleRequestIndicator = autoAcceptRescheduleRequestIndicator;
	}

	@XmlElement()
	public void setAutoAcceptRescheduleRequestCount(
			Integer autoAcceptRescheduleRequestCount) {
		this.autoAcceptRescheduleRequestCount = autoAcceptRescheduleRequestCount;
	}

	
	@XmlElement()
	public void setAutoAcceptRescheduleRequestDays(
			Integer autoAcceptRescheduleRequestDays) {
		this.autoAcceptRescheduleRequestDays = autoAcceptRescheduleRequestDays;
	}

	
	@XmlTransient()
	public void setServiceOrder(ServiceOrder order) {
		this.serviceOrder = order;
	}

	public void setPosCancellationIndicator(Integer posCancellationIndicator) {
		this.posCancellationIndicator = posCancellationIndicator;
	}
	public Integer getPosCancellationIndicator() {
		return posCancellationIndicator;
	}
	public String getProblemType() {
		return problemType;
	}
	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}
	public Boolean getTierRouteInd() {
		return tierRouteInd;
	}
	public void setTierRouteInd(Boolean tierRouteInd) {
		this.tierRouteInd = tierRouteInd;
	}
	public Double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}
	
	public BigDecimal getFinalPriceLabor() {
		return finalPriceLabor;
	}
	public void setFinalPriceLabor(BigDecimal finalPriceLabor) {
		this.finalPriceLabor = finalPriceLabor;
	}
	public BigDecimal getFinalPriceParts() {
		return finalPriceParts;
	}
	public void setFinalPriceParts(BigDecimal finalPriceParts) {
		this.finalPriceParts = finalPriceParts;
	}
	public SOSchedule getBuyerSchedule() {
        return buyerSchedule;
    }
	@XmlElement()
    public void setBuyerSchedule(SOSchedule buyerSchedule) {
        this.buyerSchedule = buyerSchedule;
    }
	public Boolean getNpsInactiveInd() {
		return npsInactiveInd;
	}
	public void setNpsInactiveInd(Boolean npsInactiveInd) {
		this.npsInactiveInd = npsInactiveInd;
	}
	public Boolean getNonFundedInd() {
		return nonFundedInd;
	}
	public void setNonFundedInd(Boolean nonFundedInd) {
		this.nonFundedInd = nonFundedInd;
	}
	public String getInvoicePartsInd() {
		return invoicePartsInd;
	}
	public void setInvoicePartsInd(String invoicePartsInd) {
		this.invoicePartsInd = invoicePartsInd;
	}
	public String getMethodOfClosure() {
		return methodOfClosure;
	}
	public void setMethodOfClosure(String methodOfClosure) {
		this.methodOfClosure = methodOfClosure;
	}
	public String getOriginalSoId() {
		return originalSoId;
	}
	public void setOriginalSoId(String originalSoId) {
		this.originalSoId = originalSoId;
	}
	public Long getWarrantyProviderFirm() {
		return warrantyProviderFirm;
	}
	public void setWarrantyProviderFirm(Long warrantyProviderFirm) {
		this.warrantyProviderFirm = warrantyProviderFirm;
	}
	public String getPrimarySO() {
		return primarySO;
	}
	public void setPrimarySO(String primarySO) {
		this.primarySO = primarySO;
	}
	public String getLaborPriceRedFactor() {
		return laborPriceRedFactor;
	}
	public void setLaborPriceRedFactor(String laborPriceRedFactor) {
		this.laborPriceRedFactor = laborPriceRedFactor;
	}
	public BigDecimal getLaborTaxPercentage() {
		return laborTaxPercentage;
	}
	public void setLaborTaxPercentage(BigDecimal laborTaxPercentage) {
		this.laborTaxPercentage = laborTaxPercentage;
	}
	public BigDecimal getMaterialsTaxPercentage() {
		return materialsTaxPercentage;
	}
	public void setMaterialsTaxPercentage(BigDecimal materialsTaxPercentage) {
		this.materialsTaxPercentage = materialsTaxPercentage;
	}
	public BigDecimal getLaborTaxAmount() {
		return laborTaxAmount;
	}
	public void setLaborTaxAmount(BigDecimal laborTaxAmount) {
		this.laborTaxAmount = laborTaxAmount;
	}
	public BigDecimal getMaterialsTaxAmount() {
		return materialsTaxAmount;
	}
	public void setMaterialsTaxAmount(BigDecimal materialsTaxAmount) {
		this.materialsTaxAmount = materialsTaxAmount;
	}
    
	
	
	
	
}
