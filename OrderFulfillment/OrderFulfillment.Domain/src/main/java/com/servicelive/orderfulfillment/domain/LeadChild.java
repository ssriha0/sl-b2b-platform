package com.servicelive.orderfulfillment.domain;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public class LeadChild extends LeadRelative{

private static final long serialVersionUID = -5644744945380061732L;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "sl_lead_id")
	protected LeadHdr lead;


	@Override
	public LeadHdr getLead(){
		return lead;
	}

	@Override
	@XmlTransient()
	public void setLead(LeadHdr lead) {
		this.lead = lead;
	}	

}
