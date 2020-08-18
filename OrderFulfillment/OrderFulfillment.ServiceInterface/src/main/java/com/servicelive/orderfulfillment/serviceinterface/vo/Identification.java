package com.servicelive.orderfulfillment.serviceinterface.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

@XmlRootElement(name = "identification")
public class Identification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4891774862616124085L;

	@XmlEnum(String.class)
	public enum EntityType { BUYER, PROVIDER, SLADMIN };

	private EntityType entityType;
	private Integer roleId;
	
	private Long companyId;
	private Long resourceId;
	
	private String username;
	private String fullname;
	
	@XmlElement(name = "username")
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
		
	@XmlElement(name = "fullname")
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getFullname() {
		return fullname;
	}	
	
	@XmlElement(name = "roleId")
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	
	@XmlElement(name = "companyId")
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	
	@XmlElement(name = "resourceId")
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	
	@XmlElement(name="entityType")
	public EntityType getEntityType() {
		return entityType;
	}
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	
	@XmlTransient
	public boolean isBuyer() {
		return (this.entityType == EntityType.BUYER);
	}
	
	@XmlTransient
	public boolean isProvider() {
		return (this.entityType == EntityType.PROVIDER);
	}
	
	@XmlTransient
	public boolean isAdmin() {
		return (this.entityType == EntityType.SLADMIN);
	}
	
	@XmlTransient
	public Long getEntityId() {
			return getResourceId();
	}

	@XmlTransient
	public Long getBuyerId() {
		if (isBuyer())
			return getCompanyId();
		else
			return null;
	}

	@XmlTransient
	public Long getBuyerResourceId() {
		if (isBuyer())
			return getResourceId();
		else
			return null;
	}
	
	@XmlTransient
	public Long getProviderId() {
		if (isProvider())
			return getCompanyId();
		else
			return null;
	}

	@XmlTransient
	public Long getProviderResourceId() {
		if (isProvider())
			return getResourceId();
		else
			return null;
	}
	
	public boolean equals(Object aThat){
		if(this == aThat) return true;
		if(!(aThat instanceof Identification)) return false;
		Identification o = (Identification)aThat;
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
