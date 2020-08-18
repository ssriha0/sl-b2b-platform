package com.newco.marketplace.web.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.interfaces.OrderConstants;

public class SOProblemDTO extends SOWBaseTabDTO implements OrderConstants{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8656102514574373747L;
	private int subStatusId = 0;
	private String pbType = "";
	private String pbDesc = "";
	private String pbComment = "";
	private String resComment = "";
	
	public int getSubStatusId() {
		return subStatusId;
	}
	public void setSubStatusId(int subStatusId) {
		this.subStatusId = subStatusId;
	}
	
	public String getPbType() {
		return pbType;
	}
	public void setPbType(String pbType) {
		this.pbType = pbType;
	}
	public String getPbComment() {
		return pbComment;
	}
	public void setPbComment(String pbComment) {
		this.pbComment = pbComment;
	}
	
	public String getResComment() {
		return resComment;
	}
	public void setResComment(String resComment) {
		this.resComment = resComment;
	}
	
	public String getPbDesc() {
		return pbDesc;
	}
	
	public void setPbDesc(String pbDesc) {
		this.pbDesc = pbDesc;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("subStatusId", getSubStatusId())
			.append("pbType", getPbType())
			.append("pbDesc", getPbDesc())
			.append("pbComment", getPbComment())			
			.append("resComment", getResComment())
			.toString();
	}
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void validate(String fromPage) {
		if (fromPage.equalsIgnoreCase("Problem")){
		   //If called from problem report screen
	       if (pbType.equalsIgnoreCase("-1"))
	       {
	       	addError("Problem type", ENTER_PBTYPE, OrderConstants.SOW_TAB_ERROR);
	       }
	    }else 
		{
			//If called from resolution report screen
			if (resComment.equalsIgnoreCase(""))
	        {
	       	addError("Resolution Comment", ENTER_RESCOMMENT, OrderConstants.SOW_TAB_ERROR);
	        }
		}
	}
	
	@Override
	public void validate(){
		//Just place holder
	}
	
}
