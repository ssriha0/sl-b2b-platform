package com.newco.marketplace.api.beans.so.post;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing list of firm Ids for 
 * the SOPostFirmService
 * @author Infosys
 *
 */
@XStreamAlias("firmIds")
public class FirmList {
	
	@XStreamImplicit(itemFieldName="firmId")
	private List<Integer> firmId;

	public List<Integer> getFirmId() {
		return firmId;
	}

	public void setFirmId(List<Integer> firmId) {
		this.firmId = firmId;
	}




	
	
	




	
}
