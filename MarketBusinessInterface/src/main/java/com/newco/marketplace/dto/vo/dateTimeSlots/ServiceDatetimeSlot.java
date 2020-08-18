package com.newco.marketplace.dto.vo.dateTimeSlots;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceDatetimeSlot")
public class ServiceDatetimeSlot {

       @XStreamAlias("serviceStartDate")
       private String serviceStartDate;
       
       @XStreamAlias("serviceEndDate")
       private String serviceEndDate;
       
       @XStreamAlias("serviceStartTime")
       private String serviceStartTime;
       
       @XStreamAlias("serviceEndTime")
       private String serviceEndTime;
       
       @XStreamAlias("timeZone")
       private String timeZone;
       
       @XStreamAlias("slotSeleted")
       private String slotSeleted;
       
       @OptionalParam
       @XStreamAlias("preferenceInd")
       private String preferenceInd;

       public String getServiceStartDate() {
              return serviceStartDate;
       }

       public void setServiceStartDate(String serviceStartDate) {
              this.serviceStartDate = serviceStartDate;
       }

       public String getServiceEndDate() {
              return serviceEndDate;
       }

       public void setServiceEndDate(String serviceEndDate) {
              this.serviceEndDate = serviceEndDate;
       }

       public String getSlotSeleted() {
              return slotSeleted;
       }

       public void setSlotSeleted(String slotSeleted) {
              this.slotSeleted = slotSeleted;
       }

       public String getPreferenceInd() {
              return preferenceInd;
       }

       public void setPreferenceInd(String preferenceInd) {
              this.preferenceInd = preferenceInd;
       }

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
       
       


       

}

