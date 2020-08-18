/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author rambewa
 *
 */
public class SOOnsiteVisitVO extends CommonVO{

	
	
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public SOOnsiteVisitVO() {
	}

	private Long visitId;
	private String soId;
	private Integer resourceId;
	private Date arrivalDate;
	private Date departureDate;
	private Integer arrivalInputMethod;
	private Integer departureInputMethod;
	private Integer departureResourceId;
	private Integer departureCondition;
	private Date createdDate;
	private Date modifiedDate;
	private Date ivrcreatedate;
    private Integer deleteInd;
	private Integer arrivalDateInd = 0;
    private Integer departureDateInd = 0;
	private String arrivalReason;
	private String departureReason;
	private String buyerId;
    
    public Integer getArrivalDateInd() {
		return arrivalDateInd;
	}

	public void setArrivalDateInd(Integer arrivalDateInd) {
		this.arrivalDateInd = arrivalDateInd;
	}

	public Integer getDepartureDateInd() {
		return departureDateInd;
	}

	public void setDepartureDateInd(Integer departureDateInd) {
		this.departureDateInd = departureDateInd;
	}

	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		    
			.append("soId", getSoId())
			.append("resourceId", getResourceId())
			.append("arrivalDate", getArrivalDate())
			.append("departureDate", getDepartureDate())
			.append("arrivalInputMethod",getArrivalInputMethod())
			.append("departureInputMethod",getDepartureInputMethod())	
            .append("createdDate", getCreatedDate())
            .append("modifiedDate", getModifiedDate() )
            .append("IVRCreateDate", getIvrcreatedate())
            .append("deleteInd", getDeleteInd())
			.toString();
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
	/**
	 * @return the arrivalDate
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}
	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	/**
	 * @return the departureDate
	 */
	public Date getDepartureDate() {
		return departureDate;
	}
	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	/**
	 * @return the arrivalInputMethod
	 */
	public Integer getArrivalInputMethod() {
		return arrivalInputMethod;
	}
	/**
	 * @param arrivalInputMethod the arrivalInputMethod to set
	 */
	public void setArrivalInputMethod(Integer arrivalInputMethod) {
		this.arrivalInputMethod = arrivalInputMethod;
	}
	/**
	 * @return the departureInputMethod
	 */
	public Integer getDepartureInputMethod() {
		return departureInputMethod;
	}
	/**
	 * @param departureInputMethod the departureInputMethod to set
	 */
	public void setDepartureInputMethod(Integer departureInputMethod) {
		this.departureInputMethod = departureInputMethod;
	}
	/**
	 * @return the departureResourceId
	 */
	public Integer getDepartureResourceId() {
		return departureResourceId;
	}
	/**
	 * @param departureResourceId the departureResourceId to set
	 */
	public void setDepartureResourceId(Integer departureResourceId) {
		this.departureResourceId = departureResourceId;
	}
	/**
	 * @return the departureCondition
	 */
	public Integer getDepartureCondition() {
		return departureCondition;
	}
	/**
	 * @param departureCondition the departureCondition to set
	 */
	public void setDepartureCondition(Integer departureCondition) {
		this.departureCondition = departureCondition;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public Long getVisitId() {
		return visitId;
	}
	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}


	public Date getIvrcreatedate() {
		return ivrcreatedate;
	}


	public void setIvrcreatedate(Date ivrcreatedate) {
		this.ivrcreatedate = ivrcreatedate;
	}

	public Integer getDeleteInd() {
		return deleteInd;
	}

	public void setDeleteInd(Integer deleteInd) {
		this.deleteInd = deleteInd;
	}

	/**
	 * @return the arrivalReason
	 */
	public String getArrivalReason() {
		return arrivalReason;
	}

	/**
	 * @param arrivalReason the arrivalReason to set
	 */
	public void setArrivalReason(String arrivalReason) {
		this.arrivalReason = arrivalReason;
	}

	/**
	 * @return the departureReason
	 */
	public String getDepartureReason() {
		return departureReason;
	}

	/**
	 * @param departureReason the departureReason to set
	 */
	public void setDepartureReason(String departureReason) {
		this.departureReason = departureReason;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	
}
