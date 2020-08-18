package com.servicelive.domain.spn.network;

import java.io.Serializable;



/**
 * Class to hold exceptions types,gracePeriod values (for SL_18018)
 *
 */
public class ExceptionsAndGracePeriodVO  implements Serializable
{

	private static final long serialVersionUID = 5254692630787236707L;
	private int id;
	private String descr;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	
	
	
}
