package com.newco.marketplace.api.beans.search.providerProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("type")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillType {
	@XStreamAlias("name")
	@XStreamAsAttribute()  
	private String name;
	
	public SkillType(){
		
	}
	
	public SkillType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
