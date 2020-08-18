package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "so_addon")
@XmlRootElement()
public class SOAddon  extends SOChild implements Comparable<SOAddon>{

	private static final long serialVersionUID = -541014570414519372L;
	/** Number of decimals to retain. Also referred to as "scale". */
	private static int DECIMALS = Currency.getInstance("USD").getDefaultFractionDigits();
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_addon_id")
	private Integer addonId;

	@Column(name = "sku")
	private String sku;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "scope_of_work")
	private String scopeOfWork;
	
	@Column(name = "retail_price")
	private BigDecimal retailPrice;
	
	@Column(name = "margin")
	private Double margin;
	
	@Column(name = "misc_ind")
	private boolean miscInd;
	
	@Column(name = "qty")
	private Integer quantity;
	
	@Column(name = "service_fee_final")
	private BigDecimal serviceFee;
	
	@Column(name = "coverage")
	private String coverage;

	@Column(name = "auto_gen_ind")
	private boolean autoGenInd;
	
	@Column(name = "tax_percentage")
	private Double taxPercentage=0.0000;
	
	@Column(name = "sku_group_type")
	private Integer skuGroupType=0;

    @Transient
    private Integer dispatchDaysOut;
    
    /**SMP-1346:Make the Default value to true to validate AddOn*/
    @Transient
    private boolean isNewAddOne = true;

	public Integer getAddonId() {
		return addonId;
	}

	public String getCoverage() {
		return coverage;
	}

	public String getDescription() {
		return description;
	}

	public Double getMargin() {
		return margin;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public String getScopeOfWork() {
		return scopeOfWork;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public String getSku() {
		return sku;
	}

	public boolean isMiscInd() {
		return miscInd;
	}
	
	public boolean isAutoGenInd() {
		return autoGenInd;
	}

    public Integer getDispatchDaysOut() {
        return dispatchDaysOut;
    }

	@XmlElement()
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	@XmlElement()
	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement()
	public void setMargin(Double margin) {
		this.margin = margin;
	}

	@XmlElement()
	public void setMiscInd(boolean miscInd) {
		this.miscInd = miscInd;
	}
	
	@XmlElement()
	public void setAutoGenInd(boolean autoGenInd) {
		this.autoGenInd = autoGenInd;
	}
	
	@XmlElement()
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@XmlElement()
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	@XmlElement()
	public void setScopeOfWork(String scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	@XmlElement()
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	@XmlElement()
	public void setSku(String sku) {
		this.sku = sku;
	}

	@XmlElement()
	public void setAddonId(Integer addonId) {
		this.addonId = addonId;
	}

    @XmlElement()
    public void setDispatchDaysOut(Integer dispatchDaysOut) {
        this.dispatchDaysOut = dispatchDaysOut;
    }

	/**
	 * Define equality of SOAddon.
	 */
	@Override 
	public boolean equals( Object aThat ) {
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof SOAddon) ) return false;
	
	    SOAddon o = (SOAddon)aThat;
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(coverage, o.coverage).append(description, o.description)
                .append(dispatchDaysOut, o.dispatchDaysOut).append(margin, o.margin)
                .append(miscInd, o.miscInd).append(quantity, o.quantity)
                .append(retailPrice, o.retailPrice).append(scopeOfWork, o.scopeOfWork)
                .append(serviceFee, o.serviceFee).append(sku, o.sku);
        return equalsBuilder.isEquals();
	}

	/**
   	* A class that overrides equals must also override hashCode.
  	*/
	@Override 
  	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(this.coverage).append(this.description)
		 	.append(this.dispatchDaysOut).append(this.margin)
		 	.append(this.miscInd).append(this.quantity)
		 	.append(this.retailPrice).append(this.scopeOfWork)
		 	.append(serviceFee).append(this.sku);
		 return hcb.toHashCode();
  	}
  
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);

    }
    
	public int compareTo(SOAddon o) {
        CompareToBuilder compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(coverage, o.coverage).append(description, o.description)
                .append(dispatchDaysOut, o.dispatchDaysOut).append(margin, o.margin)
                .append(miscInd, o.miscInd).append(quantity, o.quantity)
                .append(retailPrice, o.retailPrice).append(scopeOfWork, o.scopeOfWork)
                .append(serviceFee, o.serviceFee).append(sku, o.sku);
        return compareToBuilder.toComparison();
	}
	
    public BigDecimal getAddonPrice(){
        //return this.getRetailPrice().multiply(new BigDecimal(1 - this.margin)).setScale(2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(this.quantity)).setScale(2, RoundingMode.HALF_EVEN);
     	BigDecimal addonPrice = this.getRoundedMoneyBigDecimal(this.quantity * (this.getRetailPrice().doubleValue() - (this.getRetailPrice().doubleValue() * new BigDecimal(this.margin).doubleValue())));
        return addonPrice;
    }
    
    public BigDecimal getAddonPriceWithoutTax(){
        //return this.getRetailPrice().multiply(new BigDecimal(1 - this.margin)).setScale(2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(this.quantity)).setScale(2, RoundingMode.HALF_EVEN);
    	BigDecimal originalPrice = BigDecimal.valueOf(this.getRetailPrice().doubleValue() *(1.0000/(1.0000+this.getTaxPercentage())));
    	BigDecimal addonPrice = this.getRoundedMoneyBigDecimal(this.quantity * (originalPrice.doubleValue() - 
     			(originalPrice.doubleValue() * new BigDecimal(this.margin).doubleValue())));
        return addonPrice;
    
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

	public boolean isNewAddOne() {
		return isNewAddOne;
	}

	public void setNewAddOne(boolean isNewAddOne) {
		this.isNewAddOne = isNewAddOne;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public Integer getSkuGroupType() {
		return skuGroupType;
	}

	public void setSkuGroupType(Integer skuGroupType) {
		this.skuGroupType = skuGroupType;
	}
	
	
}
