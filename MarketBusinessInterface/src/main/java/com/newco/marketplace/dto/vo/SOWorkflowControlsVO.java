package com.newco.marketplace.dto.vo;



import com.sears.os.vo.SerializableBaseVO;

/**
 * @author sahmad7
 *
 */
public class SOWorkflowControlsVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String soId;
	private Boolean tierRouteInd;
	private Double performanceScore;
	private Double finalPriceLabor;
	private Double finalPriceParts;
	private Boolean nonFundedInd = false;
	private String invoicePartsPricingModel;
    private String originalSoId;
    private Integer warrantyProvider;
	private String invoicePartsInd;

	//Priority 5B
	private String invalidModelSerialInd;
	private Double laborTaxPercentage;
	private Double materialsTaxPercentage;


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
	public Double getFinalPriceLabor() {
		return finalPriceLabor;
	}
	public void setFinalPriceLabor(Double finalPriceLabor) {
		this.finalPriceLabor = finalPriceLabor;
	}
	public Double getFinalPriceParts() {
		return finalPriceParts;
	}
	public void setFinalPriceParts(Double finalPriceParts) {
		this.finalPriceParts = finalPriceParts;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Boolean getNonFundedInd() {
		return nonFundedInd;
	}
	public void setNonFundedInd(Boolean nonFundedInd) {
		this.nonFundedInd = nonFundedInd;
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
	public Integer getWarrantyProvider() {
		return warrantyProvider;
	}
	public void setWarrantyProvider(Integer warrantyProvider) {
		this.warrantyProvider = warrantyProvider;
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
    
	
	

}
