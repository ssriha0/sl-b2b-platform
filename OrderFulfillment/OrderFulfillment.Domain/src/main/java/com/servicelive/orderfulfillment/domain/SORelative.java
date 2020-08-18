package com.servicelive.orderfulfillment.domain;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class serves as a base class for all entities that are related to
 * {@link ServiceOrder};
 * @author sahmed
 *
 */
public abstract class SORelative extends SOBase {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -2948203165334809285L;
	
	@XmlTransient
	public abstract void setServiceOrder(ServiceOrder serviceOrder);
	public abstract ServiceOrder getServiceOrder();

	/**
	 * "Class defined" callback method invoked by the {@code JAXB}
	 * {@link Unmarshaller} after it has unmarshalled all the properties for
	 * this object
	 * 
	 * @param unmarshaller A reference to the {@link Unmarshaller}}
	 * @param parent A reference to the parent
	 */
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		if (parent instanceof ServiceOrder) {
			this.setServiceOrder((ServiceOrder)parent);
		}
	}
}
