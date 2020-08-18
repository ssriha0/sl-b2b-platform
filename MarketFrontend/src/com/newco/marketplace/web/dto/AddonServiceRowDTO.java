package com.newco.marketplace.web.dto;

public class AddonServiceRowDTO extends SerializedBaseDTO
{
	private static final long serialVersionUID = -7266509881218708689L;
	private String sku = "";
	private Integer addonId;
	private String description = "";
	private Integer quantity = 0;
	private Double providerPaid = 0.0;
	private Double endCustomerCharge = 0.0;
	private Double endCustomerSubtotal = 0.0;
	private boolean misc;
	private Double margin;
	private String coverage;
	private Integer permitType;
	private boolean autoGenInd = false;
	private Integer addonPermitTypeId;
	private Double taxPercentage;
	private Double providerTax;
	private Integer skuGroupType=0;
	/* skip Required addon from adding to Total price, this is set to true only for HSR for now 
	 * This is quick fix, will be handled in right way in SKu level pricing*/
	private boolean skipReqAddon = false;
	
	public AddonServiceRowDTO(Integer addonId,String sku,String  description, Integer quantity, Double providerPaid ,Double endCustomerPrice, 
				Double subtotal ,  boolean misc,Double margin, String coverage, boolean skipReqAddon, boolean autoGenInd,Integer addonPermitTypeId){
		super();
		this.addonId = addonId;
		this.sku = sku;
		this.description = description;
		this.quantity = quantity;
		this.providerPaid = providerPaid;
		this.endCustomerCharge = endCustomerPrice;
		this.endCustomerSubtotal = subtotal;
		this.misc =  misc;
		this.margin = margin;
		this.coverage = coverage;
		this.skipReqAddon = skipReqAddon;
		this.autoGenInd = autoGenInd;
		this.addonPermitTypeId = addonPermitTypeId;
		this.permitType = addonPermitTypeId;
	}
	
	public AddonServiceRowDTO(Integer addonId, String sku, String description, Integer quantity, Double providerPaid,
			Double endCustomerPrice, Double subtotal, boolean misc, Double margin, String coverage, boolean skipReqAddon,
			boolean autoGenInd, Integer addonPermitTypeId, Double taxPercentage,Integer skuGroupType) {
		this(addonId, sku, description, quantity, providerPaid, endCustomerPrice, subtotal, misc, margin, coverage, skipReqAddon,
				autoGenInd, addonPermitTypeId);
		this.taxPercentage = taxPercentage;
		this.skuGroupType = skuGroupType;
	}
	
	public AddonServiceRowDTO(){
	 super();
	}
	
	public Double getMargin() {
		return margin;
	}
	public void setMargin(Double margin) {
		this.margin = margin;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Integer getQuantity()
	{
		return quantity;
	}
	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}
	public Double getProviderPaid()
	{
		return com.newco.marketplace.utils.MoneyUtil.getRoundedMoney( providerPaid );
	}
	public void setProviderPaid(Double providerPaid)
	{
		this.providerPaid = providerPaid;
	}
	public Double getEndCustomerCharge()
	{
		return com.newco.marketplace.utils.MoneyUtil.getRoundedMoney( endCustomerCharge );
	}
	public void setEndCustomerCharge(Double endCustomerCharge)
	{
		this.endCustomerCharge = endCustomerCharge;
	}
	public Double getEndCustomerSubtotal()
	{
		return com.newco.marketplace.utils.MoneyUtil.getRoundedMoney( endCustomerSubtotal );
	}
	public void setEndCustomerSubtotal(Double endCustomerSubtotal)
	{
		this.endCustomerSubtotal = endCustomerSubtotal;
	}
	public boolean getMisc()
	{
		return misc;
	}
	public void setMisc(boolean misc)
	{
		this.misc = misc;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public boolean isSkipReqAddon() {
		return skipReqAddon;
	}

	public void setSkipReqAddon(boolean skipReqAddon) {
		this.skipReqAddon = skipReqAddon;
	}

	public boolean isAutoGenInd() {
		return autoGenInd;
	}

	public void setAutoGenInd(boolean autoGenInd) {
		this.autoGenInd = autoGenInd;
	}

	public Integer getPermitType() {
		return permitType;
	}

	public void setPermitType(Integer permitType) {
		this.permitType = permitType;
	}

	public Integer getAddonId() {
		return addonId;
	}

	public void setAddonId(Integer addonId) {
		this.addonId = addonId;
	}

	public Integer getAddonPermitTypeId() {
		return addonPermitTypeId;
	}

	public void setAddonPermitTypeId(Integer addonPermitTypeId) {
		this.addonPermitTypeId = addonPermitTypeId;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public Double getProviderTax() {
		return providerTax;
	}

	public void setProviderTax(Double providerTax) {
		this.providerTax = providerTax;
	}

	public Integer getSkuGroupType() {
		return skuGroupType;
	}

	public void setSkuGroupType(Integer skuGroupType) {
		this.skuGroupType = skuGroupType;
	}
	
	
}