package com.servicelive.orderfulfillment.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;

@MappedSuperclass
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SOScheduleDate extends SOElement{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1053514758611267654L;
	
	private static final DateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Transient
	private Date serviceDate1;
	
	@Transient
	private Date serviceDate2;
	
	@Transient
	private String serviceTimeStart;
	
	@Transient
	private String serviceTimeEnd;
	
	@Transient
	private Date rescheduleServiceDate1INGMT;
	
	@Transient
	private Date rescheduleServiceDate2INGMT;
	
	@Transient
	private String serviceLocationTimeZone;
	
	@Column(name = "service_date1")
	private Date serviceDateGMT1;
	
	@Column(name = "service_date2")
	private Date serviceDateGMT2;
	
	@Column(name = "service_time_start")
	private String serviceTimeStartGMT;
	
	@Column(name = "service_time_end")
	private String serviceTimeEndGMT;

    public int compareTo(SOScheduleDate o) {
		return new CompareToBuilder().append(getServiceDateAsString(serviceDate1), getServiceDateAsString(o.serviceDate1))
				.append(getServiceDateAsString(serviceDate2), getServiceDateAsString(o.serviceDate2))
				.append(serviceTimeEnd, o.serviceTimeEnd)
				.append(serviceTimeStart, o.serviceTimeStart).toComparison();
	}

	public boolean equals(Object aThat){
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof SOScheduleDate) ) return false;
	
	    SOScheduleDate o = (SOScheduleDate)aThat;
	    return new EqualsBuilder().append(getServiceDateAsString(serviceDate1), getServiceDateAsString(o.serviceDate1))
					.append(getServiceDateAsString(serviceDate2), getServiceDateAsString(o.serviceDate2))
					.append(serviceTimeEnd, o.serviceTimeEnd)
					.append(serviceTimeStart, o.serviceTimeStart).isEquals();
	}

	private String getServiceDateAsString(Date date){
		if(date == null) return "";
		return shortDateFormat.format(date);
	}
	
	public Date getServiceDate1() {
		return serviceDate1;
	}

	public Date getServiceDate2() {
		return serviceDate2;
	}
	
	public Date getServiceDateGMT1() {
		return serviceDateGMT1;
	}
	
	public Date getServiceDateGMT2() {
		return serviceDateGMT2;
	}

	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}

	public String getServiceTimeEndGMT() {
		return serviceTimeEndGMT;
	}

	public String getServiceTimeStart() {
		return serviceTimeStart;
	}
	
	public String getServiceTimeStartGMT() {
		return serviceTimeStartGMT;
	}
	
	public int hashCode(){
		return new HashCodeBuilder().append(serviceDate1)
						.append(serviceDate2)
						.append(serviceTimeEnd)
						.append(serviceTimeStart).toHashCode();
	}
	
	public void populateGMT(String serviceLocationTimeZone){
		this.serviceLocationTimeZone = serviceLocationTimeZone;
		populateServiceDate1GMT();
		populateServiceDate2GMT();
	}

	public void populateLocalTime(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
		populateServiceDate1LocationTime();
		populateServiceDate2LocationTime();
	}

	private void populateServiceDate1GMT(){
		if(this.serviceDate1 != null && StringUtils.isNotBlank(this.serviceLocationTimeZone) && StringUtils.isNotBlank(this.serviceTimeStart)){
			Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(this.serviceDate1, this.serviceTimeStart, TimeZone.getTimeZone(this.serviceLocationTimeZone));
			this.serviceDateGMT1 = TimeChangeUtil.getDate(startDateTime, TimeZone.getTimeZone("GMT"));
			this.serviceTimeStartGMT = TimeChangeUtil.getTimeString(startDateTime, TimeZone.getTimeZone("GMT"));
		}		
	}

	private void populateServiceDate1LocationTime() {
		if(this.serviceDateGMT1 != null && StringUtils.isNotBlank(this.serviceLocationTimeZone) && StringUtils.isNotBlank(this.serviceTimeStartGMT)){
			Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(this.serviceDateGMT1, this.serviceTimeStartGMT, TimeZone.getTimeZone("GMT"));
			this.serviceDate1 = TimeChangeUtil.getDate(startDateTime, TimeZone.getTimeZone(this.serviceLocationTimeZone));
			this.serviceTimeStart = TimeChangeUtil.getTimeString(startDateTime, TimeZone.getTimeZone(this.serviceLocationTimeZone));
		}		
	}

	private void populateServiceDate2GMT(){
		if(this.serviceDate2 != null && StringUtils.isNotBlank(this.serviceLocationTimeZone) && StringUtils.isNotBlank(this.serviceTimeEnd)){
			Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(this.serviceDate2, this.serviceTimeEnd, TimeZone.getTimeZone(this.serviceLocationTimeZone));
			this.serviceDateGMT2 = TimeChangeUtil.getDate(startDateTime, TimeZone.getTimeZone("GMT"));
			this.serviceTimeEndGMT = TimeChangeUtil.getTimeString(startDateTime, TimeZone.getTimeZone("GMT"));
		}		
	}

	private void populateServiceDate2LocationTime() {
		if(this.serviceDateGMT2 != null && StringUtils.isNotBlank(this.serviceLocationTimeZone) && StringUtils.isNotBlank(this.serviceTimeEndGMT)){
			Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(this.serviceDateGMT2, this.serviceTimeEndGMT, TimeZone.getTimeZone("GMT"));
			this.serviceDate2 = TimeChangeUtil.getDate(startDateTime, TimeZone.getTimeZone(this.serviceLocationTimeZone));
			this.serviceTimeEnd = TimeChangeUtil.getTimeString(startDateTime, TimeZone.getTimeZone(this.serviceLocationTimeZone));
		}		
	}

	public void setServiceDate1(Date serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	public void setServiceDate2(Date serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}

	public void setServiceDateGMT1(Date serviceDateGMT1) {
		this.serviceDateGMT1 = serviceDateGMT1;
	}

	public void setServiceDateGMT2(Date serviceDateGMT2) {
		this.serviceDateGMT2 = serviceDateGMT2;
	}

	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}

	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}

	public void setServiceTimeEndGMT(String serviceTimeEndGMT) {
		this.serviceTimeEndGMT = serviceTimeEndGMT;
	}

	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}
	
	public void setServiceTimeStartGMT(String serviceTimeStartGMT) {
		this.serviceTimeStartGMT = serviceTimeStartGMT;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

    public String getDateRange(){
        return (null != serviceDate2)? getServiceDateAsString(serviceDate1) + " - " + getServiceDateAsString(serviceDate2) : getServiceDateAsString(serviceDate1);
    }

    public String getTimeRange(){
        return (StringUtils.isNotBlank(serviceTimeEnd))? serviceTimeStart + " - " + serviceTimeEnd : serviceTimeStart;        
    }

	public Date getRescheduleServiceDate1INGMT() {
		return rescheduleServiceDate1INGMT;
	}

	public void setRescheduleServiceDate1INGMT(Date rescheduleServiceDate1INGMT) {
		this.rescheduleServiceDate1INGMT = rescheduleServiceDate1INGMT;
	}

	public Date getRescheduleServiceDate2INGMT() {
		return rescheduleServiceDate2INGMT;
	}

	public void setRescheduleServiceDate2INGMT(Date rescheduleServiceDate2INGMT) {
		this.rescheduleServiceDate2INGMT = rescheduleServiceDate2INGMT;
	}
    

}
