package com.newco.marketplace.vo.mobile;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author Infosys
 * 
 */
public class MobileSOOnsiteVisitVO extends CommonVO {

	private static final long serialVersionUID = 1L;

	public MobileSOOnsiteVisitVO() {
	}

	private Long visitId;
	private String soId;
	private Integer resourceId;
	private Date arrivalDate;
	private Integer arrivalInputMethod;
	private Date departureDate;
	private Integer departureResourceId;
	private Integer departureInputMethod;
	private Integer departureCondition;
	private Date createdDate;
	private Date modifiedDate;
	private Date ivrcreatedate;
	private Integer deleteInd;
	private String modifiedBy;
	private Double arrivalLatitude;
	private Double arrivalLongitude;
	private Double departureLatitude;
	private Double departureLongitude;
	private Integer currentTripNo;
	private String arrivalReason;
	private String departureReason;
	private String tripSource;

	@Override
	public String toString() {
		return new ToStringBuilder(this)

		.append("soId", getSoId()).append("resourceId", getResourceId())
				.append("arrivalDate", getArrivalDate())
				.append("departureDate", getDepartureDate())
				.append("arrivalInputMethod", getArrivalInputMethod())
				.append("departureInputMethod", getDepartureInputMethod())
				.append("createdDate", getCreatedDate())
				.append("modifiedDate", getModifiedDate())
				.append("IVRCreateDate", getIvrcreatedate())
				.append("deleteInd", getDeleteInd()).toString();
	}

	/**
	 * @return the visitId
	 */
	public Long getVisitId() {
		return visitId;
	}

	/**
	 * @param visitId
	 *            the visitId to set
	 */
	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}

	/**
	 * @param soId
	 *            the soId to set
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
	 * @param resourceId
	 *            the resourceId to set
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
	 * @param arrivalDate
	 *            the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the arrivalInputMethod
	 */
	public Integer getArrivalInputMethod() {
		return arrivalInputMethod;
	}

	/**
	 * @param arrivalInputMethod
	 *            the arrivalInputMethod to set
	 */
	public void setArrivalInputMethod(Integer arrivalInputMethod) {
		this.arrivalInputMethod = arrivalInputMethod;
	}

	/**
	 * @return the departureDate
	 */
	public Date getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate
	 *            the departureDate to set
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * @return the departureResourceId
	 */
	public Integer getDepartureResourceId() {
		return departureResourceId;
	}

	/**
	 * @param departureResourceId
	 *            the departureResourceId to set
	 */
	public void setDepartureResourceId(Integer departureResourceId) {
		this.departureResourceId = departureResourceId;
	}

	/**
	 * @return the departureInputMethod
	 */
	public Integer getDepartureInputMethod() {
		return departureInputMethod;
	}

	/**
	 * @param departureInputMethod
	 *            the departureInputMethod to set
	 */
	public void setDepartureInputMethod(Integer departureInputMethod) {
		this.departureInputMethod = departureInputMethod;
	}

	/**
	 * @return the departureCondition
	 */
	public Integer getDepartureCondition() {
		return departureCondition;
	}

	/**
	 * @param departureCondition
	 *            the departureCondition to set
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
	 * @param createdDate
	 *            the createdDate to set
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
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the ivrcreatedate
	 */
	public Date getIvrcreatedate() {
		return ivrcreatedate;
	}

	/**
	 * @param ivrcreatedate
	 *            the ivrcreatedate to set
	 */
	public void setIvrcreatedate(Date ivrcreatedate) {
		this.ivrcreatedate = ivrcreatedate;
	}

	/**
	 * @return the deleteInd
	 */
	public Integer getDeleteInd() {
		return deleteInd;
	}

	/**
	 * @param deleteInd
	 *            the deleteInd to set
	 */
	public void setDeleteInd(Integer deleteInd) {
		this.deleteInd = deleteInd;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the arrivalLatitude
	 */
	public Double getArrivalLatitude() {
		return arrivalLatitude;
	}

	/**
	 * @param arrivalLatitude
	 *            the arrivalLatitude to set
	 */
	public void setArrivalLatitude(Double arrivalLatitude) {
		this.arrivalLatitude = arrivalLatitude;
	}

	/**
	 * @return the arrivalLongitude
	 */
	public Double getArrivalLongitude() {
		return arrivalLongitude;
	}

	/**
	 * @param arrivalLongitude
	 *            the arrivalLongitude to set
	 */
	public void setArrivalLongitude(Double arrivalLongitude) {
		this.arrivalLongitude = arrivalLongitude;
	}

	/**
	 * @return the departureLatitude
	 */
	public Double getDepartureLatitude() {
		return departureLatitude;
	}

	/**
	 * @param departureLatitude
	 *            the departureLatitude to set
	 */
	public void setDepartureLatitude(Double departureLatitude) {
		this.departureLatitude = departureLatitude;
	}

	/**
	 * @return the departureLongitude
	 */
	public Double getDepartureLongitude() {
		return departureLongitude;
	}

	/**
	 * @param departureLongitude
	 *            the departureLongitude to set
	 */
	public void setDepartureLongitude(Double departureLongitude) {
		this.departureLongitude = departureLongitude;
	}

	/**
	 * @return the currentTripNo
	 */
	public Integer getCurrentTripNo() {
		return currentTripNo;
	}

	/**
	 * @param currentTripNo
	 *  the currentTripNo to set
	 */
	public void setCurrentTripNo(Integer currentTripNo) {
		this.currentTripNo = currentTripNo;
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

	public String getTripSource() {
		return tripSource;
	}

	public void setTripSource(String tripSource) {
		this.tripSource = tripSource;
	}
	
}
