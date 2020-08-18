package com.newco.marketplace.web.dto;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.web.utils.Config;

public class PBClaimedTabDTO extends SLBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5595252960010170944L;
	private List<PBClaimedOrderDTO> claimedOrders;
	private String soId;
	private String note;
	private String completedRadio;	
	private String radioSelection = "0"; //0 Public, 1 Private
	private String date;
	private String time;
	private Date datetime;
	transient private ResourceBundle resourceBundle = Config.getResouceBundle();
	private Boolean unclaimVerification;
	private String uniqueNumber; 
	
	public String getUniqueNumber() {
		return uniqueNumber;
	}
	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}
	public List<PBClaimedOrderDTO> getClaimedOrders()
	{
		return claimedOrders;
	}
	public String getRadioSelection() {
		return radioSelection;
	}
	public void setRadioSelection(String radioSelection) {
		this.radioSelection = radioSelection;
	}
	public void setClaimedOrders(List<PBClaimedOrderDTO> claimedOrders)
	{
		this.claimedOrders = claimedOrders;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public String getCompletedRadio()
	{
		return completedRadio;
	}
	public void setCompletedRadio(String completedRadio)
	{
		this.completedRadio = completedRadio;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public void reset(){
		this.completedRadio=null;
		this.date=null;
		this.datetime=null;
		this.note=null;
		this.soId=null;
		this.time=null;
		this.radioSelection="0";
		this.claimedOrders=null;
		this.getAllMessages().clear();
	}
	
	@Override
	public boolean validateErrors() {
		this.getAllMessages().clear();
		if(this.getCompletedRadio()==null){
			addError("time", "pb.claim.claim.status");
		}else if("0".equals(getCompletedRadio())){
			if(!(DateUtils.isValidDate(getDate()))){
				addError("Date", "pb.claim.reque.invaliddate");	
			}else if(!(StringUtils.isBlank(getDate()) && "[HH:MM]".equals(getTime()))){
					try {
						datetime=TimeUtils.combineDateTime(DateUtils.defaultFormatStringToDate(getDate()),getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(datetime==null ||(datetime!=null && new Date().after(datetime))){
						addError("time", "pb.claim.reque.datetime");
					}
					long days = DateUtils.getDaysBetweenDates(new Date(), datetime);
					if(datetime!=null && days >  Long.parseLong(resourceBundle.getString("requeue.duration.date"))){
						addError("Date", "pb.claim.reque.date.duration.msg");
					}
				}
		}
		if(StringUtils.isBlank(soId)){
			addError("soId", "pb.claim.soId");
		}
		if(StringUtils.isBlank(note)){
			addError("note", "pb.claim.note");
		}		
		return !this.getAllMessages().isEmpty();
	}
	@Override
	public boolean validateWarnings() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}
	/**
	 * @param soId the soId to set
	 */
	public void setSoId(String soId) {
		this.soId = soId;
	}
	/**
	 * @return the datetime
	 */
	public Date getDatetime() {
		return datetime;
	}
	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public Boolean getUnclaimVerification() {
		return unclaimVerification;
	}
	public void setUnclaimVerification(Boolean unclaimVerification) {
		this.unclaimVerification = unclaimVerification;
	}
	
}
