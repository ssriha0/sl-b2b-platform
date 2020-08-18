package com.newco.marketplace.dto.vo.serviceorder;

import static org.apache.commons.lang.StringUtils.replaceOnce;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.newco.marketplace.webservices.base.CommonVO;
public class ServiceOrderSpendLimitHistoryListVO extends CommonVO{
	private static final String DATE_FORMAT = "MM/dd/yyyy hh:mma zzz";
    private String soId;
	private String modifiedDate;
   private String reason;
    private Double updatedPrice;
    private Double oldPrice;
    private String userName;
    private String userId;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getModifiedDate()
	{
		return modifiedDate;
	}
    public void setModifiedDate(Date modifiedDate)
	{
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        TimeZone gmtTime = TimeZone.getTimeZone("CST");
        formatter.setTimeZone(gmtTime);
        String dateStr= formatter.format(modifiedDate);
        dateStr= replaceOnce (dateStr, "am ", "AM ");
        dateStr= replaceOnce (dateStr, "pm ", "PM ");
        if(gmtTime.inDaylightTime(modifiedDate)){
        	dateStr =replaceOnce (dateStr,"CST", "CDT");
        }
        dateStr = replaceOnce (dateStr,"CDT", "(CDT)");
        dateStr = replaceOnce (dateStr,"CST", "(CST)");
       this.modifiedDate = dateStr;
       }
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Double getUpdatedPrice() {
		return updatedPrice;
	}
	public void setUpdatedPrice(Double updatedPrice) {
		this.updatedPrice = updatedPrice;
	}
	public Double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}
}