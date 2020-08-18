package com.newco.marketplace.api.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a generic bean class for storing identification information.
 * @author Infosys
 *
 */
@XStreamAlias("identification")
public class Identification {
	
	public static final String ID_TYPE_PROVIDER_RESOURCE_ID = "ProviderResourceId";
	public static final String ID_TYPE_BUYER_RESOURCE_ID = "BuyerResourceId";
	
	@XStreamAlias("id")
	private Integer id;

	@XStreamAlias("type")   
	@XStreamAsAttribute()   
	private String type;
	
	@XStreamAlias("UserName")
	private String userName;
	
	@XStreamAlias("FullName")
	private String fullName;
	
	@XStreamAlias("EntityId")
	private Integer entityId;
	 
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}


	


}
