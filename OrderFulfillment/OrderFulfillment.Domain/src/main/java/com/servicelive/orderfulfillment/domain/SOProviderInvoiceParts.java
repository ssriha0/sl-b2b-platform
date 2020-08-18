package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_provider_invoice_parts")
@XmlRootElement()

public class SOProviderInvoiceParts  extends SOChild{

	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -6915755286350489241L;

	/** Number of decimals to retain. Also referred to as "scale". */
	private static int DECIMALS = Currency.getInstance("USD").getDefaultFractionDigits();
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_provider_invoice_parts_id")
	private Integer partsInvoiceId;

	@Column(name = "part_coverage")
	private String partCoverage;
	
	@Column(name = "source")
	private String source;
	
	@Column(name = "part_no")
	private String partNo;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "unit_cost")
	private BigDecimal unitCost;
	
	@Column(name = "retail_price")
	private BigDecimal retailPrice;
	
	@Column(name = "qty")
	private Integer qty;
	
	@Column(name = "est_provider_parts_payment")
	private BigDecimal estProviderPartsPpayment;
	
	@Column(name = "final_price")
	private BigDecimal finalPrice;
	
	@Column(name = "source_non_sears")
	private String sourceNonSears;	
	
	@Column(name="sl_gross_up_val")
	private String slGrossUpValue;
	
	@Column(name="reimbursement_rate")
	private String reimbursementRate;
	
	@Column(name="retail_cost_to_inventory")
	private String retailCostToInventory;
	
	@Column(name="retail_reimbursement")
	private String retailReimbursement;
	
	@Column(name="retail_price_sl_gross_up")
	private String retailPriceSLGrossUp;
	
	@Column(name="retail_sl_gross_up")
	private String retailSLGrossUp;
	
	@Column(name = "division_number")
	private String divisionNumber;
	
	@Column(name = "source_number")
	private String sourceNumber;
	
	@Column(name = "part_url")
	private String partsUrl;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "part_status")
	private String partStatus;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="invoiceParts")
	private SoProviderInvoicePartLocation invoicePartLocation;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "invoiceParts")
    private List<SoProviderInvoicePartDocument> documentList = new ArrayList<SoProviderInvoicePartDocument>();
	
	// additional Columns
	
	@Column(name = "parts_invoice_source")
	private String  partInvoiceSource;
	
	@Column(name = "claim_status")
	private String  claimStatus;
		
	@Column(name = "reimbursement_max")
	private BigDecimal  reimbursementMax;
	
	@Column(name = "provider_margin")
	private BigDecimal  providerMargin;
	
	
	@Column(name = "auto_adjudication_ind")
	private Boolean  autoAdjudicationInd;
	
	//Additional Column added for indicating Source of partNo(LIS)
	@Column(name = "part_source")
	private String partSource;
	
	
	@Column(name="commercial_price")
	private BigDecimal commercialPrice;
	
	


	public BigDecimal getCommercialPrice() {
		return commercialPrice;
	}
	public void setCommercialPrice(BigDecimal commercialPrice) {
		this.commercialPrice = commercialPrice;
	}
	public String getPartInvoiceSource() {
		return partInvoiceSource;
	}
	public void setPartInvoiceSource(String partInvoiceSource) {
		this.partInvoiceSource = partInvoiceSource;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public BigDecimal getReimbursementMax() {
		return reimbursementMax;
	}
	public void setReimbursementMax(BigDecimal reimbursementMax) {
		this.reimbursementMax = reimbursementMax;
	}
	public BigDecimal getProviderMargin() {
		return providerMargin;
	}
	public void setProviderMargin(BigDecimal providerMargin) {
		this.providerMargin = providerMargin;
	}
	public Boolean getAutoAdjudicationInd() {
		return autoAdjudicationInd;
	}
	public void setAutoAdjudicationInd(Boolean autoAdjudicationInd) {
		this.autoAdjudicationInd = autoAdjudicationInd;
	}
	public String getPartsUrl() {
		return partsUrl;
	}
	public void setPartsUrl(String partsUrl) {
		this.partsUrl = partsUrl;
	}

	public String getDivisionNumber() {
		return divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

	public String getSourceNumber() {
		return sourceNumber;
	}

	public void setSourceNumber(String sourceNumber) {
		this.sourceNumber = sourceNumber;
	}
	
	public Integer getPartsInvoiceId() {
		return partsInvoiceId;
	}



	public void setPartsInvoiceId(Integer partsInvoiceId) {
		this.partsInvoiceId = partsInvoiceId;
	}



	public String getPartCoverage() {
		return partCoverage;
	}



	public void setPartCoverage(String partCoverage) {
		this.partCoverage = partCoverage;
	}



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public String getPartNo() {
		return partNo;
	}



	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getInvoiceNo() {
		return invoiceNo;
	}



	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}



	public BigDecimal getUnitCost() {
		return unitCost;
	}



	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}



	public BigDecimal getRetailPrice() {
		return retailPrice;
	}



	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}



	public Integer getQty() {
		return qty;
	}



	public void setQty(Integer qty) {
		this.qty = qty;
	}



	public BigDecimal getEstProviderPartsPpayment() {
		return estProviderPartsPpayment;
	}



	public void setEstProviderPartsPpayment(BigDecimal estProviderPartsPpayment) {
		this.estProviderPartsPpayment = estProviderPartsPpayment;
	}



	public BigDecimal getFinalPrice() {
		return finalPrice;
	}



	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}



	public String getSourceNonSears() {
		return sourceNonSears;
	}



	public void setSourceNonSears(String sourceNonSears) {
		this.sourceNonSears = sourceNonSears;
	}
	public String getSlGrossUpValue() {
		return slGrossUpValue;
	}
	public void setSlGrossUpValue(String slGrossUpValue) {
		this.slGrossUpValue = slGrossUpValue;
	}
	public String getReimbursementRate() {
		return reimbursementRate;
	}
	public void setReimbursementRate(String reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}
	
	

	/**
	 * Custom rounding function used to round up in cases where 
	 * third digit after decimal is 5, returns a BigDecimal
	 * 
	 * @param doubleNumber
	 * @return
	 */
	public static BigDecimal getRoundedMoneyBigDecimal(Double doubleNumber) {
		if (doubleNumber == null) {
			return null;
		}
		BigDecimal bigDecimal = new BigDecimal(doubleNumber);
		String numberAsString = Double.toString(doubleNumber);
		int decimalPos = numberAsString.indexOf(".");
		String afterDecimal = numberAsString.substring(decimalPos);
		if(afterDecimal.length()>=4 && afterDecimal.substring(3,4).equals("5")){
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_UP);
		}else{
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_HALF_EVEN);
		}
		return bigDecimal;
	}



	public String getRetailCostToInventory() {
		return retailCostToInventory;
	}



	public void setRetailCostToInventory(String retailCostToInventory) {
		this.retailCostToInventory = retailCostToInventory;
	}



	public String getRetailReimbursement() {
		return retailReimbursement;
	}



	public void setRetailReimbursement(String retailReimbursement) {
		this.retailReimbursement = retailReimbursement;
	}



	public String getRetailPriceSLGrossUp() {
		return retailPriceSLGrossUp;
	}



	public void setRetailPriceSLGrossUp(String retailPriceSLGrossUp) {
		this.retailPriceSLGrossUp = retailPriceSLGrossUp;
	}



	public String getRetailSLGrossUp() {
		return retailSLGrossUp;
	}

    public void setRetailSLGrossUp(String retailSLGrossUp) {
		this.retailSLGrossUp = retailSLGrossUp;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}
	public SoProviderInvoicePartLocation getInvoicePartLocation() {
		return invoicePartLocation;
	}
	
	public void setInvoicePartLocation(SoProviderInvoicePartLocation invoicePartLocation) {
		this.invoicePartLocation = invoicePartLocation;
		if (this.invoicePartLocation != null) this.invoicePartLocation.setInvoiceParts(this);
	}
   public List<SoProviderInvoicePartDocument> getDocumentList() {
		return documentList;
	}
	
	public void setDocumentList(List<SoProviderInvoicePartDocument> documentList) {
		this.documentList = documentList;
		if(this.documentList != null){
			for(SoProviderInvoicePartDocument document:this.documentList){
				document.setInvoiceParts(this);
			}
		}
	}
	public String getPartSource() {
		return partSource;
	}
	public void setPartSource(String partSource) {
		this.partSource = partSource;
	}

}
