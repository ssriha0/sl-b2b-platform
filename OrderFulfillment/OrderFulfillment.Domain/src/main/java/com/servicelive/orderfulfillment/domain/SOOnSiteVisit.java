package com.servicelive.orderfulfillment.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@Entity()
@Table(name = "so_onsite_visit")
public class SOOnSiteVisit extends SOChild {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5142220725494865850L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="visit_id")
	private Long visitId;
	
	@Column(name = "resource_id")
	private Long resourceId;
	
	@Column(name = "arrival_date")
	private Date arrivalDate;
	
	@Column(name = "departure_date")
	private Date departureDate;
	
	@Column(name = "arrival_input_method")
	private Integer arrivalInputMethod;
	
	@Column(name = "departure_resource_id")
	private Long departureResourceId;
	
	@Column(name = "departure_input_method")
	private Integer departureInputMethod;
	
	//FIXME in the database this is created as varchar even though we are only storing integers
	@Column(name = "departure_condition")
	private String departureCondition;
	
	@Column(name = "ivr_create_date")
	private Date ivrCreateDate;
	
	@Column(name = "delete_ind")
	private Integer deleteIndicator;
	
	// Added the next 4 fields as part of Mobile API for Time On Site	
	@Column(name = "arrival_latitude")
	private Double arrivalLatitude;
	
	@Column(name = "arrival_longitude")
	private Double arrivalLongitude;
	
	@Column(name = "departure_latitude")
	private Double departureLatitude;
	
	@Column(name = "departure_longitude")
	private Double departureLongitude;
	
	@Column(name = "arrival_reason")
	private String arrivalReason;

	@Column(name = "departure_reason")
	private String departureReason;
	
	
	public Date getArrivalDate() {
		return arrivalDate;
	}

	public Integer getArrivalInputMethod() {
		return arrivalInputMethod;
	}

	public Integer getDeleteIndicator() {
		return deleteIndicator;
	}

	public String getDepartureCondition() {
		return departureCondition;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public Integer getDepartureInputMethod() {
		return departureInputMethod;
	}

	public Long getDepartureResourceId() {
		return departureResourceId;
	}

	public Date getIvrCreateDate() {
		return ivrCreateDate;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public Long getVisitId() {
		return visitId;
	}

	public Double getArrivalLatitude() {
		return arrivalLatitude;
	}

	public Double getArrivalLongitude() {
		return arrivalLongitude;
	}

	public Double getDepartureLatitude() {
		return departureLatitude;
	}

	public Double getDepartureLongitude() {
		return departureLongitude;
	}

	public String getArrivalReason() {
		return arrivalReason;
	}
	
	public String getDepartureReason() {
		return departureReason;
	}
	
	@XmlElement()
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@XmlElement()
	public void setArrivalInputMethod(Integer arrivalInputMethod) {
		this.arrivalInputMethod = arrivalInputMethod;
	}

	@XmlElement()
	public void setDeleteIndicator(Integer deleteIndicator) {
		this.deleteIndicator = deleteIndicator;
	}

	@XmlElement()
	public void setDepartureCondition(String departureCondition) {
		this.departureCondition = departureCondition;
	}

	@XmlElement()
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	@XmlElement()
	public void setDepartureInputMethod(Integer departureInputMethod) {
		this.departureInputMethod = departureInputMethod;
	}

	@XmlElement()
	public void setDepartureResourceId(Long departureResourceId) {
		this.departureResourceId = departureResourceId;
	}

	@XmlElement()
	public void setIvrCreateDate(Date ivrCreateDate) {
		this.ivrCreateDate = ivrCreateDate;
	}

	@XmlElement()
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@XmlElement()
	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	@XmlElement()
	public void setArrivalLatitude(Double arrivalLatitude) {
		this.arrivalLatitude = arrivalLatitude;
	}

	@XmlElement()
	public void setArrivalLongitude(Double arrivalLongitude) {
		this.arrivalLongitude = arrivalLongitude;
	}

	@XmlElement()
	public void setDepartureLatitude(Double departureLatitude) {
		this.departureLatitude = departureLatitude;
	}

	@XmlElement()
	public void setDepartureLongitude(Double departureLongitude) {
		this.departureLongitude = departureLongitude;
	}
	
	@XmlElement()
	public void setArrivalReason(String arrivalReason) {
		this.arrivalReason = arrivalReason;
	}
	
	@XmlElement()
	public void setDepartureReason(String departureReason) {
		this.departureReason = departureReason;
	}

}
