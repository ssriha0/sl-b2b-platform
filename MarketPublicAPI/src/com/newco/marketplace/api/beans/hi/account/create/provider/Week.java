package com.newco.marketplace.api.beans.hi.account.create.provider;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("week")
public class Week {

	@XStreamAlias("weekDayName")
    private String weekDayName;
	
	@XStreamAlias("wholeDayAvailableInd")
    private String wholeDayAvailableInd;
	
	@XStreamAlias("startTime")
	private String startTime;
	
	@XStreamAlias("endTime")
	private String endTime;

    /**
     * Gets the value of the weekName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */

    public String getWeekDayName() {
		return weekDayName;
	}

	public void setWeekDayName(String weekDayName) {
		this.weekDayName = weekDayName;
	}



    public String getWholeDayAvailableInd() {
		return wholeDayAvailableInd;
	}

	public void setWholeDayAvailableInd(String wholeDayAvailableInd) {
		this.wholeDayAvailableInd = wholeDayAvailableInd;
	}

	/**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the enndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the enndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

}
