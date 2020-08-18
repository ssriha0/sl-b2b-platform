package com.newco.marketplace.vo.promo;

import java.sql.Date;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author nsanzer
 * Description: Value object for the promo table.  The promo table holds conditional offeres and discounts. 
 * 
 */
public class PromoVO extends SerializableBaseVO{
	private int promoID;
	private Date beginDateTime;
	private Date endDateTime;
    private String promoType;
    private int roleID;	
	private double promoValue;
	
	// SL-18316 Renaming the maxValue variable to  
	// maxPossibleValue since maxValue is a reserved word in MySQL5.6
	private double maxPossibleValue;
	private String adjustmentType;
    private List<PromoContentVO> promoContent;
    private boolean soDependent;
	/** @return the promoID */
	public int getPromoID() {
		return promoID;
	}
	/** @param promoID the promoID to set */
	public void setPromoID(int promoID) {
		this.promoID = promoID;
	}
	/** @return the beginDateTime */
	public Date getBeginDateTime() {
		return beginDateTime;
	}
	/** @param beginDateTime the beginDateTime to set */
	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
	}
	/** @return the endDateTime */
	public Date getEndDateTime() {
		return endDateTime;
	}
	/** @param endDateTime the endDateTime to set */
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	/** @return the promoValue */
	public double getPromoValue() {
		return promoValue;
	}
	/** @param promoValue the promoValue to set */
	public void setPromoValue(double promoValue) {
		this.promoValue = promoValue;
	}
	/** @return the maxValue */
	public double getMaxPossibleValue() {
		return maxPossibleValue;
	}
	/** @param maxValue the maxValue to set */
		public void setMaxPossibleValue(double maxPossibleValue) {
		this.maxPossibleValue = maxPossibleValue;
	}
    /** @return the roleID */
    public int getRoleID() {
        return roleID;
    }
    /** @param roleID the roleID to set */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    /** @return the promoType */
    public String getPromoType() {
        return promoType;
    }
    /** @param promoType the promoType to set */
    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

	public String getAdjustmentType() {
		return adjustmentType;
	}
	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}
	public boolean isSoDependent() {
		return soDependent;
	}
	public void setSoDependent(boolean soDependent) {
		this.soDependent = soDependent;
	}
	public List<PromoContentVO> getPromoContent() {
		return promoContent;
	}
	public void setPromoContent(List<PromoContentVO> promoContent) {
		this.promoContent = promoContent;
	}
}
