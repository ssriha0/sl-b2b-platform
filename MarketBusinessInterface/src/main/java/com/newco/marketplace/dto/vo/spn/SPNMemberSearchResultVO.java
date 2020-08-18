package com.newco.marketplace.dto.vo.spn;

import java.util.List;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

public class SPNMemberSearchResultVO extends SerializableBaseVO {

	private static final long serialVersionUID = 6760009741341272610L;
	private Integer vendorId;
	private Integer resourceId;
	private Integer spnNetworkId;
	private String firstName;
    private String lastName;
    private Integer spnStatusId;
    private Integer servideOrdersCompleted;
    private String resourceZip;
    private Integer marketId;
    private boolean hasDocs;
    private Integer hasDocsInt;
    private List<DocumentVO> documents;
    
	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	/**
	 * @return the spnNetworkId
	 */
	public Integer getSpnNetworkId() {
		return spnNetworkId;
	}
	/**
	 * @param spnNetworkId the spnNetworkId to set
	 */
	public void setSpnNetworkId(Integer spnNetworkId) {
		this.spnNetworkId = spnNetworkId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the spnStatusId
	 */
	public Integer getSpnStatusId() {
		return spnStatusId;
	}
	/**
	 * @param spnStatusId the spnStatusId to set
	 */
	public void setSpnStatusId(Integer spnStatusId) {
		this.spnStatusId = spnStatusId;
	}
	/**
	 * @return the servideOrdersCompleted
	 */
	public Integer getServideOrdersCompleted() {
		return servideOrdersCompleted;
	}
	/**
	 * @param servideOrdersCompleted the servideOrdersCompleted to set
	 */
	public void setServideOrdersCompleted(Integer servideOrdersCompleted) {
		this.servideOrdersCompleted = servideOrdersCompleted;
	}
	/**
	 * @return the resourceZip
	 */
	public String getResourceZip() {
		return resourceZip;
	}
	/**
	 * @param resourceZip the resourceZip to set
	 */
	public void setResourceZip(String resourceZip) {
		this.resourceZip = resourceZip;
	}
	/**
	 * @return the marketId
	 */
	public Integer getMarketId() {
		return marketId;
	}
	/**
	 * @param marketId the marketId to set
	 */
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	/**
	 * @return the hasDocs
	 */
	public boolean isHasDocs() {
		return hasDocs;
	}
	/**
	 * @param hasDocs the hasDocs to set
	 */
	public void setHasDocs(boolean hasDocs) {
		this.hasDocs = hasDocs;
	}
	/**
	 * @return the hasDocsInt
	 */
	public Integer getHasDocsInt() {
		return hasDocsInt;
	}
	/**
	 * @param hasDocsInt the hasDocsInt to set
	 */
	public void setHasDocsInt(Integer hasDocsInt) {
		this.hasDocsInt = hasDocsInt;
		if (hasDocsInt.intValue() > 0) {
			this.hasDocs = true;
		} else {
			this.hasDocs = false;
		}
	}
	/**
	 * @return the documents
	 */
	public List<DocumentVO> getDocuments() {
		return documents;
	}
	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<DocumentVO> documents) {
		this.documents = documents;
	}
    
    
}
