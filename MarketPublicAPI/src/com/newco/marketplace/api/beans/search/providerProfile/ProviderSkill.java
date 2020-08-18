/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.search.types.Skill;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a bean class for storing all information of 
 * the Skill 
 * @author Infosys
 *
 */
@XStreamAlias("skill")
public class ProviderSkill {
 
	@XStreamOmitField
	private Integer level;
	
	@XStreamAlias("name")
	@XStreamAsAttribute()  
	private String name;
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="type")
	List<Type> type = new ArrayList<Type>();

	@OptionalParam
	@XStreamImplicit(itemFieldName="skill")  
	List<ProviderSkill> skill = new ArrayList<ProviderSkill>();
	
	public ProviderSkill() {
		
	}
	
	public ProviderSkill(Skill proSkill) {		
		this.setLevel(proSkill.getLevel());
		this.setName(proSkill.getName());
		String skillType = "";
		if (null != proSkill.getType() && (!proSkill.getType().isEmpty())) {
			for (String typeStr : proSkill.getType()) {
				Type typeObj = new Type(typeStr);	
				//skillType = skillType + type + "|";
				type.add(typeObj);
			}
		}
		if (!("".equals(skillType))) {
			//this.setTypes(skillType.substring(0, skillType.length() - 1));
			Type typeObj = new Type(skillType.substring(0, skillType.length() - 1));	
			type.add(typeObj);
		}	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Type> getType() {
		return type;
	}

	public void setType(List<Type> type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public List<ProviderSkill> getProviderSkill(){
		return skill;
	}
	
	public void setProviderSkill(ProviderSkill skill){
		this.skill.add(skill);
	}
}
