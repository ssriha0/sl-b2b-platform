package com.newco.marketplace.vo.provider;
import java.sql.Time;


public class ContactMethodPref extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4941808409560472608L;
	private int preferenceID =-1;
	private int contactId = -1;
	private int onHrPrimaryMethod = -1;
	private int onHrSecondaryMethod = -1;
	private int offHrPrimaryMethod = -1;
	private int offHrSecondaryMethod = -1;
	private Time monStart;
	private Time monEnd;
	private Time tueStart;
	private Time tueEnd;
	private Time wedStart;
	private Time wedEnd;
	private Time thurStart;
	private Time thurEnd;
	private Time friStart;
	private Time friEnd;
	private Time satStart;
	private Time satEnd;
	private Time sunStart;
	private Time sunEnd;
	private Time createdDate;
	private Time modifiedDate;
	/**
	 * @return the createdDate
	 */
	/**
	 * @return the contactId
	 */
	public int getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the createdDate
	 */
	public Time getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Time createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the friEnd
	 */
	public Time getFriEnd() {
		return friEnd;
	}
	/**
	 * @param friEnd the friEnd to set
	 */
	public void setFriEnd(Time friEnd) {
		this.friEnd = friEnd;
	}
	/**
	 * @return the friStart
	 */
	public Time getFriStart() {
		return friStart;
	}
	/**
	 * @param friStart the friStart to set
	 */
	public void setFriStart(Time friStart) {
		this.friStart = friStart;
	}
	/**
	 * @return the modifiedDate
	 */
	public Time getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Time modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the monEnd
	 */
	public Time getMonEnd() {
		return monEnd;
	}
	/**
	 * @param monEnd the monEnd to set
	 */
	public void setMonEnd(Time monEnd) {
		this.monEnd = monEnd;
	}
	/**
	 * @return the monStart
	 */
	public Time getMonStart() {
		return monStart;
	}
	/**
	 * @param monStart the monStart to set
	 */
	public void setMonStart(Time monStart) {
		this.monStart = monStart;
	}
	/**
	 * @return the offHrPrimaryMethod
	 */
	public int getOffHrPrimaryMethod() {
		return offHrPrimaryMethod;
	}
	/**
	 * @param offHrPrimaryMethod the offHrPrimaryMethod to set
	 */
	public void setOffHrPrimaryMethod(int offHrPrimaryMethod) {
		this.offHrPrimaryMethod = offHrPrimaryMethod;
	}
	/**
	 * @return the offHrSecondaryMethod
	 */
	public int getOffHrSecondaryMethod() {
		return offHrSecondaryMethod;
	}
	/**
	 * @param offHrSecondaryMethod the offHrSecondaryMethod to set
	 */
	public void setOffHrSecondaryMethod(int offHrSecondaryMethod) {
		this.offHrSecondaryMethod = offHrSecondaryMethod;
	}
	/**
	 * @return the onHrPrimaryMethod
	 */
	public int getOnHrPrimaryMethod() {
		return onHrPrimaryMethod;
	}
	/**
	 * @param onHrPrimaryMethod the onHrPrimaryMethod to set
	 */
	public void setOnHrPrimaryMethod(int onHrPrimaryMethod) {
		this.onHrPrimaryMethod = onHrPrimaryMethod;
	}
	/**
	 * @return the onHrSecondaryMethod
	 */
	public int getOnHrSecondaryMethod() {
		return onHrSecondaryMethod;
	}
	/**
	 * @param onHrSecondaryMethod the onHrSecondaryMethod to set
	 */
	public void setOnHrSecondaryMethod(int onHrSecondaryMethod) {
		this.onHrSecondaryMethod = onHrSecondaryMethod;
	}
	/**
	 * @return the preferenceID
	 */
	public int getPreferenceID() {
		return preferenceID;
	}
	/**
	 * @param preferenceID the preferenceID to set
	 */
	public void setPreferenceID(int preferenceID) {
		this.preferenceID = preferenceID;
	}
	/**
	 * @return the satEnd
	 */
	public Time getSatEnd() {
		return satEnd;
	}
	/**
	 * @param satEnd the satEnd to set
	 */
	public void setSatEnd(Time satEnd) {
		this.satEnd = satEnd;
	}
	/**
	 * @return the satStart
	 */
	public Time getSatStart() {
		return satStart;
	}
	/**
	 * @param satStart the satStart to set
	 */
	public void setSatStart(Time satStart) {
		this.satStart = satStart;
	}
	/**
	 * @return the sunEnd
	 */
	public Time getSunEnd() {
		return sunEnd;
	}
	/**
	 * @param sunEnd the sunEnd to set
	 */
	public void setSunEnd(Time sunEnd) {
		this.sunEnd = sunEnd;
	}
	/**
	 * @return the sunStart
	 */
	public Time getSunStart() {
		return sunStart;
	}
	/**
	 * @param sunStart the sunStart to set
	 */
	public void setSunStart(Time sunStart) {
		this.sunStart = sunStart;
	}
	/**
	 * @return the thurEnd
	 */
	public Time getThurEnd() {
		return thurEnd;
	}
	/**
	 * @param thurEnd the thurEnd to set
	 */
	public void setThurEnd(Time thurEnd) {
		this.thurEnd = thurEnd;
	}
	/**
	 * @return the thurStart
	 */
	public Time getThurStart() {
		return thurStart;
	}
	/**
	 * @param thurStart the thurStart to set
	 */
	public void setThurStart(Time thurStart) {
		this.thurStart = thurStart;
	}
	/**
	 * @return the tueEnd
	 */
	public Time getTueEnd() {
		return tueEnd;
	}
	/**
	 * @param tueEnd the tueEnd to set
	 */
	public void setTueEnd(Time tueEnd) {
		this.tueEnd = tueEnd;
	}
	/**
	 * @return the tueStart
	 */
	public Time getTueStart() {
		return tueStart;
	}
	/**
	 * @param tueStart the tueStart to set
	 */
	public void setTueStart(Time tueStart) {
		this.tueStart = tueStart;
	}
	/**
	 * @return the wedEnd
	 */
	public Time getWedEnd() {
		return wedEnd;
	}
	/**
	 * @param wedEnd the wedEnd to set
	 */
	public void setWedEnd(Time wedEnd) {
		this.wedEnd = wedEnd;
	}
	/**
	 * @return the wedStart
	 */
	public Time getWedStart() {
		return wedStart;
	}
	/**
	 * @param wedStart the wedStart to set
	 */
	public void setWedStart(Time wedStart) {
		this.wedStart = wedStart;
	}

	

}
