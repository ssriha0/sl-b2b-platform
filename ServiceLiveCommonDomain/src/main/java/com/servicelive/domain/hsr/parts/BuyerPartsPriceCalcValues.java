package com.servicelive.domain.hsr.parts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="buyer_parts_price_calc_values" )
public class BuyerPartsPriceCalcValues {
    @Id
    @Column(name="id")
    private Integer buyerPartsPriceId;
	@Column(name="buyer_id")
	private Integer buyerId;
	
	@Column(name="part_coverage_type_id")
	private Integer partCoverageTypeId;
	
	@Column(name="part_sourcing_level_id")
	private Integer partSourcingLevelId;
	
	@Column(name="reimbursement_rate")
	private double reimbursementRate;
	
	@Column(name="sl_gross_up_val")
	private double slGrossUpVal;
	

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getPartCoverageTypeId() {
		return partCoverageTypeId;
	}

	public void setPartCoverageTypeId(Integer partCoverageTypeId) {
		this.partCoverageTypeId = partCoverageTypeId;
	}

	public Integer getPartSourcingLevelId() {
		return partSourcingLevelId;
	}

	public void setPartSourcingLevelId(Integer partSourcingLevelId) {
		this.partSourcingLevelId = partSourcingLevelId;
	}

	public double getReimbursementRate() {
		return reimbursementRate;
	}

	public void setReimbursementRate(double reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}

	public double getSlGrossUpVal() {
		return slGrossUpVal;
	}



	public Integer getBuyerPartsPriceId() {
		return buyerPartsPriceId;
	}

	public void setBuyerPartsPriceId(Integer buyerPartsPriceId) {
		this.buyerPartsPriceId = buyerPartsPriceId;
	}
	
}
