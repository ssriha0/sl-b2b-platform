/**
 * 
 */
package com.newco.marketplace.vo.ordermanagement.so;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("childBidOrder")
public class ChildBidOrder
{

	/**
	 * 
	 */	
	@XStreamAlias("soId") 
	private String soId;

	@XStreamAlias("soTitle") 
	private String soTitle;
	
	@XStreamAlias("soTitleDesc") 
	private String soTitleDesc;

	@OptionalParam
	@XStreamAlias("resourceId") 
	private Integer resourceId;

	@OptionalParam
	@XStreamAlias("providerFirstName")
	private String providerFirstName;
	
	@OptionalParam
	@XStreamAlias("providerLastName")
	private String providerLastName;
	
	@OptionalParam
	@XStreamAlias("spendLimit")
	private String spendLimit;
	
	@OptionalParam
	@XStreamAlias("offerExpirationDate") 
	private String offerExpirationDate;
	
	@OptionalParam
	@XStreamAlias("respId") 
	private Integer respId;

	private static SimpleDateFormat popupDate = new SimpleDateFormat("MM/dd/yy");
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
	private String formattedExpirationDate;

	public String getFormattedExpirationDate() {
		return formattedExpirationDate;
	}



	public void setFormattedExpirationDate() {
		this.formattedExpirationDate = getFormatExpirationDate(this.offerExpirationDate);
	}



	private String getFormatExpirationDate(String inDate) {
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
	@XStreamAlias("actions")
	private List<String> actions ;


	public String getSoId() {
		return soId;
	}



	public void setSoId(String soId) {
		this.soId = soId;
	}



	public String getSoTitle() {
		return soTitle;
	}



	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}



	public String getSoTitleDesc() {
		return soTitleDesc;
	}



	public void setSoTitleDesc(String soTitleDesc) {
		this.soTitleDesc = soTitleDesc;
	}



	


	


	public Integer getResourceId() {
		return resourceId;
	}



	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}



	public String getProviderFirstName() {
		return providerFirstName;
	}



	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}



	public String getProviderLastName() {
		return providerLastName;
	}



	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}



	public String getSpendLimit() {
		return spendLimit;
	}



	public void setSpendLimit(String spendLimit) {
		this.spendLimit = spendLimit;
	}



	public String getOfferExpirationDate() {
		return offerExpirationDate;
	}



	public void setOfferExpirationDate(String offerExpirationDate) {
		this.offerExpirationDate = offerExpirationDate;
	}


	

	



	public Integer getRespId() {
		return respId;
	}



	public void setRespId(Integer respId) {
		this.respId = respId;
	}



	public List<String> getActions() {
		return actions;
	}



	public void setActions(List<String> actions) {
		this.actions = actions;
	}




}