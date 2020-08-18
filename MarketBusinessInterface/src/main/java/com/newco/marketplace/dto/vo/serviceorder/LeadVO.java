/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;



/**
 * Value object for Service Order work flow  state transitions
 * 
 * @author langara
 *
 */
public class LeadVO extends SerializableBaseVO
{
    /**
	 * 
	 */
	private Integer vendorId;
	
	private String staleAfter;
	
	private String leadId;
	
	private Date matchedDate;
	
	private Date historyDate;
	
	private Integer difference;
	

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getStaleAfter() {
		return staleAfter;
	}

	public void setStaleAfter(String staleAfter) {
		this.staleAfter = staleAfter;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public Date getMatchedDate() {
		return matchedDate;
	}

	public void setMatchedDate(Date matchedDate) {
		this.matchedDate = matchedDate;
	}

	public Date getHistoryDate() {
		return historyDate;
	}

	public void setHistoryDate(Date historyDate) {
		this.historyDate = historyDate;
	}

	public Integer getDifference() {
		return difference;
	}

	public void setDifference(Integer difference) {
		this.difference = difference;
	}

	 
	
	

}//end class