package com.servicelive.spn.buyer.network;

import java.io.Serializable;
import java.util.List;

public class SPNTierDTO implements Serializable 
{
	private static final long serialVersionUID = 0L;
	
	// Display only
	private Integer tier; 
	
	// checkbox
	private Boolean enable; 
	
	// dropdown selections
	private Integer minutes;
	private Integer hours;
	private Integer days;
	
	// Performance Level radio button testing
	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private List<String> pList;
	
	
	
	public Boolean getEnable()
	{
		return enable;
	}
	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}
	public Integer getMinutes()
	{
		return minutes;
	}
	public void setMinutes(Integer minutes)
	{
		this.minutes = minutes;
	}
	public Integer getHours()
	{
		return hours;
	}
	public void setHours(Integer hours)
	{
		this.hours = hours;
	}
	public Integer getDays()
	{
		return days;
	}
	public void setDays(Integer days)
	{
		this.days = days;
	}
	public Integer getTier()
	{
		return tier;
	}
	public void setTier(Integer tier)
	{
		this.tier = tier;
	}
	public String getP2()
	{
		return p2;
	}
	public void setP2(String p2)
	{
		this.p2 = p2;
	}
	public String getP3()
	{
		return p3;
	}
	public void setP3(String p3)
	{
		this.p3 = p3;
	}
	public String getP4()
	{
		return p4;
	}
	public void setP4(String p4)
	{
		this.p4 = p4;
	}
	public List<String> getPList()
	{
		return pList;
	}
	public void setPList(List<String> list)
	{
		pList = list;
	}
	public String getP1()
	{
		return p1;
	}
	public void setP1(String p1)
	{
		this.p1 = p1;
	}
	
	
}
