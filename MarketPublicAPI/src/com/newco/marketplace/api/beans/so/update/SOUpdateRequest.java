
/*
 * This is a bean class for storing response information for 
 * the SOUpdateService
 * @author Infosys
 */

package com.newco.marketplace.api.beans.so.update;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.common.IAPIRequest;
import com.newco.marketplace.dto.vo.dateTimeSlots.ScheduleServiceSlot;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("soUpdateRequest")
@Namespace("http://www.servicelive.com/namespaces/soRequest")
public class SOUpdateRequest extends UserIdentificationRequest implements IAPIRequest {

	@OptionalParam
	@XStreamAlias("spendLimitIncrease")
	private SpendLimitIncrease spendLimitIncrease;
	
	@OptionalParam
	@XStreamAlias("spendLimitDecrease")
	private SpendLimitDecrease spendLimitDecrease;
	
	@OptionalParam
	@XStreamAlias("customReferences")
	private CustomReferences customReferences;
	
	@OptionalParam
	@XStreamAlias("overView")
	private String overView;
	
	@OptionalParam
	@XStreamAlias("location")
	private SoUpdateLocation location;
	
	@OptionalParam
	@XStreamAlias("contact")
	private SoUpdateContact contact;
	
	@OptionalParam
	@XStreamAlias("soSubstatus")
	private String soSubstatus;

	@OptionalParam
	@XStreamAlias("schedule")
	private SOSchedule schedule;
	
	@OptionalParam
	@XStreamAlias("scheduleServiceSlot")
	private ScheduleServiceSlot scheduleServiceSlot;
	
	@XStreamAlias("version")
	@XStreamAsAttribute()
	private String version;
 
	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation;

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance;
	
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}

	public SpendLimitIncrease getSpendLimitIncrease() {
		return spendLimitIncrease;
	}

	public void setSpendLimitIncrease(SpendLimitIncrease spendLimitIncrease) {
		this.spendLimitIncrease = spendLimitIncrease;
	}
	
	
    


	public SpendLimitDecrease getSpendLimitDecrease() {
		return spendLimitDecrease;
	}

	public void setSpendLimitDecrease(SpendLimitDecrease spendLimitDecrease) {
		this.spendLimitDecrease = spendLimitDecrease;
	}

	public CustomReferences getCustomReferences() {
		return customReferences;
	}

	public void setCustomReferences(CustomReferences customReferences) {
		this.customReferences = customReferences;
	}

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

	public SoUpdateLocation getLocation() {
		return location;
	}

	public void setLocation(SoUpdateLocation location) {
		this.location = location;
	}

	public SoUpdateContact getContact() {
		return contact;
	}

	public void setContact(SoUpdateContact contact) {
		this.contact = contact;
	}

	public String getSoSubstatus() {
		return soSubstatus;
	}

	public void setSoSubstatus(String soSubstatus) {
		this.soSubstatus = soSubstatus;
	}

	public SOSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(SOSchedule schedule) {
		this.schedule = schedule;
	}

	public ScheduleServiceSlot getScheduleServiceSlot() {
		return scheduleServiceSlot;
	}

	public void setScheduleServiceSlot(ScheduleServiceSlot scheduleServiceSlot) {
		this.scheduleServiceSlot = scheduleServiceSlot;
	}


}
