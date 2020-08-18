/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of 
 * the category 
 * @author Infosys
 *
 */
@XStreamAlias("category")
public class ProviderCategory {

	@XStreamAlias("root")   
	@XStreamAsAttribute()   
	private String root;
	
	@XStreamAlias("id")   
	@XStreamAsAttribute()   
	private Integer rootId;
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="skill")
	private List<ProviderSkill> skillList;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public List<ProviderSkill> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<ProviderSkill> skillList) {
		this.skillList = skillList;
	}

	public Integer getRootId() {
		return rootId;
	}

	public void setRootId(Integer rootId) {
		this.rootId = rootId;
	}

}
