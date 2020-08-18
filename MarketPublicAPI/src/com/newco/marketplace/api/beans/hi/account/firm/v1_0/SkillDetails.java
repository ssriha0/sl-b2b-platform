package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XmlRootElement(name = "skills")
@XStreamAlias("skills")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillDetails {

	@XStreamImplicit(itemFieldName = "skill")
    private List<SkillDetail> skill;

	public List<SkillDetail> getSkill() {
		return skill;
	}

	public void setSkill(List<SkillDetail> skill) {
		this.skill = skill;
	}
	
	
}
