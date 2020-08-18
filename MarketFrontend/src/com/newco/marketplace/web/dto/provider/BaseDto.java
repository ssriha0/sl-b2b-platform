/**
 * 
 */
package com.newco.marketplace.web.dto.provider;

import com.newco.marketplace.web.dto.SerializedBaseDTO;


/**
 * @author KSudhanshu
 *
 */
public class BaseDto extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1478477189420680390L;
	private String id;
	private String fullResoueceName;
	private boolean primaryInd;
	private boolean mktPlaceInd;
	private Integer adminResourceId =-1;
	private Integer loggedResourceId = -1;

	public Integer getAdminResourceId() {
		return adminResourceId;
	}

	public void setAdminResourceId(Integer adminResourceId) {
		this.adminResourceId = adminResourceId;
	}

	public boolean isPrimaryInd() {
		return primaryInd;
	}

	public void setPrimaryInd(boolean primaryInd) {
		this.primaryInd = primaryInd;
	}

	public boolean isMktPlaceInd() {
		return mktPlaceInd;
	}

	public void setMktPlaceInd(boolean mktPlaceInd) {
		this.mktPlaceInd = mktPlaceInd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLoggedResourceId() {
		return loggedResourceId;
	}

	public void setLoggedResourceId(Integer loggedResourceId) {
		this.loggedResourceId = loggedResourceId;
	}

	public String getFullResoueceName() {
		return fullResoueceName;
	}

	public void setFullResoueceName(String fullResoueceName) {
		this.fullResoueceName = fullResoueceName;
	} 

}
