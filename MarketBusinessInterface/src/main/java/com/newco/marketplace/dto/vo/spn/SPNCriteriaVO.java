package com.newco.marketplace.dto.vo.spn;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNCriteriaVO extends SerializableBaseVO {

	private static final long serialVersionUID = -5822180369828700757L;

	private Integer resourceCredTypeId;
	private Integer resourceCredCategoryId;
	private Integer vendorCredTypeId;
	private Integer vendorCredCategoryId;
	private boolean insGeneralLiability;
	private Double insGeneralLiabilityMinAmt;
	private boolean insAutoLiability;
	private Double insAutoLiabilityMinAmt;
	private boolean insWorkmanComp;
	private Double insWorkmanCompMinAmt;
	private Double starRating;
	private boolean starRatingIncludeNonRated;
	private Integer languageId;
	private Integer minSOClosed;
	private List<SPNSkillVO> skills;
	
	
	/**
	 * @return the resourceCredTypeId
	 */
	public Integer getResourceCredTypeId() {
		return resourceCredTypeId;
	}
	/**
	 * @param resourceCredTypeId the resourceCredTypeId to set
	 */
	public void setResourceCredTypeId(Integer resourceCredTypeId) {
		this.resourceCredTypeId = resourceCredTypeId;
	}
	/**
	 * @return the resourceCredCategoryId
	 */
	public Integer getResourceCredCategoryId() {
		return resourceCredCategoryId;
	}
	/**
	 * @param resourceCredCategoryId the resourceCredCategoryId to set
	 */
	public void setResourceCredCategoryId(Integer resourceCredCategoryId) {
		this.resourceCredCategoryId = resourceCredCategoryId;
	}
	/**
	 * @return the vendorCredTypeId
	 */
	public Integer getVendorCredTypeId() {
		return vendorCredTypeId;
	}
	/**
	 * @param vendorCredTypeId the vendorCredTypeId to set
	 */
	public void setVendorCredTypeId(Integer vendorCredTypeId) {
		this.vendorCredTypeId = vendorCredTypeId;
	}
	/**
	 * @return the vendorCredCategoryId
	 */
	public Integer getVendorCredCategoryId() {
		return vendorCredCategoryId;
	}
	/**
	 * @param vendorCredCategoryId the vendorCredCategoryId to set
	 */
	public void setVendorCredCategoryId(Integer vendorCredCategoryId) {
		this.vendorCredCategoryId = vendorCredCategoryId;
	}
	/**
	 * @return the insGeneralLiability
	 */
	public boolean isInsGeneralLiability() {
		return insGeneralLiability;
	}
	/**
	 * @param insGeneralLiability the insGeneralLiability to set
	 */
	public void setInsGeneralLiability(boolean insGeneralLiability) {
		this.insGeneralLiability = insGeneralLiability;
	}
	/**
	 * @return the insGeneralLiabilityMinAmt
	 */
	public Double getInsGeneralLiabilityMinAmt() {
		return insGeneralLiabilityMinAmt;
	}
	/**
	 * @param insGeneralLiabilityMinAmt the insGeneralLiabilityMinAmt to set
	 */
	public void setInsGeneralLiabilityMinAmt(Double insGeneralLiabilityMinAmt) {
		this.insGeneralLiabilityMinAmt = insGeneralLiabilityMinAmt;
	}
	/**
	 * @return the insAutoLiability
	 */
	public boolean isInsAutoLiability() {
		return insAutoLiability;
	}
	/**
	 * @param insAutoLiability the insAutoLiability to set
	 */
	public void setInsAutoLiability(boolean insAutoLiability) {
		this.insAutoLiability = insAutoLiability;
	}
	/**
	 * @return the insAutoLiabilityMinAmt
	 */
	public Double getInsAutoLiabilityMinAmt() {
		return insAutoLiabilityMinAmt;
	}
	/**
	 * @param insAutoLiabilityMinAmt the insAutoLiabilityMinAmt to set
	 */
	public void setInsAutoLiabilityMinAmt(Double insAutoLiabilityMinAmt) {
		this.insAutoLiabilityMinAmt = insAutoLiabilityMinAmt;
	}
	/**
	 * @return the insWorkmanComp
	 */
	public boolean isInsWorkmanComp() {
		return insWorkmanComp;
	}
	/**
	 * @param insWorkmanComp the insWorkmanComp to set
	 */
	public void setInsWorkmanComp(boolean insWorkmanComp) {
		this.insWorkmanComp = insWorkmanComp;
	}
	/**
	 * @return the insWorkmanCompMinAmt
	 */
	public Double getInsWorkmanCompMinAmt() {
		return insWorkmanCompMinAmt;
	}
	/**
	 * @param insWorkmanCompMinAmt the insWorkmanCompMinAmt to set
	 */
	public void setInsWorkmanCompMinAmt(Double insWorkmanCompMinAmt) {
		this.insWorkmanCompMinAmt = insWorkmanCompMinAmt;
	}
	/**
	 * @return the starRating
	 */
	public Double getStarRating() {
		return starRating;
	}
	/**
	 * @param starRating the starRating to set
	 */
	public void setStarRating(Double starRating) {
		this.starRating = starRating;
	}
	/**
	 * @return the starRatingIncludeNonRated
	 */
	public boolean isStarRatingIncludeNonRated() {
		return starRatingIncludeNonRated;
	}
	/**
	 * @param starRatingIncludeNonRated the starRatingIncludeNonRated to set
	 */
	public void setStarRatingIncludeNonRated(boolean starRatingIncludeNonRated) {
		this.starRatingIncludeNonRated = starRatingIncludeNonRated;
	}
	/**
	 * @return the languageId
	 */
	public Integer getLanguageId() {
		return languageId;
	}
	/**
	 * @param languageId the languageId to set
	 */
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	/**
	 * @return the minSOClosed
	 */
	public Integer getMinSOClosed() {
		return minSOClosed;
	}
	/**
	 * @param minSOClosed the minSOClosed to set
	 */
	public void setMinSOClosed(Integer minSOClosed) {
		this.minSOClosed = minSOClosed;
	}
	/**
	 * @return the skills
	 */
	public List<SPNSkillVO> getSkills() {
		return skills;
	}
	/**
	 * @param skills the skills to set
	 */
	public void setSkills(List<SPNSkillVO> skills) {
		this.skills = skills;
	}
}
/*
 * Maintenance History:
 * $Log: SPNCriteriaVO.java,v $
 * Revision 1.2  2008/05/02 21:23:59  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.3  2008/04/11 14:57:38  mhaye05
 * removed attribute that is not needed anymore
 *
 * Revision 1.1.2.2  2008/04/10 21:49:03  mhaye05
 * updates
 *
 * Revision 1.1.2.1  2008/04/09 14:44:10  mhaye05
 * Initial check in
 *
 */