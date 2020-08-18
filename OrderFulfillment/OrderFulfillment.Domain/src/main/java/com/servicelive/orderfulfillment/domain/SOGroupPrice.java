package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Parameter;

/**
 * User: Yunus Burhani
 * Date: Mar 19, 2010
 * Time: 1:49:01 PM
 */
@Entity()
@Table(name = "so_group_price")
@XmlRootElement
public class SOGroupPrice extends SOBase{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6508509278070080180L;

	@Column(name = "discounted_group_spend_limit_labor")
    private BigDecimal discountedSpendLimitLabor;

    @Column(name = "discounted_group_spend_limit_parts")
    private BigDecimal discountedSpendLimitParts;

    @Column(name = "final_group_spend_limit_labor")
    private BigDecimal finalSpendLimitLabor;

    @Column(name = "final_group_spend_limit_parts")
    private BigDecimal finalSpendLimitParts;
    
    @Column(name = "total_permit_price")
    private BigDecimal totalPermitPrice;

    @Id() @GeneratedValue(generator = "priceForeignGenerator")
    @org.hibernate.annotations.GenericGenerator(name = "priceForeignGenerator",
	        strategy = "foreign",
	        parameters = @Parameter(name = "property", value = "soGroup")
	    )
    @Column(name = "so_group_id")
    private String groupId;

     @Column(name = "original_group_spend_limit_labor")
    private BigDecimal originalSpendLimitLabor;

    @Column(name = "original_group_spend_limit_parts")
    private BigDecimal originalSpendLimitParts;

    @OneToOne(fetch=FetchType.EAGER,mappedBy = "groupPrice",optional=false)
	@PrimaryKeyJoinColumn(name="so_group_id",referencedColumnName="so_group_id")
    private SOGroup soGroup;

    public BigDecimal getDiscountedSpendLimitLabor() {
        return discountedSpendLimitLabor;
    }

    public BigDecimal getDiscountedSpendLimitParts() {
        return discountedSpendLimitParts;
    }

    public BigDecimal getFinalSpendLimitLabor() {
        return finalSpendLimitLabor;
    }

    public BigDecimal getFinalSpendLimitParts() {
        return finalSpendLimitParts;
    }

    public String getGroupId() {
        return groupId;
    }

    public BigDecimal getOriginalSpendLimitLabor() {
        return originalSpendLimitLabor;
    }

    public BigDecimal getOriginalSpendLimitParts() {
        return originalSpendLimitParts;
    }

    public SOGroup getSoGroup() {
        return soGroup;
    }

    @XmlElement
    public void setDiscountedSpendLimitLabor(BigDecimal discountedSpendLimitLabor) {
        this.discountedSpendLimitLabor = discountedSpendLimitLabor;
    }

    @XmlElement
    public void setDiscountedSpendLimitParts(BigDecimal discountedSpendLimitParts) {
        this.discountedSpendLimitParts = discountedSpendLimitParts;
    }

    @XmlElement
    public void setFinalSpendLimitLabor(BigDecimal finalSpendLimitLabor) {
        this.finalSpendLimitLabor = finalSpendLimitLabor;
    }

    @XmlElement
    public void setFinalSpendLimitParts(BigDecimal finalSpendLimitParts) {
        this.finalSpendLimitParts = finalSpendLimitParts;
    }

    @XmlElement
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @XmlElement
    public void setOriginalSpendLimitLabor(BigDecimal originalSpendLimitLabor) {
        this.originalSpendLimitLabor = originalSpendLimitLabor;
    }

    @XmlElement
    public void setOriginalSpendLimitParts(BigDecimal originalSpendLimitParts) {
        this.originalSpendLimitParts = originalSpendLimitParts;
    }

    @XmlTransient
    public void setSoGroup(SOGroup soGroup) {
        this.soGroup = soGroup;
    }
	
    public void afterUnmarshal(Unmarshaller u, Object parent) {
	    this.soGroup = (SOGroup) parent;
	}

	public BigDecimal getTotalPermitPrice() {
		return totalPermitPrice;
	}

	public void setTotalPermitPrice(BigDecimal totalPermitPrice) {
		this.totalPermitPrice = totalPermitPrice;
	}
}
