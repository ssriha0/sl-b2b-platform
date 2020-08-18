/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author sldev
 *
 */
public class MarketMakerProviderResponse extends SerializableBaseVO {

	private static final long serialVersionUID = 3525453580921354324L;
	
	//fields from mkt_maker_providers_response table
	private String soId;
	private Integer resourceId;
	private String mktMakerComments;
	private String callStatusId;
	private String callStatusDesc;
	private String modifyingAdmin;
	private Timestamp modifiedDateTime;

	
	public MarketMakerProviderResponse() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the mktMakerComments
	 */
	public String getMktMakerComments() {
		return mktMakerComments;
	}


	/**
	 * @param mktMakerComments the mktMakerComments to set
	 */
	public void setMktMakerComments(String mktMakerComments) {
		this.mktMakerComments = mktMakerComments;
	}


	/**
	 * @return the callStatusId
	 */
	public String getCallStatusId() {
		return callStatusId;
	}


	/**
	 * @param callStatusId the callStatusId to set
	 */
	public void setCallStatusId(String callStatusId) {
		this.callStatusId = callStatusId;
	}


	/**
	 * @return the callStatusDesc
	 */
	public String getCallStatusDesc() {
		return callStatusDesc;
	}


	/**
	 * @param callStatusDesc the callStatusDesc to set
	 */
	public void setCallStatusDesc(String callStatusDesc) {
		this.callStatusDesc = callStatusDesc;
	}


	/**
	 * @return the modifyingAdmin
	 */
	public String getModifyingAdmin() {
		return modifyingAdmin;
	}


	/**
	 * @param modifyingAdmin the modifyingAdmin to set
	 */
	public void setModifyingAdmin(String modifyingAdmin) {
		this.modifyingAdmin = modifyingAdmin;
	}


	/**
	 * @return the modifiedDateTime
	 */
	public Timestamp getModifiedDateTime() {
		return modifiedDateTime;
	}


	/**
	 * @param modifiedDateTime the modifiedDateTime to set
	 */
	public void setModifiedDateTime(Timestamp modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}


	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}


	/**
	 * @param soId the soId to set
	 */
	public void setSoId(String soId) {
		this.soId = soId;
	}


	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}


	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

}
