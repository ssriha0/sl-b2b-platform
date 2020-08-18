package com.servicelive.orderfulfillment.domain;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

public abstract class LeadRelative extends LeadBase {
	/** generated serialVersionUID */
	private static final long serialVersionUID = -2948203165334809285L;
	
	@XmlTransient
	public abstract void setLead(LeadHdr leadhdr);
	public abstract LeadHdr getLead();

	/**
	 * "Class defined" callback method invoked by the {@code JAXB}
	 * {@link Unmarshaller} after it has unmarshalled all the properties for
	 * this object
	 * 
	 * @param unmarshaller A reference to the {@link Unmarshaller}}
	 * @param parent A reference to the parent
	 */
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		if (parent instanceof LeadHdr) {
			this.setLead((LeadHdr)parent);
		}
	}
}
