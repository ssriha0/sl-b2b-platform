package com.servicelive.domain.spn.network;

import java.io.Serializable;
import java.util.Date;



/**
 * Class to hold exceptions types,gracePeriod values (for SL_18018)
 *
 */
public class RequirementVO  implements Serializable
{

	private static final long serialVersionUID = 5254692630787236707L;
	private String value;
	private String descr;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	
	
	
	
}
