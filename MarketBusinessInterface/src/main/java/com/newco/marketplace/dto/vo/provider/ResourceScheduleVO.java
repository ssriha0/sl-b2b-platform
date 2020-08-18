package com.newco.marketplace.dto.vo.provider;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;
/**
 * Resource schedule vo for team members 
 * 
 * @version
 * @author Kapil Sharma
 *
 */
public class ResourceScheduleVO extends SerializableBaseVO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4509031289682189573L;
	private int resourceId=-1;
	private int timeZoneId=-1;
	private Timestamp monStart = null;
	private Timestamp monEnd = null;
	private Timestamp tueStart =null;
	private Timestamp tueEnd = null;
	private Timestamp wedStart = null;
	private Timestamp wedEnd = null;
	private Timestamp thuStart =null;
	private Timestamp thuEnd =null;
	private Timestamp friStart =null;
	private Timestamp friEnd =null;
	private Timestamp satStart = null;
	private Timestamp satEnd = null;
	private Timestamp sunStart =null;
	private Timestamp sunEnd = null;
	/**
	 * @return the friEnd
	 */
	public Timestamp getFriEnd() {
		return friEnd;
	}
	/**
	 * @param friEnd the friEnd to set
	 */
	public void setFriEnd(Timestamp friEnd) {
		this.friEnd = friEnd;
	}
	/**
	 * @return the friStart
	 */
	public Timestamp getFriStart() {
		return friStart;
	}
	/**
	 * @param friStart the friStart to set
	 */
	public void setFriStart(Timestamp friStart) {
		this.friStart = friStart;
	}
	
	/**
	 * @return the resourceId
	 */
	public int getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the monEnd
	 */
	public Timestamp getMonEnd() {
		return monEnd;
	}
	/**
	 * @param monEnd the monEnd to set
	 */
	public void setMonEnd(Timestamp monEnd) {
		this.monEnd = monEnd;
	}
	/**
	 * @return the monStart
	 */
	public Timestamp getMonStart() {
		return monStart;
	}
	/**
	 * @param monStart the monStart to set
	 */
	public void setMonStart(Timestamp monStart) {
		this.monStart = monStart;
	}
	/**
	 * @return the satEnd
	 */
	public Timestamp getSatEnd() {
		return satEnd;
	}
	/**
	 * @param satEnd the satEnd to set
	 */
	public void setSatEnd(Timestamp satEnd) {
		this.satEnd = satEnd;
	}
	/**
	 * @return the satStart
	 */
	public Timestamp getSatStart() {
		return satStart;
	}
	/**
	 * @param satStart the satStart to set
	 */
	public void setSatStart(Timestamp satStart) {
		this.satStart = satStart;
	}
	/**
	 * @return the sunEnd
	 */
	public Timestamp getSunEnd() {
		return sunEnd;
	}
	/**
	 * @param sunEnd the sunEnd to set
	 */
	public void setSunEnd(Timestamp sunEnd) {
		this.sunEnd = sunEnd;
	}
	/**
	 * @return the sunStart
	 */
	public Timestamp getSunStart() {
		return sunStart;
	}
	/**
	 * @param sunStart the sunStart to set
	 */
	public void setSunStart(Timestamp sunStart) {
		this.sunStart = sunStart;
	}
	/**
	 * @return the thuEnd
	 */
	public Timestamp getThuEnd() {
		return thuEnd;
	}
	/**
	 * @param thuEnd the thuEnd to set
	 */
	public void setThuEnd(Timestamp thuEnd) {
		this.thuEnd = thuEnd;
	}
	/**
	 * @return the thuStart
	 */
	public Timestamp getThuStart() {
		return thuStart;
	}
	/**
	 * @param thuStart the thuStart to set
	 */
	public void setThuStart(Timestamp thuStart) {
		this.thuStart = thuStart;
	}
	
	/**
	 * @return the timeZoneId
	 */
	public int getTimeZoneId() {
		return timeZoneId;
	}
	/**
	 * @param timeZoneId the timeZoneId to set
	 */
	public void setTimeZoneId(int timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	/**
	 * @return the tueEnd
	 */
	public Timestamp getTueEnd() {
		return tueEnd;
	}
	/**
	 * @param tueEnd the tueEnd to set
	 */
	public void setTueEnd(Timestamp tueEnd) {
		this.tueEnd = tueEnd;
	}
	/**
	 * @return the tueStart
	 */
	public Timestamp getTueStart() {
		return tueStart;
	}
	/**
	 * @param tueStart the tueStart to set
	 */
	public void setTueStart(Timestamp tueStart) {
		this.tueStart = tueStart;
	}
	/**
	 * @return the wedEnd
	 */
	public Timestamp getWedEnd() {
		return wedEnd;
	}
	/**
	 * @param wedEnd the wedEnd to set
	 */
	public void setWedEnd(Timestamp wedEnd) {
		this.wedEnd = wedEnd;
	}
	/**
	 * @return the wedStart
	 */
	public Timestamp getWedStart() {
		return wedStart;
	}
	/**
	 * @param wedStart the wedStart to set
	 */
	public void setWedStart(Timestamp wedStart) {
		this.wedStart = wedStart;
	}
	

}
