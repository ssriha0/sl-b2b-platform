package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("changeLog")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOTripChangeLog {
	
	@XStreamAlias("updatedComponent")
	private String updatedComponent;

	@XStreamAlias("changeComments")
	private String changeComments;
	
	
	/**
	 * @return the updatedComponent
	 */
	public String getUpdatedComponent() {
		return updatedComponent;
	}

	/**
	 * @param updatedComponent the updatedComponent to set
	 */
	public void setUpdatedComponent(String updatedComponent) {
		this.updatedComponent = updatedComponent;
	}

	public String getChangeComments() {
		return changeComments;
	}

	public void setChangeComments(String changeComments) {
		this.changeComments = changeComments;
	}

	
}
