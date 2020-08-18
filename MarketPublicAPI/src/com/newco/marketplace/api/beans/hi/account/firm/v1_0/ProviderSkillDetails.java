package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name ="provider")
@XStreamAlias("provider")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderSkillDetails {

	@XStreamAlias("providerId")
    private String providerId;
	
	@XStreamAlias("skills")
	@XmlElement(name="skills")
    private SkillDetails skills;

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public SkillDetails getSkills() {
		return skills;
	}

	public void setSkills(SkillDetails skills) {
		this.skills = skills;
	}
}
