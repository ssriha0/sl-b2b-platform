package com.servicelive.orderfulfillment.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_service_datetime_slot")
@XmlRootElement()
public class SOServiceDatetimeSlot  extends SOChild implements Comparable<SOServiceDatetimeSlot> {
	
	private static final long serialVersionUID = -3864786776912014544L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_service_datetime_slot_id")
	private Integer soServiceDateTime; 

	@Column(name="service_start_date")
	private Date serviceStartDateDB;
	
	@Column(name="service_start_time")
	private String serviceStartTimeDB;
	
	@Column(name="service_end_date")
	private Date serviceEndDateDB;
	
	@Column(name="service_end_time")
	private String serviceEndTimeDB;
	
	@Transient
	private Date serviceStartDate;
	
	@Transient
	private String serviceStartTime;
	
	@Transient
	private Date serviceEndDate;
	
	@Transient
	private String serviceEndTime;
	
	@Column(name="slot_seleted_ind")
	private Integer slotSeletedInd;
	
	@Column(name="preference_ind")
	private Integer preferenceInd;

	public Integer getSoServiceDateTime() {
		return soServiceDateTime;
	}

	@XmlElement
	public void setSoServiceDateTime(Integer soServiceDateTime) {
		this.soServiceDateTime = soServiceDateTime;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	@XmlElement
	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	@XmlElement
	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	@XmlElement
	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	@XmlElement
	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public int compareTo(SOServiceDatetimeSlot newObj) {
		int comparedValue = 0;
		
		if (null != newObj && null !=newObj.getPreferenceInd() && null != this && null != this.getPreferenceInd()) {
			comparedValue = this.getPreferenceInd() - newObj.getPreferenceInd();//asc based on prefind
		} 

		/*if (null == newObj) {
			comparedValue = -1;
		} else {
			if (null != this.getServiceStartDate()) {
				comparedValue = (null != newObj.getServiceStartDate()) ? this.getServiceStartDate().compareTo(newObj.getServiceStartDate())
						: -1;
			} else if (null != this.getServiceStartDateDB()) {
				comparedValue = (null != newObj.getServiceStartDateDB()) ? this.getServiceStartDateDB().compareTo(
						newObj.getServiceStartDateDB()) : -1;
			}

			if (comparedValue == 0) {
				if (null != this.getServiceEndDate()) {
					comparedValue = null != newObj.getServiceEndDate() ? this.getServiceEndDate().compareTo(newObj.getServiceEndDate())
							: -1;
				} else if (null != this.getServiceEndDateDB()) {
					comparedValue = null != newObj.getServiceEndDateDB() ? this.getServiceEndDateDB().compareTo(
							newObj.getServiceEndDateDB()) : -1;
				}
			}
		}*/

		return comparedValue;
	}

	public Date getServiceStartDateDB() {
		return serviceStartDateDB;
	}

	public void setServiceStartDateDB(Date serviceStartDateDB) {
		this.serviceStartDateDB = serviceStartDateDB;
	}

	public String getServiceStartTimeDB() {
		return serviceStartTimeDB;
	}

	public void setServiceStartTimeDB(String serviceStartTimeDB) {
		this.serviceStartTimeDB = serviceStartTimeDB;
	}

	public Date getServiceEndDateDB() {
		return serviceEndDateDB;
	}

	public void setServiceEndDateDB(Date serviceEndDateDB) {
		this.serviceEndDateDB = serviceEndDateDB;
	}

	public String getServiceEndTimeDB() {
		return serviceEndTimeDB;
	}

	public void setServiceEndTimeDB(String serviceEndTimeDB) {
		this.serviceEndTimeDB = serviceEndTimeDB;
	}

	public Integer getSlotSeletedInd() {
		return slotSeletedInd;
	}

	public void setSlotSeletedInd(Integer slotSeletedInd) {
		this.slotSeletedInd = slotSeletedInd;
	}

	public Integer getPreferenceInd() {
		return preferenceInd;
	}

	public void setPreferenceInd(Integer preferenceInd) {
		this.preferenceInd = preferenceInd;
	}
}
