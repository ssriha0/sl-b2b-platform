package com.servicelive.orderfulfillment.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement()
@XmlSeeAlso(value = {ServiceOrder.class, SOContact.class,
			SOLogging.class, SONote.class,
			SOPart.class, SOPrice.class, SOTask.class,
			RoutedProvider.class,SOElementCollection.class, SOOnSiteVisit.class,SOWorkflowControls.class})
@MappedSuperclass()
public abstract class SOElement implements Serializable {
	
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = -7184105455539071307L;
	
	// validate this
	public List<String> validate() {
		return Collections.emptyList();
	}
	
	// copy from another element
	public void assign(SOElement se) {
		if (se.getTypeName().equals(getTypeName())) {
		}
	}
	
	// update service order
	public void update(SOElement se) 	{
		if (se.getTypeName().equals("SERVICE_ORDER")) {
		}
	}

	public String getTypeName() {
		return this.getClass().getName();
	}

}
