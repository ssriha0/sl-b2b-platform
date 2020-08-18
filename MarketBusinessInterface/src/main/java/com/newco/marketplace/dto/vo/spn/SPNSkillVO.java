package com.newco.marketplace.dto.vo.spn;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNSkillVO extends SerializableBaseVO {

	private static final long serialVersionUID = 7506166263039574588L;

	private Integer criteriaId;
	private Integer mainCategory;
	private Integer category;
	private Integer subCategory;
	private Integer skill;
	
	
	/**
	 * @return the criteriaId
	 */
	public Integer getCriteriaId() {
		return criteriaId;
	}
	/**
	 * @param criteriaId the criteriaId to set
	 */
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	/**
	 * @return the mainCategory
	 */
	public Integer getMainCategory() {
		return mainCategory;
	}
	/**
	 * @param mainCategory the mainCategory to set
	 */
	public void setMainCategory(Integer mainCategory) {
		this.mainCategory = mainCategory;
	}
	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}
	/**
	 * @return the skill
	 */
	public Integer getSkill() {
		return skill;
	}
	/**
	 * @param skill the skill to set
	 */
	public void setSkill(Integer skill) {
		this.skill = skill;
	}
	/**
	 * @return the subCategory
	 */
	public Integer getSubCategory() {
		return subCategory;
	}
	/**
	 * @param subCategory the subCategory to set
	 */
	public void setSubCategory(Integer subCategory) {
		this.subCategory = subCategory;
	}
	
	
}
/*
 * Maintenance History:
 * $Log: SPNSkillVO.java,v $
 * Revision 1.2  2008/05/02 21:23:58  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.3  2008/04/10 21:49:03  mhaye05
 * updates
 *
 * Revision 1.1.2.2  2008/04/10 19:07:14  mhaye05
 * added additional attributes for skills
 *
 * Revision 1.1.2.1  2008/04/09 14:44:10  mhaye05
 * Initial check in
 *
 */