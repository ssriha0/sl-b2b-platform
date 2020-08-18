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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

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
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillItem {
 
	@XStreamOmitField
	private Integer level;
	
	@XStreamAlias("name")
	@XStreamAsAttribute()  
	private String name;
	
	@XStreamAlias("id")
	@XStreamAsAttribute()  
	private Integer id;
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="type")
	List<SkillType> type = new ArrayList<SkillType>();

	@OptionalParam
	@XStreamImplicit(itemFieldName="skill")  
	List<SkillItem> skill = new ArrayList<SkillItem>();
	
	public SkillItem() {
		
	}
	
	public SkillItem(Skill proSkill) {		
		this.setLevel(proSkill.getLevel());
		this.setName(proSkill.getName());
		this.setId(proSkill.getNodeId());
		String skillType = "";
		if (null != proSkill.getType() && (!proSkill.getType().isEmpty())) {
			for (String typeStr : proSkill.getType()) {
				SkillType typeObj = new SkillType(typeStr);	
				//skillType = skillType + type + "|";
				type.add(typeObj);
			}
		}
		if (!("".equals(skillType))) {
			//this.setTypes(skillType.substring(0, skillType.length() - 1));
			SkillType typeObj = new SkillType(skillType.substring(0, skillType.length() - 1));	
			type.add(typeObj);
		}	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<SkillType> getType() {
		return type;
	}

	public void setType(List<SkillType> type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public List<SkillItem> getProviderSkill(){
		return skill;
	}
	
	public void setProviderSkill(SkillItem skill){
		this.skill.add(skill);
	}
}
