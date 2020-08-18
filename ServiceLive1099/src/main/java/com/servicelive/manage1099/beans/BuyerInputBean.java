/**
 * 
 */
package com.servicelive.manage1099.beans;

/**
 * @author mjoshi1
 *
 */
public class BuyerInputBean {
	
	private String buyerid="";
	private String startDate="";
	private String endDate="";
	
	
	public String getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("buyerId:"+buyerid);
		sb.append("\n");
		sb.append("startDate:"+startDate);
		sb.append("\n");
		sb.append("endDate:"+endDate);
		sb.append("\n");
		return sb.toString();
	}
	

}
