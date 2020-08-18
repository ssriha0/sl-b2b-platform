package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
@XmlRootElement(name = "History")
@XStreamAlias("History")
@XmlAccessorType(XmlAccessType.FIELD)
public class History {

	@XStreamAlias("Description")
	private String description;

	@XStreamAlias("HistoryDate")
	private String historyDate;
	
	@XStreamAlias("HistoryBy")
	private String historyBy;

	@XStreamAlias("HistoryAction")
	private  String historyAction;
	
	@XStreamAlias("RoleId")
	private  Integer roleId;
	
	@XStreamAlias("ActionId")
	private  Integer actionId;
	
	@XStreamOmitField
	private String modifiedBy;	
	
	@XStreamOmitField
	private Integer entityId;
	
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHistoryDate() {
		return historyDate;
	}

	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}
	
	

	public String getHistoryBy() {
		return historyBy;
	}

	public void setHistoryBy(String historyBy) {
		this.historyBy = historyBy;
	}

	public String getHistoryAction() {
		return historyAction;
	}

	public void setHistoryAction(String historyAction) {
		this.historyAction = historyAction;
	}
	//private static SimpleDateFormat popupDate = new SimpleDateFormat("EEEE MMM d, yyyy");
	//private static SimpleDateFormat defaultFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
	private static SimpleDateFormat popupDate = new SimpleDateFormat("MMM d, yyyy hh:mm a");
	private String formattedHistoryDate;	
	
	public String getFormattedHistoryDate() {
	return formattedHistoryDate;
	}

	public void setFormattedHistoryDate(String formattedHistoryDate) {
	this.formattedHistoryDate = formattedHistoryDate;
	}
	public void setFormattedHistoryDate() {
		this.formattedHistoryDate = getFormattedDate(this.historyDate);
	}

	public String getFormattedDate(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getPopUpDate(date);
		return outDate;

	}
	public static Date formatStringToDate(String source)
	{
			try {
				return defaultFormat.parse(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}	
	public static String getPopUpDate(Date pDate) {
		return popupDate.format(pDate);
	}
}
