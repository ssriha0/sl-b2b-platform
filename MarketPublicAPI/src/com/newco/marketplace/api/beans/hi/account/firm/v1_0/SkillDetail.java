package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "skill")
@XStreamAlias("skill")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillDetail {

	@XStreamAlias("rootSkillName")
	private String rootSkillName;
	
	@XStreamAlias("skillName")
	private String skillName;
	
	@XStreamAlias("skillCategoryName")
	private String skillCategoryName;
	
	@XStreamAlias("skillServiceTypes")
	@XmlElement(name="skillServiceTypes")
    private SkillServiceType skillServiceTypes;
	
	
	public String getRootSkillName() {
		return rootSkillName;
	}

	public void setRootSkillName(String rootSkillName) {
		this.rootSkillName = rootSkillName;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillCategoryName() {
		return skillCategoryName;
	}

	public void setSkillCategoryName(String skillCategoryName) {
		this.skillCategoryName = skillCategoryName;
	}

	public SkillServiceType getSkillServiceTypes() {
		return skillServiceTypes;
	}

	public void setSkillServiceTypes(SkillServiceType skillServiceTypes) {
		this.skillServiceTypes = skillServiceTypes;
	}

	
}
