package com.newco.marketplace.web.dto;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.utils.SLStringUtils;

public class SOWPhoneDTO extends SOWBaseTabDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2693762127273180400L;
	private static int US_DIGITS_AREA_CODE = 3;
	private static int US_DIGITS_PART_1 = 3;
	private static int US_DIGITS_PART_2 = 4;
	private Integer phoneId;
	
	private String phone;
	private String areaCode="";
	private String phonePart1="";
	private String phonePart2="";
	private String ext;
	private Integer phoneType;//tells primary ,secondary & fax
	private Integer phoneClassId; //tells weather home,work,mobile,pager,other

	public SOWPhoneDTO()
	{		
	}

	public SOWPhoneDTO(SOWPhoneDTO phone)
	{		
		if(phone == null)
			return;
		
		copy(phone);
	}
	
	public SOWPhoneDTO(String phone, String ext)
	{
		if(ext != null)
			this.ext = ext;
		
		if(phone == null)
			return;

		if(phone.equals(""))
			return;
		
		if(phone.length() < 10)
			return;
		
		// Check for dashes '-'.  Remove them
		if(phone.contains("-"))
		{
			phone = phone.replaceAll("-", "");
		}
		
		String subStr;
		
		subStr = phone.substring(0, 3);
		if(SLStringUtils.isNullOrEmpty(subStr) == false)
			setAreaCode(phone.substring(0, 3));
		
		subStr = phone.substring(3, 6);
		if(SLStringUtils.isNullOrEmpty(subStr) == false)
			setPhonePart1(phone.substring(3, 6));
		
		subStr = phone.substring(6,10);
		if(SLStringUtils.isNullOrEmpty(subStr) == false)			
			setPhonePart2(phone.substring(6, 10));
		
		
	}
	
	public void copy(SOWPhoneDTO phone)
	{
		setAreaCode(phone.getAreaCode());
		setPhonePart1(phone.getPhonePart1());
		setPhonePart2(phone.getPhonePart2());
		
		setExt(phone.getExt());
		setPhone(phone.getPhone());
		
		setPhoneType(phone.getPhoneType());
		setPhoneClassId(phone.getPhoneClassId());			
	}
	
	public String getPhone() {
		if(this.getAreaCode()!=null && this.getPhonePart1() != null && this.getPhonePart2()!= null){
			return this.getAreaCode()+this.getPhonePart1()+this.getPhonePart2();
		}
		return null;
	}
	public void setPhone(String phone) {
		if(this.getAreaCode() != null && this.getPhonePart1() != null && this.getPhonePart2() != null){
			this.phone = this.getAreaCode()+this.getPhonePart1()+this.getPhonePart2();
		}
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String phoneExt) {
		ext = phoneExt;
	}
	public Integer getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(Integer phoneType) {
		this.phoneType = phoneType;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPhonePart1() {
		return phonePart1;
	}
	public void setPhonePart1(String phonePart1) {
		this.phonePart1 = phonePart1;
	}
	public String getPhonePart2() {
		return phonePart2;
	}
	public void setPhonePart2(String phonePart2) {
		this.phonePart2 = phonePart2;
	}
	
	public Integer getPhoneClassId() {
		return phoneClassId;
	}
	public void setPhoneClassId(Integer phoneClassId) {
		this.phoneClassId = phoneClassId;
	}
	
	// Returns true if either area code, part1, or part2 is filled.
	public boolean isAnySectionEntered()
	{
		if(getAreaCode() != null && getAreaCode().length() > 0)
			return true;
		if(getPhonePart1() != null && getPhonePart1().length() > 0)
			return true;
		if(getPhonePart2() != null && getPhonePart2().length() > 0)
			return true;
		
		return false;
	}
	
	
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void validate() {
		
		String errorStr;
		
		// TODO Auto-generated method stub
		if(getAreaCode() == null || getAreaCode().trim().equals(""))
		{
			addError(new SOWError("Phone: area code","phone section is empty",OrderConstants.SOW_TAB_ERROR));
		}
		else if(getAreaCode().length() < US_DIGITS_AREA_CODE)
		{
			errorStr = "area code must be " + US_DIGITS_AREA_CODE +  " digits";
			addError(new SOWError("Phone: areacode", errorStr, OrderConstants.SOW_TAB_ERROR));
		}
		
		
		if(getPhonePart1() == null || getPhonePart1().trim().equals(""))
		{
			addError(new SOWError("Phone: part1","phone section is empty",OrderConstants.SOW_TAB_ERROR));
		}
		else if(getPhonePart1().length() < US_DIGITS_PART_1)
		{
			errorStr = "part1 must be " + US_DIGITS_PART_1 + " digits";
			addError(new SOWError("Phone: part1",errorStr,OrderConstants.SOW_TAB_ERROR));
		}
		
		
		if(getPhonePart2() == null || getPhonePart2().trim().equals(""))
		{
			addError(new SOWError("Phone: part2","phone section is empty",OrderConstants.SOW_TAB_ERROR));
		}
		else if(getPhonePart2().length() < US_DIGITS_PART_2)
		{
			errorStr = "part2 must be " + US_DIGITS_PART_2 + " digits";
			addError(new SOWError("Phone: part2", errorStr,OrderConstants.SOW_TAB_ERROR));
		}
		
		
		
		

		
		//return getErrors();
	}
	public Integer getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(Integer phoneId) {
		this.phoneId = phoneId;
	}
	
	
	

}
