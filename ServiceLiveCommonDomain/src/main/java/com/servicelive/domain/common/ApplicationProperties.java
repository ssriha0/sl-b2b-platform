package com.servicelive.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table(name="application_properties")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationProperties extends LoggableBaseDomain {

	private static final long serialVersionUID = 20090408L;

    @Id
    @Column(name="app_key", length=255)
	private String key;
	
    @Column(name="app_value", length=255)
	private String value;
	
    @Column(name="app_desc", length=255)
	private String description;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
