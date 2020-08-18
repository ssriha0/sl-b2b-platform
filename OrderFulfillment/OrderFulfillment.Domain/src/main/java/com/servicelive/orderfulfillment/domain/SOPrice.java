package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "so_price")
@XmlRootElement()
public class SOPrice extends SORelative {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1996869528449310772L;

	/*
	 * Unfortunately JPA does not allow a PrimaryKey Object to participate in 
	 * Entity Associations. As noted by Christian Bauer & King (pg. 281)
	 */
	@Id @GeneratedValue(generator = "priceForeignGenerator")
    @org.hibernate.annotations.GenericGenerator(name = "priceForeignGenerator",
	        strategy = "foreign",
	        parameters = @Parameter(name = "property", value = "serviceOrder")
	    )
	 @Column(name="so_id")
	 @SuppressWarnings("unused")
	 private String soId;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="price",optional=false)
	private ServiceOrder serviceOrder;
	
	@Column(name = "original_spend_limit_labor")
	private BigDecimal origSpendLimitLabor;
	
	@Column(name = "original_spend_limit_parts")
	private BigDecimal origSpendLimitParts;
	
	@Column(name = "initial_posted_labor_spend_limit")
	private BigDecimal initPostedSpendLimitLabor;
	
	@Column(name = "initial_posted_parts_spend_limit")
	private BigDecimal initPostedSpendLimitParts;
	
	@Column(name = "discounted_spend_limit_labor")
	private BigDecimal discountedSpendLimitLabor;
	
	@Column(name = "discounted_spend_limit_parts")
	private BigDecimal discountedSpendLimitParts;
	
	@Column(name = "condl_offer_accepted_price")
	private BigDecimal conditionalOfferPrice;
	
	@Column(name = "final_service_fee")
	private BigDecimal finalServiceFee;
	
	@Column(name = "total_addon_price_gl")
	private BigDecimal totalAddonPriceGL;
	
	
	// Sprint 5: Front end completion
	
	@Column(name = "invoice_parts_retail_price")
	private BigDecimal invoicePartsRetailPrice;
	
	@Column(name = "invoice_parts_retail_reimbursement")
	private BigDecimal invoicePartsRetailReimbursement;
	
	@Column(name = "invoice_parts_retail_sl_gross_up")
	private BigDecimal invoicePartsRetailSlGrossUp;
	
	@Column(name = "invoice_parts_final_price")
	private BigDecimal invoicePartsFinalPrice;
	
	@Column(name = "discount_percent_parts_spend_limit")
	private BigDecimal discountPercentPartsSL;
	
	@Column(name = "discount_percent_labor_spend_limit")
	private BigDecimal discountPercentLaborSL;
	
	@Column(name = "tax_percent_parts_spend_limit")
	private BigDecimal taxPercentPartsSL;
	
	@Column(name = "tax_percent_labor_spend_limit")
	private BigDecimal taxPercentLaborSL;

	public BigDecimal getInvoicePartsRetailPrice() {
		return invoicePartsRetailPrice;
	}

	public void setInvoicePartsRetailPrice(BigDecimal invoicePartsRetailPrice) {
		this.invoicePartsRetailPrice = invoicePartsRetailPrice;
	}

	public BigDecimal getInvoicePartsRetailReimbursement() {
		return invoicePartsRetailReimbursement;
	}

	public void setInvoicePartsRetailReimbursement(
			BigDecimal invoicePartsRetailReimbursement) {
		this.invoicePartsRetailReimbursement = invoicePartsRetailReimbursement;
	}

	public BigDecimal getInvoicePartsRetailSlGrossUp() {
		return invoicePartsRetailSlGrossUp;
	}

	public void setInvoicePartsRetailSlGrossUp(
			BigDecimal invoicePartsRetailSlGrossUp) {
		this.invoicePartsRetailSlGrossUp = invoicePartsRetailSlGrossUp;
	}

	public BigDecimal getInvoicePartsFinalPrice() {
		return invoicePartsFinalPrice;
	}

	public void setInvoicePartsFinalPrice(BigDecimal invoicePartsFinalPrice) {
		this.invoicePartsFinalPrice = invoicePartsFinalPrice;
	}

	public BigDecimal getConditionalOfferPrice() {
		return conditionalOfferPrice;
	}

	public BigDecimal getDiscountedSpendLimitLabor() {
		return discountedSpendLimitLabor;
	}

	public BigDecimal getDiscountedSpendLimitParts() {
		return discountedSpendLimitParts;
	}

	public BigDecimal getFinalServiceFee() {
		return finalServiceFee;
	}

	public BigDecimal getTotalAddonPriceGL() {
		return totalAddonPriceGL;
	}
	
	public BigDecimal getInitPostedSpendLimitLabor() {
		return initPostedSpendLimitLabor;
	}

	public BigDecimal getInitPostedSpendLimitParts() {
		return initPostedSpendLimitParts;
	}

	public BigDecimal getOrigSpendLimitLabor() {
		return origSpendLimitLabor;
	}

	public BigDecimal getOrigSpendLimitParts() {
		return origSpendLimitParts;
	}

	@Override
	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	@XmlElement()
	public void setConditionalOfferPrice(BigDecimal conditionalOfferPrice) {
		this.conditionalOfferPrice = conditionalOfferPrice;
	}

	@XmlElement()
	public void setDiscountedSpendLimitLabor(BigDecimal discountedSpendLimitLabor) {
		this.discountedSpendLimitLabor = discountedSpendLimitLabor;
	}

	@XmlElement()
	public void setDiscountedSpendLimitParts(BigDecimal discountedSpendLimitParts) {
		this.discountedSpendLimitParts = discountedSpendLimitParts;
	}

	@XmlElement()
	public void setFinalServiceFee(BigDecimal finalServiceFee) {
		this.finalServiceFee = finalServiceFee;
	}
	
	@XmlElement()
	public void setTotalAddonPriceGL(BigDecimal totalAddonPriceGL) {
		this.totalAddonPriceGL = totalAddonPriceGL;
	}

	@XmlElement()
	public void setInitPostedSpendLimitLabor(BigDecimal initPostedSpendLimitLabor) {
		this.initPostedSpendLimitLabor = initPostedSpendLimitLabor;
	}

	@XmlElement()
	public void setInitPostedSpendLimitParts(BigDecimal initPostedSpendLimitParts) {
		this.initPostedSpendLimitParts = initPostedSpendLimitParts;
	}

	@XmlElement()
	public void setOrigSpendLimitLabor(BigDecimal origSpendLimitLabor) {
		this.origSpendLimitLabor = origSpendLimitLabor;
	}

	@XmlElement()
	public void setOrigSpendLimitParts(BigDecimal origSpendLimitParts) {
		this.origSpendLimitParts = origSpendLimitParts;
	}

	@Override
	@XmlTransient()
	public void setServiceOrder(ServiceOrder order) {
		this.serviceOrder = order;
	}
	
    @Override
    public List<String> validate() {
        List<String> returnVal = new ArrayList<String>();
        if(null!=origSpendLimitLabor && null != origSpendLimitParts){
            BigDecimal origTotal = origSpendLimitLabor.add(origSpendLimitParts);
            //Evalutate total order price.
            if(1 > origTotal.signum()){
                returnVal.add("The value for the Prices estimated can not be less than 1. Please revise your prices.");
            }
        }else if(null == origSpendLimitLabor || 1 > origSpendLimitLabor.signum()){
            returnVal.add("The value for Labor charges can not be less than 1. Please revise your prices.");
        }

        return returnVal;
    }

    public void reset() {
        this.discountedSpendLimitLabor = this.origSpendLimitLabor;
        this.discountedSpendLimitParts = this.origSpendLimitParts;
    }

	public BigDecimal getDiscountPercentPartsSL() {
		return discountPercentPartsSL;
	}

	public void setDiscountPercentPartsSL(BigDecimal discountPercentPartsSL) {
		this.discountPercentPartsSL = discountPercentPartsSL;
	}

	public BigDecimal getDiscountPercentLaborSL() {
		return discountPercentLaborSL;
	}

	public void setDiscountPercentLaborSL(BigDecimal discountPercentLaborSL) {
		this.discountPercentLaborSL = discountPercentLaborSL;
	}

	public BigDecimal getTaxPercentPartsSL() {
		return taxPercentPartsSL;
	}

	public void setTaxPercentPartsSL(BigDecimal taxPercentPartsSL) {
		this.taxPercentPartsSL = taxPercentPartsSL;
	}

	public BigDecimal getTaxPercentLaborSL() {
		return taxPercentLaborSL;
	}

	public void setTaxPercentLaborSL(BigDecimal taxPercentLaborSL) {
		this.taxPercentLaborSL = taxPercentLaborSL;
	}
}
