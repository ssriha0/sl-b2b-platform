package com.newco.marketplace.api.beans.search.providerProfile;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("type")
public class Type {
	@XStreamAlias("name")
	@XStreamAsAttribute()  
	private String name;
	
	public Type(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
